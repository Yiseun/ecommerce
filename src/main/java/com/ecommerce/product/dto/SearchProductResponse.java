package com.ecommerce.product.dto;

import com.ecommerce.product.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SearchProductResponse {
    private final List<ProductDto> productDtos;

    public static SearchProductResponse from(final List<Product> products){
        final List<ProductDto> productDtos = products.stream().map(i->ProductDto.from(i)).toList();
        return new SearchProductResponse(productDtos);
    }
}
