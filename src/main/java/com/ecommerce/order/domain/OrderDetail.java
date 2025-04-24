package com.ecommerce.order.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class OrderDetail {
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
}
