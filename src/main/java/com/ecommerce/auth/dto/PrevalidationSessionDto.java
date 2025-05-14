package com.ecommerce.auth.dto;

import com.ecommerce.auth.domain.Constraint;
import com.ecommerce.auth.domain.PrevalidationSessionData;
import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.domain.prevalidation.Email;
import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.domain.prevalidation.ValidateInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PrevalidationSessionDto {
    private final Integer constraintTryCount;
    private final String email;
    private final String accessCode;
    private final Integer validateTryCount;
    private final String validateState;
    public PrevalidationSessionData toPrevalidationSessionData(){
        final Constraint constraint = Constraint.from(this.constraintTryCount);
        final Email email = Email.from(this.email);
        final AccessCode accessCode = AccessCode.from(this.accessCode);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,this.validateTryCount,this.validateState);
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        return PrevalidationSessionData.of(prevalidation,constraint);
    }

    public static PrevalidationSessionDto from(final PrevalidationSessionData prevalidationSessionData){
        final Integer constraintTryCount = prevalidationSessionData.getConstraint().getTryCount();
        final String email = prevalidationSessionData.getPrevalidation().getEmail().getValue();
        final String accessCode = prevalidationSessionData.getPrevalidation().getValidateInfo().getAccessCode().getValue();
        final Integer validateTryCount = prevalidationSessionData.getPrevalidation().getValidateInfo().getTryCount();
        final String validateState = prevalidationSessionData.getPrevalidation().getValidateInfo().getValidateState();
        return new PrevalidationSessionDto(constraintTryCount,email,accessCode,validateTryCount,validateState);
    }

    public static PrevalidationSessionDto init(){
        return new PrevalidationSessionDto(null,null,null,null,null);
    }
}
