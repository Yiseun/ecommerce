package com.ecommerce.product.concurrency.estimate.pesistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConcurrencyInfoEntityRepository extends JpaRepository<ConcurrencyInfoEntity,Long> {
    List<ConcurrencyInfoEntity> findAllByProductIdIn(final List<Long> productIds);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ConcurrencyInfoEntity> findByProductId(final Long productId);
}
