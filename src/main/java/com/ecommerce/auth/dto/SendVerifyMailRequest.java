package com.ecommerce.auth.dto;

import com.ecommerce.auth.AccessCodeCreator;
import com.ecommerce.auth.domain.Constraint;
import com.ecommerce.auth.domain.prevalidation.*;
import com.ecommerce.auth.domain.PrevalidationSessionData;
import com.ecommerce.auth.port.CreatePrevalidationClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendVerifyMailRequest {
    private final SendVerifyMailRequestBody body;
    private final CreatePrevalidationClient client;

    public PrevalidationSessionData toPrevalidationSessionData(final AccessCodeCreator accessCodeCreator){
        final AccessCode accessCode = accessCodeCreator.createAccessCode();
        final Email email = Email.from(body.getEmail());
        final ValidateInfo validateInfo = ValidateInfo.init(accessCode);
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(null);
        return PrevalidationSessionData.of(prevalidation,constraint);
    }
    public static SendVerifyMailRequest of(final CreatePrevalidationClient client,final SendVerifyMailRequestBody body){
        return new SendVerifyMailRequest(body, client);
    }
}
