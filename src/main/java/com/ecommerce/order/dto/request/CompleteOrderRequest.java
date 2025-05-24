package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.*;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.port.OrderClient;
import com.ecommerce.order.port.OrderCreateClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompleteOrderRequest implements OrderRequest {
    private final String memberId;
    private final CompleteOrderRequestBody body;
    private final OrderClient client;

    public TmpOrder toTmpOrder(){
        final List<TmpOrderItem> tmpOrderItems = body.getOrderItemDtos().stream().map(orderItemDto ->
                TmpOrderItem.builder()
                        .orderItemId(OrderItemId.from(orderItemDto.getOrderItemId()))
                        .productId(orderItemDto.getProductId())
                        .productName(orderItemDto.getProductName())
                        .quantity(orderItemDto.getQuantity())
                        .price(orderItemDto.getPrice())
                        .discountPrice(orderItemDto.getDiscountPrice())
                        .couponDiscountPercent(orderItemDto.getCouponDiscountPercent())
                        .couponId(orderItemDto.getCouponId())
                        .userCouponId(orderItemDto.getUserCouponId())
                        .build()
                ).toList();
        return TmpOrder.builder()
                .orderId(OrderId.from(body.getOrderId()))
                .memberId(this.memberId)
                .totalPrice(body.getTotalPrice())
                .buyerName(body.getBuyerName())
                .buyerPhoneNumber(body.getBuyerPhoneNumber())
                .buyerEmail(body.getBuyerEmail())
                .buyerAddress(body.getBuyerAddress())
                .buyerPostcode(body.getBuyerPostcode())
                .tmpOrderItems(tmpOrderItems)
                .build();
    }

    public static CompleteOrderRequest of(final String memberId, final CompleteOrderRequestBody body, final OrderCreateClient client){
        return new CompleteOrderRequest(memberId,body,client);
    }
}
