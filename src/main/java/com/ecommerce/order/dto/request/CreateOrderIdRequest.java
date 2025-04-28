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
public class CreateOrderIdRequest implements OrderRequest {
    private final String memberId;
    private final CreateOrderIdRequestBody body;
    private final OrderClient client;

    public TmpOrder toTmpOrder(){
        final List<TmpOrderItem> tmpOrderItems = body.getOrderItemDtos().stream().map(orderItemDto ->
                TmpOrderItem.builder()
                        .orderItemId(OrderItemId.createEmpty())
                        .productId(orderItemDto.getProductId())
                        .productName(orderItemDto.getProductName())
                        .quantity(orderItemDto.getQuantity())
                        .price(orderItemDto.getPrice())
                        .discountPrice(orderItemDto.getDiscountPrice())
                        .couponDiscountPercent(orderItemDto.getCouponDiscountPercent())
                        .couponId(orderItemDto.getCouponId())
                        .userCouponId(orderItemDto.getUserCouponId())
                        .build()).toList();
        return TmpOrder.builder()
                .orderId(OrderId.createEmpty())
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
    public static CreateOrderIdRequest of(final String memberId,final CreateOrderIdRequestBody body,final OrderCreateClient client){
        return new CreateOrderIdRequest(memberId, body, client);
    }
}

//패키지변경
//TmpOrder변경