package com.ecommerce.grobal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "grobal.time")
@Getter
@RequiredArgsConstructor
public class TimeProperty {
    private final String timeZone;
}
