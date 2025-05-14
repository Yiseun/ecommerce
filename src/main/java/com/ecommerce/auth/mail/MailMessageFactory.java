package com.ecommerce.auth.mail;

import com.ecommerce.auth.mail.property.MailSenderProperty;
import com.ecommerce.grobal.Time;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MailMessageFactory {
    private final MailSenderProperty mailSenderProperty;
    private final Time time;

    public ValidationMailMessage getPrevalidationMessage(final String target,final String necessaryText){
        return new ValidationMailMessage(mailSenderProperty.getFrom(),target,time.getDate(),necessaryText);
    }
}
