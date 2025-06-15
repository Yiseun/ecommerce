package com.ecommerce.product.concurrency.pending;

import com.ecommerce.product.concurrency.estimate.ConcurrencyEstimator;
import com.ecommerce.product.concurrency.pending.domain.PendingTask;
import com.ecommerce.product.concurrency.pending.dto.PendingTaskRequest;
import com.ecommerce.product.concurrency.pending.persistence.ConcurrencyProductRepository;
import com.ecommerce.product.concurrency.pending.persistence.PendingTaskEntity;
import com.ecommerce.product.concurrency.pending.persistence.PendingTaskEntityRepository;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.exception.application.DuplicatedCreationException;
import com.ecommerce.product.persistence.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PendingService {

    private final PendingTaskEntityRepository pendingTaskEntityRepository;
    private final ConcurrencyProductRepository productRepository;
    private final ConcurrencyEstimator estimator;


    @Transactional
    public void createPendingTask(final PendingTaskRequest request){
        final PendingTask requestPendingTask = request.toPendingTask();
        final PendingTaskEntity requestPendingTaskEntity = PendingTaskEntity.from(requestPendingTask);
        try {
            pendingTaskEntityRepository.save(requestPendingTaskEntity);
        }catch (DataIntegrityViolationException e){
            throw new DuplicatedCreationException("중복된 요청입니다.");
        }
    }

    @Retryable(retryFor = {OptimisticLockingFailureException.class})
    @Transactional
    public List<PendingTask> updatePendingTask(final List<PendingTaskRequest> requests){
        final List<PendingTask> requestPendingTasks = requests.stream().map(i->i.toPendingTask()).toList();
        final List<PendingTaskEntity> requestPendingTaskEntities = requestPendingTasks.stream().map(requestPendingTask->PendingTaskEntity.from(requestPendingTask)).toList();
        final List<String> sortedRequestPendingTaskEntityOrderIds = requestPendingTaskEntities.stream().map(requestPendingTaskEntity->requestPendingTaskEntity.getOrderId()).sorted().toList();
        final List<PendingTaskEntity> serverPendingTaskEntities = pendingTaskEntityRepository.findAllByOrderIdIn(sortedRequestPendingTaskEntityOrderIds);
        final List<PendingTask> serverPendingTasks = serverPendingTaskEntities.stream().map(serverPendingTaskEntity->serverPendingTaskEntity.toPendingTask()).toList();
        final Map<PendingTask,PendingTask> serverPendingTaskMap = serverPendingTasks.stream().collect(Collectors.toUnmodifiableMap(i->i, i->i));
        final List<PendingTask> resultPendingTasks = requestPendingTasks.stream().map(requestPendingTask->{
            final PendingTask serverPendingTask = serverPendingTaskMap.get(requestPendingTask);
            return requestPendingTask.update(serverPendingTask);
        }).filter(resultPendingTask->resultPendingTask.isUpdatableWithProduct()).toList();
        final List<PendingTaskEntity> resultPendingTaskEntities = resultPendingTasks.stream().map(i->PendingTaskEntity.from(i)).toList();
        pendingTaskEntityRepository.saveAll(resultPendingTaskEntities);
        return resultPendingTasks;
    }
    @Recover
    @Transactional
    private List<PendingTask> updatePendingTask(final OptimisticLockingFailureException e,final List<PendingTaskRequest> requests){
        final List<PendingTask> requestPendingTasks = requests.stream().map(i->i.toPendingTask()).toList();
        final List<PendingTaskEntity> requestPendingTaskEntities = requestPendingTasks.stream().map(i->PendingTaskEntity.from(i)).toList();
        final List<String> sortedRequestPendingTaskEntityOrderIds = requestPendingTaskEntities.stream().map(i->i.getOrderId()).sorted().toList();
        final List<PendingTaskEntity> serverPendingTaskEntities = sortedRequestPendingTaskEntityOrderIds.stream()
                .map(orderId->pendingTaskEntityRepository.findByOrderId(orderId))
                .filter(optional->optional.isPresent())
                .map(optional->optional.get())
                .toList();
        final List<PendingTask> serverPendingTasks = serverPendingTaskEntities.stream().map(serverPendingTaskEntity->serverPendingTaskEntity.toPendingTask()).toList();
        final Map<PendingTask,PendingTask> serverPendingTaskMap = serverPendingTasks.stream().collect(Collectors.toUnmodifiableMap(i->i,i->i));
        final List<PendingTask> resultPendingTasks = requestPendingTasks.stream().map(requestPendingTask->{
            final PendingTask serverPendingTask = serverPendingTaskMap.get(requestPendingTask);
            return requestPendingTask.update(serverPendingTask);
        }).filter(resultPendingTask->resultPendingTask.isUpdatableWithProduct()).toList();
        final List<PendingTaskEntity> resultPendingTaskEntities = resultPendingTasks.stream().map(i->PendingTaskEntity.from(i)).toList();
        pendingTaskEntityRepository.saveAll(resultPendingTaskEntities);
        return resultPendingTasks;
    }

    public List<PendingTask> updateProductWithPendingTask(final List<PendingTask> requestPendingTasks) {
        final List<PendingTask> sortedRequestPendingTask = requestPendingTasks.stream()
                .sorted(Comparator.comparingLong(PendingTask::getPendingTaskId).reversed())
                .toList();
        final List<PendingTaskEntity> serverPendingTaskEntities = sortedRequestPendingTask.stream()
                .map(pendingTask -> pendingTaskEntityRepository.findByOrderId(pendingTask.getOrderId()))
                .filter(optional-> optional.isPresent())
                .map(optional->optional.get())
                .toList();
        final Map<PendingTask,PendingTask> serverPendingTaskMap = serverPendingTaskEntities.stream().map(i->i.toPendingTask()).collect(Collectors.toUnmodifiableMap(i->i,i->i));
        final List<Long> targetProductIds = requestPendingTasks.stream()
                .flatMap(pendingTask -> pendingTask.getTaskItems().stream())
                .map(taskItem -> ProductEntity.from(taskItem.getProduct()).getProductId())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();
        estimator.updateConcurrencyInfo(targetProductIds,true);
        final  List<ProductEntity> serverProductEntities = targetProductIds.stream()
                .map(id->productRepository.findByProductId(id))
                .filter(optional->optional.isPresent())
                .map(optional->optional.get())
                .toList();
        final Map<Product, Product> serverProductMap = serverProductEntities.stream().collect(Collectors.toMap(i->i.toProduct(), i->i.toProduct()));
        final List<PendingTask> resultPendingTasks = requestPendingTasks.stream()
                .map(pendingTask -> {
                    final PendingTask serverPendingTask = serverPendingTaskMap.get(pendingTask);
                    return pendingTask.interact(serverPendingTask,serverProductMap);
                }).toList();
        final List<PendingTask> failedPendingTasks = resultPendingTasks.stream()
                .map(pendingTask -> pendingTask.getFailedTasks())
                .toList();
        final List<Product> resultProducts = serverProductMap.values().stream().toList();
        final List<ProductEntity> resultProductEntities = resultProducts.stream().map(resultProduct->ProductEntity.from(resultProduct)).toList();
        productRepository.saveAll(resultProductEntities);
        estimator.updateConcurrencyInfo(targetProductIds,false);
        return failedPendingTasks;
    }
}
