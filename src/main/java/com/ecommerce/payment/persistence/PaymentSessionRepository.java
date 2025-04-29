package com.ecommerce.payment.persistence;

import com.ecommerce.payment.persistence.entity.PaymentSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentSessionRepository extends JpaRepository<PaymentSessionEntity,String>{ //id변경

    Optional<PaymentSessionEntity> findByOrderId(final String orderId);
}
