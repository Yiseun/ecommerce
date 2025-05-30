package com.ecommerce.order.domain;

import com.ecommerce.order.domain.orderitem.OrderItem;
import com.ecommerce.order.domain.orderitem.Price;
import com.ecommerce.order.exception.application.domain.FailedCreationException;
import com.ecommerce.order.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {
    private static final int MAX_ORDERITEM_COUNT = 100;
    private static final Long MINIMUM_ORDER_PRICE = 0L;
    private final Long orderEntityId;
    @EqualsAndHashCode.Include
    private final OrderBase orderBase;
    private final OrderDetail orderDetail;
    private final Price originPrice;
    private final List<OrderItem> orderItems;
    private final Long version;
    private final LocalDate createdTime;

    private Order(final Long orderEntityId,final OrderBase orderBase,final OrderDetail orderDetail,final Price originPrice,final List<OrderItem> orderItems,final Long version,final LocalDate createdTime){
        this.orderEntityId = orderEntityId;
        this.orderBase = validate(orderBase);
        this.orderDetail = validate(orderDetail);
        this.originPrice = validate(originPrice);
        this.orderItems = validate(orderItems);
        this.version = version;
        this.createdTime = createdTime;
    }
    private OrderBase validate(final OrderBase orderBase){
        if(orderBase==null){
            throw new InvalidConstructionException("orderbase가 null일수 없습니다.");
        }
        return orderBase;
    }
    private OrderDetail validate(final OrderDetail orderDetail){
        if(orderDetail==null){
            throw new InvalidConstructionException("orderdetail이 null일수 없습니다");
        }
        return orderDetail;
    }
    private Price validate(final Price price){
        if(price==null){
            throw new InvalidConstructionException("price가 null일수 없습니다.");
        }
        return price;
    }

    private List<OrderItem> validate(final List<OrderItem> orderItems){
        if(orderItems==null){
            throw new InvalidConstructionException("orderItem이 null일수 없습니다.");
        }
        if(orderItems.size()>MAX_ORDERITEM_COUNT){
            throw new FailedCreationException("한 주문의 최대상품은 "+MAX_ORDERITEM_COUNT+"개까지 입니다.");
        }
        return orderItems;
    }

    public Order update(final Order request){
        if(!this.equals(request)){
            throw new InvalidConstructionException("주문정보가 일치하지 않습니다.");
        }
        if(this.orderEntityId==null||this.version==null||orderBase.isEmpty()){
            throw new InvalidConstructionException("한번도 저장되지않은 order는 변경할수 없습니다.");
        }
        final Map<OrderItem,OrderItem> requestOrderItemMap = request.orderItems.stream().collect(Collectors.toUnmodifiableMap(i->i,i->i));
        final List<OrderItem> resultOrderItems = this.orderItems.stream().map(serverOrderItem->{
            final OrderItem requestOrderItem = requestOrderItemMap.get(serverOrderItem);
            if(requestOrderItem==null){
                return serverOrderItem;
            }
            return serverOrderItem.update(requestOrderItem);
        }).toList();
        return new Order(this.orderEntityId,this.orderBase,this.orderDetail,this.originPrice,resultOrderItems,this.version,this.createdTime);
    }

    public static Order createRequestOrder(final OrderBase orderBase,final OrderDetail orderDetail,final Price price,final List<OrderItem> orderItems){
        return new Order(null,orderBase,orderDetail,price,orderItems,null,null);
    }
    public static Order of(final Long orderEntityId,final OrderBase orderBase,final OrderDetail orderDetail,final Price totalPrice,final List<OrderItem> orderItems,final Long version,final LocalDate createdTime){
        return new Order(orderEntityId,orderBase,orderDetail,totalPrice,orderItems,version,createdTime);
    }
}
