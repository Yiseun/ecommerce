package com.ecommerce.order.persistence.entity;

import com.ecommerce.order.domain.Order;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderEntityId;
    @Column(unique = true)
    private String orderId;
    private String memberId;
    private String totalPrice;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerEmail;
    private String buyerAddress;
    private String buyerPostcode;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemEntityList;
    @Version
    private Long version;

    public static OrderEntity from(final Order order){
        final List<OrderItemEntity> orderItemEntities = order.getOrderItems().stream().map(orderItem ->
                OrderItemEntity.builder()
                        .orderItemId(orderItem.getOrderItemInfo().getOrderItemId().getValue().toString())
                        .productId(orderItem.getOrderItemInfo().getProductId())
                        .productName(orderItem.getOrderItemInfo().getProductName())
                        .quantity(orderItem.getOrderItemInfo().getQuantity().getValue().toString())
                        .price(orderItem.getOrderItemInfo().getPrice().getValue().toString())
                        .discountPrice(orderItem.getOrderItemInfo().getDiscountPrice().getValue().toString())
                        .couponDiscountPercent(orderItem.getOrderItemInfo().getCouponDiscountPercent())
                        .couponId(orderItem.getOrderItemInfo().getCouponId())
                        .userCouponId(orderItem.getOrderItemInfo().getUserCouponId())
                        .orderItemState(orderItem.getOrderItemState().name())
                        .trackingInfo(orderItem.getTrackingInfo().getValue())
                        .build()).toList();
        return OrderEntity.builder()
                .orderId(order.getOrderBase().getOrderId().getValue().toString())
                .memberId(order.getOrderBase().getMemberId())
                .totalPrice(order.getOriginPrice().getValue().toString())
                .buyerName(order.getOrderDetail().getBuyerName())
                .buyerPhoneNumber(order.getOrderDetail().getBuyerPhoneNumber())
                .buyerEmail(order.getOrderDetail().getBuyerEmail())
                .buyerAddress(order.getOrderDetail().getBuyerAddress())
                .buyerPostcode(order.getOrderDetail().getBuyerPostcode())
                .orderItemEntityList(orderItemEntities)
                .build();
    }
}
