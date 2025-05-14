package com.ecommerce.auth.mail.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "mail")
@Getter
public class MailProperty {
    private final String debug;
    private final List<String> trustList;

    private MailProperty(final String debug,final List<String> trustList){
        this.debug = debug;
        this.trustList = trustList;
    }
}
