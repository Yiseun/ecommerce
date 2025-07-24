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
    private final ProductExtraInfo productExtraInfo;
    private final Quantity quantity;
    private final Long version;

    private Product(final ProductInfo productInfo,final ProductExtraInfo productExtraInfo,final Quantity quantity,final Long version){
        this.productInfo = validate(productInfo);
        this.productExtraInfo = validate(productExtraInfo);
        this.quantity = validate(quantity);
        this.version = version;
    }
    private <T> T validate(T t){
        if(t==null){
            throw new InvalidConstructionException(t.getClass().getSimpleName()+"은(는) null일수 없습니다.");
        }
        return t;
    }

    public void validate(final Product requestProduct){
        this.quantity.validate(requestProduct.quantity);
    }

    public Product update(final Product requestProduct){
        if(!this.equals(requestProduct)){
            throw new EqualityException("다른상품의 변경을 시도하고 있습니다.");
        }
        final Quantity resultQuantity = this.quantity.update(requestProduct.quantity);
        return new Product(this.productInfo,this.productExtraInfo,resultQuantity,this.version);
    }
    public static Product from(final ProductInfo productInfo){
        return new Product(productInfo,ProductExtraInfo.createEmpty(),Quantity.createEmpty(),null);
    }

    public static Product of(final ProductInfo productInfo,final Quantity quantity){
        return new Product(productInfo,ProductExtraInfo.createEmpty(),quantity,null);
    }

    public static Product of(final ProductInfo productInfo,final ProductExtraInfo productExtraInfo,final Quantity quantity){
        return new Product(productInfo,productExtraInfo,quantity,null);
    }

    public static Product of(final ProductInfo productInfo,final ProductExtraInfo productExtraInfo,final Quantity quantity,final Long version){
        return new Product(productInfo, productExtraInfo, quantity, version);
    }
}
