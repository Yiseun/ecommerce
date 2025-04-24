package com.ecommerce.order.domain.orderitem;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class OrderItem {
    @EqualsAndHashCode.Include
    private final OrderItemInfo orderItemInfo;
    private final OrderItemState orderItemState;
    private final TrackingInfo trackingInfo;

    private OrderItem(final OrderItemInfo orderItemInfo){
        this.orderItemInfo = orderItemInfo;
        this.orderItemState = OrderItemState.init();
        this.trackingInfo = TrackingInfo.init();
    }
    public static OrderItem from(final OrderItemInfo orderItemInfo){
        return new OrderItem(orderItemInfo);
    }

    public static OrderItem of(final OrderItemInfo orderItemInfo,final OrderItemState orderItemState,final TrackingInfo trackingInfo){
        return new OrderItem(orderItemInfo,orderItemState,trackingInfo);
    }
}
