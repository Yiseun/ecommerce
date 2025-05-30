package com.ecommerce.order.persistence;

import com.ecommerce.order.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> , JpaSpecificationExecutor<OrderEntity> {

    Optional<OrderEntity> findByOrderId(final String orderId);
    @Override
    Page<OrderEntity> findAll(Specification<OrderEntity> spec, Pageable pageable);
}
