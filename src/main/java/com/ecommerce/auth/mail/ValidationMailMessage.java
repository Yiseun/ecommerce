package com.ecommerce.auth.mail;

import com.ecommerce.auth.mail.exception.UnsupportedException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public class ValidationMailMessage extends SimpleMailMessage {
    public ValidationMailMessage(final String from,
                                 final String to,
                                 final Date sentDate,
                                 final String text){
        super.setFrom(from);
        super.setTo(to);
        super.setSentDate(sentDate);
        super.setSubject(createDefaultSubject());
        super.setText(createDefaultText(text));
    }

    private String createDefaultSubject(){
        return "[ECommerce] 이메일 인증을 위한 인증번호를 안내 드립니다.";
    }
    private String createDefaultText(final String text){
        return "인증번호는 "+text+" 입니다.";
    }

    @Override
    public void setFrom(String from) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }
    @Override
    public void setReplyTo(String replyTo) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setTo(String to) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setTo(String... to) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setCc(String cc) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setCc(String... cc) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setBcc(String bcc) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setBcc(String... bcc) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setSentDate(Date sentDate) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setSubject(String subject) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }

    @Override
    public void setText(String text) throws MailParseException {
        throw new UnsupportedException("지원하지않는 동작입니다.");
    }
}
