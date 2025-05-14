package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.domain.prevalidation.ValidateInfo;
import org.junit.jupiter.api.Test;

public class ValidateInfoTests {

    @Test
    void 인증코드가_비어있을때_빈것으로_간주한다(){
        final AccessCode accessCode = AccessCode.from(null);
        final ValidateInfo sut = ValidateInfo.of(accessCode,23,"SUCCESS");
        final boolean expect = true;

        assertThat(sut.isEmpty()).isEqualTo(expect);
    }
}
