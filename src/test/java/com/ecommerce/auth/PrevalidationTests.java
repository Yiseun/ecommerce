package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.sessiondata.prevalidation.AccessCode;
import com.ecommerce.auth.domain.Email;
import com.ecommerce.auth.domain.sessiondata.prevalidation.Prevalidation;
import com.ecommerce.auth.domain.sessiondata.prevalidation.ValidateInfo;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import org.junit.jupiter.api.Test;

public class PrevalidationTests {
    @Test
    void 이메일과_사전검증정보_둘중하나만_비어있어도_빈것으로_간주한다(){
        final Email email = Email.from("fdgdfg@feerf.ef");
        final AccessCode accessCode = AccessCode.from(null);
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"SUCCESS");
        final Prevalidation sut = Prevalidation.of(email,validateInfo);
        final boolean expect = true;

        assertThat(sut.isEmpty()).isEqualTo(expect);
    }

    @Test
    void 매개변수가_비어있다면_업데이트_할수없다(){
        final Email email = Email.from("dsfdsf@fdef.efe");
        final AccessCode accessCode = AccessCode.from("DSFE23");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"PREPARE");
        final Prevalidation sut = Prevalidation.of(email,validateInfo);
        final Prevalidation request = null;

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 업데이트시_매개변수의_이메일과_상관없이_기존_이메일을_유지한다(){
        final Email email = Email.from("aaaa@aaa.aa");
        final Email theOtherEmail = Email.from("bbbb@bbb.bb");
        final AccessCode commonAccessCode = AccessCode.from("SDFFE23");
        final ValidateInfo commonValidateInfo = ValidateInfo.of(commonAccessCode,3,"SUCCESS");
        final Prevalidation sut = Prevalidation.of(email,commonValidateInfo);
        final Prevalidation request = Prevalidation.of(theOtherEmail,commonValidateInfo);
        final Prevalidation expect = Prevalidation.of(email,commonValidateInfo);

        final Prevalidation result = sut.update(request);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    void 자신의_이메일이_비어있다면_업데이트_할수없다(){
        final Email emptyEmail = Email.from(null);
        final Email notEmptyEmail = Email.from("dsfdsf@fef.ewf");
        final AccessCode accessCode = AccessCode.from("ASDEE3");
        final ValidateInfo validateInfo = ValidateInfo.of(accessCode,3,"PREPARE");
        final Prevalidation sut = Prevalidation.of(emptyEmail,validateInfo);
        final Prevalidation request = Prevalidation.of(notEmptyEmail,validateInfo);

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

}
