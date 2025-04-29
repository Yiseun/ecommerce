package com.ecommerce.payment;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.ecommerce.payment.domain.session.PaymentKey;
import com.ecommerce.payment.dto.FindPortoneResponse;
import com.ecommerce.payment.dto.internal.InternalPaymentPurchaseRequest;
import com.ecommerce.payment.dto.request.CreatePaymentSessionRequest;
import com.ecommerce.payment.dto.request.CreatePaymentSessionRequestBody;
import com.ecommerce.payment.dto.CreatePaymentSessionResponse;
import com.ecommerce.payment.dto.PurchaseItemDto;
import com.ecommerce.payment.exception.application.DuplicatedPurchaseException;
import com.ecommerce.payment.persistence.PaymentSessionRepository;
import com.ecommerce.payment.persistence.entity.PaymentSessionEntity;
import com.ecommerce.payment.persistence.entity.PurchaseItemEntity;
import com.ecommerce.payment.portone.PortoneClient;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.instantiator.Instantiator;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class PaymentServiceTests {
    @MockBean
    private PaymentKeyCreator paymentKeyCreator;
    @MockBean
    private PortoneClient portoneClient;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentSessionRepository paymentSessionRepository;

    @Test
    @Transactional
    void 하나의_주문에대해_paymentSessionKey_생성을_여러번시도하면_마지막정보만_저장된다(){
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final String givenOriginOrderId = "234324";
        final PurchaseItemDto firstPurchaseItemDto = fixtureMonkey.giveMeBuilder(PurchaseItemDto.class)
                .set(javaGetter(PurchaseItemDto::getPrice),"3000")
                .set(javaGetter(PurchaseItemDto::getDiscountPrice),"3434")
                .set(javaGetter(PurchaseItemDto::getQuantity),"10")
                .sample();
        final List<PurchaseItemDto> firstPurchaseItemDtos = List.of(firstPurchaseItemDto);
        final CreatePaymentSessionRequestBody firstRequestBody = fixtureMonkey.giveMeBuilder(CreatePaymentSessionRequestBody.class)
                .set(javaGetter(CreatePaymentSessionRequestBody::getOrderId),givenOriginOrderId)
                .set(javaGetter(CreatePaymentSessionRequestBody::getTotalPrice),"30")
                .set(javaGetter(CreatePaymentSessionRequestBody::getPayMethod),"CARD")
                .set(javaGetter(CreatePaymentSessionRequestBody::getPurchaseItemDtos),firstPurchaseItemDtos)
                .sample();
        final CreatePaymentSessionRequest firstRequest = fixtureMonkey.giveMeBuilder(CreatePaymentSessionRequest.class)
                .set(javaGetter(CreatePaymentSessionRequest::getBody),firstRequestBody)
                .sample();
        final PaymentKey firstPaymentKey = PaymentKey.from("firstRandomPaymentKey");
        when(paymentKeyCreator.createPaymentKey()).thenReturn(firstPaymentKey);
        final CreatePaymentSessionResponse firstResponse = paymentService.createPaymentSession(firstRequest);
        final PurchaseItemDto secondPurchaseItemDto = fixtureMonkey.giveMeBuilder(PurchaseItemDto.class)
                .set(javaGetter(PurchaseItemDto::getPrice),"3001")
                .set(javaGetter(PurchaseItemDto::getDiscountPrice),"31929")
                .set(javaGetter(PurchaseItemDto::getQuantity),"11")
                .sample();
        final List<PurchaseItemDto> secondPurchaseItemDtos = List.of(secondPurchaseItemDto);
        final CreatePaymentSessionRequestBody secondRequestBody = fixtureMonkey.giveMeBuilder(CreatePaymentSessionRequestBody.class)
                .set(javaGetter(CreatePaymentSessionRequestBody::getOrderId),givenOriginOrderId)
                .set(javaGetter(CreatePaymentSessionRequestBody::getTotalPrice),"31")
                .set(javaGetter(CreatePaymentSessionRequestBody::getPayMethod),"MOBILE")
                .set(javaGetter(CreatePaymentSessionRequestBody::getPurchaseItemDtos),secondPurchaseItemDtos)
                .sample();
        final CreatePaymentSessionRequest secondRequest = fixtureMonkey.giveMeBuilder(CreatePaymentSessionRequest.class)
                .set(javaGetter(CreatePaymentSessionRequest::getBody),secondRequestBody)
                .sample();
        final PaymentKey secondPaymentKey = PaymentKey.from("secondRandomPaymentKey");
        when(paymentKeyCreator.createPaymentKey()).thenReturn(secondPaymentKey);

        final CreatePaymentSessionResponse secondResponse = paymentService.createPaymentSession(secondRequest);

        assertThat(firstResponse.getOrderId()).isEqualTo(secondResponse.getOrderId());
        assertThat(firstResponse.getPaymentKey()).isNotEqualTo(secondResponse.getPaymentKey());
        final PaymentSessionEntity savedValue = paymentSessionRepository.findByOrderId(givenOriginOrderId).orElseThrow(()->new RuntimeException("테스트 실패"));
        assertThat(savedValue.getPaymentKey()).isNotEqualTo(firstResponse.getPaymentKey());
        assertThat(savedValue.getOrderId()).isEqualTo(givenOriginOrderId);
        assertThat(savedValue.getPaymentKey()).isEqualTo(secondResponse.getPaymentKey());
        assertThat(savedValue.getMemberId()).isEqualTo(secondRequest.getMemberId());
        assertThat(savedValue.getBuyerName()).isEqualTo(secondRequestBody.getBuyerName());
        assertThat(savedValue.getBuyerPhoneNumber()).isEqualTo(secondRequestBody.getBuyerPhoneNumber());
        assertThat(savedValue.getBuyerEmail()).isEqualTo(secondRequestBody.getBuyerEmail());
        assertThat(savedValue.getBuyerAddress()).isEqualTo(secondRequestBody.getBuyerAddress());
        assertThat(savedValue.getBuyerPostcode()).isEqualTo(secondRequestBody.getBuyerPostcode());
        assertThat(savedValue.getTotalPrice()).isEqualTo(secondRequestBody.getTotalPrice());
        assertThat(savedValue.getPayMethod()).isEqualTo(secondRequestBody.getPayMethod());
        assertThat(savedValue.getPgProvider()).isEqualTo(secondResponse.getPgProvider());
    }

    @Transactional
    @Test
    void 이미_완료된_결제에대해_또_다시_완료를_시도한다면_일치여부와_무관하게_실패한다(){
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final PurchaseItemEntity purchaseItemEntity = fixtureMonkey.giveMeBuilder(PurchaseItemEntity.class)
                .instantiate(
                        Instantiator.constructor()
                                .parameter(Long.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                                .parameter(String.class)
                )
                .set(javaGetter(PurchaseItemEntity::getPurchaseItemId),null)
                .set(javaGetter(PurchaseItemEntity::getQuantity),"23213")
                .set(javaGetter(PurchaseItemEntity::getPrice),"123213")
                .set(javaGetter(PurchaseItemEntity::getDiscountPrice),"23123")
                .sample();
        final List<PurchaseItemEntity> purchaseItemEntities = List.of(purchaseItemEntity);
        final PaymentSessionEntity paymentSessionEntity = fixtureMonkey.giveMeBuilder(PaymentSessionEntity.class)
                .instantiate(Instantiator.constructor()
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(String.class)
                        .parameter(new TypeReference<List<PurchaseItemEntity>>() {
                        })
                )
                .set(javaGetter(PaymentSessionEntity::getOrderId),"34234")
                .set(javaGetter(PaymentSessionEntity::getPayMethod),"CARD")
                .set(javaGetter(PaymentSessionEntity::getPgProvider),"NAVER")
                .set(javaGetter(PaymentSessionEntity::getTotalPrice),"232")
                .set(javaGetter(PaymentSessionEntity::getPurchaseItemEntities),purchaseItemEntities)
                .sample();
        final PaymentSessionEntity resultPaymentSessionEntity = paymentSessionRepository.save(paymentSessionEntity);
        final PurchaseItemEntity resultPurchaeItemEntity = resultPaymentSessionEntity.getPurchaseItemEntities().get(0);
        final PurchaseItemDto purchaseItemDto = fixtureMonkey.giveMeBuilder(PurchaseItemDto.class)
                .set(javaGetter(PurchaseItemDto::getOrderItemId),resultPurchaeItemEntity.getOrderItemId())
                .set(javaGetter(PurchaseItemDto::getProductId),resultPurchaeItemEntity.getProductId())
                .set(javaGetter(PurchaseItemDto::getProductName),resultPurchaeItemEntity.getProductName())
                .set(javaGetter(PurchaseItemDto::getQuantity),resultPurchaeItemEntity.getQuantity())
                .set(javaGetter(PurchaseItemDto::getPrice),resultPurchaeItemEntity.getPrice())
                .set(javaGetter(PurchaseItemDto::getDiscountPrice),resultPurchaeItemEntity.getDiscountPrice())
                .set(javaGetter(PurchaseItemDto::getCouponDiscountPercent),resultPurchaeItemEntity.getCouponDiscountPercent())
                .set(javaGetter(PurchaseItemDto::getCouponId),resultPurchaeItemEntity.getCouponId())
                .set(javaGetter(PurchaseItemDto::getUserCouponId),resultPurchaeItemEntity.getUserCouponId())
                .sample();
        final List<PurchaseItemDto> purchaseItemDtos = List.of(purchaseItemDto);
        final InternalPaymentPurchaseRequest request = fixtureMonkey.giveMeBuilder(InternalPaymentPurchaseRequest.class)
                .set(javaGetter(InternalPaymentPurchaseRequest::getOrderId),resultPaymentSessionEntity.getOrderId())
                .set(javaGetter(InternalPaymentPurchaseRequest::getPaymentKey),resultPaymentSessionEntity.getPaymentKey())
                .set(javaGetter(InternalPaymentPurchaseRequest::getMemberId),resultPaymentSessionEntity.getMemberId())
                .set(javaGetter(InternalPaymentPurchaseRequest::getBuyerName),resultPaymentSessionEntity.getBuyerName())
                .set(javaGetter(InternalPaymentPurchaseRequest::getBuyerPhoneNumber),resultPaymentSessionEntity.getBuyerPhoneNumber())
                .set(javaGetter(InternalPaymentPurchaseRequest::getBuyerEmail),resultPaymentSessionEntity.getBuyerEmail())
                .set(javaGetter(InternalPaymentPurchaseRequest::getBuyerAddress),resultPaymentSessionEntity.getBuyerAddress())
                .set(javaGetter(InternalPaymentPurchaseRequest::getBuyerPostcode),resultPaymentSessionEntity.getBuyerPostcode())
                .set(javaGetter(InternalPaymentPurchaseRequest::getPayMethod),resultPaymentSessionEntity.getPayMethod())
                .set(javaGetter(InternalPaymentPurchaseRequest::getPgProvider),resultPaymentSessionEntity.getPgProvider())
                .set(javaGetter(InternalPaymentPurchaseRequest::getTotalPrice),resultPaymentSessionEntity.getTotalPrice())
                .set(javaGetter(InternalPaymentPurchaseRequest::getPurchaseItemDtos),purchaseItemDtos)
                .sample();
        final FindPortoneResponse portoneResponse = fixtureMonkey.giveMeBuilder(FindPortoneResponse.class)
                .set(javaGetter(FindPortoneResponse::getImpUid),request.getImpUid())
                .set(javaGetter(FindPortoneResponse::getOrderId),request.getOrderId())
                .set(javaGetter(FindPortoneResponse::getBuyerName),request.getBuyerName())
                .set(javaGetter(FindPortoneResponse::getBuyerPhoneNumber),request.getBuyerPhoneNumber())
                .set(javaGetter(FindPortoneResponse::getBuyerEmail),request.getBuyerEmail())
                .set(javaGetter(FindPortoneResponse::getBuyerAddress),request.getBuyerAddress())
                .set(javaGetter(FindPortoneResponse::getBuyerPostcode),request.getBuyerPostcode())
                .set(javaGetter(FindPortoneResponse::getPrice),Long.valueOf(request.getTotalPrice()))
                .set(javaGetter(FindPortoneResponse::getPgProvider),request.getPgProvider())
                .set(javaGetter(FindPortoneResponse::getPayMethod),request.getPayMethod())
                .sample();
        when(portoneClient.findBy(request)).thenReturn(portoneResponse);
        paymentService.purchase(request);

        assertThatThrownBy(()->paymentService.purchase(request)).isInstanceOf(DuplicatedPurchaseException.class);
    }
}
