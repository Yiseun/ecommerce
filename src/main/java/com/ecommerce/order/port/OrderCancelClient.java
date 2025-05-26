package com.ecommerce.order.port;

import com.ecommerce.order.dto.request.UpdateOrderRequest;
import com.ecommerce.payment.PaymentReceiver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCancelClient implements OrderUpdateClient{
    private final PaymentReceiver paymentReceiver;
    @Override
    public void sendMessage(final UpdateOrderRequest request) {

    }

    public static OrderCancelClient from(final PaymentReceiver paymentReceiver){
        return new OrderCancelClient(paymentReceiver);
    }
}
