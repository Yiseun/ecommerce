package com.ecommerce.order;

import static org.assertj.core.api.Assertions.*;

import com.ecommerce.order.domain.orderitem.*;
import com.ecommerce.order.exception.application.domain.InvalidConstructionException;
import org.junit.jupiter.api.Test;

public class OrderItemTests {

    @Test
    void 주문상품을_수정해도_주문상품의_기본정보는_항상_기존의값을_유지한다(){
        final OrderItemInfo orderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from("21323123")).build();
        final OrderItemState orderItemState = OrderItemState.ORDER_COMPLETE;
        final OrderItemState theOtherOrderItemState = OrderItemState.CANCELED;
        final TrackingInfo trackingInfo = TrackingInfo.createEmpty();
        final OrderItem sut = OrderItem.of(orderItemInfo,orderItemState,trackingInfo);
        final OrderItem request = OrderItem.of(orderItemInfo,theOtherOrderItemState,trackingInfo);

        final OrderItem result = sut.update(request);

        assertThat(result).isEqualTo(sut);
        assertThat(result).isNotSameAs(sut);
        assertThat(result.getOrderItemInfo()).isSameAs(sut.getOrderItemInfo());
    }

    @Test
    void 일치하지않는_주문상품정보를_수정할수없다(){
        final OrderItemInfo orderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from("343242")).build();
        final OrderItem sut = OrderItem.from(orderItemInfo);
        final OrderItem request = null;

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 주문상품의상태가_운송장정보를_입력할수있을때만_운송장정보가_달라진다(){
        final OrderItemInfo orderItemInfo = OrderItemInfo.builder().orderItemId(OrderItemId.from("324324")).build();
        final OrderItemState orderItemState = OrderItemState.ORDER_COMPLETE;
        final TrackingInfo trackingInfo = TrackingInfo.createEmpty();
        final TrackingInfo theOtherTackingInfo = TrackingInfo.from("2324324");
        final OrderItem sut = OrderItem.of(orderItemInfo,orderItemState,trackingInfo);
        final OrderItem request = OrderItem.of(orderItemInfo,orderItemState,theOtherTackingInfo);

        final OrderItem result = sut.update(request);

        assertThat(orderItemState.isUpdatableStateForTrackingInfo(orderItemState)).isEqualTo(true);
        assertThat(result.getTrackingInfo()).isEqualTo(request.getTrackingInfo());
    }

}
