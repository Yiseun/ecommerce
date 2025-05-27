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

    public OrderIdCreateClient getOrderCreateClient(){
        return OrderIdCreateClient.of(couponReceiver,productReceiver);
    }
    public OrderCreateClient getOrderCompleteClient(){
        return OrderCreateClient.of(paymentReceiver,productReceiver,couponReceiver);
    }
    public OrderCancelClient getOrderCancelClient(){
        return OrderCancelClient.from(paymentReceiver);
    }
    public NoOperationClient getNoOperationClient(){
        return NoOperationClient.init();
    }
}
