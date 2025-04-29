package com.ecommerce.order.dto.response;

import com.ecommerce.order.domain.TmpOrder;
import com.ecommerce.order.dto.OrderItemDto;
import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderIdResponse {
    private final String orderId;
    private final List<OrderItemDto> orderItemDtos;

    public static CreateOrderIdResponse from(final TmpOrder tmpOrder){
        final String orderId = tmpOrder.getOrderId().getValue().toString();
        final List<OrderItemDto> orderItemDtoList = tmpOrder.getTmpOrderItems().stream().map(i->
                OrderItemDto.builder()
                        .orderItemId(i.getOrderItemId().getValue().toString())
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .discountPrice(i.getDiscountPrice())
                        .couponDiscountPercent(i.getCouponDiscountPercent())
                        .couponId(i.getCouponId())
                        .userCouponId(i.getUserCouponId())
                        .build()
        ).toList();
        return new CreateOrderIdResponse(orderId,orderItemDtoList);
    }
}
