package com.ecommerce.payment.persistence.entity;

import com.ecommerce.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @Column(unique = true)
    private String orderId;
    private String impUid;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerEmail;
    private String buyerAddress;
    private String buyerPostcode;
    private String payMethod;
    private String pgProvider;
    private String memberId;
    private String totalPrice;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PaymentItemEntity> paymentItemEntities;

    public static PaymentEntity from(final Payment payment){
        final List<PaymentItemEntity> paymentItemEntityList = payment.getPaymentItems().stream().map(paymentItem ->
                PaymentItemEntity.builder().orderItemId(paymentItem.getOrderItemId())
                        .productId(paymentItem.getPaymentItemInfo().getProductId())
                        .productName(paymentItem.getPaymentItemInfo().getProductName())
                        .quantity(paymentItem.getPaymentItemInfo().getQuantity().getValue().toString())
                        .price(paymentItem.getPaymentItemInfo().getPrice().getValue().toString())
                        .discountPrice(paymentItem.getPaymentItemInfo().getDiscountPrice().toString())
                        .couponDiscountPercent(paymentItem.getPaymentItemInfo().getCouponDiscountPercent())
                        .couponId(paymentItem.getPaymentItemInfo().getCouponId())
                        .userCouponId(paymentItem.getPaymentItemInfo().getUserCouponId())
                        .build()).toList();
        return PaymentEntity.builder()
                .orderId(payment.getOrderId())
                .impUid(payment.getImpUid())
                .buyerName(payment.getPaymentInfo().getBuyerName())
                .buyerPhoneNumber(payment.getPaymentInfo().getBuyerPhoneNumber())
                .buyerEmail(payment.getPaymentInfo().getBuyerEmail())
                .buyerAddress(payment.getPaymentInfo().getBuyerAddress())
                .buyerPostcode(payment.getPaymentInfo().getBuyerPostcode())
                .payMethod(payment.getPaymentInfo().getPayMethod().name())
                .pgProvider(payment.getPaymentInfo().getPgProvider().name())
                .memberId(payment.getMemberId())
                .totalPrice(payment.getTotalPrice().getValue().toString())
                .paymentItemEntities(paymentItemEntityList)
                .build();
    }
}
