package com.ecommerce.order.port;

import com.ecommerce.order.dto.request.UpdateOrderRequest;

public interface OrderUpdateClient {

    void sendMessage(final UpdateOrderRequest request);
}
