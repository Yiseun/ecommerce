package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ecommerce.auth.domain.prevalidation.AccessCode;
import com.ecommerce.auth.dto.PrevalidationSessionDto;
import com.ecommerce.auth.dto.SendVerifyMailRequest;
import com.ecommerce.auth.dto.SendVerifyMailRequestBody;
import com.ecommerce.auth.dto.VerifyAccessCodeRequest;
import com.ecommerce.auth.port.CreatePrevalidationClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AuthServiceTests {
    @Mock
    private CreatePrevalidationClient createPrevalidationClient;
    @MockBean
    private AccessCodeCreator accessCodeCreator;
    @Autowired
    private AuthService sut;

    @Test
    void 인증정보를_새로만들면_기존_인증정보를_대체한다(){
        final String email = "AAAAAA@aaa.aa";
        final String theOtherEmail = "BBBBBB@bbb.bb";
        final String accessCode = "AAAAAA";
        final String theOtherAccessCode = "BBBBB";
        final int tryCount = 2;
        final int theOtherTryCount = 10;
        final String validateState = "FAIL";
        final String theOtherValidateState = "PREPARE";
        final PrevalidationSessionDto requestPrevalidationSessionDto = new PrevalidationSessionDto(4,email,accessCode,tryCount,validateState);
        final SendVerifyMailRequestBody body = new SendVerifyMailRequestBody(theOtherEmail);
        final SendVerifyMailRequest sendVerifyMailRequest = new SendVerifyMailRequest(body,createPrevalidationClient);
        final PrevalidationSessionDto expect = new PrevalidationSessionDto(3,theOtherEmail,theOtherAccessCode,theOtherTryCount,theOtherValidateState);
        when(accessCodeCreator.createAccessCode()).thenReturn(AccessCode.from(theOtherAccessCode));

        final PrevalidationSessionDto result = sut.createPrevalidation(requestPrevalidationSessionDto,sendVerifyMailRequest);

        assertThat(result.getConstraintTryCount()).isEqualTo(expect.getConstraintTryCount());
        assertThat(result.getEmail()).isEqualTo(expect.getEmail());
        assertThat(result.getAccessCode()).isEqualTo(expect.getAccessCode());
        assertThat(result.getValidateTryCount()).isEqualTo(expect.getValidateTryCount());
        assertThat(result.getValidateState()).isEqualTo(expect.getValidateState());
    }

    @Test
    void 인증코드를_검증하면_인증정보를_업데이트한다(){
        final String commonEmail = "fdfdsf@dfdsf.fsd";
        final String accessCode = "AAAAAA";
        final String requestAccessCode = "BBBBBB";
        final int commonConstraintTryCount = 3;
        final int tryCount = 5;
        final int expectTryCount = 4;
        final String validateState = "PREPARE";
        final String expectValidateState = "FAIL";
        final PrevalidationSessionDto prevalidationSessionDto = new PrevalidationSessionDto(commonConstraintTryCount,commonEmail,accessCode,tryCount,validateState);
        final VerifyAccessCodeRequest request = new VerifyAccessCodeRequest(requestAccessCode);
        final PrevalidationSessionDto expect = new PrevalidationSessionDto(commonConstraintTryCount,commonEmail,accessCode,expectTryCount,expectValidateState);

        final PrevalidationSessionDto result = sut.updatePrevalidation(prevalidationSessionDto,request);

        assertThat(result.getConstraintTryCount()).isEqualTo(expect.getConstraintTryCount());
        assertThat(result.getEmail()).isEqualTo(expect.getEmail());
        assertThat(result.getAccessCode()).isEqualTo(expect.getAccessCode());
        assertThat(result.getValidateTryCount()).isEqualTo(expect.getValidateTryCount());
        assertThat(result.getValidateState()).isEqualTo(expect.getValidateState());
    }
}
