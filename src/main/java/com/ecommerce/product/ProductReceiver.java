package com.ecommerce.product;

import com.ecommerce.product.dto.UpdateProductRequest;
import com.ecommerce.product.dto.InternalProductValidateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReceiver {
    private final ProductService productService;
    public void validate(final InternalProductValidateRequest request){
        productService.validate(request);
    }

    public void purchase(final UpdateProductRequest request){
        productService.update(request);
    }
}
