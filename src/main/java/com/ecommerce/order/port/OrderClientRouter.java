package com.ecommerce.order.port;

import com.ecommerce.order.dto.request.*;
import com.ecommerce.order.exception.application.InvalidConstructionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClientRouter {

    private final OrderCheckClient orderCheckClient;
    private final OrderInitCreateClient orderInitCreateClient;
    private final OrderCompleteClient orderCompleteClient;
    private final OrderCancelClient orderCancelClient;


    public void createOrderId(final CreateOrderIdRequest request){
        orderCheckClient.sendMessage(request);
    }

    public void createInitOrder(final CreateInitOrderRequest request){
        orderInitCreateClient.sendMessage(request);
    }



    public void updateOrder(final UpdateOrderRequest rawRequest){
        if(rawRequest instanceof CompleteOrderRequest request){
            orderCompleteClient.sendMessage(request);
            return;
        }
        if(rawRequest instanceof RegisterTrackingInfoRequest request){
            return;
        }
        if(rawRequest instanceof CancelOrderRequest request){
            orderCancelClient.sendMessage(request);
            return;
        }
        throw new InvalidConstructionException("적절한 client를 찾을수 없습니다.");
    }
}
