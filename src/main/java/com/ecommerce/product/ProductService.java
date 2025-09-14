package com.ecommerce.product;

import com.ecommerce.grobal.util.NonDuplicatedList;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.*;
import com.ecommerce.product.exception.application.ProductNotFoundException;
import com.ecommerce.product.persistence.*;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Trie;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MaterialRepository materialRepository;
    private final ColorRepository colorRepository;

    @Transactional(readOnly = true)
    public ReadSpecificProductResponse readSpecificProduct(final ReadSpecificProductRequest request){
        final List<Product> requestProducts = request.toProducts();
        final List<ProductEntity> requestProductEntities = requestProducts.stream().map(i->ProductEntity.from(i)).toList();
        final List<Long> requestProductEntityIds = requestProductEntities.stream().map(i->i.getProductId()).toList();
        final List<ProductEntity> serverProductEntities = productRepository.findAllById(requestProductEntityIds);
        final List<Product> serverProducts = serverProductEntities.stream().map(i->i.toProduct()).toList();
        return ReadSpecificProductResponse.from(serverProducts);
    }

    @Cacheable("productOptions")
    public ReadProductOptionsResponse readAllOptions(){

        final List<BrandEntity> brandEntities = brandRepository.findAll();
        final List<ColorEntity> colorEntities = colorRepository.findAll();
        final List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        final List<MaterialEntity> materialEntities = materialRepository.findAll();

        final Trie brands = Trie.builder().addKeywords(brandEntities.stream().map(i->i.getBrandName()).toList()).build();
        final Trie colors = Trie.builder().addKeywords(colorEntities.stream().map(i->i.getColorName()).toList()).build();
        final Trie categories = Trie.builder().addKeywords(categoryEntities.stream().map(i->i.getCategoryName()).toList()).build();
        final Trie materials = Trie.builder().addKeywords(materialEntities.stream().map(i->i.getMaterialName()).toList()).build();

        return ReadProductOptionsResponse.from(brands,colors,categories,materials);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public void update(final UpdateProductRequest request){
        final NonDuplicatedList<Product> requestProducts = request.toProductList();
        final List<Long> requestProductIds = requestProducts.getStream().map(i->i.getProductInfo().getProductId()).sorted().toList();
        final List<ProductEntity> serverProductEntities =requestProductIds.stream()
                .map(id->productRepository.findByIdWithPessimisticWriteLock(id))
                .filter(optional->optional.isPresent())
                .map(optional->optional.get())
                .toList();
        if(serverProductEntities.isEmpty()){
            throw new RuntimeException("서버에 상품이 존재하지 않습니다.");
        }
        final List<Product> serverProducts = serverProductEntities.stream().map(i->i.toProduct()).toList();
        final Map<Product,Product> serverProductMap = serverProducts.stream().collect(Collectors.toMap(i->i,i->i));
        final List<Product> resultProducts = requestProducts.getStream().map(requestProduct->{
            final Product serverProduct = serverProductMap.get(requestProduct);
            if(serverProduct==null){
                throw new ProductNotFoundException("서버에 존재하지않는 상품이 있습니다.");
            }
            return serverProduct.update(requestProduct);
        }).toList();
        final List<ProductEntity> resultProductEntities = resultProducts.stream().map(i->ProductEntity.from(i)).toList();
        productRepository.saveAll(resultProductEntities);
    }

}
