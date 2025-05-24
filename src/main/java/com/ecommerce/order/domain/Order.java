package com.ecommerce.order.domain;

import com.ecommerce.order.domain.orderitem.OrderItem;
import com.ecommerce.order.domain.orderitem.Price;
import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    private static final int MAX_ORDERITEM_COUNT = 100;
    private static final Long MINIMUM_ORDER_PRICE = 0L;
    @EqualsAndHashCode.Include
    private final OrderBase orderBase;
    private final OrderDetail orderDetail;
    private final Price originPrice;
    private final List<OrderItem> orderItems;
    private final Long version;

    private Order(final OrderBase orderBase,final OrderDetail orderDetail,final Price originPrice,final List<OrderItem> orderItems,final Long version){
        this.orderBase = orderBase;
        this.orderDetail = orderDetail;
        this.originPrice = originPrice;
        this.orderItems = validateOrderItems(orderItems);
        this.version = version;
    }

    private List<OrderItem> validateOrderItems(final List<OrderItem> orderItems){
        if(orderItems.size()>MAX_ORDERITEM_COUNT){
            throw new FailedCreationException("한 주문의 최대상품은 "+MAX_ORDERITEM_COUNT+"개까지 입니다.");
        }
        return orderItems;
    }

    public static Order of(final OrderBase orderBase,final OrderDetail orderDetail,final Price totalPrice,final List<OrderItem> orderItems,final Long version){
        return new Order(orderBase,orderDetail,totalPrice,orderItems,version);
    }
}
