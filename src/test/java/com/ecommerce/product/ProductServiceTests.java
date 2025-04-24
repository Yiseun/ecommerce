package com.ecommerce.product;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.*;
import com.ecommerce.product.dto.InternalProductValidateRequest;
import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.exception.application.ProductNotFoundException;
import com.ecommerce.product.persistence.ProductEntity;
import com.ecommerce.product.persistence.ProductRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void 상품이_하나라도_일치하지_않는것이_있다면_검증에_실패한다(){
        final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
        final String productName = "fsdfdg";
        final Long requestProductPrice = 500L;
        final Long serverProductPrice = 30000L;
        final Long productQuantity = 30L;
        final ProductEntity serverProductEntity = fixtureMonkey.giveMeBuilder(ProductEntity.class)
                .set(javaGetter(ProductEntity::getProductId),null)
                .set(javaGetter(ProductEntity::getProductName),productName)
                .set(javaGetter(ProductEntity::getPrice),serverProductPrice)
                .set(javaGetter(ProductEntity::getQuantity),productQuantity)
                .sample();
        final ProductEntity savedProductEntity = productRepository.save(serverProductEntity);
        final ProductDto requestProductDto = fixtureMonkey.giveMeBuilder(ProductDto.class)
                .set(javaGetter(ProductDto::getProductId),savedProductEntity.getProductId().toString())
                .set(javaGetter(ProductDto::getProductName),productName)
                .set(javaGetter(ProductDto::getPrice),requestProductPrice.toString())
                .set(javaGetter(ProductDto::getQuantity),productQuantity.toString())
                .sample();
        final List<ProductDto> productDtos = List.of(requestProductDto);
        final InternalProductValidateRequest request = fixtureMonkey.giveMeBuilder(InternalProductValidateRequest.class)
                .set(javaGetter(InternalProductValidateRequest::getProductDtos),productDtos)
                .sample();

        assertThatThrownBy(()->productService.validate(request)).isInstanceOf(ProductNotFoundException.class);
    }
}
