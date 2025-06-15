package com.ecommerce.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class OrderDto {
    private final String orderId;
    private final String memberId;
    private final String totalPrice;
    private final List<OrderItemDto> orderItemDtos;
}
