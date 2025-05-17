package com.ecommerce.auth.dto;

import com.ecommerce.auth.AccessCodeCreator;
import com.ecommerce.auth.domain.sessiondata.Constraint;
import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.domain.sessiondata.validation.Email;
import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.domain.sessiondata.validation.ValidateInfo;
import com.ecommerce.auth.port.CreateValidationClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendVerifyMailRequest {
    private final SendVerifyMailRequestBody body;
    private final CreateValidationClient client;

    public ValidationSessionData toValidationSessionData(final AccessCodeCreator accessCodeCreator){
        final AccessCode accessCode = accessCodeCreator.createAccessCode();
        final Email email = Email.from(body.getEmail());
        final ValidateInfo validateInfo = ValidateInfo.init(accessCode);
        final Validation validation = Validation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(null);
        return ValidationSessionData.of(validation,constraint);
    }
    public static SendVerifyMailRequest of(final CreateValidationClient client, final SendVerifyMailRequestBody body){
        return new SendVerifyMailRequest(body, client);
    }
}
