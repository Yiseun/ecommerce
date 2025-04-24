package com.ecommerce.coupon.persistence;

import com.ecommerce.coupon.domain.UserCoupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponId;
    private String memberId;
    @OneToOne
    private CouponEntity couponEntity;

    public UserCoupon toUserCoupon(){
        return UserCoupon.of(this.userCouponId.toString(),this.memberId,couponEntity.toCoupon());
    }

    public static UserCouponEntity from(final UserCoupon userCoupon){
        final CouponEntity couponEntity = CouponEntity.from(userCoupon.getCoupon());
        return new UserCouponEntity(userCoupon.getUserCouponId(),userCoupon.getMemberId(),couponEntity);
    }
}
