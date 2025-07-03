package com.ecommerce.auth.dto.response;

import com.ecommerce.auth.domain.EncryptedAuth;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private final String memberId;

    public static final LoginResponse from(final EncryptedAuth encryptedAuth){
        return new LoginResponse(encryptedAuth.getMemberId().getValue());
    }
}
