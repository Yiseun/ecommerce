package com.ecommerce.order.port;

import com.ecommerce.order.dto.OrderRequest;

public interface OrderClient {

    void sendMessage(final OrderRequest request);
}
