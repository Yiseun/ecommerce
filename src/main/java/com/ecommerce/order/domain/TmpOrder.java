package com.ecommerce.order.domain;

import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.payment.exception.application.domain.FailedCreationException;
import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode
@Getter
public class TmpOrder {
    private final OrderId orderId;
    private final String memberId;
    private final String totalPrice;
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
    private final List<TmpOrderItem> tmpOrderItems;

    public Order createOrderWith(final TmpOrder tmpOrder){
        if(!this.equals(tmpOrder)){
            throw new FailedCreationException("정보가 다릅니다.");
        }
        final OrderBase orderBase = OrderBase.of(this.orderId,this.memberId);
        final OrderDetail orderDetail = OrderDetail.builder()
                .buyerName(this.buyerName)
                .buyerPhoneNumber(this.buyerPhoneNumber)
                .buyerEmail(this.buyerEmail)
                .buyerAddress(this.buyerAddress)
                .buyerPostcode(this.buyerPostcode)
                .build();
        final Price price = Price.from(this.totalPrice);
        final List<OrderItem> orderItems = tmpOrderItems.stream().map(tmpOrderItem -> {
            final OrderItemInfo orderItemInfo = OrderItemInfo.builder()
                    .orderItemId(OrderItemId.createEmpty())
                    .productId(tmpOrderItem.getProductId())
                    .productName(tmpOrderItem.getProductName())
                    .quantity(Quantity.from(tmpOrderItem.getQuantity()))
                    .price(Price.from(tmpOrderItem.getPrice()))
                    .discountPrice(Price.from(tmpOrderItem.getDiscountPrice()))
                    .couponDiscountPercent(tmpOrderItem.getCouponDiscountPercent())
                    .couponId(tmpOrderItem.getCouponId())
                    .userCouponId(tmpOrderItem.getUserCouponId())
                    .build();
            return OrderItem.from(orderItemInfo);
        }).toList();
        return Order.createRequestOrder(orderBase,orderDetail,price,orderItems);
    }
}
