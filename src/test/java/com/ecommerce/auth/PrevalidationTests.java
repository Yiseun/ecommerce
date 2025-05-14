package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.domain.prevalidation.Email;
import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.domain.prevalidation.ValidateInfo;
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
}
