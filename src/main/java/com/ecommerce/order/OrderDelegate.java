package com.ecommerce.order;

import com.ecommerce.order.dto.request.CreateInitOrderRequest;
import com.ecommerce.order.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDelegate {
    private final OrderReceiver orderReceiver;

    @KafkaListener(topics = "order", containerFactory = "kafkaListenerContainerFactory")
    public void consume(final List<CreateInitOrderRequest> requests){
        for(CreateInitOrderRequest request : requests){
            try {
                orderReceiver.complete(request);
            } catch (OrderException e){
                orderReceiver.cancel(request);
            }
        }
        
    }
}
