package com.ecommerce.order.persistence;

import com.ecommerce.order.persistence.entity.TmpOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmpOrderRepository extends JpaRepository<TmpOrderEntity,Long> {
}
