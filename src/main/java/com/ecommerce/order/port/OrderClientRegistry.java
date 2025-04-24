package com.ecommerce.order.port;

import com.ecommerce.coupon.CouponReceiver;
import com.ecommerce.product.ProductReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientRegistry {

    private final CouponReceiver couponReceiver;
    private final ProductReceiver productReceiver;


    public OrderCreateClient createOrderCreateClient(){
        return OrderCreateClient.of(couponReceiver,productReceiver);
    }
}
