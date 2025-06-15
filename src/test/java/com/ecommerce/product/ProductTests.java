package com.ecommerce.product;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import com.ecommerce.product.exception.application.domain.BusinessLogicException;
import org.junit.jupiter.api.Test;

public class ProductTests {

    @Test
    void requestProduct가_serverProduct에_유효하지않은_상품이라면_실패한다(){
        final ProductInfo commonProductInfo = ProductInfo.of("3241","hdsfdsf","234324");
        final Product requestProduct = Product.of(commonProductInfo, Quantity.from("100"));
        final Product serverProduct = Product.of(commonProductInfo,Quantity.from("5"));

        assertThatThrownBy(()->serverProduct.validate(requestProduct)).isInstanceOf(BusinessLogicException.class);
    }
}
