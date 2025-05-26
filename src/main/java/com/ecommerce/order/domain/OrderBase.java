package com.ecommerce.order.domain;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderBase {
    private final OrderId orderId;
    private final String memberId;

    private OrderBase(final OrderId orderId,final String memberId){
        this.orderId = orderId;
        this.memberId = validate(memberId);
    }
    private OrderBase(final String memberId){
        this.orderId = OrderId.createEmpty();
        this.memberId = validate(memberId);
    }

    private String validate(final String memberId){
        if(memberId==null){
            throw new FailedCreationException("memberId를 입력해주세요.");
        }
        return memberId;
    }

    public boolean isEmpty(){
        return this.orderId.isEmpty();
    }

    public static OrderBase init(final String memberId){
        return new OrderBase(memberId);
    }
    public static OrderBase of(final OrderId orderId,final String memberId){
        return new OrderBase(orderId,memberId);
    }
}
