package com.ecommerce.payment.persistence;

import com.ecommerce.payment.persistence.entity.PaymentSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSessionRepository extends JpaRepository<PaymentSessionEntity,Long>{
}
