package com.ecommerce.product.concurrency.estimate.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("estimator")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EstimatorProperties {
    private final String corePoolSize;
    private final String maxPoolSize;
    private final String queueCapacity;
    private final String keepAliveSeconds;
    private final String threadNamePrefix;
}
