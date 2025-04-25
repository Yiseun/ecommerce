package com.ecommerce.payment;

import com.ecommerce.payment.domain.session.PaymentKey;
import com.ecommerce.payment.exception.PaymentKeyCreationFailureException;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PaymentKeyCreator {
    private static final int KEY_LENGTH = 40;
    private static final String SUPPORTED_CHARACTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private SecureRandom createSecureRandomInstance(){
        try {
            return SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new PaymentKeyCreationFailureException(e.getMessage(),e.getCause());
        }
    }
    public PaymentKey createPaymentKey(){
        final String secureRandomString = IntStream.generate(()->createSecureRandomInstance().nextInt(SUPPORTED_CHARACTER.length()))
                .limit(KEY_LENGTH)
                .mapToObj(num->String.valueOf(SUPPORTED_CHARACTER.charAt(num)))
                .collect(Collectors.joining());
        return PaymentKey.from(secureRandomString);
    }
}
