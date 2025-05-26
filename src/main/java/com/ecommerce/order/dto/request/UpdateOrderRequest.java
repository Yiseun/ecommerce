package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.port.OrderUpdateClient;

public interface UpdateOrderRequest {

    Order toOrder();

    OrderUpdateClient getClient();
}
