package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class TrackingInfo {
    private final String value;

    private TrackingInfo(){
        this.value = null;
    }
    private TrackingInfo(final String value){
        this.value = value;
    }

    public TrackingInfo update(final TrackingInfo request){
        if(request==null){
            throw new InvalidConstructionException("비어있는값으로 변경할수 없습니다.");
        }
        if(request.value==null){
            return this;
        }
        return request;
    }

    public static TrackingInfo createEmpty(){
        return new TrackingInfo();
    }
    public static TrackingInfo from(final String value){
        return new TrackingInfo(value);
    }
}