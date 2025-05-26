package com.ecommerce.order.persistence.entity;

import com.ecommerce.order.domain.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
        final List<TmpOrderItem> tmpOrderItems = tmpOrderItemEntityList.stream().map(tmpOrderItemEntity ->
                TmpOrderItem.builder()
                        .tmpOrderItemId(tmpOrderItemEntity.getTmpOrderItemId())
                        .productId(tmpOrderItemEntity.getProductId())
                        .productName(tmpOrderItemEntity.getProductName())
                        .quantity(tmpOrderItemEntity.getQuantity())
                        .price(tmpOrderItemEntity.getPrice())
                        .discountPrice(tmpOrderItemEntity.getDiscountPrice())
                        .couponDiscountPercent(tmpOrderItemEntity.getCouponDiscountPercent())
                        .couponId(tmpOrderItemEntity.getCouponId())
                        .userCouponId(tmpOrderItemEntity.getUserCouponId())
                        .build()
        ).toList();

        return TmpOrder.builder()
                .orderId(OrderId.from(this.orderId.toString()))
                .memberId(this.memberId)
                .totalPrice(this.totalPrice)
                .buyerName(this.buyerName)
                .buyerPhoneNumber(this.buyerPhoneNumber)
                .buyerEmail(this.buyerEmail)
                .buyerAddress(this.buyerAddress)
                .buyerPostcode(this.buyerPostcode)
                .tmpOrderItems(tmpOrderItems)
                .build();
    }


    public static TmpOrderEntity from(final TmpOrder tmpOrder){
        final Long orderId = tmpOrder.getOrderId().getValue();
        final String memberId = tmpOrder.getMemberId();
        final String totalPrice = tmpOrder.getTotalPrice();
        final String buyerName = tmpOrder.getBuyerName();
        final String buyerPhoneNumber = tmpOrder.getBuyerPhoneNumber();
        final String buyerEmail = tmpOrder.getBuyerEmail();
        final String buyerAddress = tmpOrder.getBuyerAddress();
        final String buyerPostcode = tmpOrder.getBuyerPostcode();

        final List<TmpOrderItemEntity> tmpOrderItemEntityList = tmpOrder.getTmpOrderItems().stream()
                .map(tmpOrderItem->TmpOrderItemEntity.builder()
                        .tmpOrderItemId(tmpOrderItem.getTmpOrderItemId())
                        .productId(tmpOrderItem.getProductId())
                        .productName(tmpOrderItem.getProductName())
                        .quantity(tmpOrderItem.getQuantity())
                        .price(tmpOrderItem.getPrice())
                        .discountPrice(tmpOrderItem.getDiscountPrice())
                        .couponDiscountPercent(tmpOrderItem.getCouponDiscountPercent())
                        .couponId(tmpOrderItem.getCouponId())
                        .userCouponId(tmpOrderItem.getUserCouponId())
                        .build())
                .toList();
        return new TmpOrderEntity(orderId,memberId,totalPrice,buyerName,buyerPhoneNumber,buyerEmail,buyerAddress,
                buyerPostcode,tmpOrderItemEntityList);
    }
}
