package com.ecommerce.product.concurrency.pending.persistence;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PendingTaskEntityRepository extends JpaRepository<PendingTaskEntity,Long> {
    List<PendingTaskEntity> findAllByOrderIdIn(final List<String> orderIds);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PendingTaskEntity> findByOrderId(final String orderId);
}
