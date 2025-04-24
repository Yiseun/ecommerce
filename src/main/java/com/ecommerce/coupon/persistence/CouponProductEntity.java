package com.ecommerce.coupon.persistence;

import com.ecommerce.coupon.domain.Coupon;
import com.ecommerce.coupon.domain.CouponProduct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "product_price_index",columnList = "couponEntity_couponId, product_price")
})
public class CouponProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponProductId;
    @ManyToOne
    private CouponEntity couponEntity;
    private String productId;
    private String productPrice;
    private CouponProductEntity(final CouponEntity couponEntity,final String productId, final String productPrice){
        this.couponProductId = null;
        this.couponEntity = couponEntity;
        this.productId = productId;
        this.productPrice = productPrice;
    }
    public CouponProduct toCouponProduct(){
        final Coupon coupon = this.couponEntity.toCoupon();
        return CouponProduct.of(coupon,this.productId,this.productPrice);
    }
    public static CouponProductEntity from(final CouponProduct couponProduct){
        final CouponEntity coupon = CouponEntity.from(couponProduct.getCoupon());
        return new CouponProductEntity(coupon,couponProduct.getProductId(),couponProduct.getProductPrice());
    }
}
