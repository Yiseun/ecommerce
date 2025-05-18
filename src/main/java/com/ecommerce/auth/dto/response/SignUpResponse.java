package com.ecommerce.auth.dto.response;

import com.ecommerce.auth.domain.EncryptedAuth;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpResponse {
    private final String memberId;

    public static SignUpResponse from(final EncryptedAuth encryptedAuth){
        return new SignUpResponse(encryptedAuth.getMemberId().getValue());
    }
}
