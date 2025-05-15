package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.Constraint;
import com.ecommerce.auth.domain.PrevalidationSessionData;
import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.domain.prevalidation.Email;
import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.domain.prevalidation.ValidateInfo;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import com.ecommerce.auth.exception.application.domain.business.BusinessLogicException;
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

        assertThatThrownBy(()->sut.substitute(emptyPrevalidationSessionData)).isInstanceOf(InvalidConstructionException.class);
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

    @Test
    void 업데이트시_매개변수의_제약조건과_상관없이_기존_제약조건을_유지한다(){
        final Constraint requestConstraint = Constraint.createEmpty();
        final Email requestEmail = Email.from("dfgdfg@ferfe.fef");
        final AccessCode requestAccessCode = AccessCode.from("FDSF23");
        final ValidateInfo requestValidateInfo = ValidateInfo.of(requestAccessCode,null,null);
        final Prevalidation requestPrevalidation = Prevalidation.of(requestEmail,requestValidateInfo);
        final PrevalidationSessionData request = PrevalidationSessionData.of(requestPrevalidation,requestConstraint);
        final Constraint currentConstraint = Constraint.from(32434);
        final Email email = Email.from("fgfdg@efwfws.ed");
        final AccessCode accessCode = AccessCode.from("DFE332");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"PREPARE");
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final PrevalidationSessionData sut = PrevalidationSessionData.of(prevalidation,currentConstraint);

        final PrevalidationSessionData result = sut.update(request);

        assertThat(result.getConstraint()).isEqualTo(currentConstraint);
    }


    @Test
    void 매개변수가_비어있다면_업데이트_할수없다(){
        final Constraint constraint = Constraint.from(3);
        final Email email = Email.from("dfdsfd@fefef.ef");
        final AccessCode accessCode = AccessCode.from("DFE23DF");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,2,"PREPARE");
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final PrevalidationSessionData sut = PrevalidationSessionData.of(prevalidation,constraint);
        final PrevalidationSessionData request = null;

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 자신의_제약조건이_비어있다면_업데이트_할수없다(){
        final Constraint constraint = Constraint.createEmpty();
        final Email email = Email.from("sdfdsf@fewfwe.efw");
        final AccessCode accessCode = AccessCode.from("EFEEF22");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"PREPARE");
        final Prevalidation prevalidation = Prevalidation.of(email,validateInfo);
        final PrevalidationSessionData sut = PrevalidationSessionData.of(prevalidation,constraint);
        final PrevalidationSessionData request = PrevalidationSessionData.of(prevalidation,constraint);

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);

    }

}
