package com.ecommerce.order.domain.orderitem;

public enum OrderItemState {
    WAITING_FOR_PAYMENT,
    ORDER_COMPLETE,
    ORDER_FINALIZED;
    public static OrderItemState init(){
        return OrderItemState.WAITING_FOR_PAYMENT;
    }
}