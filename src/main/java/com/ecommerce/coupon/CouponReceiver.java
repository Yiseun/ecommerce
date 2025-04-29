package com.ecommerce.coupon;

import com.ecommerce.coupon.dto.InternalCouponUseRequest;
import com.ecommerce.coupon.dto.InternalCouponValidateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponReceiver {
    private final CouponService couponService;

    public void validate(final InternalCouponValidateRequest request){
        couponService.validate(request);
    }

    public void use(final InternalCouponUseRequest request){
        couponService.useUserCoupon(request);
    }
}
