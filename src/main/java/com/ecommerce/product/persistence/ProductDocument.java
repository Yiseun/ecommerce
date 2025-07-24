package com.ecommerce.product.persistence;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDocument {
    @Id
    private final String productId;
    private final String productName;
    private final String brand;
    private final String category;
    private final String price;

    public Product toProduct(){
        final ProductInfo productInfo = ProductInfo.of(this.productId,this.productName,this.price);
        return Product.from(productInfo);
    }
}
