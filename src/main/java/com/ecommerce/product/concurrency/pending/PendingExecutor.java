package com.ecommerce.product.concurrency.pending;

import com.ecommerce.order.OrderReceiver;
import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.dto.request.InternalOrderUpdateRequest;
import com.ecommerce.product.concurrency.pending.domain.PendingTask;
import com.ecommerce.product.concurrency.pending.dto.PendingTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PendingExecutor {

    private final PendingService pendingService;
    private final OrderReceiver orderReceiver;
    @KafkaListener(topics = "pendingTask", containerFactory = "kafkaListenerContainerFactory")
    public void consume(final List<PendingTaskRequest> requests){
        final List<PendingTask> exactlyOnceTasks = pendingService.updatePendingTask(requests);
        final List<PendingTask> failedPendingTask = pendingService.updateProductWithPendingTask(exactlyOnceTasks);
        final List<OrderDto> requestOrders = failedPendingTask.stream()
                .map(pendingTask -> {
                    final List<OrderItemDto> orderItemDtos = pendingTask.getTaskItems().stream().map(taskItem -> OrderItemDto.builder().orderItemId(taskItem.getOrderItemId()).build()).toList();
                    return OrderDto.builder()
                            .orderId(pendingTask.getOrderId())
                            .memberId(pendingTask.getMemberId())
                            .orderItemDtos(orderItemDtos)
                            .build();
                }).toList();
        final InternalOrderUpdateRequest request = InternalOrderUpdateRequest.from(requestOrders);
        orderReceiver.update(request);
    }
}
