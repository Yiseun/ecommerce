package com.ecommerce.auth.encrypt;

import com.ecommerce.auth.domain.EncryptedPassword;
import com.ecommerce.auth.domain.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Encryptor {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public EncryptedPassword encrypt(final RawPassword password){
        final String encodedPassword = bCryptPasswordEncoder.encode(password.getValue());
        return EncryptedPassword.from(encodedPassword);
    }

}