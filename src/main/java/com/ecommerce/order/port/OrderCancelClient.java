package com.ecommerce.order.port;

import com.ecommerce.order.dto.request.UpdateOrderRequest;
import com.ecommerce.payment.PaymentReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCancelClient implements OrderUpdateClient{
    private final PaymentReceiver paymentReceiver;
    @Override
    public void sendMessage(final UpdateOrderRequest request) {

    }
}
