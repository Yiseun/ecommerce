package com.ecommerce.order;

import com.ecommerce.order.dto.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReceiver {
    private final OrderService orderService;
    public void complete(final CreateInitOrderRequest originRequest){
        final CompleteOrderRequest request = CompleteOrderRequest.builder()
                .orderItemDtos(originRequest.getBody().getOrderItemDtos())
                .orderId(originRequest.getBody().getOrderId())
                .memberId(originRequest.getMemberId())
                .impUid(originRequest.getBody().getImpUid())
                .paymentKey(originRequest.getBody().getPaymentKey())
                .payMethod(originRequest.getBody().getPayMethod())
                .pgProvider(originRequest.getBody().getPgProvider())
                .totalPrice(originRequest.getBody().getTotalPrice())
                .buyerName(originRequest.getBody().getBuyerName())
                .buyerPhoneNumber(originRequest.getBody().getBuyerPhoneNumber())
                .buyerEmail(originRequest.getBody().getBuyerEmail())
                .buyerAddress(originRequest.getBody().getBuyerAddress())
                .buyerPostcode(originRequest.getBody().getBuyerPostcode())
                .build();
        orderService.updateOrder(request);
    }

    public void cancel(final CreateInitOrderRequest originRequest){
        final CancelOrderRequestBody body = CancelOrderRequestBody.of(originRequest.getBody().getOrderItemDtos(),
                originRequest.getBody().getOrderId(),
                originRequest.getBody().getTotalPrice());
        final CancelOrderRequest request = CancelOrderRequest.of(originRequest.getMemberId(),body);
        orderService.updateOrder(request);
    }

}
