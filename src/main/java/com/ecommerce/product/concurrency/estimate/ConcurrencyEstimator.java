package com.ecommerce.product.concurrency.estimate;

import com.ecommerce.product.concurrency.estimate.domain.ConcurrencyInfo;
import com.ecommerce.product.concurrency.estimate.pesistence.ConcurrencyInfoEntity;
import com.ecommerce.product.concurrency.estimate.pesistence.ConcurrencyInfoEntityRepository;
import com.ecommerce.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConcurrencyEstimator {
    private final ConcurrencyInfoEntityRepository concurrencyInfoEntityRepository;
    @Transactional(readOnly = true)
    public List<Product> getConcurrencyProduct(final List<Product> requestProductList){
        final Map<ConcurrencyInfo,Product> originProductMap = requestProductList.stream().collect(Collectors.toMap(i->ConcurrencyInfo.from(i.getProductInfo().getProductId()), i->i));
        final List<ConcurrencyInfo> requestConcurrencyInfoList = requestProductList.stream().map(product ->ConcurrencyInfo.from(product.getProductInfo().getProductId())).toList();
        final List<Long> requestProductIds = requestConcurrencyInfoList.stream().map(concurrencyInfo -> ConcurrencyInfoEntity.from(concurrencyInfo).getProductId()).toList();
        final List<ConcurrencyInfoEntity> serverConcurrencyInfoEntities = concurrencyInfoEntityRepository.findAllByProductIdIn(requestProductIds);
        final List<ConcurrencyInfo> serverConcurrencyInfos = serverConcurrencyInfoEntities.stream().map(concurrencyInfoEntity -> concurrencyInfoEntity.toConcurrencyInfo()).toList();
        return serverConcurrencyInfos.stream().filter(assumed->assumed.isConcurrency()).map(assumed->originProductMap.get(assumed)).toList();
    }
    @Async("estimatorCommandThreadPool")
    @Transactional
    public void updateConcurrencyInfo(final List<Long> productIds, final boolean isIncrease){
        final List<ConcurrencyInfo> requestConcurrencyInfos = productIds.stream().map(productId -> ConcurrencyInfo.from(productId)).toList();
        final List<Long> requestProductIds = requestConcurrencyInfos.stream()
                .map(requestConcurrency -> ConcurrencyInfoEntity.from(requestConcurrency).getProductId())
                .sorted(Comparator.reverseOrder())
                .toList();
        final List<ConcurrencyInfoEntity> serverConcurrencyInfoEntities = requestProductIds.stream()
                .map(requestProductId -> concurrencyInfoEntityRepository.findByProductId(requestProductId))
                .filter(serverConcurrencyInfoEntity -> serverConcurrencyInfoEntity.isPresent())
                .map(serverConcurrencyInfoEntity -> serverConcurrencyInfoEntity.get())
                .toList();
        final List<ConcurrencyInfo> serverConcurrencyInfos = serverConcurrencyInfoEntities.stream().map(concurrencyInfoEntity -> concurrencyInfoEntity.toConcurrencyInfo()).toList();
        final Map<ConcurrencyInfo,ConcurrencyInfo> serverConcurrencyInfoMap = serverConcurrencyInfos.stream().collect(Collectors.toMap(i->i,i->i));
        final List<ConcurrencyInfo> resultConcurrencyInfos = requestConcurrencyInfos.stream().map(requestConcurrencyInfo->{
            final ConcurrencyInfo serverConcurrencyInfo = serverConcurrencyInfoMap.get(requestConcurrencyInfo);
            return requestConcurrencyInfo.update(serverConcurrencyInfo,isIncrease);
        }).toList();
        final List<ConcurrencyInfoEntity> resultConcurrencyInfoEntities = resultConcurrencyInfos.stream().map(i->ConcurrencyInfoEntity.from(i)).toList();
        concurrencyInfoEntityRepository.saveAll(resultConcurrencyInfoEntities);
    }
}
