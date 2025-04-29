package com.ecommerce.order.dto.request;

import com.ecommerce.order.dto.OrderItemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter //패키지변경
@RequiredArgsConstructor
public class CreateOrderIdRequestBody {
    private final List<OrderItemDto> orderItemDtos;
    private final String totalPrice;
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
}
