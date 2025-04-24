package com.ecommerce.order.dto;

import com.ecommerce.order.domain.TmpOrder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderIdResponse {
    private final String orderId;

    public static CreateOrderIdResponse from(final TmpOrder tmpOrder){
        return new CreateOrderIdResponse(tmpOrder.getOrder().getOrderBase().getOrderId().getValue().toString());
    }
}
