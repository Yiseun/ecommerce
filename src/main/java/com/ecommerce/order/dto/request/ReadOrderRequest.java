package com.ecommerce.order.dto.request;

import com.ecommerce.order.domain.OrderBase;
import com.ecommerce.order.domain.OrderId;
import com.ecommerce.order.domain.constraint.Constraint;
import com.ecommerce.order.domain.constraint.DateRange;
import com.ecommerce.order.domain.constraint.OrderLocalDate;
import lombok.Builder;

@Builder
public class ReadOrderRequest {
    private final String memberId;
    private final String orderId;
    private final String startDate;
    private final String endDate;
    private final String size;

    public Constraint toConstraint(){
        final OrderId orderId = OrderId.from(this.orderId);
        final OrderBase orderBase = OrderBase.of(orderId,this.memberId);
        final OrderLocalDate startDate = OrderLocalDate.from(this.startDate);
        final OrderLocalDate endDate = OrderLocalDate.from(this.endDate);
        final DateRange range = DateRange.of(startDate,endDate);
        return Constraint.of(orderBase,range,this.size);
    }

}
