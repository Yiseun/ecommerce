package com.ecommerce.auth.port;

import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.exception.application.MailSendFailureException;
import com.ecommerce.auth.mail.MailMessageFactory;
import com.ecommerce.auth.mail.ValidationMailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateValidationClient {
    private final JavaMailSender mailSender;
    private final MailMessageFactory messageFactory;
    public void sendMessage(final Validation validation){
        final String requestEmail = validation.getEmail().getValue();
        final String requestAccessCode = validation.getValidateInfo().getAccessCode().getValue();
        final ValidationMailMessage message = messageFactory.getPrevalidationMessage(requestEmail,requestAccessCode);
        try{
            mailSender.send(message);
        }catch (MailException e){
            throw new MailSendFailureException("인증메일 전송에 실패했습니다.");
        }
    }
}
