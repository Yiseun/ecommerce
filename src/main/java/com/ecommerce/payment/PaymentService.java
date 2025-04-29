package com.ecommerce.payment;

import com.ecommerce.payment.domain.Payment;
import com.ecommerce.payment.domain.session.PaymentSession;
import com.ecommerce.payment.dto.FindPortoneResponse;
import com.ecommerce.payment.dto.request.CreatePaymentSessionRequest;
import com.ecommerce.payment.dto.CreatePaymentSessionResponse;
import com.ecommerce.payment.dto.internal.InternalPaymentPurchaseRequest;
import com.ecommerce.payment.exception.application.DuplicatedPurchaseException;
import com.ecommerce.payment.exception.application.PaymentSessionNotFoundException;
import com.ecommerce.payment.persistence.PaymentRepository;
import com.ecommerce.payment.persistence.entity.PaymentEntity;
import com.ecommerce.payment.persistence.entity.PaymentSessionEntity;
import com.ecommerce.payment.persistence.PaymentSessionRepository;
import com.ecommerce.payment.portone.PortoneClient;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PortoneClient portoneClient;
    private final PaymentSessionRepository paymentSessionRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentKeyCreator paymentKeyCreator;
    @Transactional
    public CreatePaymentSessionResponse createPaymentSession(final CreatePaymentSessionRequest request){
        final PaymentSession requestPaymentSession = request.toPaymentSession(paymentKeyCreator);
        final PaymentSessionEntity requestPaymentSessionEntity = PaymentSessionEntity.from(requestPaymentSession);
        final PaymentSessionEntity serverPaymentSessionEntity = paymentSessionRepository.save(requestPaymentSessionEntity);
        portoneClient.prepare(request);
        final PaymentSession serverPaymentSession = serverPaymentSessionEntity.toPaymentSession();
        return CreatePaymentSessionResponse.from(serverPaymentSession);
    }
    @Transactional
    public void purchase(final InternalPaymentPurchaseRequest request){
        final PaymentSession requestPaymentSession = request.toPaymentSession();
        final PaymentSessionEntity requestPaymentSessionEntity = PaymentSessionEntity.from(requestPaymentSession);
        final PaymentSessionEntity serverPaymentSessionEntity = paymentSessionRepository.findByOrderId(requestPaymentSessionEntity.getOrderId()).orElseThrow(()->new PaymentSessionNotFoundException("일치하는 결제정보가 존재하지않습니다."));
        final PaymentSession serverPaymentSession = serverPaymentSessionEntity.toPaymentSession();
        final FindPortoneResponse portoneResponse = portoneClient.findBy(request);
        final Payment requestPayment = portoneResponse.createWith(serverPaymentSession);
        final PaymentEntity requestPaymentEntity = PaymentEntity.from(requestPayment);
        try {
            paymentRepository.save(requestPaymentEntity);
        }catch (DataIntegrityViolationException e){
            throw new DuplicatedPurchaseException("이미 결제된 요청입니다.");
        }
    }
}
