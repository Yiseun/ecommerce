package com.ecommerce.order;

import com.ecommerce.grobal.session.MemberRequest;
import com.ecommerce.order.dto.request.*;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.dto.response.ReadOrderResponse;
import com.ecommerce.order.port.OrderClientRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderClientRegistry registry;
    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<CreateOrderIdResponse> createId(final MemberRequest memberRequest, @RequestBody final CreateOrderIdRequestBody body){
        final CreateOrderIdResponse response = orderService.createOrderId(CreateOrderIdRequest.of(memberRequest.getMemberId(),body,registry.getOrderCreateClient()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeOrder(final MemberRequest memberRequest, @RequestBody final CompleteOrderRequestBody body){
        orderService.createOrder(CompleteOrderRequest.of(memberRequest.getMemberId(), body,registry.getOrderCompleteClient()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(final MemberRequest memberRequest, @RequestBody final CancelOrderRequestBody body){
        orderService.updateOrder(CancelOrderRequest.of(memberRequest.getMemberId(), body,registry.getOrderCancelClient()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trackingInfo/register")
    public ResponseEntity<Void> registerTrackingInfo(final MemberRequest memberRequest, @RequestBody final RegisterTrackingInfoRequestBody body){
        orderService.updateOrder(RegisterTrackingInfoRequest.of(memberRequest.getMemberId(), body,registry.getNoOperationClient()));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ReadOrderResponse> readOrder(final MemberRequest memberRequest,
                                                       @RequestParam(required = false) final String orderId,
                                                       @RequestParam(required = false) final String startDate,
                                                       @RequestParam(required = false) final String endDate,
                                                       @RequestParam(required = false) final String size){
        final ReadOrderRequest request = ReadOrderRequest.builder()
                .memberId(memberRequest.getMemberId())
                .orderId(orderId)
                .startDate(startDate)
                .endDate(endDate)
                .size(size)
                .build();
        final ReadOrderResponse response = orderService.findOrder(request);
        return ResponseEntity.ok(response);
    }
}
