package com.ecommerce.product;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.InternalProductValidateRequest;
import com.ecommerce.product.exception.application.ProductNotFoundException;
import com.ecommerce.product.persistence.ProductEntity;
import com.ecommerce.product.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public void validate(final InternalProductValidateRequest request){
        final NonDuplicatedList<Product> requestProducts = request.toProductList();
        final List<Long> requestProductEntityIds = requestProducts.getStream().map(product->ProductEntity.from(product).getProductId()).toList();
        final List<ProductEntity> serverProductEntities = productRepository.findAllById(requestProductEntityIds);
        if(serverProductEntities.isEmpty()){
            throw new ProductNotFoundException("서버에 상품이 존재하지 않습니다.");
        }
        final List<Product> serverProducts = serverProductEntities.stream().map(i->i.toProduct()).toList();
        final Map<Product,Product> serverProductMap = serverProducts.stream().collect(Collectors.toMap(i->i,i->i));
        requestProducts.getStream().forEach(requestProduct->{
            final Product serverProduct = serverProductMap.get(requestProduct);
            if(serverProduct==null){
                throw new ProductNotFoundException("서버에 존재하지않는 상품이 있습니다.");
            }
            serverProduct.validate(requestProduct);
        });

    }
}
