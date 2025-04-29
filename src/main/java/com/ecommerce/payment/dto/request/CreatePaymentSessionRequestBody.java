package com.ecommerce.payment.dto.request;

import com.ecommerce.payment.dto.PurchaseItemDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CreatePaymentSessionRequestBody {
    private final String orderId;
    private final List<PurchaseItemDto> purchaseItemDtos;
    private final String totalPrice;
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
    private final String payMethod;
}
