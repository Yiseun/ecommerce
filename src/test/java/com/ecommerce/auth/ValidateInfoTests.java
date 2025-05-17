package com.ecommerce.auth;

import static org.assertj.core.api.Assertions.*;
import com.ecommerce.auth.domain.sessiondata.validation.AccessCode;
import com.ecommerce.auth.domain.sessiondata.validation.ValidateInfo;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import com.ecommerce.auth.exception.application.domain.business.OutOfTryCountException;
import org.junit.jupiter.api.Test;

public class ValidateInfoTests {

    @Test
    void 인증코드가_비어있을때_빈것으로_간주한다(){
        final AccessCode accessCode = AccessCode.from(null);
        final ValidateInfo sut = ValidateInfo.of(accessCode,23,"SUCCESS");
        final boolean expect = true;

        assertThat(sut.isEmpty()).isEqualTo(expect);
    }

    @Test
    void 매개변수_자체가_비어있을때는_모든조건을_고려하지않고_실패한다(){
        final AccessCode accessCode = AccessCode.from("DFDGFE2");
        final ValidateInfo request = null;
        final ValidateInfo sut = ValidateInfo.of(accessCode,34,"SUCCESS");

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(InvalidConstructionException.class);
    }

    @Test
    void 매개변수의_시도횟수는_고려하지_않는다(){
        final AccessCode accessCode = AccessCode.from("AAAAAA");
        final AccessCode theOtherAccessCode = AccessCode.from("BBBBBB");
        final ValidateInfo request = ValidateInfo.of(accessCode,534,null);
        final ValidateInfo sut = ValidateInfo.of(theOtherAccessCode,2,"PREPARE");
        final ValidateInfo expect = ValidateInfo.of(theOtherAccessCode,1,"FAIL");

        final ValidateInfo result = sut.update(request);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    void 매개변수의_검증상태는_고려하지_않는다(){
        final AccessCode accessCode = AccessCode.from("AAAAAA");
        final AccessCode theOtherAccessCode = AccessCode.from("BBBBBB");
        final ValidateInfo request = ValidateInfo.of(accessCode,0,"SUCCESS");
        final ValidateInfo sut = ValidateInfo.of(theOtherAccessCode,3,"FAIL");
        final ValidateInfo expect = ValidateInfo.of(theOtherAccessCode,2,"FAIL");

        final ValidateInfo result = sut.update(request);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    void 기존_남은횟수가_부족한게아니고_지금의_시도로_남은횟수가_다_떨어지게된다면_실패하지않고_결과를_반환한다(){//이거 너무 구체적인가 //여기껀 구체적인걸 알아도되지 오히려 이게 방어선역할을 한다
        final AccessCode accessCode = AccessCode.from("AAAAAA");
        final AccessCode theOtherAccessCode = AccessCode.from("BBBBB");
        final ValidateInfo request = ValidateInfo.of(accessCode,null,null);
        final ValidateInfo sut = ValidateInfo.of(theOtherAccessCode,1,"PREPARE");
        final ValidateInfo expect = ValidateInfo.of(theOtherAccessCode,0,"FAIL");

        final ValidateInfo result = sut.update(request);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    void 기존_검증상태가_성공이_아닐때_남은횟수가_없다면_인증코드의_일치여부와_무관하게_실패한다(){
        final AccessCode commonAccessCode = AccessCode.from("DFJE22D");
        final ValidateInfo request = ValidateInfo.of(commonAccessCode,null,null);
        final ValidateInfo sut = ValidateInfo.of(commonAccessCode,0,"PREPARE");

        assertThatThrownBy(()->sut.update(request)).isInstanceOf(OutOfTryCountException.class);
    }
    
    @Test
    void 기존_검증상태가_성공이라면_남은횟수는_고려하지_않고_멱등성을_유지한다(){
        final AccessCode commonAccessCode = AccessCode.from("DFGFGE32");
        final ValidateInfo request = ValidateInfo.of(commonAccessCode,null,null);
        final ValidateInfo sut = ValidateInfo.of(commonAccessCode,0,"SUCCESS");
        final ValidateInfo expect = ValidateInfo.of(commonAccessCode,0,"SUCCESS");

        final ValidateInfo result = sut.update(request);

        assertThat(result).isEqualTo(expect);
    }

    @Test
    void 기존_검증상태가_성공이라면_인증코드의_일치여부를_고려하지_않고_멱등성을_유지한다(){
        final AccessCode accessCode = AccessCode.from("AAAAAAA");
        final AccessCode theOtherAccessCode = AccessCode.from("BBBBBBB");
        final ValidateInfo request = ValidateInfo.of(accessCode,null,null);
        final ValidateInfo sut = ValidateInfo.of(theOtherAccessCode,323,"SUCCESS");
        final ValidateInfo expect = ValidateInfo.of(theOtherAccessCode,323,"SUCCESS");

        final ValidateInfo result = sut.update(request);

        assertThat(result).isEqualTo(expect);
    }

}
