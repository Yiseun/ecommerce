package com.ecommerce.product.dto;

import com.ecommerce.product.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReadSpecificProductResponse {
    private final List<ProductDto> productDtos;

    public List<Product> toProducts(){
        return productDtos.stream().map(i->i.toProduct()).toList();
    }

    public static ReadSpecificProductResponse from(final List<Product> products){
        final List<ProductDto> productDtos = products.stream().map(i->ProductDto.from(i)).toList();
        return new ReadSpecificProductResponse(productDtos);
    }
}
