package com.ecommerce.order.dto.response;

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
        return new CreateOrderIdResponse(tmpOrder.getOrderId().getValue().toString());
    }
}

//변경사항
//1.패키지 위치 변경
