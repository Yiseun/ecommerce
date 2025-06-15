package com.ecommerce.product.domain;

import com.ecommerce.payment.exception.application.domain.EqualityException;
import com.ecommerce.product.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Product {
    @EqualsAndHashCode.Include
    private final ProductInfo productInfo;
    private final Quantity quantity;

    private Product(final ProductInfo productInfo,final Quantity quantity){
        this.productInfo = validate(productInfo);
        this.quantity = validate(quantity);
    }

    private ProductInfo validate(final ProductInfo productInfo){
        if(productInfo==null){
            throw new InvalidConstructionException("productInfo는 null일수 없습니다.");
        }
        return productInfo;
    }
    private Quantity validate(final Quantity quantity){
        if(quantity==null){
            throw new InvalidConstructionException("quantity는 null일수 없습니다.");
        }
        return quantity;
    }

    public void validate(final Product requestProduct){
        this.quantity.validate(requestProduct.quantity);
    }

    public Product update(final Product requestProduct){
        if(!this.equals(requestProduct)){
            throw new EqualityException("다른상품의 변경을 시도하고 있습니다.");
        }
        final Quantity resultQuantity = this.quantity.update(requestProduct.quantity);
        return new Product(this.productInfo,resultQuantity);
    }
    public static Product from(final ProductInfo productInfo){
        return new Product(productInfo,Quantity.createEmpty());
    }

    public static Product of(final ProductInfo productInfo,final Quantity quantity){
        return new Product(productInfo,quantity);
    }
}
