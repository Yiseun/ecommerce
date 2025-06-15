package com.ecommerce.product.concurrency.pending.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaProperties {
    private final String bootstrapServers;
    private final String groupId;
}
