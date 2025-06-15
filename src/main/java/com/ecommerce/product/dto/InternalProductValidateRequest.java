package com.ecommerce.product.dto;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalProductValidateRequest {
    private final List<ProductDto> productDtos;
    public NonDuplicatedList<Product> toProductList(){
        final List<Product> productList = this.productDtos.stream()
                .map(request->{
                    final ProductInfo productInfo = ProductInfo.of(request.getProductId(),
                                                                   request.getProductName(),
                                                                   request.getPrice());
                    return Product.of(productInfo, Quantity.from(request.getQuantity()));
                })
                .toList();
        return new NonDuplicatedList<>(productList);
    }

    public static InternalProductValidateRequest from(final List<ProductDto> productDtos){
        return new InternalProductValidateRequest(productDtos);
    }
}
