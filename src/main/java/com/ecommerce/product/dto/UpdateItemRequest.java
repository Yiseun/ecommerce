package com.ecommerce.product.dto;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateItemRequest {
    private final String orderItemId;
    private final ProductDto productDto;

    public Product toProduct(){
        final ProductInfo productInfo = ProductInfo.of(productDto.getProductId(), productDto.getProductName(), productDto.getPrice());
        return Product.of(productInfo, Quantity.from(productDto.getQuantity()));
    }

    public static UpdateItemRequest of(final String orderItemId,final ProductDto productDto){
        return new UpdateItemRequest(orderItemId, productDto);
    }
}
