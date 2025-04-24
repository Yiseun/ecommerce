package com.ecommerce.product.domain;

import com.ecommerce.product.exception.domain.BusinessLogicException;
import com.ecommerce.product.exception.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Product {
    @EqualsAndHashCode.Include
    private final ProductInfo productInfo;
    private final Long quantity;

    private Product(final ProductInfo productInfo,final String quantity){
        this.productInfo = productInfo;
        this.quantity = validateQuantity(quantity);
    }

    private Long validateQuantity(final String quantity){
        try {
            return Long.valueOf(quantity);
        }catch (NumberFormatException e){
            throw new FailedCreationException("수량형식이 잘못됐습니다.");
        }
    }

    public void validate(final Product requestProduct){
        if(this.quantity<requestProduct.quantity){
            throw new BusinessLogicException("재고 수량이 부족합니다");
        }
    }

    public static Product of(final ProductInfo productInfo,final String quantity){
        return new Product(productInfo,quantity);
    }
}
