package com.ecommerce.order;

import com.ecommerce.order.dto.request.CancelOrderRequest;
import com.ecommerce.order.dto.request.CancelOrderRequestBody;
import com.ecommerce.order.dto.request.InternalOrderUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderReceiver {
    private final OrderService orderService;

    public void update(final InternalOrderUpdateRequest originRequests){
        final List<CancelOrderRequest> requests = originRequests.getOrderDtos().stream().map(originRequest->{
            final CancelOrderRequestBody body = CancelOrderRequestBody.of(originRequest.getOrderItemDtos(),originRequest.getOrderId(),originRequest.getTotalPrice());
            return CancelOrderRequest.of(originRequest.getMemberId(),body);
        }).toList();
        requests.forEach(request->orderService.updateOrder(request));
    }
}
