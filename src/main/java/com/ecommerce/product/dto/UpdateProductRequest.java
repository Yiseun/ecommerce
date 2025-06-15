package com.ecommerce.product.dto;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InternalProductPurchaseRequest implements UpdateProductRequest{
    final List<ProductDto> productDtos;

    public NonDuplicatedList<Product> toProductList(){
        final List<Product> productList = this.productDtos.stream().map(productDto -> {
            final ProductInfo productInfo = ProductInfo.of(productDto.getProductId(),productDto.getProductName(),productDto.getPrice());
            return Product.of(productInfo,productDto.getQuantity());
        }).toList();
        return new NonDuplicatedList<>(productList);
    }

    public static InternalProductPurchaseRequest from(final List<ProductDto> productDtos){
        return new InternalProductPurchaseRequest(productDtos);
    }
}
