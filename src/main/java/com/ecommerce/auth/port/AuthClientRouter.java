package com.ecommerce.auth.port;

import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.dto.response.InternalAuthUpdateResponse;
import com.ecommerce.auth.mail.MailMessageFactory;
import com.ecommerce.member.MemberReceiver;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class AuthClientRouter {

    private final CreateValidationClient createValidationClient;
    private final UpdateAuthClient updateAuthClient;

    public AuthClientRouter(final JavaMailSender javaMailSender,
                            final MailMessageFactory messageFactory,
                            final MemberReceiver memberReceiver){
        this.createValidationClient = CreateValidationClient.init(javaMailSender,messageFactory);
        this.updateAuthClient = UpdateAuthClient.from(memberReceiver);
    }

    public void createValidation(final Validation validation){
        createValidationClient.sendMessage(validation);
    }

    public InternalAuthUpdateResponse updateAuth(final Validation validation){
        return updateAuthClient.sendMessage(validation);
    }
}
