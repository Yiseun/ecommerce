package com.ecommerce.order.persistence.entity;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.domain.orderitem.OrderItemState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @CreatedDate
    private LocalDate createdTime;

    public Order toOrder(){
        final OrderBase orderBase = OrderBase.of(OrderId.from(this.orderId),this.memberId);
        final OrderDetail orderDetail = OrderDetail.builder()
                .buyerName(this.buyerName)
                .buyerPhoneNumber(this.buyerPhoneNumber)
                .buyerEmail(this.buyerEmail)
                .buyerAddress(this.buyerAddress)
                .buyerPostcode(this.buyerPostcode)
                .build();
        final Price originPrice = Price.from(this.totalPrice);
        final List<OrderItem> orderItems = orderItemEntityList.stream().map(orderItemEntity -> {
            final OrderItemInfo orderItemInfo = OrderItemInfo.builder()
                    .orderItemId(OrderItemId.from(orderItemEntity.getOrderItemId().toString()))
                    .productId(orderItemEntity.getProductId())
                    .productName(orderItemEntity.getProductName())
                    .quantity(Quantity.from(orderItemEntity.getQuantity()))
                    .price(Price.from(orderItemEntity.getPrice()))
                    .discountPrice(Price.from(orderItemEntity.getDiscountPrice()))
                    .couponDiscountPercent(orderItemEntity.getCouponDiscountPercent())
                    .couponId(orderItemEntity.getCouponId())
                    .userCouponId(orderItemEntity.getUserCouponId())
                    .build();
            final OrderItemState orderItemState = OrderItemState.from(orderItemEntity.getOrderItemState());
            final TrackingInfo trackingInfo = TrackingInfo.from(orderItemEntity.getTrackingInfo());
            return OrderItem.of(orderItemInfo,orderItemState,trackingInfo);
        }).toList();
        return Order.of(this.orderEntityId,orderBase,orderDetail,originPrice,orderItems,this.version,this.createdTime);
    }
    public static OrderEntity from(final Order order){
        final List<OrderItemEntity> orderItemEntities = order.getOrderItems().stream().map(orderItem ->
                OrderItemEntity.builder()
                        .orderItemId(orderItem.getOrderItemInfo().getOrderItemId().getValue())
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
                .orderEntityId(order.getOrderEntityId())
                .orderId(order.getOrderBase().getOrderId().getValue().toString())
                .memberId(order.getOrderBase().getMemberId())
                .totalPrice(order.getOriginPrice().getValue().toString())
                .buyerName(order.getOrderDetail().getBuyerName())
                .buyerPhoneNumber(order.getOrderDetail().getBuyerPhoneNumber())
                .buyerEmail(order.getOrderDetail().getBuyerEmail())
                .buyerAddress(order.getOrderDetail().getBuyerAddress())
                .buyerPostcode(order.getOrderDetail().getBuyerPostcode())
                .orderItemEntityList(orderItemEntities)
                .version(order.getVersion())
                .createdTime(order.getCreatedTime())
                .build();
    }
}
