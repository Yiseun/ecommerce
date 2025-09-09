package com.ecommerce.auth.dto.request;

import com.ecommerce.auth.AccessCodeCreator;
import com.ecommerce.auth.domain.sessiondata.Constraint;
import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.domain.sessiondata.validation.Email;
import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.domain.sessiondata.validation.ValidateInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendVerifyMailRequest {
    private final String email;

    public ValidationSessionData toValidationSessionData(final AccessCodeCreator accessCodeCreator){
        final AccessCode accessCode = accessCodeCreator.createAccessCode();
        final Email email = Email.from(this.email);
        final ValidateInfo validateInfo = ValidateInfo.init(accessCode);
        final Validation validation = Validation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(null);
        return ValidationSessionData.of(validation,constraint);
    }
}
