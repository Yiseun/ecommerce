package com.ecommerce.payment.domain;

import com.ecommerce.payment.domain.paymentitem.Price;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class PaymentInfo {
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
    private final PayMethod payMethod;
    private final PgProvider pgProvider;
}