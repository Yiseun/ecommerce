package com.ecommerce.product.persistence;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long price;
    private Long quantity;
    @Version
    private Long version;


    public Product toProduct(){
        final ProductInfo productInfo = ProductInfo.of(productId.toString(),productName,price.toString());
        return Product.of(productInfo, Quantity.from(quantity.toString()),version);
    }

    public static ProductEntity from(final Product product){
        final Long productId = product.getProductInfo().getProductId();
        final String productName = product.getProductInfo().getProductName();
        final Long price = product.getProductInfo().getPrice();
        final Long quantity = product.getQuantity().getValue();
        final Long version = product.getVersion();
        return new ProductEntity(productId,productName,price,quantity,version);
    }
}
