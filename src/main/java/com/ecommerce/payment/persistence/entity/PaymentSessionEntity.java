package com.ecommerce.payment.persistence.entity;

import com.ecommerce.payment.domain.PayMethod;
import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.PgProvider;
import com.ecommerce.payment.domain.paymentitem.PaymentItemInfo;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.paymentitem.PurchaseItem;
import com.ecommerce.payment.domain.paymentitem.Quantity;
import com.ecommerce.payment.domain.session.PaymentKey;
import com.ecommerce.payment.domain.session.PaymentSession;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSessionEntity {

    @Id
    private String orderId;
    private String paymentKey;
    private String memberId;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerEmail;
    private String buyerAddress;
    private String buyerPostcode;
    private String payMethod;
    private String pgProvider;
    private String totalPrice;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PurchaseItemEntity> purchaseItemEntities;

    public PaymentSession toPaymentSession(){
        final List<PurchaseItem> purchaseItems = purchaseItemEntities.stream().map(purchaseItemEntity ->{
            final PaymentItemInfo paymentItemInfo = PaymentItemInfo.builder()
                    .productId(purchaseItemEntity.getProductId())
                    .productName(purchaseItemEntity.getProductName())
                    .quantity(Quantity.from(purchaseItemEntity.getQuantity()))
                    .price(Price.from(purchaseItemEntity.getPrice()))
                    .discountPrice(Price.from(purchaseItemEntity.getDiscountPrice()))
                    .couponDiscountPercent(purchaseItemEntity.getCouponDiscountPercent())
                    .couponId(purchaseItemEntity.getCouponId())
                    .userCouponId(purchaseItemEntity.getUserCouponId())
                    .build();
            return PurchaseItem.of(purchaseItemEntity.getOrderItemId(),paymentItemInfo);
        }).toList();
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .buyerName(this.buyerName)
                .buyerPhoneNumber(this.buyerPhoneNumber)
                .buyerEmail(this.buyerEmail)
                .buyerAddress(this.buyerAddress)
                .buyerPostcode(this.buyerPostcode)
                .payMethod(PayMethod.from(this.payMethod))
                .pgProvider(PgProvider.from(this.pgProvider))
                .build();
        return PaymentSession.builder()
                .paymentKey(PaymentKey.from(this.paymentKey))
                .memberId(this.memberId)
                .orderId(this.orderId)
                .paymentInfo(paymentInfo)
                .totalPrice(Price.from(this.totalPrice))
                .purchaseItems(purchaseItems)
                .build();
    }

    public static PaymentSessionEntity from(final PaymentSession paymentSession){
        final List<PurchaseItemEntity> purchaseItemEntityList = paymentSession.getPurchaseItems().stream().map(purchaseItem ->
            PurchaseItemEntity.builder()
                    .orderItemId(purchaseItem.getOrderItemId())
                    .productId(purchaseItem.getPaymentItemInfo().getProductId())
                    .productName(purchaseItem.getPaymentItemInfo().getProductName())
                    .quantity(purchaseItem.getPaymentItemInfo().getQuantity().getValue().toString())
                    .price(purchaseItem.getPaymentItemInfo().getPrice().getValue().toString())
                    .discountPrice(purchaseItem.getPaymentItemInfo().getDiscountPrice().getValue().toString())
                    .couponDiscountPercent(purchaseItem.getPaymentItemInfo().getCouponDiscountPercent())
                    .couponId(purchaseItem.getPaymentItemInfo().getCouponId())
                    .userCouponId(purchaseItem.getPaymentItemInfo().getUserCouponId())
                    .build()
        ).toList();

        return PaymentSessionEntity.builder()
                .paymentKey(paymentSession.getPaymentKey().getValue())
                .memberId(paymentSession.getMemberId())
                .orderId(paymentSession.getOrderId())
                .buyerName(paymentSession.getPaymentInfo().getBuyerName())
                .buyerPhoneNumber(paymentSession.getPaymentInfo().getBuyerPhoneNumber())
                .buyerEmail(paymentSession.getPaymentInfo().getBuyerEmail())
                .buyerAddress(paymentSession.getPaymentInfo().getBuyerAddress())
                .buyerPostcode(paymentSession.getPaymentInfo().getBuyerPostcode())
                .payMethod(paymentSession.getPaymentInfo().getPayMethod().name())
                .pgProvider(paymentSession.getPaymentInfo().getPgProvider().name())
                .totalPrice(paymentSession.getTotalPrice().getValue().toString())
                .purchaseItemEntities(purchaseItemEntityList)
                .build();

    }
}
