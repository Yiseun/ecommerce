package com.ecommerce.payment;

import com.ecommerce.grobal.session.MemberRequest;
import com.ecommerce.payment.dto.request.CreatePaymentSessionRequest;
import com.ecommerce.payment.dto.request.CreatePaymentSessionRequestBody;
import com.ecommerce.payment.dto.CreatePaymentSessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/create")
    public ResponseEntity<CreatePaymentSessionResponse> createPaymentSession(final MemberRequest memberRequest, @RequestBody final CreatePaymentSessionRequestBody body){
        final CreatePaymentSessionResponse response = paymentService.createPaymentSession(CreatePaymentSessionRequest.of(memberRequest.getMemberId(),body));
        return ResponseEntity.ok(response);
    }
}
