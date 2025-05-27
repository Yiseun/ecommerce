package com.ecommerce.order.dto.request;

import com.ecommerce.order.dto.OrderItemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterTrackingInfoRequestBody {
    private final List<OrderItemDto> orderItemDtos;
    private final String orderId;
}
