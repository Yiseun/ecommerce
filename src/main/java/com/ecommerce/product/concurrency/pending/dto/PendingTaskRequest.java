package com.ecommerce.product.concurrency.pending.dto;

import com.ecommerce.product.concurrency.pending.domain.PendingTask;
import com.ecommerce.product.concurrency.pending.domain.TaskItem;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PendingTaskRequest {
    private final String orderId;
    private final String memberId;
    private final List<TaskItemRequest> taskItemRequests;

    public PendingTask toPendingTask(){
        final List<TaskItem> taskItems = taskItemRequests.stream()
                .map(taskItemRequest -> {
                    final ProductInfo productInfo = ProductInfo.of(taskItemRequest.getProductId(),taskItemRequest.getProductName(),taskItemRequest.getPrice());
                    final Product product = Product.of(productInfo, Quantity.from(taskItemRequest.getQuantity()));
                    return TaskItem.init(taskItemRequest.getOrderItemId(),product);
                }).toList();
        return PendingTask.init(this.orderId,this.memberId,taskItems);
    }

    public static PendingTaskRequest of(final String orderId,final String memberId,final List<TaskItemRequest> taskItem){
        return new PendingTaskRequest(orderId,memberId,taskItem);
    }
}
