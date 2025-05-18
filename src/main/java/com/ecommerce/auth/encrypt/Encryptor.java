package com.ecommerce.auth.encrypt;

import com.ecommerce.auth.domain.Auth;
import com.ecommerce.auth.domain.EncryptedAuth;
import com.ecommerce.auth.domain.EncryptedPassword;
import com.ecommerce.auth.domain.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Encryptor {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncryptedAuth encrypt(final Auth auth){
        final EncryptedPassword encryptedPassword = EncryptedPassword.from(bCryptPasswordEncoder.encode(auth.getRawPassword().getValue()));
        return EncryptedAuth.of(auth.getMemberId(),encryptedPassword);
    }
}