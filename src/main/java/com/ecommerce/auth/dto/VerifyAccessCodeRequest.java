package com.ecommerce.auth.dto;

import com.ecommerce.auth.domain.Constraint;
import com.ecommerce.auth.domain.PrevalidationSessionData;
import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.domain.prevalidation.Email;
import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.domain.prevalidation.ValidateInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyAccessCodeRequest {
    private final String accessCode;

    public PrevalidationSessionData toPrevalidationSessionData(){
        final AccessCode accessCode = AccessCode.from(this.accessCode);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,null,null);
        final Email email = Email.from(null);
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final Constraint constraint = Constraint.createEmpty();
        return PrevalidationSessionData.of(prevalidation,constraint);
    }
}
