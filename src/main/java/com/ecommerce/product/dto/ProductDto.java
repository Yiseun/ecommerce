package com.ecommerce.product.dto;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.ProductExtraInfo;
import com.ecommerce.product.domain.ProductInfo;
import com.ecommerce.product.domain.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ProductDto {
    private final String productId;
    private final String productName;
    private final String quantity;
    private final String price;
    private final String categoryId;
    private final String brandId;
    private final String colorId;
    private final String materialId;

    public Product toProduct(){
        final ProductInfo productInfo = ProductInfo.of(this.productId,this.getProductName(),this.price);
        final ProductExtraInfo productExtraInfo = ProductExtraInfo.of(this.categoryId,this.brandId,this.colorId,this.materialId);
        final Quantity quantity = Quantity.from(this.quantity);
        return Product.of(productInfo,productExtraInfo,quantity);
    }

    public static ProductDto from(final Product product){
        return new ProductDto(product.getProductInfo().getProductId().toString()
                ,product.getProductInfo().getProductName()
                ,product.getQuantity().getValue().toString()
                ,product.getProductInfo().getPrice().toString()
                ,product.getProductExtraInfo().getCategoryId()
                ,product.getProductExtraInfo().getBrandId()
                ,product.getProductExtraInfo().getColorId()
                ,product.getProductExtraInfo().getMaterialId());
    }
}
