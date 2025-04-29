package com.ecommerce.product.domain;

import com.ecommerce.payment.exception.domain.EqualityException;
import com.ecommerce.payment.exception.domain.UnderstockedException;
import com.ecommerce.product.exception.domain.BusinessLogicException;
import com.ecommerce.product.exception.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Product {
    private static final long MINIMUM_QUANTITY = 0L;
    @EqualsAndHashCode.Include
    private final ProductInfo productInfo;
    private final Long quantity;

    private Product(final ProductInfo productInfo,final Long quantity){
        this.productInfo = productInfo;
        this.quantity = quantity;
    }
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

    public Product update(final Product requestProduct){
        if(!this.equals(requestProduct)){
            throw new EqualityException("다른상품의 변경을 시도하고 있습니다.");
        }
        final long resultQuantity = this.quantity- requestProduct.quantity;
        if(resultQuantity<MINIMUM_QUANTITY){
            throw new UnderstockedException("재고수량이 부족합니다.");
        }
        return new Product(this.productInfo,resultQuantity);
    }

    public static Product of(final ProductInfo productInfo,final String quantity){
        return new Product(productInfo,quantity);
    }
}
