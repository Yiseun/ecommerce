package com.ecommerce.order.dto.response;

import com.ecommerce.order.domain.TmpOrder;
import com.ecommerce.order.dto.OrderItemDto;
import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateOrderIdResponse {
    private final String orderId;

    public static CreateOrderIdResponse from(final TmpOrder tmpOrder){
        final String orderId = tmpOrder.getOrderId().getValue().toString();
        return new CreateOrderIdResponse(orderId);
    }
}
