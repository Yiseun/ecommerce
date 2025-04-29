package com.ecommerce.payment.dto.request;

import com.ecommerce.payment.PaymentKeyCreator;
import com.ecommerce.payment.domain.PayMethod;
import com.ecommerce.payment.domain.PgProvider;
import com.ecommerce.payment.domain.paymentitem.*;
import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.session.PaymentSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CreatePaymentSessionRequest {
    private final String memberId;
    private final CreatePaymentSessionRequestBody body;

    public PaymentSession toPaymentSession(final PaymentKeyCreator paymentKeyCreator){
        final List<PurchaseItem> purchaseItems = body.getPurchaseItemDtos().stream().map(i->{
            final PaymentItemInfo paymentItemInfo = PaymentItemInfo.builder()
                    .productId(i.getProductId())
                    .productName(i.getProductName())
                    .quantity(Quantity.from(i.getQuantity()))
                    .price(Price.from(i.getPrice()))
                    .discountPrice(Price.from(i.getDiscountPrice()))
                    .couponDiscountPercent(i.getCouponDiscountPercent())
                    .couponId(i.getCouponId())
                    .userCouponId(i.getUserCouponId())
                    .build();
            return PurchaseItem.of(i.getOrderItemId(),paymentItemInfo);
        }).toList();
        final PayMethod payMethod = PayMethod.from(body.getPayMethod());
        final PgProvider pgProvider = PgProvider.createDefault(payMethod);
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .buyerName(body.getBuyerName())
                .buyerPhoneNumber(body.getBuyerPhoneNumber())
                .buyerEmail(body.getBuyerEmail())
                .buyerAddress(body.getBuyerAddress())
                .buyerPostcode(body.getBuyerPostcode())
                .payMethod(payMethod)
                .pgProvider(pgProvider)
                .build();
        return PaymentSession.builder()
                .paymentKey(paymentKeyCreator.createPaymentKey())
                .memberId(this.memberId)
                .orderId(this.body.getOrderId())
                .paymentInfo(paymentInfo)
                .totalPrice(Price.from(this.body.getTotalPrice()))
                .purchaseItems(purchaseItems)
                .build();
    }

    public static CreatePaymentSessionRequest of(final String memberId, final CreatePaymentSessionRequestBody body){
        return new CreatePaymentSessionRequest(memberId,body);
    }
}
