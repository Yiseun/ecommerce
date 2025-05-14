package com.ecommerce.auth.mail;

import com.ecommerce.auth.mail.property.MailProperty;
import com.ecommerce.auth.mail.property.MailSenderProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfiguration {
    @Bean
    public JavaMailSender javaMailsender(final MailSenderProperty property, final MailProperty mailProperty) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(property.getHost());
        javaMailSender.setUsername(property.getUsername());
        javaMailSender.setPassword(property.getPassword());
        javaMailSender.setPort(property.getPort());
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug",mailProperty.getDebug());
        mailProperty.getTrustList().forEach(trust->{
            properties.setProperty("mail.smtp.ssl.trust",trust);
        });
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
}
