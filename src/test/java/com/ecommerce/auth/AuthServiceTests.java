package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.ecommerce.auth.domain.*;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.encrypt.Encryptor;
import com.ecommerce.auth.persistence.EncryptedAuthEntity;
import com.ecommerce.auth.persistence.EncryptedAuthEntityRepository;
import com.ecommerce.auth.port.CreateValidationClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AuthServiceTests {
    @Mock
    private CreateValidationClient createValidationClient;
    @MockBean
    private AccessCodeCreator accessCodeCreator;
    @MockBean
    private Encryptor encryptor;
    @Autowired
    private EncryptedAuthEntityRepository encryptedAuthEntityRepository;
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
        final ValidationSessionDto requestValidationSessionDto = new ValidationSessionDto(4,email,accessCode,tryCount,validateState);
        final SendVerifyMailRequestBody body = new SendVerifyMailRequestBody(theOtherEmail);
        final SendVerifyMailRequest sendVerifyMailRequest = new SendVerifyMailRequest(body, createValidationClient);
        final ValidationSessionDto expect = new ValidationSessionDto(3,theOtherEmail,theOtherAccessCode,theOtherTryCount,theOtherValidateState);
        when(accessCodeCreator.createAccessCode()).thenReturn(AccessCode.from(theOtherAccessCode));

        final ValidationSessionDto result = sut.createValidation(requestValidationSessionDto,sendVerifyMailRequest);

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
        final ValidationSessionDto validationSessionDto = new ValidationSessionDto(commonConstraintTryCount,commonEmail,accessCode,tryCount,validateState);
        final VerifyAccessCodeRequest request = new VerifyAccessCodeRequest(requestAccessCode);
        final ValidationSessionDto expect = new ValidationSessionDto(commonConstraintTryCount,commonEmail,accessCode,expectTryCount,expectValidateState);

        final ValidationSessionDto result = sut.updateValidation(validationSessionDto,request);

        assertThat(result.getConstraintTryCount()).isEqualTo(expect.getConstraintTryCount());
        assertThat(result.getEmail()).isEqualTo(expect.getEmail());
        assertThat(result.getAccessCode()).isEqualTo(expect.getAccessCode());
        assertThat(result.getValidateTryCount()).isEqualTo(expect.getValidateTryCount());
        assertThat(result.getValidateState()).isEqualTo(expect.getValidateState());
    }

    @Test
    @Transactional
    void 인증정보가_완료상태라면_유저를_생성할수있다(){
        final String email = "dfgdfg@ergreg.rg";
        final String memberId = "dgdereds";
        final String password = "DSFDsdsf#$#@$324";
        final ValidationSessionDto validationSessionDto = new ValidationSessionDto(3,email,"DSFD33",4,"SUCCESS");
        final SignUpRequest signUpRequest = new SignUpRequest(memberId,password);
        final SignUpResponse expect = new SignUpResponse(memberId);
        final MemberId expectMemberId = MemberId.from(memberId);
        final Auth expectAuth = Auth.of(expectMemberId,RawPassword.from(password));
        final EncryptedPassword expectEncryptedPassword = EncryptedPassword.from("$2a$10$CacO40Z5mg6C7QigZFqXn.1hz4Zw4OXphrdNmxhfK2SmFmkt7jXD2");
        final EncryptedAuth expectEncryptedAuth = EncryptedAuth.of(expectMemberId,expectEncryptedPassword);
        final EncryptedAuthEntity expectEncryptedAuthEntity = EncryptedAuthEntity.from(expectEncryptedAuth);
        when(encryptor.encrypt(expectAuth)).thenReturn(expectEncryptedAuth);

        final SignUpResponse resultReturn = sut.createAuth(validationSessionDto,signUpRequest);

        assertThat(resultReturn.getMemberId()).isEqualTo(expect.getMemberId());
        final EncryptedAuthEntity resultEncryptedAuthEntity = encryptedAuthEntityRepository.findByMemberId(memberId).orElseThrow(()->new RuntimeException("테스트 실패"));
        assertThat(resultEncryptedAuthEntity.getMemberId()).isEqualTo(expectEncryptedAuthEntity.getMemberId());
        assertThat(resultEncryptedAuthEntity.getEncryptedPassword()).isEqualTo(expectEncryptedAuthEntity.getEncryptedPassword());
    }


}
