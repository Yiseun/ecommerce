package com.ecommerce.order;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.*;
import com.ecommerce.order.dto.request.CompleteOrderRequest;
import com.ecommerce.order.dto.request.CompleteOrderRequestBody;
import com.ecommerce.order.dto.request.CreateOrderIdRequest;
import com.ecommerce.order.dto.request.CreateOrderIdRequestBody;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.persistence.TmpOrderRepository;
import com.ecommerce.order.persistence.entity.TmpOrderEntity;
import com.ecommerce.order.persistence.entity.TmpOrderItemEntity;
import com.ecommerce.order.port.OrderClient;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.instantiator.Instantiator;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.type.TypeReference;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
public class OrderServiceTests {
    @Mock
    private OrderClient orderClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private TmpOrderRepository tmpOrderRepository;

    @Transactional
    @Test
    void OrderId를_생성할수있다(){
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final OrderItemDto givenItem = fixtureMonkey.giveMeBuilder(OrderItemDto.class)
                .set(javaGetter(OrderItemDto::getQuantity),"10")
                .set(javaGetter(OrderItemDto::getPrice),"100")
                .set(javaGetter(OrderItemDto::getDiscountPrice),"10")
                .set(javaGetter(OrderItemDto::getOrderItemState),null)
                .set(javaGetter(OrderItemDto::getTrackingInfo),null)
                .sample();
        final List<OrderItemDto> givenItems = List.of(givenItem);
        final CreateOrderIdRequestBody givenBody = fixtureMonkey.giveMeBuilder(CreateOrderIdRequestBody.class)
                .set(javaGetter(CreateOrderIdRequestBody::getTotalPrice),"5000")
                .set(javaGetter(CreateOrderIdRequestBody::getOrderItemDtos),givenItems)
                .sample();
        final CreateOrderIdRequest givenRequest = fixtureMonkey.giveMeBuilder(CreateOrderIdRequest.class)
                .set(javaGetter(CreateOrderIdRequest::getClient),orderClient)
                .set(javaGetter(CreateOrderIdRequest::getBody),givenBody)
                .sample();

        final CreateOrderIdResponse result = orderService.createOrderId(givenRequest);

        final TmpOrderEntity tmpOrderEntity = tmpOrderRepository.findById(Long.valueOf(result.getOrderId())).orElseThrow(()->new RuntimeException("테스트실패"));
        assertThat(result).isEqualTo(CreateOrderIdResponse.from(tmpOrderEntity.toTmpOrder()));
        assertThat(tmpOrderEntity.getOrderId().toString()).isEqualTo(result.getOrderId());
        assertThat(tmpOrderEntity.getMemberId()).isEqualTo(givenRequest.getMemberId());
        assertThat(tmpOrderEntity.getTotalPrice()).isEqualTo(givenBody.getTotalPrice());
        assertThat(tmpOrderEntity.getBuyerName()).isEqualTo(givenBody.getBuyerName());
        assertThat(tmpOrderEntity.getBuyerPhoneNumber()).isEqualTo(givenBody.getBuyerPhoneNumber());
        assertThat(tmpOrderEntity.getBuyerEmail()).isEqualTo(givenBody.getBuyerEmail());
        assertThat(tmpOrderEntity.getBuyerAddress()).isEqualTo(givenBody.getBuyerAddress());
        assertThat(tmpOrderEntity.getBuyerPostcode()).isEqualTo(givenBody.getBuyerPostcode());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getProductId()).isEqualTo(givenItem.getProductId());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getProductName()).isEqualTo(givenItem.getProductName());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getQuantity()).isEqualTo(givenItem.getQuantity());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getPrice()).isEqualTo(givenItem.getPrice());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getDiscountPrice()).isEqualTo(givenItem.getDiscountPrice());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getCouponDiscountPercent()).isEqualTo(givenItem.getCouponDiscountPercent());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getCouponId()).isEqualTo(givenItem.getCouponId());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getUserCouponId()).isEqualTo(givenItem.getUserCouponId());
    }

    @Transactional
    @Test
    void 생성완료된_주문에대해_또_다시_생성을_시도한다면_일치여부와_무관하게_실패한다(){
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final TmpOrderItemEntity tmpOrderItemEntity = fixtureMonkey.giveMeBuilder(TmpOrderItemEntity.class)
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
                )
                .set(javaGetter(TmpOrderItemEntity::getOrderItemId),null)
                .set(javaGetter(TmpOrderItemEntity::getQuantity),"324")
                .set(javaGetter(TmpOrderItemEntity::getPrice),"343")
                .set(javaGetter(TmpOrderItemEntity::getDiscountPrice),"43")
                .sample();
        final List<TmpOrderItemEntity> tmpOrderItemEntityList = List.of(tmpOrderItemEntity);
        final TmpOrderEntity tmpOrderEntity = fixtureMonkey.giveMeBuilder(TmpOrderEntity.class)
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
                                .parameter(new TypeReference<List<TmpOrderItemEntity>>() {
                                })
                )
                .set(javaGetter(TmpOrderEntity::getOrderId),null)
                .set(javaGetter(TmpOrderEntity::getTmpOrderItemEntityList),tmpOrderItemEntityList)
                .set(javaGetter(TmpOrderEntity::getTotalPrice),"324")
                .sample();
        final TmpOrderEntity serverTmpOrderEntity = tmpOrderRepository.save(tmpOrderEntity);
        final List<OrderItemDto> orderItemDto = serverTmpOrderEntity.getTmpOrderItemEntityList().stream().map(i->
                OrderItemDto.builder()
                        .orderItemId(i.getOrderItemId().toString())
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .discountPrice(i.getDiscountPrice())
                        .couponDiscountPercent(i.getCouponDiscountPercent())
                        .couponId(i.getCouponId())
                        .userCouponId(i.getUserCouponId())
                        .build()
        ).toList();
        final CompleteOrderRequestBody body = fixtureMonkey.giveMeBuilder(CompleteOrderRequestBody.class)
                .set(javaGetter(CompleteOrderRequestBody::getOrderItemDtos),orderItemDto)
                .set(javaGetter(CompleteOrderRequestBody::getOrderId),serverTmpOrderEntity.getOrderId().toString())
                .set(javaGetter(CompleteOrderRequestBody::getTotalPrice),serverTmpOrderEntity.getTotalPrice())
                .set(javaGetter(CompleteOrderRequestBody::getBuyerName),serverTmpOrderEntity.getBuyerName())
                .set(javaGetter(CompleteOrderRequestBody::getBuyerPhoneNumber),serverTmpOrderEntity.getBuyerPhoneNumber())
                .set(javaGetter(CompleteOrderRequestBody::getBuyerEmail),serverTmpOrderEntity.getBuyerEmail())
                .set(javaGetter(CompleteOrderRequestBody::getBuyerAddress),serverTmpOrderEntity.getBuyerAddress())
                .set(javaGetter(CompleteOrderRequestBody::getBuyerPostcode),serverTmpOrderEntity.getBuyerPostcode())
                .sample();
        final CompleteOrderRequest request = fixtureMonkey.giveMeBuilder(CompleteOrderRequest.class)
                .set(javaGetter(CompleteOrderRequest::getBody),body)
                .set(javaGetter(CompleteOrderRequest::getClient),orderClient)
                .set(javaGetter(CompleteOrderRequest::getMemberId),serverTmpOrderEntity.getMemberId())
                .sample();
        orderService.createOrder(request);

        assertThatThrownBy(()->orderService.createOrder(request)).isInstanceOf(DuplicateRequestException.class);
    }

}
