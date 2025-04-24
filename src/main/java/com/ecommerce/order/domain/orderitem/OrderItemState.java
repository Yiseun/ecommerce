package com.ecommerce.order.domain.orderitem;

public enum OrderItemState {
    주문완료;
    public static OrderItemState init(){
        return OrderItemState.주문완료;
    }
}
