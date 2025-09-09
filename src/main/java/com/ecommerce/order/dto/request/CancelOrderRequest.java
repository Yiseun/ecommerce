package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.domain.orderitem.OrderItemState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CancelOrderRequest implements UpdateOrderRequest{
    private final String memberId;
    private final CancelOrderRequestBody body;
    @Override
    public Order toOrder() {
        final OrderBase orderBase = OrderBase.of(OrderId.from(body.getOrderId()),this.memberId);
        final OrderDetail orderDetail = OrderDetail.createEmpty();
        final Price price = Price.from(body.getTotalPrice());
        final List<OrderItem> orderItems = body.getOrderItemDtos().stream().map(orderItemDto -> {
            final OrderItemInfo orderItemInfo = OrderItemInfo.builder()
                    .orderItemId(OrderItemId.from(orderItemDto.getOrderItemId()))
                    .productId(orderItemDto.getProductId())
                    .productName(orderItemDto.getProductName())
                    .quantity(Quantity.from(orderItemDto.getQuantity()))
                    .price(Price.from(orderItemDto.getPrice()))
                    .discountPrice(Price.from(orderItemDto.getDiscountPrice()))
                    .couponDiscountPercent(orderItemDto.getCouponDiscountPercent())
                    .couponId(orderItemDto.getCouponId())
                    .userCouponId(orderItemDto.getUserCouponId())
                    .build();
            final OrderItemState orderItemState = OrderItemState.CANCELED;
            final TrackingInfo trackingInfo = TrackingInfo.createEmpty();
            return OrderItem.of(orderItemInfo,orderItemState,trackingInfo);
        }).toList();
        return Order.createRequestOrder(orderBase,orderDetail,price,orderItems);
    }

    public static CancelOrderRequest of(final String memberId,final CancelOrderRequestBody body){
        return new CancelOrderRequest(memberId, body);
    }
}
