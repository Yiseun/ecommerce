package com.ecommerce.order.port;

import com.ecommerce.coupon.CouponReceiver;
import com.ecommerce.payment.PaymentReceiver;
import com.ecommerce.product.ProductReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientRegistry {

    private final PaymentReceiver paymentReceiver;
    private final ProductReceiver productReceiver;
    private final CouponReceiver couponReceiver;

    public OrderCreateClient createOrderCreateClient(){
        return OrderCreateClient.of(couponReceiver,productReceiver);
    }
    public OrderCompleteClient createOrderCompleteClient(){
        return OrderCompleteClient.of(paymentReceiver,productReceiver,couponReceiver);
    }
}
