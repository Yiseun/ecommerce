package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.Order;

public interface UpdateOrderRequest {

    Order toOrder();

}
