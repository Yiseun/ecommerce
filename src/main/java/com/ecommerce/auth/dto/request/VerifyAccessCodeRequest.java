package com.ecommerce.auth.dto.request;

import com.ecommerce.auth.domain.sessiondata.Constraint;
import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.domain.sessiondata.validation.Email;
import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.domain.sessiondata.validation.ValidateInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyAccessCodeRequest {
    private final String accessCode;

    public ValidationSessionData toValidationSessionData(){
        final AccessCode accessCode = AccessCode.from(this.accessCode);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,null,null);
        final Email email = Email.from(null);
        final Validation validation = Validation.of(email,validateInfo);
        final Constraint constraint = Constraint.createEmpty();
        return ValidationSessionData.of(validation,constraint);
    }
}
