package com.ecommerce.order.domain.orderitem;

public enum OrderItemState {
    ORDER_COMPLETE,
    ORDER_FINALIZED;
    public static OrderItemState init(){
        return OrderItemState.ORDER_COMPLETE;
    }
}