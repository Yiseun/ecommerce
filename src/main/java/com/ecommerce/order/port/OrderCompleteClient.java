package com.ecommerce.order.port;

import com.ecommerce.coupon.CouponReceiver;
import com.ecommerce.coupon.dto.CouponRequest;
import com.ecommerce.coupon.dto.InternalCouponUseRequest;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.request.CompleteOrderRequest;
import com.ecommerce.order.dto.request.CreateInitOrderRequest;
import com.ecommerce.payment.PaymentReceiver;
import com.ecommerce.payment.dto.PurchaseItemDto;
import com.ecommerce.payment.dto.internal.InternalPaymentPurchaseRequest;
import com.ecommerce.product.ProductReceiver;
import com.ecommerce.product.dto.UpdateItemRequest;
import com.ecommerce.product.dto.UpdateProductRequest;
import com.ecommerce.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCompleteClient {
    private final PaymentReceiver paymentReceiver;
    private final ProductReceiver productReceiver;
    private final CouponReceiver couponReceiver;

    public void sendMessage(final CompleteOrderRequest request){
        final List<PurchaseItemDto> purchaseItemDtos = request.getOrderItemDtos().stream().map(orderItemDto ->
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
                .orderId(request.getOrderId())
                .impUid(request.getImpUid())
                .paymentKey(request.getPaymentKey())
                .memberId(request.getMemberId())
                .buyerName(request.getBuyerName())
                .buyerPhoneNumber(request.getBuyerPhoneNumber())
                .buyerEmail(request.getBuyerEmail())
                .buyerAddress(request.getBuyerAddress())
                .buyerPostcode(request.getBuyerPostcode())
                .payMethod(request.getPayMethod())
                .pgProvider(request.getPgProvider())
                .totalPrice(request.getTotalPrice())
                .purchaseItemDtos(purchaseItemDtos)
                .build();
        paymentReceiver.purchase(paymentPurchaseRequest);

        final List<UpdateItemRequest> updateItemRequests = request.getOrderItemDtos().stream().map(orderItem -> {
            final ProductDto productDto = ProductDto.builder()
                    .productId(orderItem.getProductId())
                    .productName(orderItem.getProductName())
                    .quantity(orderItem.getQuantity())
                    .price(orderItem.getPrice())
                    .build();
            return UpdateItemRequest.of(orderItem.getOrderItemId(),productDto);
        }).toList();
        final UpdateProductRequest productPurchaseRequest = UpdateProductRequest.from(request.getOrderId(),request.getMemberId(),updateItemRequests);
        productReceiver.purchase(productPurchaseRequest);

        final List<CouponRequest> couponRequests = request.getOrderItemDtos().stream().map(i->
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

    public static OrderCompleteClient of(final PaymentReceiver paymentReceiver, final ProductReceiver productReceiver, final CouponReceiver couponReceiver){
        return new OrderCompleteClient(paymentReceiver, productReceiver, couponReceiver);
    }
}
