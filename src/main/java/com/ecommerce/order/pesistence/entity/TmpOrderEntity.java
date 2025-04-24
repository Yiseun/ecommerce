package com.ecommerce.order.pesistence.entity;

import com.ecommerce.order.domain.*;
import com.ecommerce.order.domain.orderitem.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TmpOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String memberId;
    private String totalPrice;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerEmail;
    private String buyerAddress;
    private String buyerPostcode;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TmpOrderItemEntity> tmpOrderItemEntityList;

    public TmpOrder toTmpOrder(){
        final List<OrderItem> orderItems = this.tmpOrderItemEntityList.stream()
                .map(i-> OrderItem.of(
                        OrderItemInfo.builder()
                                     .orderItemId(OrderItemId.from(String.valueOf(i.getOrderItemId())))
                                     .productId(i.getProductId())
                                     .productName(i.getProductName())
                                     .quantity(Quantity.from(i.getQuantity()))
                                     .price(Price.from(i.getPrice()))
                                     .discountPrice(Price.from(i.getDiscountPrice()))
                                     .couponDiscountPercent(i.getCouponDiscountPercent())
                                     .couponId(i.getCouponId())
                                     .userCouponId(i.getUserCouponId())
                                     .build(),
                        OrderItemState.valueOf(i.getOrderItemState()),
                        TrackingInfo.from(i.getTrackingInfo())))
                .toList();

        final OrderBase orderBase = OrderBase.of(OrderId.from(this.orderId.toString()),this.memberId);
        final OrderDetail orderDetail = OrderDetail.builder().buyerName(this.buyerName)
                                                             .buyerPhoneNumber(this.buyerPhoneNumber)
                                                             .buyerEmail(this.buyerEmail)
                                                             .buyerAddress(this.buyerAddress)
                                                             .buyerPostcode(this.buyerPostcode)
                                                             .build();
        final Price totalPrice = Price.from(this.totalPrice);
        final Order order = Order.of(orderBase,orderDetail,totalPrice,orderItems);
        return TmpOrder.from(order);
    }


    public static TmpOrderEntity from(final TmpOrder tmpOrder){
        final Order order = tmpOrder.getOrder();
        final Long orderId = order.getOrderBase().getOrderId().getValue();
        final String memberId = order.getOrderBase().getMemberId();
        final String totalPrice = order.getOriginPrice().getValue().toString();
        final String buyerName = order.getOrderDetail().getBuyerName();
        final String buyerPhoneNumber = order.getOrderDetail().getBuyerPhoneNumber();
        final String buyerEmail = order.getOrderDetail().getBuyerEmail();
        final String buyerAddress = order.getOrderDetail().getBuyerAddress();
        final String buyerPostcode = order.getOrderDetail().getBuyerPostcode();

        final List<TmpOrderItemEntity> tmpOrderItemEntityList = order.getOrderItems().stream()
                .map(i->TmpOrderItemEntity.builder()
                        .orderItemId(i.getOrderItemInfo().getOrderItemId().getValue())
                        .productId(i.getOrderItemInfo().getProductId())
                        .productName(i.getOrderItemInfo().getProductName())
                        .quantity(i.getOrderItemInfo().getQuantity().getValue().toString())
                        .price(i.getOrderItemInfo().getPrice().getValue().toString())
                        .discountPrice(i.getOrderItemInfo().getDiscountPrice().getValue().toString())
                        .couponDiscountPercent(i.getOrderItemInfo().getCouponDiscountPercent())
                        .couponId(i.getOrderItemInfo().getCouponId())
                        .userCouponId(i.getOrderItemInfo().getUserCouponId())
                        .orderItemState(i.getOrderItemState().name())
                        .trackingInfo(i.getTrackingInfo().getValue())
                        .build())
                .toList();
        return new TmpOrderEntity(orderId,memberId,totalPrice,buyerName,buyerPhoneNumber,buyerEmail,buyerAddress,
                buyerPostcode,tmpOrderItemEntityList);
    }
}
