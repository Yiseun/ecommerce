package com.ecommerce.order.dto.request;

import com.ecommerce.order.dto.OrderDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class InternalOrderUpdateRequest {
    final List<OrderDto> orderDtos;

    public static InternalOrderUpdateRequest from(final List<OrderDto> orderDtos){
        return new InternalOrderUpdateRequest(orderDtos);
    }
}
