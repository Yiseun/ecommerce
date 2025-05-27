package com.ecommerce.order.port;

import com.ecommerce.order.dto.request.UpdateOrderRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoOperationClient implements OrderUpdateClient{
    @Override
    public void sendMessage(UpdateOrderRequest request) {

    }

    public static NoOperationClient init(){
        return new NoOperationClient();
    }
}
