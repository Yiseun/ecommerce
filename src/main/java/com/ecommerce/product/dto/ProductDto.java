package com.ecommerce.product.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDto {
    private final String productId;
    private final String productName;
    private final String quantity;
    private final String price;
}
