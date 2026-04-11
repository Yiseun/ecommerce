package com.ecommerce.image;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "s3.property")
@Getter
@RequiredArgsConstructor
public class S3Property {
    private String prefix;
    private String bucketName;
}
