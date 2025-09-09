package com.ecommerce.auth.dto.request;

import com.ecommerce.auth.domain.RawPassword;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateAuthRequest {
    private final String password;

    public RawPassword toPassword(){
        return RawPassword.from(this.password);
    }
}
