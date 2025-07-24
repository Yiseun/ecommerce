package com.ecommerce.product;

import com.ecommerce.product.dto.ReadSpecificProductRequest;
import com.ecommerce.product.dto.ReadSpecificProductResponse;
import com.ecommerce.product.dto.SearchProductRequest;
import com.ecommerce.product.dto.SearchProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ElasticService elasticService;

    @GetMapping("/search")
    public ResponseEntity<SearchProductResponse> searchProducts(final SearchProductRequest request){
        final SearchProductResponse response = elasticService.searchProductsByKeywordWithSpecificOptions(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ReadSpecificProductResponse> findTargetProduct(final ReadSpecificProductRequest request){
        final ReadSpecificProductResponse response = productService.readSpecificProduct(request);
        return ResponseEntity.ok(response);
    }
}
