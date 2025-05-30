package com.ecommerce.order.dto.response;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReadOrderResponse {
    private final List<OrderDto> orderDtos;

    public static ReadOrderResponse from(final List<Order> orders){
        final List<OrderDto> orderDtos = orders.stream().map(order -> {
            final List<OrderItemDto> orderItemDtos = order.getOrderItems().stream().map(orderItem ->
                OrderItemDto.builder()
                        .orderItemId(orderItem.getOrderItemInfo().getOrderItemId().getValue().toString())
                        .productId(orderItem.getOrderItemInfo().getProductId())
                        .productName(orderItem.getOrderItemInfo().getProductName())
                        .quantity(orderItem.getOrderItemInfo().getQuantity().getValue().toString())
                        .price(orderItem.getOrderItemInfo().getPrice().getValue().toString())
                        .discountPrice(orderItem.getOrderItemInfo().getDiscountPrice().toString())
                        .couponDiscountPercent(orderItem.getOrderItemInfo().getCouponDiscountPercent())
                        .couponId(orderItem.getOrderItemInfo().getCouponId())
                        .userCouponId(orderItem.getOrderItemInfo().getUserCouponId())
                        .orderItemState(orderItem.getOrderItemState().name())
                        .trackingInfo(orderItem.getTrackingInfo().getValue())
                        .build()
            ).toList();
            return new OrderDto(order.getOrderBase().getOrderId().getValue().toString(),order.getOriginPrice().getValue().toString(),orderItemDtos);
        }).toList();
        return new ReadOrderResponse(orderDtos);
    }
}
