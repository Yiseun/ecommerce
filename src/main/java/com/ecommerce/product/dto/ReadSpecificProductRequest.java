package com.ecommerce.product.dto;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ReadSpecificProductRequest {
    private final List<ProductDto> productDtos;

    public List<Product> toProducts(){
        return productDtos.stream().map(i->{
            final ProductInfo productInfo = ProductInfo.of(i.getProductId(),i.getProductName(),i.getPrice());
            return Product.from(productInfo);
        }).toList();
    }

    public static ReadSpecificProductRequest from(final List<Product> products){
        final List<ProductDto> productDtos = products.stream().map(i->ProductDto.from(i)).toList();
        return new ReadSpecificProductRequest(productDtos);
    }
}
