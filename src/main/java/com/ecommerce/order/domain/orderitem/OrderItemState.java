package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import com.ecommerce.order.exception.application.domain.InvalidConstructionException;

public enum OrderItemState {
    ORDER_COMPLETE {
        @Override
        public OrderItemState updateDetail(OrderItemState request) {
            return null;
        }
    },
    CANCELED {
        @Override
        public OrderItemState updateDetail(OrderItemState request) {
            return null;
        }
    },
    ON_DELIVERY {
        @Override
        public OrderItemState updateDetail(OrderItemState request) {
            return null;
        }
    },
    COMPLETE_DELIVERY {
        @Override
        public OrderItemState updateDetail(OrderItemState request) {
            return
        }
    },
    ORDER_FINALIZED {
        @Override
        public OrderItemState updateDetail(OrderItemState request) {
            return null;
        }
    };
    public OrderItemState update(final OrderItemState request){
        if(this.equals(request)||request==null){
            return this;
        }
        return this.updateDetail(request);
    }
    public boolean isUpdatableStateForTrackingInfo(final OrderItemState preState){
        return this.equals(OrderItemState.ORDER_COMPLETE) && this.equals(preState);
    }
    protected abstract OrderItemState updateDetail(final OrderItemState request);
    public static OrderItemState init(){
        return OrderItemState.ORDER_COMPLETE;
    }
    public static OrderItemState from(final String orderItemState){
        try{
            return OrderItemState.valueOf(orderItemState);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new FailedCreationException("올바르지않은 입력입니다.");
        }
    }
    //from에서 null을 잡는이유는 유저의 입력판별
    //createEmpty를 따로 만든 이유는 개발자의 의도된 nullable객체를 만들기위함
    public static OrderItemState createEmpty(){
        return null;
    }

}