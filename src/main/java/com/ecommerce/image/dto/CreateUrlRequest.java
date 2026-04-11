package com.ecommerce.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateUrlRequest {
    private final String fileName;

    public static CreateUrlRequest from(final String fileName){
        return new CreateUrlRequest(fileName);
    }
}
