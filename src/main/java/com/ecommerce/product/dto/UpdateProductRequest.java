package com.ecommerce.product.dto;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductInfo;

import java.util.List;

public interface UpdateProductRequest {
    NonDuplicatedList<Product> toProductList();
}
