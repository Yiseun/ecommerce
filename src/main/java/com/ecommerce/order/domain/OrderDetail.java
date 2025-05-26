package com.ecommerce.order.domain;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class OrderDetail {
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;

    private OrderDetail(){
        this.buyerName = null;
        this.buyerPhoneNumber = null;
        this.buyerEmail = null;
        this.buyerAddress = null;
        this.buyerPostcode = null;
    }

    @Builder
    public OrderDetail(final String buyerName,
                       final String buyerPhoneNumber,
                       final String buyerEmail,
                       final String buyerAddress,
                       final String buyerPostcode){
        this.buyerName = validate(buyerName);
        this.buyerPhoneNumber = validate(buyerPhoneNumber);
        this.buyerEmail = validate(buyerEmail);
        this.buyerAddress = validate(buyerAddress);
        this.buyerPostcode = validate(buyerPostcode);
    }

    private String validate(final String value){
        if(value==null){
            throw new FailedCreationException("주문자정보를 입력해주세요.");
        }
        return value;
    }

    public static OrderDetail createEmpty(){
        return new OrderDetail();
    }
}
