package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.Constraint;
import com.ecommerce.auth.domain.PrevalidationSessionData;
import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.domain.prevalidation.Email;
import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.domain.prevalidation.ValidateInfo;
import com.ecommerce.auth.exception.application.domain.BusinessLogicException;
import org.junit.jupiter.api.Test;

public class PrevalidationSessionDataTests {

    @Test
    void 매개변수가_비어있다면_대체할수없다(){
        final Email email = Email.from("dfdf@erfef.ef");
        final AccessCode accessCode = AccessCode.from("EKF32");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,4,"PREPARE");
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(5);
        final PrevalidationSessionData sut = PrevalidationSessionData.of(prevalidation,constraint);
        final PrevalidationSessionData emptyPrevalidationSessionData = null;

        assertThatThrownBy(()->sut.substitute(emptyPrevalidationSessionData)).isInstanceOf(BusinessLogicException.class);
    }

    @Test
    void 매개변수의_사전인증정보가_비어있다면_대체할수없다(){
        final Email email = Email.from("fdgfdg");
        final AccessCode accessCode = AccessCode.from(null);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,2,"SUCCESS");
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(2);
        final PrevalidationSessionData sut = PrevalidationSessionData.of(prevalidation,constraint);
        final PrevalidationSessionData request = PrevalidationSessionData.of(prevalidation,constraint);

        assertThatThrownBy(()->sut.substitute(request)).isInstanceOf(BusinessLogicException.class);
    }


}
