package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.sessiondata.Constraint;
import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.domain.Email;
import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.domain.sessiondata.validation.ValidateInfo;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import com.ecommerce.auth.exception.application.domain.business.BusinessLogicException;
import org.junit.jupiter.api.Test;

public class ValidationSessionDataTests {

    @Test
    void 매개변수가_비어있다면_대체할수없다(){
        final Email email = Email.from("dfdf@erfef.ef");
        final AccessCode accessCode = AccessCode.from("EKF32");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,4,"PREPARE");
        final Validation validation = Validation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(5);
        final ValidationSessionData sut = ValidationSessionData.of(validation,constraint);
        final ValidationSessionData emptyValidationSessionData = null;

        assertThatThrownBy(()->sut.substitute(emptyValidationSessionData)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 매개변수의_사전인증정보가_비어있다면_대체할수없다(){
        final Email email = Email.from("fdgfdg");
        final AccessCode accessCode = AccessCode.from(null);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,2,"SUCCESS");
        final Validation validation = Validation.of(email,validateInfo);
        final Constraint constraint = Constraint.from(2);
        final ValidationSessionData sut = ValidationSessionData.of(validation,constraint);
        final ValidationSessionData request = ValidationSessionData.of(validation,constraint);

        assertThatThrownBy(()->sut.substitute(request)).isInstanceOf(BusinessLogicException.class);
    }

    @Test
    void 업데이트시_매개변수의_제약조건과_상관없이_기존_제약조건을_유지한다(){
        final Constraint requestConstraint = Constraint.createEmpty();
        final Email requestEmail = Email.from("dfgdfg@ferfe.fef");
        final AccessCode requestAccessCode = AccessCode.from("FDSF23");
        final ValidateInfo requestValidateInfo = ValidateInfo.of(requestAccessCode,null,null);
        final Validation requestValidation = Validation.of(requestEmail,requestValidateInfo);
        final ValidationSessionData request = ValidationSessionData.of(requestValidation,requestConstraint);
        final Constraint currentConstraint = Constraint.from(32434);
        final Email email = Email.from("fgfdg@efwfws.ed");
        final AccessCode accessCode = AccessCode.from("DFE332");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"PREPARE");
        final Validation validation = Validation.of(email,validateInfo);
        final ValidationSessionData sut = ValidationSessionData.of(validation,currentConstraint);

        final ValidationSessionData result = sut.update(request);

        assertThat(result.getConstraint()).isEqualTo(currentConstraint);
    }


    @Test
    void 매개변수가_비어있다면_업데이트_할수없다(){
        final Constraint constraint = Constraint.from(3);
        final Email email = Email.from("dfdsfd@fefef.ef");
        final AccessCode accessCode = AccessCode.from("DFE23DF");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,2,"PREPARE");
        final Validation validation = Validation.of(email,validateInfo);
        final ValidationSessionData sut = ValidationSessionData.of(validation,constraint);
        final ValidationSessionData request = null;

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 자신의_제약조건이_비어있다면_업데이트_할수없다(){
        final Constraint constraint = Constraint.createEmpty();
        final Email email = Email.from("sdfdsf@fewfwe.efw");
        final AccessCode accessCode = AccessCode.from("EFEEF22");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"PREPARE");
        final Validation validation = Validation.of(email,validateInfo);
        final ValidationSessionData sut = ValidationSessionData.of(validation,constraint);
        final ValidationSessionData request = ValidationSessionData.of(validation,constraint);

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);

    }

}
