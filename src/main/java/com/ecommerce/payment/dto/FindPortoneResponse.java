package com.ecommerce.payment.dto;

import com.ecommerce.payment.domain.PayMethod;
import com.ecommerce.payment.domain.Payment;
import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.PgProvider;
import com.ecommerce.payment.domain.paymentitem.PaymentItem;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.session.PaymentSession;
import com.ecommerce.payment.exception.application.domain.FailedCreationException;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FindPortoneResponse {

    private final String impUid;
    private final String orderId;

    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
    private final Long price;
    private final String pgProvider;
    private final String payMethod;

    public Payment createWith(final PaymentSession paymentSession){
        final PaymentInfo portonePaymentInfo = PaymentInfo.builder()
                .buyerName(this.buyerName)
                .buyerPhoneNumber(this.buyerPhoneNumber)
                .buyerEmail(this.buyerEmail)
                .buyerAddress(this.buyerAddress)
                .buyerPostcode(this.buyerPostcode)
                .pgProvider(PgProvider.from(this.pgProvider))
                .payMethod(PayMethod.from(this.payMethod))
                .build();
        final Price portoneTotalPrice = Price.from(this.price.toString());
        final String portoneOrderId = this.orderId;
        final List<PaymentItem> paymentItems = paymentSession.getPurchaseItems().stream().map(purchaseItem ->
                PaymentItem.of(purchaseItem.getOrderItemId(),purchaseItem.getPaymentItemInfo())).toList();
        if(!portoneOrderId.equals(paymentSession.getOrderId())||
                !portonePaymentInfo.equals(paymentSession.getPaymentInfo())||
                !portoneTotalPrice.equals(paymentSession.getTotalPrice())){
            throw new FailedCreationException("결제정보생성에 실패했습니다.");
        }
        return Payment.builder()
                .impUid(this.impUid)
                .orderId(this.orderId)
                .paymentInfo(portonePaymentInfo)
                .memberId(paymentSession.getMemberId())
                .totalPrice(portoneTotalPrice)
                .paymentItems(paymentItems)
                .build();
    }


    public static FindPortoneResponse from(final IamportResponse<com.siot.IamportRestClient.response.Payment> response){
        final com.siot.IamportRestClient.response.Payment payment = response.getResponse();
        return FindPortoneResponse.builder()
                .impUid(payment.getImpUid())
                .orderId(payment.getMerchantUid())
                .buyerName(payment.getBuyerName())
                .buyerPhoneNumber(payment.getBuyerTel())
                .buyerEmail(payment.getBuyerEmail())
                .buyerAddress(payment.getBuyerAddr())
                .buyerPostcode(payment.getBuyerPostcode())
                .price(payment.getAmount().longValue())
                .pgProvider(payment.getPgProvider())
                .payMethod(payment.getPayMethod())
                .build();
    }
}
