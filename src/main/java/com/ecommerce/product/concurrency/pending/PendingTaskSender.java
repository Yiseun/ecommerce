package com.ecommerce.product.concurrency.pending;

import com.ecommerce.product.concurrency.pending.dto.PendingTaskRequest;
import com.ecommerce.product.concurrency.pending.dto.TaskItemRequest;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PendingTaskSender {
    private final PendingService pendingTaskService;
    private final KafkaTemplate<String, PendingTaskRequest> kafkaTemplate;
    @Transactional
    public void send(final UpdateProductRequest request){
        send(request, List.copyOf(Collections.emptyList()));
    }

    @Transactional
    public void send(final UpdateProductRequest request, final List<Product> excluded){
        final Set<Product> excludedProductSet = Set.copyOf(excluded);
        final List<TaskItemRequest> taskItemRequests = request.getUpdateItemRequests().stream()
                .filter(updateItem -> !excludedProductSet.contains(updateItem.toProduct()))
                .map(updateItem ->
                        TaskItemRequest.builder()
                                .orderItemId(updateItem.getOrderItemId())
                                .productId(updateItem.getProductDto().getProductId())
                                .productName(updateItem.getProductDto().getProductName())
                                .quantity(updateItem.getProductDto().getQuantity())
                                .price(updateItem.getProductDto().getPrice())
                                .build())
                .toList();
        if(taskItemRequests.isEmpty()){
            return;
        }
        final PendingTaskRequest message = PendingTaskRequest.of(request.getOrderId(),request.getMemberId(),taskItemRequests);
        pendingTaskService.createPendingTask(message);
        kafkaTemplate.send("pendingTask",message);
    }
    
}
