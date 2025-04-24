package com.ecommerce.product;

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
}
