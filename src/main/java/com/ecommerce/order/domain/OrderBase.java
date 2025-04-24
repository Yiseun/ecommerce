package com.ecommerce.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderBase {
    private final OrderId orderId;
    private final String memberId;

    private OrderBase(final OrderId orderId,final String memberId){
        this.orderId = orderId;
        this.memberId = memberId;
    }
    private OrderBase(final String memberId){
        this.orderId = OrderId.createEmpty();
        this.memberId = memberId;
    }

    public static OrderBase init(final String memberId){
        return new OrderBase(memberId);
    }
    public static OrderBase of(final OrderId orderId,final String memberId){
        return new OrderBase(orderId,memberId);
    }
}
