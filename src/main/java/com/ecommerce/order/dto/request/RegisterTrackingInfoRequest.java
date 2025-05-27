package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.port.NoOperationClient;
import com.ecommerce.order.port.OrderUpdateClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterTrackingInfoRequest implements UpdateOrderRequest{
    private final String memeberId;
    private final RegisterTrackingInfoRequestBody body;
    private final NoOperationClient client;

    @Override
    public Order toOrder() {
        final OrderBase orderBase = OrderBase.of(OrderId.from(body.getOrderId()),memeberId);
        final OrderDetail orderDetail = OrderDetail.createEmpty();
        final Price price = Price.createEmpty();
        final List<OrderItem> orderItems = body.getOrderItemDtos().stream().map(orderItemDto -> {
            final OrderItemInfo orderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from(orderItemDto.getOrderItemId())).build();
            final OrderItemState orderItemState = OrderItemState.createEmpty();
            final TrackingInfo trackingInfo = TrackingInfo.from(orderItemDto.getTrackingInfo());
            return OrderItem.of(orderItemInfo,orderItemState,trackingInfo);
        }).toList();
        return Order.createRequestOrder(orderBase,orderDetail,price,orderItems);
    }
    @Override
    public OrderUpdateClient getClient() {
        return this.client;
    }

    public static RegisterTrackingInfoRequest of(final String memberId,final RegisterTrackingInfoRequestBody body,final NoOperationClient client){
        return new RegisterTrackingInfoRequest(memberId,body,client);
    }

}
