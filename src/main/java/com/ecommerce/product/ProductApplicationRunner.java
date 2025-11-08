package com.ecommerce.product;

import com.ecommerce.product.exception.ProductConfigurationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ProductApplicationRunner implements ApplicationRunner {

    private final ProductService productService;
    @Override
    public void run(final ApplicationArguments arguments){
        try {
            productService.readAllOptions();
        }catch (final Exception e){
            throw new ProductConfigurationException("cache prewarming failed");
        }
    }
}
