package com.ecommerce.auth.dto;

import com.ecommerce.auth.domain.Auth;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpResponse {
    private final String memberId;

    public static SignUpResponse from(final Auth auth){
        return new SignUpResponse(auth.getMemberId().getValue());
    }
}
