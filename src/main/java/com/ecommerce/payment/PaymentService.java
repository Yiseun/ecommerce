package com.ecommerce.payment;

import com.ecommerce.payment.domain.session.PaymentSession;
import com.ecommerce.payment.dto.CreatePaymentSessionRequest;
import com.ecommerce.payment.dto.CreatePaymentSessionResponse;
import com.ecommerce.payment.persistence.entity.PaymentSessionEntity;
import com.ecommerce.payment.persistence.PaymentSessionRepository;
import com.ecommerce.payment.portone.PortoneClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PortoneClient portoneClient;
    private final PaymentSessionRepository paymentSessionRepository;
    private final PaymentKeyCreator paymentKeyCreator;
    public CreatePaymentSessionResponse createPaymentSession(final CreatePaymentSessionRequest request){
        final PaymentSession requestPaymentSession = request.toPaymentSession(paymentKeyCreator);
        final PaymentSessionEntity requestPaymentSessionEntity = PaymentSessionEntity.from(requestPaymentSession);
        final PaymentSessionEntity serverPaymentSessionEntity = paymentSessionRepository.save(requestPaymentSessionEntity);
        portoneClient.prepare(request);
        final PaymentSession serverPaymentSession = serverPaymentSessionEntity.toPaymentSession();
        return CreatePaymentSessionResponse.from(serverPaymentSession);
    }
}
