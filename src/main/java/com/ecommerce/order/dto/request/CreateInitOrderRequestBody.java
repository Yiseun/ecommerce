package com.ecommerce.order.dto.request;

import com.ecommerce.order.dto.OrderItemDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class CreateInitOrderRequestBody {
    private final List<OrderItemDto> orderItemDtos;
    private final String orderId;
    private final String impUid;
    private final String paymentKey;
    private final String payMethod;
    private final String pgProvider;
    private final String totalPrice;
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
}
