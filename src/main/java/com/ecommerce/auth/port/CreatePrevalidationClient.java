package com.ecommerce.auth.port;

import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.exception.application.MailSendFailureException;
import com.ecommerce.auth.mail.MailMessageFactory;
import com.ecommerce.auth.mail.ValidationMailMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePrevalidationClient {
    private final JavaMailSender mailSender;
    private final MailMessageFactory messageFactory;
    public void sendMessage(final Prevalidation prevalidation){
        final String requestEmail = prevalidation.getEmail().getValue();
        final String requestAccessCode = prevalidation.getValidateInfo().getAccessCode().getValue();
        final ValidationMailMessage message = messageFactory.getPrevalidationMessage(requestEmail,requestAccessCode);
        try{
            mailSender.send(message);
        }catch (MailException e){
            throw new MailSendFailureException("인증메일 전송에 실패했습니다.");
        }
    }
    public static CreatePrevalidationClient init(final JavaMailSender javaMailSender, final MailMessageFactory messageFactory){
        return new CreatePrevalidationClient(javaMailSender,messageFactory);
    }
}
