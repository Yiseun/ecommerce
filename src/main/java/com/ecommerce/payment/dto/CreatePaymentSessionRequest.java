package com.ecommerce.payment.dto;

import com.ecommerce.payment.PaymentKeyCreator;
import com.ecommerce.payment.domain.PayMethod;
import com.ecommerce.payment.domain.PgProvider;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.paymentitem.Quantity;
import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.session.PaymentSession;
import com.ecommerce.payment.domain.paymentitem.PurchaseItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CreatePaymentSessionRequest {
    private final String memberId;
    private final CreatePaymentSessionRequestBody body;

    public PaymentSession toPaymentSession(final PaymentKeyCreator paymentKeyCreator){
        final List<PurchaseItem> purchaseItems = body.getPurchaseItemDtos().stream().map(i->PurchaseItem.builder()
                .productId(i.getProductId())
                .productName(i.getProductName())
                .quantity(Quantity.from(i.getQuantity()))
                .price(Price.from(i.getPrice()))
                .discountPrice(i.getDiscountPrice())
                .couponDiscountPercent(i.getCouponDiscountPercent())
                .couponId(i.getCouponId())
                .userCouponId(i.getUserCouponId())
                .build()).toList();
        final PayMethod payMethod = PayMethod.from(body.getPayMethod());
        final PgProvider pgProvider = PgProvider.createDefault(payMethod);
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .orderId(body.getOrderId())
                .buyerName(body.getBuyerName())
                .buyerPhoneNumber(body.getBuyerPhoneNumber())
                .buyerEmail(body.getBuyerEmail())
                .buyerAddress(body.getBuyerAddress())
                .buyerPostcode(body.getBuyerPostcode())
                .payMethod(payMethod)
                .pgProvider(pgProvider)
                .build();
        return PaymentSession.of(paymentKeyCreator.createPaymentKey(),this.memberId,paymentInfo,Price.from(body.getTotalPrice()),purchaseItems);
    }

    public static CreatePaymentSessionRequest of(final String memberId, final CreatePaymentSessionRequestBody body){
        return new CreatePaymentSessionRequest(memberId,body);
    }
}
