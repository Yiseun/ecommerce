package com.ecommerce.auth;

import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.exception.application.AccessCodeCreationFailureException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AccessCodeCreator {
    private static final int ACCESS_CODE_LENGTH = 7;
    private static final String SUPPORTED_CHARACTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private SecureRandom createSecureRandomInstance(){
        try {
            return SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new AccessCodeCreationFailureException(e.getMessage(),e.getCause());
        }
    }
    public AccessCode createAccessCode(){
        final String secureRandomString = IntStream.generate(()->createSecureRandomInstance().nextInt(SUPPORTED_CHARACTER.length()))
                .limit(ACCESS_CODE_LENGTH)
                .mapToObj(num->String.valueOf(SUPPORTED_CHARACTER.charAt(num)))
                .collect(Collectors.joining());
        return AccessCode.from(secureRandomString);
    }
}
