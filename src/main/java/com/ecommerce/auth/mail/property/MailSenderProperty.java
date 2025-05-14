package com.ecommerce.auth.mail.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail.sender")
@Getter
public class MailSenderProperty {
    private final String host;
    private final String username;
    private final String password;
    private final int port;
    private final String from;

    private MailSenderProperty(final String host,
                               final String username,
                               final String password,
                               final int port,
                               final String from){
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.from = from;
    }
}
