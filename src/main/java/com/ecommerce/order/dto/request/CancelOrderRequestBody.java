package com.ecommerce.order.dto.request;

import com.ecommerce.order.dto.OrderItemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CancelOrderRequestBody {
    private final List<OrderItemDto> orderItemDtos;
    private final String orderId;
    private final String totalPrice;

    public static CancelOrderRequestBody of(final List<OrderItemDto> orderItemDtos,final String orderId,final String totalPrice){
        return new CancelOrderRequestBody(orderItemDtos, orderId, totalPrice);
    }
}
