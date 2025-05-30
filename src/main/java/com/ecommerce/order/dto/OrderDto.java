package com.ecommerce.order.dto;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDto {
    private final String orderId;
    private final String totalPrice;
    private final List<OrderItemDto> orderItemDtos;
}
