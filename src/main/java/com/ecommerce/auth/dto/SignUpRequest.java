package com.ecommerce.auth.dto;

import com.ecommerce.auth.domain.*;
import com.ecommerce.auth.encrypt.Encryptor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SignUpRequest {
    private final String memberId;
    private final String password;

    public Auth toAuth(final PrevalidationSessionDto serverData, final Encryptor encryptor){
        final EncryptedPassword password = encryptor.encrypt(RawPassword.from(this.password));
        final Email email = Email.from(serverData.getEmail());
        final MemberId memberId = MemberId.from(this.memberId);
        return Auth.of(memberId,email,password);
    }
}
