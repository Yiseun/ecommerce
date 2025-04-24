package com.ecommerce.order;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.*;
import com.ecommerce.order.domain.orderitem.OrderItemState;
import com.ecommerce.order.dto.CreateOrderIdRequest;
import com.ecommerce.order.dto.CreateOrderIdRequestBody;
import com.ecommerce.order.dto.CreateOrderIdResponse;
import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.pesistence.TmpOrderRepository;
import com.ecommerce.order.pesistence.entity.TmpOrderEntity;
import com.ecommerce.order.port.OrderClient;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
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
        assertThat(result.getOrderId()).isEqualTo(tmpOrderEntity.getOrderId().toString());
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
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getOrderItemState()).isEqualTo(OrderItemState.init().toString());
        assertThat(tmpOrderEntity.getTmpOrderItemEntityList().get(0).getTrackingInfo()).isEqualTo(null);
    }

}
