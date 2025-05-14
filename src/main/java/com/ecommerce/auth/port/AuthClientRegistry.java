package com.ecommerce.auth.port;

import com.ecommerce.auth.mail.MailMessageFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthClientRegistry {
    private final JavaMailSender javaMailSender;
    private final MailMessageFactory messageFactory;

    public CreatePrevalidationClient getCreatePrevalidationClient(){
        return CreatePrevalidationClient.init(javaMailSender,messageFactory);
    }
}
