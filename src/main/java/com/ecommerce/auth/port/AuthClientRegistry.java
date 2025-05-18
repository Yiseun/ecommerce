package com.ecommerce.auth.port;

import com.ecommerce.auth.mail.MailMessageFactory;
import com.ecommerce.member.MemberReceiver;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthClientRegistry {
    private final JavaMailSender javaMailSender;
    private final MailMessageFactory messageFactory;
    private final MemberReceiver memberReceiver;

    public CreateValidationClient getCreatePrevalidationClient(){
        return CreateValidationClient.init(javaMailSender,messageFactory);
    }
    public UpdateAuthClient getUpdateAuthClient(){
        return UpdateAuthClient.from(memberReceiver);
    }
}
