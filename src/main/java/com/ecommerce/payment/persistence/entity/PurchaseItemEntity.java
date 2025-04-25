package com.ecommerce.payment.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseItemId;
    private String productId;
    private String productName;
    private String quantity;
    private String price;
    private String discountPrice;
    private String couponDiscountPercent;
    private String couponId;
    private String userCouponId;
}
