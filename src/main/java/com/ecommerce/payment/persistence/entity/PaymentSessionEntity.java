package com.ecommerce.payment.persistence.entity;

import com.ecommerce.payment.domain.PayMethod;
import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.PgProvider;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.paymentitem.PurchaseItem;
import com.ecommerce.payment.domain.paymentitem.Quantity;
import com.ecommerce.payment.domain.session.PaymentKey;
import com.ecommerce.payment.domain.session.PaymentSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentSessionId;
    private String paymentKey;
    private String memberId;
    private String orderId;
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
        final List<PurchaseItem> purchaseItems = purchaseItemEntities.stream().map(purchaseItemEntity ->
                PurchaseItem.builder()
                        .productId(purchaseItemEntity.getProductId())
                        .productName(purchaseItemEntity.getProductName())
                        .quantity(Quantity.from(purchaseItemEntity.getQuantity()))
                        .price(Price.from(purchaseItemEntity.getPrice()))
                        .discountPrice(purchaseItemEntity.getDiscountPrice())
                        .couponDiscountPercent(purchaseItemEntity.getCouponDiscountPercent())
                        .couponId(purchaseItemEntity.getCouponId())
                        .userCouponId(purchaseItemEntity.getUserCouponId())
                        .build()
        ).toList();
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .orderId(this.orderId)
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
                .paymentInfo(paymentInfo)
                .totalPrice(Price.from(this.totalPrice))
                .purchaseItems(purchaseItems)
                .build();
    }

    public static PaymentSessionEntity from(final PaymentSession paymentSession){
        final List<PurchaseItemEntity> purchaseItemEntityList = paymentSession.getPurchaseItems().stream().map(purchaseItem ->
            PurchaseItemEntity.builder()
                    .productId(purchaseItem.getProductId())
                    .productName(purchaseItem.getProductName())
                    .quantity(purchaseItem.getQuantity().getValue().toString())
                    .price(purchaseItem.getPrice().getValue().toString())
                    .discountPrice(purchaseItem.getDiscountPrice())
                    .couponDiscountPercent(purchaseItem.getCouponDiscountPercent())
                    .couponId(purchaseItem.getCouponId())
                    .userCouponId(purchaseItem.getUserCouponId())
                    .build()
        ).toList();

        return PaymentSessionEntity.builder()
                .paymentKey(paymentSession.getPaymentKey().getValue())
                .memberId(paymentSession.getMemberId())
                .orderId(paymentSession.getPaymentInfo().getOrderId())
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
