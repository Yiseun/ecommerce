package com.ecommerce.auth.encrypt;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncryptConfiguration {
    @Bean
    public BCryptPasswordEncoder BCryptPasswordEncoder(final EncryptProperty encryptProperty){
        try{
            final int strength = Integer.parseInt(encryptProperty.getStrength());
            return new BCryptPasswordEncoder(strength);
        } catch (IllegalArgumentException e){
            throw new BeanCreationException("strength 범위내의 값을 입력해주세요");
        }
    }
}