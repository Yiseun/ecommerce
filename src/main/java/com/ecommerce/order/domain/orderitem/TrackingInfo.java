package com.ecommerce.order.domain.orderitem;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class TrackingInfo {
    private final String value;
    private TrackingInfo(final String value){
        this.value = value;
    }

    public static TrackingInfo init(){
        return new TrackingInfo(null);
    }
    public static TrackingInfo from(final String value){
        return new TrackingInfo(value);
    }
}
