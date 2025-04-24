package com.ecommerce.order.dto;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.TmpOrder;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.port.OrderClient;
import com.ecommerce.order.port.OrderCreateClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderIdRequest implements OrderRequest{
    private final String memberId;
    private final CreateOrderIdRequestBody body;
    private final OrderClient client;

    public TmpOrder toTmpOrder(){
        final List<OrderItem> orderItems = body.getOrderItemDtos().stream()
                .map(i->OrderItem.from(
                        OrderItemInfo.builder()
                                     .orderItemId(OrderItemId.createEmpty())
                                     .productId(i.getProductId())
                                     .productName(i.getProductName())
                                     .quantity(Quantity.from(i.getQuantity()))
                                     .price(Price.from(i.getPrice()))
                                     .discountPrice(Price.from(i.getDiscountPrice()))
                                     .couponDiscountPercent(i.getCouponDiscountPercent())
                                     .couponId(i.getCouponId())
                                     .userCouponId(i.getUserCouponId())
                                     .build()
                ))
                .toList();
        final OrderDetail orderDetail = OrderDetail.builder().buyerName(body.getBuyerName())
                .buyerPhoneNumber(body.getBuyerPhoneNumber())
                .buyerEmail(body.getBuyerEmail())
                .buyerAddress(body.getBuyerAddress())
                .buyerPostcode(body.getBuyerPostcode())
                .build();
        final OrderBase orderBase = OrderBase.init(this.memberId);
        final Price totalPrice = Price.from(body.getTotalPrice());
        final Order order = Order.of(orderBase,orderDetail,totalPrice,orderItems);
        return TmpOrder.from(order);
    }
    public static CreateOrderIdRequest of(final String memberId,final CreateOrderIdRequestBody body,final OrderCreateClient client){
        return new CreateOrderIdRequest(memberId, body, client);
    }
}
