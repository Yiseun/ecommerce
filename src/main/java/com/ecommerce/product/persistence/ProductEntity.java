package com.ecommerce.product.persistence;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


    public Product toProduct(){
        final ProductInfo productInfo = ProductInfo.of(productId.toString(),productName,price.toString());
        return Product.of(productInfo,quantity.toString());
    }

    public static ProductEntity from(final Product product){
        final Long productId = product.getProductInfo().getProductId();
        final String productName = product.getProductInfo().getProductName();
        final Long price = product.getProductInfo().getPrice();
        final Long quantity = product.getQuantity();
        return new ProductEntity(productId,productName,price,quantity);
    }
}
