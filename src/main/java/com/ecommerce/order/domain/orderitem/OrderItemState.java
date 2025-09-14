package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import com.ecommerce.order.exception.application.domain.InvalidConstructionException;

public enum OrderItemState {

    ORDER_STANDBY{
        @Override
        protected OrderItemState updateInternal(OrderItemState request) {
            if(this.equals(request)||request.equals(ORDER_COMPLETE)||request.equals(CANCELED)){
                return request;
            }
            throw new InvalidConstructionException("변경할수없는 상태로 변경을 시도하고 있습니다.");
        }
    },
    ORDER_COMPLETE {
        @Override
        protected OrderItemState updateInternal(OrderItemState request) {
            if(this.equals(request)||request.equals(CANCELED)||request.equals(ON_DELIVERY)){
                return request;
            }
            throw new InvalidConstructionException("변경할수없는 상태로 변경을 시도하고 있습니다.");
        }
    },
    CANCELED {
        @Override
        protected OrderItemState updateInternal(OrderItemState request) {
            throw new InvalidConstructionException("변경할수없는 상태에서 변경을 시도하고 있습니다.");
        }
    },
    ON_DELIVERY {
        @Override
        protected OrderItemState updateInternal(OrderItemState request) {
            if(this.equals(request)||request.equals(COMPLETE_DELIVERY)||request.equals(ORDER_FINALIZED)){
                return request;
            }
            throw new InvalidConstructionException("변경할수없는 상태로 변경을 시도하고 있습니다.");
        }
    },
    COMPLETE_DELIVERY {
        @Override
        protected OrderItemState updateInternal(OrderItemState request) {
            if(this.equals(request)||request.equals(ORDER_FINALIZED)){
                return request;
            }
            throw new InvalidConstructionException("변경할수없는 상태로 변경을 시도하고 있습니다.");
        }
    },
    ORDER_FINALIZED {
        @Override
        protected OrderItemState updateInternal(OrderItemState request) {
            throw new InvalidConstructionException("변경할수없는 상태에서 변경을 시도하고 있습니다.");
        }
    };
    public OrderItemState update(final OrderItemState request){
        if(this.equals(request)||request==null){
            return this;
        }
        return this.updateInternal(request);
    }
    public boolean isUpdatableStateForTrackingInfo(final OrderItemState preState){
        return this.equals(OrderItemState.ORDER_COMPLETE) && this.equals(preState);
    }
    protected abstract OrderItemState updateInternal(final OrderItemState request);

    public static OrderItemState init(){
        return OrderItemState.ORDER_STANDBY;
    }
    public static OrderItemState from(final String orderItemState){
        try{
            return OrderItemState.valueOf(orderItemState);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new FailedCreationException("올바르지않은 입력입니다.");
        }
    }
    public static OrderItemState createEmpty(){
        return null;
    }

}