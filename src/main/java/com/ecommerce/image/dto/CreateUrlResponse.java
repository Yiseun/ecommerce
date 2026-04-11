package com.ecommerce.image.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUrlResponse {
    private final String url;

    public static CreateUrlResponse from(final String url){
        return new CreateUrlResponse(url);
    }
}
