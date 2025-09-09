package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RegisterTrackingInfoRequest implements UpdateOrderRequest{
    private final List<OrderItemDto> orderItemDtos;
    private final String orderId;
    private final String memberId;

    @Override
    public Order toOrder() {
        final OrderBase orderBase = OrderBase.of(OrderId.from(this.orderId),this.memberId);
        final OrderDetail orderDetail = OrderDetail.createEmpty();
        final Price price = Price.createEmpty();
        final List<OrderItem> orderItems = this.orderItemDtos.stream().map(orderItemDto -> {
            final OrderItemInfo orderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from(orderItemDto.getOrderItemId())).build();
            final OrderItemState orderItemState = OrderItemState.createEmpty();
            final TrackingInfo trackingInfo = TrackingInfo.from(orderItemDto.getTrackingInfo());
            return OrderItem.of(orderItemInfo,orderItemState,trackingInfo);
        }).toList();
        return Order.createRequestOrder(orderBase,orderDetail,price,orderItems);
    }

}
