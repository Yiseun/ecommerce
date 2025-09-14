package com.ecommerce.order;

import com.ecommerce.grobal.session.MemberRequest;
import com.ecommerce.order.dto.request.*;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.dto.response.ReadOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/create")
    public ResponseEntity<CreateOrderIdResponse> createId(final MemberRequest memberRequest, @RequestBody final CreateOrderIdRequestBody body){
        final CreateOrderIdResponse response = orderService.createOrderId(CreateOrderIdRequest.of(memberRequest.getMemberId(),body));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/init")
    public ResponseEntity<Void> createInitOrder(final MemberRequest memberRequest, @RequestBody final CreateInitOrderRequestBody body){
        orderService.createInitOrder(CreateInitOrderRequest.of(memberRequest.getMemberId(), body));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(final MemberRequest memberRequest, @RequestBody final CancelOrderRequestBody body){
        orderService.updateOrder(CancelOrderRequest.of(memberRequest.getMemberId(), body));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trackingInfo/register")
    public ResponseEntity<Void> registerTrackingInfo(@RequestBody final RegisterTrackingInfoRequest request){
        orderService.updateOrder(request);
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
