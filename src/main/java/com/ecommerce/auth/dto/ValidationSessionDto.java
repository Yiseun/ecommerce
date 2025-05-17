package com.ecommerce.auth.dto;

import com.ecommerce.auth.domain.sessiondata.Constraint;
import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.domain.sessiondata.validation.Email;
import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.domain.sessiondata.validation.ValidateInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationSessionDto {
    private final Integer constraintTryCount;
    private final String email;
    private final String accessCode;
    private final Integer validateTryCount;
    private final String validateState;
    public ValidationSessionData toValidationSessionData(){
        final Constraint constraint = Constraint.from(this.constraintTryCount);
        final Email email = Email.from(this.email);
        final AccessCode accessCode = AccessCode.from(this.accessCode);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,this.validateTryCount,this.validateState);
        final Validation validation = Validation.of(email,validateInfo);
        return ValidationSessionData.of(validation,constraint);
    }

    public static ValidationSessionDto from(final ValidationSessionData validationSessionData){
        final Integer constraintTryCount = validationSessionData.getConstraint().getTryCount();
        final String email = validationSessionData.getValidation().getEmail().getValue();
        final String accessCode = validationSessionData.getValidation().getValidateInfo().getAccessCode().getValue();
        final Integer validateTryCount = validationSessionData.getValidation().getValidateInfo().getTryCount();
        final String validateState = validationSessionData.getValidation().getValidateInfo().getValidateState();
        return new ValidationSessionDto(constraintTryCount,email,accessCode,validateTryCount,validateState);
    }

    public static ValidationSessionDto init(){
        return new ValidationSessionDto(null,null,null,null,null);
    }
}
