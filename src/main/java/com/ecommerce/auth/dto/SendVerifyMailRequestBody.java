package com.ecommerce.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendVerifyMailRequestBody {
    private final String email;
}
