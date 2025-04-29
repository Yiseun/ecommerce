package com.ecommerce.payment.portone;

import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@RequiredArgsConstructor
public class PortoneConfiguration {
    private final String apiKey;
    private final String apiSecret;
    @Bean
    public IamportClient createIamPortClient(){
        return new IamportClient(this.apiKey,this.apiSecret);
    }
}
