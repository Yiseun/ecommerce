package com.ecommerce.product.dto;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UpdateProductRequest {
    private final String orderId;
    private final String memberId;
    final List<UpdateItemRequest> updateItemRequests;

    public NonDuplicatedList<Product> toProductList(){
        final List<Product> products = this.updateItemRequests.stream().map(updateItemRequest -> {
            final ProductInfo productInfo = ProductInfo.of(updateItemRequest.getProductDto().getProductId(),
                    updateItemRequest.getProductDto().getProductName(),
                    updateItemRequest.getProductDto().getPrice());
            final Quantity quantity = Quantity.from(updateItemRequest.getProductDto().getQuantity());
            return Product.of(productInfo,quantity);
        }).toList();
        return new NonDuplicatedList<>(products);
    }

    public static UpdateProductRequest from(final String orderId,final String memberId,final List<UpdateItemRequest> productDtos){
        return new UpdateProductRequest(orderId,memberId,productDtos);
    }
}
