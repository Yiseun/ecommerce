package com.ecommerce.product.concurrency.pending.persistence;

import com.ecommerce.product.persistence.ProductEntity;
import com.ecommerce.product.persistence.ProductRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConcurrencyProductRepository extends ProductRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductEntity> findByProductId(final Long productId);
}
