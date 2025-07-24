package com.ecommerce.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SearchProductRequest {
    private final String keyword;
    private final List<String> brands;
    private final List<String> colors;
    private final String category;
    private final List<String> materials;
}
