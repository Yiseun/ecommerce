package com.ecommerce.auth.dto.request;

import com.ecommerce.auth.domain.*;
import com.ecommerce.auth.domain.sessiondata.validation.Email;
import com.ecommerce.auth.encrypt.Encryptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignUpRequest {
    private final String memberId;
    private final String password;

    public Auth toAuth(){
        final RawPassword password = RawPassword.from(this.password);
        final MemberId memberId = MemberId.from(this.memberId);
        return Auth.of(memberId,password);
    }
}
