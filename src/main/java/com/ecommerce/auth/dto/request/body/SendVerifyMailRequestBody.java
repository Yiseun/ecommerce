package com.ecommerce.auth.dto.request.body;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendVerifyMailRequestBody {
    private final String email;
}
