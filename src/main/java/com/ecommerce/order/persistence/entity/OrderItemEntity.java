package com.ecommerce.order.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemEntityId;
    @Column(unique = true)
    private String orderItemId;
    private String productId;
    private String productName;
    private String quantity;
    private String price;
    private String discountPrice;
    private String couponDiscountPercent;
    private String couponId;
    private String userCouponId;
    private String orderItemState;
    private String trackingInfo;
}
