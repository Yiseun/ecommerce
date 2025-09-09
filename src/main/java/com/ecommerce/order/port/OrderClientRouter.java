package com.ecommerce.order.port;

import com.ecommerce.coupon.CouponReceiver;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.request.*;
import com.ecommerce.order.exception.application.InvalidConstructionException;
import com.ecommerce.payment.PaymentReceiver;
import com.ecommerce.product.ProductReceiver;
import org.springframework.stereotype.Component;

@Component
public class OrderClientRouter {

    private final OrderIdCreateClient orderIdCreateClient;
    private final OrderCreateClient orderCreateClient;
    private final OrderCancelClient orderCancelClient;

    public OrderClientRouter(final PaymentReceiver paymentReceiver,
                             final ProductReceiver productReceiver,
                             final CouponReceiver couponReceiver){
        this.orderIdCreateClient = OrderIdCreateClient.of(couponReceiver,productReceiver);
        this.orderCreateClient = OrderCreateClient.of(paymentReceiver, productReceiver, couponReceiver);
        this.orderCancelClient = OrderCancelClient.from(paymentReceiver);
    }

    public void createOrderId(final CreateOrderIdRequest request){
        orderIdCreateClient.sendMessage(request);
    }

    public void createOrder(final CompleteOrderRequest request,final Order order){
        orderCreateClient.sendMessage(request,order);
    }

    public void updateOrder(final UpdateOrderRequest request){
        if(request instanceof RegisterTrackingInfoRequest){
            return;
        }
        if(request instanceof CancelOrderRequest){
            orderCancelClient.sendMessage(request);
            return;
        }
        throw new InvalidConstructionException("적절한 client를 찾을수 없습니다.");
    }
}
