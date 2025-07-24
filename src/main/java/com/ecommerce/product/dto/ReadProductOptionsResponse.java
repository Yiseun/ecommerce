package com.ecommerce.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Trie;

@Getter
@RequiredArgsConstructor
public class ReadProductOptionsResponse {
    private final Trie brands;
    private final Trie colors;
    private final Trie categories;
    private final Trie materials;

    public static ReadProductOptionsResponse from(final Trie brands,final Trie colors,final Trie categories,final Trie materials){
        return new ReadProductOptionsResponse(brands, colors, categories, materials);
    }
}
