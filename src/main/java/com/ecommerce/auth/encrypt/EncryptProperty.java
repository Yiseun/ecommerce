package com.ecommerce.auth.encrypt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.encrypt")
@Getter
@RequiredArgsConstructor
public class EncryptProperty {
    private final String strength;
}
