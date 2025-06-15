package com.ecommerce.product;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.concurrency.pending.PendingTaskSender;
import com.ecommerce.product.concurrency.estimate.ConcurrencyEstimator;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.UpdateProductRequest;
import com.ecommerce.product.dto.InternalProductValidateRequest;
import com.ecommerce.product.exception.application.ProductNotFoundException;
import com.ecommerce.product.persistence.ProductEntity;
import com.ecommerce.product.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ConcurrencyEstimator concurrencyEstimator;
    private final PendingTaskSender pendingTaskSender;
    private final ProductRepository productRepository;

    public void validate(final InternalProductValidateRequest request){
        final NonDuplicatedList<Product> requestProducts = request.toProductList();
        final List<Long> requestProductEntityIds = requestProducts.getStream().map(product->ProductEntity.from(product).getProductId()).toList();
        final List<ProductEntity> serverProductEntities = productRepository.findAllById(requestProductEntityIds);
        if(serverProductEntities.isEmpty()){
            throw new ProductNotFoundException("서버에 상품이 존재하지 않습니다.");
        }
        final List<Product> serverProducts = serverProductEntities.stream().map(i->i.toProduct()).toList();
        final Map<Product,Product> serverProductMap = serverProducts.stream().collect(Collectors.toMap(i->i,i->i));
        requestProducts.getStream().forEach(requestProduct->{
            final Product serverProduct = serverProductMap.get(requestProduct);
            if(serverProduct==null){
                throw new ProductNotFoundException("서버에 존재하지않는 상품이 있습니다.");
            }
            serverProduct.validate(requestProduct);
        });
    }

    @Retryable(retryFor = {PessimisticLockingFailureException.class})
    @Transactional
    public void update(final UpdateProductRequest request){
        final NonDuplicatedList<Product> requestProducts = request.toProductList();
        final List<Product> originRequestProducts = requestProducts.getStream().toList();
        final List<Product> assumedConcurrencyProductList = concurrencyEstimator.getConcurrencyProduct(originRequestProducts);
        final Set<Product> assumedConcurrencyProductSet = Set.copyOf(assumedConcurrencyProductList);
        final List<Product> assumedNotConcurrecncyProductList = originRequestProducts.stream()
                .filter(product -> !assumedConcurrencyProductSet.contains(product))
                .toList();

        final List<Long> requestProductEntityIds = assumedNotConcurrecncyProductList.stream().map(product->ProductEntity.from(product).getProductId()).toList();
        final List<ProductEntity> serverProductEntities = productRepository.findAllById(requestProductEntityIds);
        if(serverProductEntities.isEmpty()){
            throw new ProductNotFoundException("서버에 상품이 존재하지 않습니다.");
        }
        final List<Product> serverProducts = serverProductEntities.stream().map(i->i.toProduct()).toList();
        final Map<Product,Product> serverProductMap = serverProducts.stream().collect(Collectors.toMap(i->i,i->i));
        final List<Product> resultProducts = requestProducts.getStream().map(requestProduct->{
            final Product serverProduct = serverProductMap.get(requestProduct);
            if(serverProduct==null){
                throw new ProductNotFoundException("서버에 존재하지않는 상품이 있습니다.");
            }
            return serverProduct.update(requestProduct);
        }).toList();
        final List<ProductEntity> resultProductEntities = resultProducts.stream().map(i->ProductEntity.from(i)).toList();
        productRepository.saveAll(resultProductEntities);
        pendingTaskSender.send(request,resultProducts);
    }

    @Recover
    @Transactional
    public void onConcurrencyUpdate(final PessimisticLockingFailureException e,final UpdateProductRequest request){
        pendingTaskSender.send(request);
    }
}
