package com.ecommerce.payment.dto.internal;

import com.ecommerce.payment.domain.PayMethod;
import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.PgProvider;
import com.ecommerce.payment.domain.paymentitem.PaymentItemInfo;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.paymentitem.PurchaseItem;
import com.ecommerce.payment.domain.paymentitem.Quantity;
import com.ecommerce.payment.domain.session.PaymentKey;
import com.ecommerce.payment.domain.session.PaymentSession;
import com.ecommerce.payment.dto.PurchaseItemDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InternalPaymentPurchaseRequest {
    private final String orderId;
    private final String impUid;
    private final String paymentKey;
    private final String memberId;
    private final String buyerName;
    private final String buyerPhoneNumber;
    private final String buyerEmail;
    private final String buyerAddress;
    private final String buyerPostcode;
    private final String payMethod;
    private final String pgProvider;
    private final String totalPrice;
    private final List<PurchaseItemDto> purchaseItemDtos;

    public PaymentSession toPaymentSession(){
        final PaymentInfo paymentInfo = PaymentInfo.builder()
                .buyerName(this.buyerName)
                .buyerPhoneNumber(this.buyerPhoneNumber)
                .buyerEmail(this.buyerEmail)
                .buyerAddress(this.buyerAddress)
                .buyerPostcode(this.buyerPostcode)
                .payMethod(PayMethod.from(this.payMethod))
                .pgProvider(PgProvider.from(this.pgProvider))
                .build();
        final Price totalPrice = Price.from(this.totalPrice);
        final List<PurchaseItem> purchaseItems = purchaseItemDtos.stream().map(purchaseItemDto -> {
            final PaymentItemInfo paymentItemInfo = PaymentItemInfo.builder()
                    .productId(purchaseItemDto.getProductId())
                    .productName(purchaseItemDto.getProductName())
                    .quantity(Quantity.from(purchaseItemDto.getQuantity()))
                    .price(Price.from(purchaseItemDto.getPrice()))
                    .discountPrice(Price.from(purchaseItemDto.getDiscountPrice()))
                    .couponDiscountPercent(purchaseItemDto.getCouponDiscountPercent())
                    .couponId(purchaseItemDto.getCouponId())
                    .userCouponId(purchaseItemDto.getUserCouponId())
                    .build();
            return PurchaseItem.of(purchaseItemDto.getOrderItemId(),paymentItemInfo);
        }).toList();
        final PaymentKey paymentKey = PaymentKey.from(this.paymentKey);

        return PaymentSession.builder()
                .paymentKey(paymentKey)
                .memberId(this.memberId)
                .orderId(this.orderId)
                .paymentInfo(paymentInfo)
                .totalPrice(totalPrice)
                .purchaseItems(purchaseItems)
                .build();
    }
}
