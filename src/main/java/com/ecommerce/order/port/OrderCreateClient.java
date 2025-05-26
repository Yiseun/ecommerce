package com.ecommerce.order.port;

import com.ecommerce.coupon.CouponReceiver;
import com.ecommerce.coupon.dto.CouponRequest;
import com.ecommerce.coupon.dto.InternalCouponUseRequest;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.request.CompleteOrderRequest;
import com.ecommerce.payment.PaymentReceiver;
import com.ecommerce.payment.dto.PurchaseItemDto;
import com.ecommerce.payment.dto.internal.InternalPaymentPurchaseRequest;
import com.ecommerce.product.ProductReceiver;
import com.ecommerce.product.dto.InternalProductPurchaseRequest;
import com.ecommerce.product.dto.ProductDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateClient {
    private final PaymentReceiver paymentReceiver;
    private final ProductReceiver productReceiver;
    private final CouponReceiver couponReceiver;

    public void sendMessage(final CompleteOrderRequest request, final Order order){
        final List<PurchaseItemDto> purchaseItemDtos = request.getBody().getOrderItemDtos().stream().map(orderItemDto ->
                PurchaseItemDto.builder()
                        .productId(orderItemDto.getProductId())
                        .productName(orderItemDto.getProductName())
                        .quantity(orderItemDto.getQuantity())
                        .price(orderItemDto.getPrice())
                        .discountPrice(orderItemDto.getDiscountPrice())
                        .couponDiscountPercent(orderItemDto.getCouponDiscountPercent())
                        .couponId(orderItemDto.getCouponId())
                        .userCouponId(orderItemDto.getUserCouponId())
                        .build()).toList();
        final InternalPaymentPurchaseRequest paymentPurchaseRequest = InternalPaymentPurchaseRequest.builder()
                .orderId(request.getBody().getOrderId())
                .impUid(request.getBody().getImpUid())
                .paymentKey(request.getBody().getPaymentKey())
                .memberId(request.getMemberId())
                .buyerName(request.getBody().getBuyerName())
                .buyerPhoneNumber(request.getBody().getBuyerPhoneNumber())
                .buyerEmail(request.getBody().getBuyerEmail())
                .buyerAddress(request.getBody().getBuyerAddress())
                .buyerPostcode(request.getBody().getBuyerPostcode())
                .payMethod(request.getBody().getPayMethod())
                .pgProvider(request.getBody().getPgProvider())
                .totalPrice(request.getBody().getTotalPrice())
                .purchaseItemDtos(purchaseItemDtos)
                .build();
        paymentReceiver.purchase(paymentPurchaseRequest);

        final List<ProductDto> productDtos = request.getBody().getOrderItemDtos().stream().map(i->
                ProductDto.builder()
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .price(i.getPrice())
                        .quantity(i.getQuantity())
                        .build()).toList();
        final InternalProductPurchaseRequest productPurchaseRequest = InternalProductPurchaseRequest.from(productDtos);
        productReceiver.purchase(productPurchaseRequest);

        final List<CouponRequest> couponRequests = request.getBody().getOrderItemDtos().stream().map(i->
                CouponRequest.builder()
                        .userCouponId(i.getUserCouponId())
                        .couponId(i.getCouponId())
                        .productId(i.getProductId())
                        .originPrice(i.getPrice())
                        .discountedPrice(i.getDiscountPrice())
                        .couponDiscountPercent(i.getCouponDiscountPercent())
                        .build()).toList();
        couponReceiver.use(InternalCouponUseRequest.of(request.getMemberId(),couponRequests));
    }

    public static OrderCreateClient of(final PaymentReceiver paymentReceiver, final ProductReceiver productReceiver, final CouponReceiver couponReceiver){
        return new OrderCreateClient(paymentReceiver, productReceiver, couponReceiver);
    }
}
