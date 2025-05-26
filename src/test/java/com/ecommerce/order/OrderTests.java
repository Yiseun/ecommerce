package com.ecommerce.order;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderDetail;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.exception.application.domain.InvalidConstructionException;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderTests {

    @Test
    void 변경하지않은_orderItem은_기존의값을_유지한다(){
        final OrderItemInfo notModifiedOrderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from("111111")).build();
        final OrderItemInfo modifiedOrderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from("222222")).build();
        final OrderItemState notModifiedOrderItemState = OrderItemState.ORDER_FINALIZED;
        final OrderItemState preModifiedOrderItemState = OrderItemState.ORDER_COMPLETE;
        final OrderItemState afterModifiedOrderItemState = OrderItemState.ON_DELIVERY;
        final TrackingInfo trackingInfo = TrackingInfo.createEmpty();
        final OrderItem notModifiedOrderItem = OrderItem.of(notModifiedOrderItemInfo,notModifiedOrderItemState,trackingInfo);
        final OrderItem preModifiedOrderItem = OrderItem.of(modifiedOrderItemInfo,preModifiedOrderItemState,trackingInfo);
        final OrderItem afterModifiedOrderItem = OrderItem.of(modifiedOrderItemInfo,afterModifiedOrderItemState,trackingInfo);
        final List<OrderItem> preOrderItems = List.of(notModifiedOrderItem,preModifiedOrderItem);
        final List<OrderItem> requestOrderItems = List.of(notModifiedOrderItem,afterModifiedOrderItem);
        final OrderBase orderBase = OrderBase.of(OrderId.from("123123"),"dfgdfg");
        final OrderDetail orderDetail = OrderDetail.builder()
                .buyerName("김정훈")
                .buyerPhoneNumber("010-0000-0000")
                .buyerEmail("ewfewf@fefef.efef")
                .buyerAddress("무슨시 무슨구 무슨동 무슨아파트 201동 1802호")
                .buyerPostcode("00000")
                .build();
        final Price price = Price.from("30000");
        final Order sut = Order.of(1L,orderBase,orderDetail,price,preOrderItems,1L);
        final Order request = Order.createRequestOrder(orderBase,orderDetail,price,requestOrderItems);
        final Order expect = Order.of(1L,orderBase,orderDetail,price,requestOrderItems,1L);

        final Order result = sut.update(request);

        assertThat(result.getOrderItems()).isEqualTo(expect.getOrderItems());
        assertThat(result.getOrderItems()).isNotSameAs(expect.getOrderItems());
    }

    @Test
    void 일치하지않는_주문정보를_수정할수없다(){
        final OrderItemInfo orderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from("23434")).build();
        final OrderItem orderItem = OrderItem.from(orderItemInfo);
        final List<OrderItem> orderItems = List.of(orderItem);
        final OrderBase orderBase = OrderBase.init("fdsfd");
        final OrderDetail orderDetail = OrderDetail.createEmpty();
        final Order sut = Order.of(1L,orderBase,orderDetail,Price.createEmpty(),orderItems,1L);
        final Order request = null;

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

}
