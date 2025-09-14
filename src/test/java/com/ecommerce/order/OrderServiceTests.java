package com.ecommerce.order;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.*;

import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.orderitem.OrderItemState;
import com.ecommerce.order.dto.request.*;
import com.ecommerce.order.dto.response.CreateOrderIdResponse;
import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.persistence.OrderRepository;
import com.ecommerce.order.persistence.TmpOrderRepository;
import com.ecommerce.order.persistence.entity.OrderEntity;
import com.ecommerce.order.persistence.entity.OrderItemEntity;
import com.ecommerce.order.persistence.entity.TmpOrderEntity;
import com.ecommerce.order.persistence.entity.TmpOrderItemEntity;
import com.ecommerce.order.port.OrderClientRouter;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.sun.jdi.request.DuplicateRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class OrderServiceTests {
    @Autowired
    private OrderService sut;
    @Autowired
    private TmpOrderRepository tmpOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @MockBean
    private OrderClientRouter router;

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
        final String memberId = "dfdsf";
        final CreateOrderIdRequest request = CreateOrderIdRequest.of(memberId,givenBody);

        final CreateOrderIdResponse result = sut.createOrderId(request);

        final TmpOrderEntity tmpOrderEntity = tmpOrderRepository.findById(Long.valueOf(result.getOrderId())).orElseThrow(()->new RuntimeException("테스트실패"));
        assertThat(result).isEqualTo(CreateOrderIdResponse.from(tmpOrderEntity.toTmpOrder()));
        assertThat(tmpOrderEntity.getOrderId().toString()).isEqualTo(result.getOrderId());
        assertThat(tmpOrderEntity.getMemberId()).isEqualTo(request.getMemberId());
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
        final String productId = "11010";
        final String productName = "gdfgdfd";
        final String quantity = "23";
        final String price = "10000";
        final String discountPrice = "100";
        final String couponDiscountPercent = "1";
        final String couponId = "1232";
        final String userCouponId = "12312";
        final TmpOrderItemEntity tmpOrderItemEntity = TmpOrderItemEntity.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .price(price)
                .discountPrice(discountPrice)
                .couponDiscountPercent(couponDiscountPercent)
                .couponId(couponId)
                .userCouponId(userCouponId)
                .build();
        final List<TmpOrderItemEntity> tmpOrderItemEntityList = List.of(tmpOrderItemEntity);
        final String memberId = "dfgdfg";
        final String totalPrice = "34534";
        final String buyerName = "dfgfddsfdg";
        final String buyerPhoneNumber = "010-0000-0000";
        final String buyerEmail = "fdgdfg@fdf.esd";
        final String buyerAddress = "regregreg";
        final String buyerPostcode = "34566";
        final TmpOrderEntity tmpOrderEntity = TmpOrderEntity.builder()
                .memberId(memberId)
                .totalPrice(totalPrice)
                .buyerName(buyerName)
                .buyerPhoneNumber(buyerPhoneNumber)
                .buyerEmail(buyerEmail)
                .buyerAddress(buyerAddress)
                .buyerPostcode(buyerPostcode)
                .tmpOrderItemEntityList(tmpOrderItemEntityList)
                .build();
        final TmpOrderEntity serverTmpOrderEntity = tmpOrderRepository.save(tmpOrderEntity);
        final List<OrderItemDto> orderItemDto = serverTmpOrderEntity.getTmpOrderItemEntityList().stream().map(i->
                OrderItemDto.builder()
                        .orderItemId(null)
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
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final CreateInitOrderRequestBody body = fixtureMonkey.giveMeBuilder(CreateInitOrderRequestBody.class)
                .set(javaGetter(CreateInitOrderRequestBody::getOrderItemDtos),orderItemDto)
                .set(javaGetter(CreateInitOrderRequestBody::getOrderId),serverTmpOrderEntity.getOrderId().toString())
                .set(javaGetter(CreateInitOrderRequestBody::getTotalPrice),serverTmpOrderEntity.getTotalPrice())
                .set(javaGetter(CreateInitOrderRequestBody::getBuyerName),serverTmpOrderEntity.getBuyerName())
                .set(javaGetter(CreateInitOrderRequestBody::getBuyerPhoneNumber),serverTmpOrderEntity.getBuyerPhoneNumber())
                .set(javaGetter(CreateInitOrderRequestBody::getBuyerEmail),serverTmpOrderEntity.getBuyerEmail())
                .set(javaGetter(CreateInitOrderRequestBody::getBuyerAddress),serverTmpOrderEntity.getBuyerAddress())
                .set(javaGetter(CreateInitOrderRequestBody::getBuyerPostcode),serverTmpOrderEntity.getBuyerPostcode())
                .sample();
        final CreateInitOrderRequest request = CreateInitOrderRequest.of(serverTmpOrderEntity.getMemberId(),body);

        sut.createInitOrder(request);

        assertThatThrownBy(()->sut.createInitOrder(request)).isInstanceOf(DuplicateRequestException.class);
    }

    @Test
    @Transactional
    void 주문을_수정할수있다(){
        final String orderId = "324342234";
        final String memberId = "sdsdefds";
        final int orderItemIndex = 0;
        final String quantity = "22";
        final String price = "300";
        final String discountPrice = "30";
        final String totalPrice = "300";
        final OrderDetail savedOrderDetail =  OrderDetail.builder().buyerName("강하늘").buyerPhoneNumber("010-0000-0000").buyerEmail("ewrewr@efef.ef").buyerAddress("무슨시 무슨동 저기어딘가길 어떤아파트 502동 2201호").buyerPostcode("234").build();
        final OrderItemEntity orderItem = OrderItemEntity.builder().quantity(quantity).price(price).discountPrice(discountPrice).orderItemState(OrderItemState.ORDER_COMPLETE.name()).build();
        final List<OrderItemEntity> orderItemEntities = new ArrayList<>(Collections.singletonList(orderItem));
        final OrderEntity orderEntity = OrderEntity.builder().orderId(OrderId.from(orderId).getValue().toString()).totalPrice(totalPrice).memberId(memberId).buyerName(savedOrderDetail.getBuyerName()).buyerPhoneNumber(savedOrderDetail.getBuyerPhoneNumber()).buyerEmail(savedOrderDetail.getBuyerEmail()).buyerAddress(savedOrderDetail.getBuyerAddress()).buyerPostcode(savedOrderDetail.getBuyerPostcode()).orderItemEntityList(orderItemEntities).build();
        final OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        final OrderItemEntity savedOrderItemEntity = savedOrderEntity.getOrderItemEntityList().get(orderItemIndex);
        final OrderItemDto orderItemDto = OrderItemDto.builder().orderItemId(savedOrderItemEntity.getOrderItemId().toString()).quantity(quantity).price(price).discountPrice(discountPrice).build();
        final List<OrderItemDto> orderItemDtos = List.of(orderItemDto);
        final CancelOrderRequestBody body = new CancelOrderRequestBody(orderItemDtos,orderId,totalPrice);
        final CancelOrderRequest request = CancelOrderRequest.of(memberId,body);

        sut.updateOrder(request);

        final OrderEntity resultOrderEntity = orderRepository.findByOrderId(orderId).orElseThrow(()->new RuntimeException("테스트가 실패했습니다. 예상되는 결과를 찾을수 없습니다."));
        final OrderItemEntity resultOrderItemEntity = resultOrderEntity.getOrderItemEntityList().get(orderItemIndex);
        assertThat(OrderItemState.CANCELED).isEqualTo(OrderItemState.from(resultOrderItemEntity.getOrderItemState()));
    }

}
