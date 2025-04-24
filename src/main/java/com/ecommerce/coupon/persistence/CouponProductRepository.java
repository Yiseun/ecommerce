package com.ecommerce.coupon.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponProductRepository extends JpaRepository<CouponProductEntity,Long> {
    Optional<CouponProductEntity> findByCouponEntityAndProductId(final CouponEntity couponEntity, final String productId);
}
