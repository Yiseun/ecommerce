package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.InvalidConstructionException;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class OrderItem {
    @EqualsAndHashCode.Include
    private final OrderItemInfo orderItemInfo;
    private final OrderItemState orderItemState;
    private final TrackingInfo trackingInfo;

    private OrderItem(final OrderItemInfo orderItemInfo){
        this.orderItemInfo = validate(orderItemInfo);
        this.orderItemState = OrderItemState.init();
        this.trackingInfo = TrackingInfo.createEmpty();
    }
    private OrderItem(final OrderItemInfo orderItemInfo,final OrderItemState orderItemState,final TrackingInfo trackingInfo){
        this.orderItemInfo = validate(orderItemInfo);
        this.orderItemState = validate(orderItemState);
        this.trackingInfo = validate(trackingInfo);
    }

    private OrderItemInfo validate(final OrderItemInfo orderItemInfo){
        if(orderItemInfo==null){
            throw new InvalidConstructionException("orderItemInfo는 null일수 없습니다.");
        }
        return orderItemInfo;
    }
    private OrderItemState validate(final OrderItemState orderItemState){
        if(orderItemState==null){
            throw new InvalidConstructionException("orderItemState는 null일수 없습니다.");
        }
        return orderItemState;
    }
    private TrackingInfo validate(final TrackingInfo trackingInfo){
        if(trackingInfo==null){
            throw new InvalidConstructionException("trackingInfo는 null일수 없습니다.");
        }
        return trackingInfo;
    }

    public OrderItem update(final OrderItem request){
        if(!this.equals(request)){
            throw new InvalidConstructionException("일치하지않는 상품정보를 변경할수 없습니다.");
        }
        final OrderItemState resultOrderItemState = this.orderItemState.update(request.orderItemState);
        if(resultOrderItemState.isUpdatableStateForTrackingInfo(this.orderItemState)){
            final TrackingInfo resultTrackingInfo = this.trackingInfo.update(request.trackingInfo);
            return new OrderItem(this.orderItemInfo,resultOrderItemState,resultTrackingInfo);
        }
        return new OrderItem(this.orderItemInfo,resultOrderItemState,this.trackingInfo);
    }

    public static OrderItem from(final OrderItemInfo orderItemInfo){
        return new OrderItem(orderItemInfo);
    }

    public static OrderItem of(final OrderItemInfo orderItemInfo,final OrderItemState orderItemState,final TrackingInfo trackingInfo){
        return new OrderItem(orderItemInfo,orderItemState,trackingInfo);
    }
}
