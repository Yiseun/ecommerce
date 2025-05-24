package com.ecommerce.product.domain;

import com.ecommerce.product.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ProductInfo {
    private static final Long MINIMUM_PRODUCT_ID = 0L;
    private static final int MAX_PRODUCT_NAME_LENGTH = 30;
    private static final Long MINIMUM_PRICE = 0L;
    private final Long productId;
    private final String productName;
    private final Long price;

    private ProductInfo(final String productId,final String productName,final String price){
        this.productId = validateProductId(productId);
        this.productName = validateProductName(productName);
        this.price = validatePrice(price);
    }

    private Long validateProductId(final String productId){
        try{
            final Long longValue = Long.valueOf(productId);
            if(longValue<MINIMUM_PRODUCT_ID){
                throw new FailedCreationException("productId는 음수일수 없습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("productId의 형식이 잘못됐습니다.");
        }
    }

    private String validateProductName(final String productName){
        if(productName==null){
            throw new FailedCreationException("productName은 필수입력입니다.");
        }
        if(productName.length()>MAX_PRODUCT_NAME_LENGTH){
            throw new FailedCreationException("proudctName의 최대길이는 "+MAX_PRODUCT_NAME_LENGTH+"입니다.");
        }
        return productName;
    }

    private Long validatePrice(final String price){
        try {
            final Long longValue = Long.valueOf(price);
            if(longValue<MINIMUM_PRICE){
                throw new FailedCreationException("가격은 음수일수 없습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("가격의 형식이 잘못됐습니다");
        }
    }

    public static ProductInfo of(final String productId,final String productName,final String price){
        return new ProductInfo(productId, productName, price);
    }
}
