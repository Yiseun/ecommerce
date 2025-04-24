package com.ecommerce.order.pesistence;

import com.ecommerce.order.pesistence.entity.TmpOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TmpOrderRepository extends JpaRepository<TmpOrderEntity,Long> {
}
