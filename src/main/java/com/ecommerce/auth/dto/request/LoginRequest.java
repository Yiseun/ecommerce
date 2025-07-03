package com.ecommerce.auth.dto.request;

import com.ecommerce.auth.domain.Auth;
import com.ecommerce.auth.domain.MemberId;
import com.ecommerce.auth.domain.RawPassword;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginRequest {
    private final String memberId;
    private final String password;
    public Auth toAuth(){
        final MemberId memberId = MemberId.from(this.memberId);
        final RawPassword rawPassword = RawPassword.from(this.password);
        return Auth.of(memberId,rawPassword);
    }
}
