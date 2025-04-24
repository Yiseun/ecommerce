package com.ecommerce.coupon;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.coupon.domain.Coupon;
import com.ecommerce.coupon.domain.CouponId;
import com.ecommerce.coupon.exception.domain.BusinessLogicException;
import org.junit.jupiter.api.Test;

public class CouponTests {

    @Test
    void 가격이_양의정수가_아니라면_CouponException이_발생한다(){
        final Coupon coupon = Coupon.of(CouponId.from("454345"),"35");
        final String givenRequest = "0";

        assertThatThrownBy(()->coupon.calculateDiscountPrice(givenRequest)).isInstanceOf(BusinessLogicException.class);
    }
}
