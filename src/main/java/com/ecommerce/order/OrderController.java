package com.ecommerce.order;

import com.ecommerce.order.dto.CreateOrderIdRequest;
import com.ecommerce.order.dto.CreateOrderIdRequestBody;
import com.ecommerce.order.dto.CreateOrderIdResponse;
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
        final CreateOrderIdResponse response = orderService.createOrderId(CreateOrderIdRequest.of(memberId,body,registry.createOrderCreateClient()));
        return ResponseEntity.ok(response);
    }
}
