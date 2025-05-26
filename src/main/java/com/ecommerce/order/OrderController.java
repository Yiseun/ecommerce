package com.ecommerce.order;

import com.ecommerce.order.dto.request.*;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.port.OrderClientRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderClientRegistry registry;
    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<CreateOrderIdResponse> createId(final String memberId, @RequestBody final CreateOrderIdRequestBody body){
        final CreateOrderIdResponse response = orderService.createOrderId(CreateOrderIdRequest.of(memberId,body,registry.getOrderCreateClient()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeOrder(final String memberId, @RequestBody CompleteOrderRequestBody body){
        orderService.createOrder(CompleteOrderRequest.of(memberId,body,registry.getOrderCompleteClient()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(final String memberId, @RequestBody CancelOrderRequestBody body){
        orderService.updateOrder(CancelOrderRequest.of(memberId,body,registry.getOrderCancelClient()));
        return ResponseEntity.ok().build();
    }

}
