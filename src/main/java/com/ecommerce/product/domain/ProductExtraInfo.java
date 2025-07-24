package com.ecommerce.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductExtraInfo {
    private final String categoryId;
    private final String brandId;
    private final String colorId;
    private final String materialId;

    public static ProductExtraInfo createEmpty(){
        return new ProductExtraInfo(null,null,null,null);
    }

    public static ProductExtraInfo of(final String categoryId,final String brandId,final String colorId,final String materialId){
        return new ProductExtraInfo(categoryId, brandId, colorId, materialId);
    }
}
