package com.ecommerce.product;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.*;
import com.ecommerce.product.persistence.ProductDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ElasticService {

    private final ElasticsearchOperations operations;
    private final ProductService productService;
    public SearchProductResponse searchProductsByKeywordWithSpecificOptions(final SearchProductRequest request){

        final ReadProductOptionsResponse productOptionsResponse = productService.readAllOptions();
        final List<FieldValue> brandsInKeyword = productOptionsResponse.getBrands().parseText(request.getKeyword()).stream().map(i-> FieldValue.of(i.getKeyword())).toList();
        final List<FieldValue> colorsInKeyword = productOptionsResponse.getColors().parseText(request.getKeyword()).stream().map(i->FieldValue.of(i.getKeyword())).toList();
        final List<FieldValue> categoriesInKeyword = productOptionsResponse.getCategories().parseText(request.getKeyword()).stream().map(i->FieldValue.of(i.getKeyword())).toList();
        final List<FieldValue> materialsInKeyword = productOptionsResponse.getMaterials().parseText(request.getKeyword()).stream().map(i->FieldValue.of(i.getKeyword())).toList();

        final List<FieldValue> requestBrands = request.getBrands().stream().map(i->FieldValue.of(i)).toList();
        final List<FieldValue> requestColors = request.getColors().stream().map(i->FieldValue.of(i)).toList();
        final List<FieldValue> requestCategory = List.of(FieldValue.of(request.getCategory()));
        final List<FieldValue> requestMaterials = request.getMaterials().stream().map(i->FieldValue.of(i)).toList();

        final List<FieldValue> completeRequestBrands = Stream.concat(brandsInKeyword.stream(),requestBrands.stream()).toList();
        final List<FieldValue> completeRequestColors = Stream.concat(colorsInKeyword.stream(),requestColors.stream()).toList();
        final List<FieldValue> completeRequestCategories = Stream.concat(categoriesInKeyword.stream(),requestCategory.stream()).toList();
        final List<FieldValue> completeRequestMaterials = Stream.concat(materialsInKeyword.stream(),requestMaterials.stream()).toList();

        final Query query = QueryBuilders.bool()
                .should(QueryBuilders.match().field("productName").query(request.getKeyword()).build()._toQuery())
                .filter(QueryBuilders.bool(b->b.filter(f->f.terms(t->t.field("brand").terms(new TermsQueryField.Builder().value(completeRequestBrands).build())))))
                .filter(QueryBuilders.bool(b->b.filter(f->f.terms(t->t.field("color").terms(new TermsQueryField.Builder().value(completeRequestColors).build())))))
                .filter(QueryBuilders.bool(b->b.filter(f->f.terms(t->t.field("category").terms(new TermsQueryField.Builder().value(completeRequestCategories).build())))))
                .filter(QueryBuilders.bool(b->b.filter(f->f.terms(t->t.field("material").terms(new TermsQueryField.Builder().value(completeRequestMaterials).build())))))
                .build()._toQuery();
        final NativeQuery nativeQuery = new NativeQueryBuilder().withQuery(query).build();

        final List<SearchHit<ProductDocument>> result = operations.search(nativeQuery, ProductDocument.class).getSearchHits();
        final List<Product> resultProducts = result.stream().map(i->i.getContent().toProduct()).toList();
        final ReadSpecificProductRequest readSpecificProductRequest = ReadSpecificProductRequest.from(resultProducts);
        final ReadSpecificProductResponse readSpecificProductResponse = productService.readSpecificProduct(readSpecificProductRequest);
        return SearchProductResponse.from(readSpecificProductResponse.toProducts());
    }
}
