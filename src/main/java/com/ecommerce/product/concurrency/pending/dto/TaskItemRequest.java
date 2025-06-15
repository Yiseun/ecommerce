package com.ecommerce.product.concurrency.pending.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskItemRequest {
    private final String orderItemId;
    private final String productId;
    private final String productName;
    private final String quantity;
    private final String price;
}
