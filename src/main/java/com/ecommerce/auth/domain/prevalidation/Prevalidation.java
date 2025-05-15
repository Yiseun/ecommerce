package com.ecommerce.auth.domain.prevalidation;

import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Prevalidation {
    private final Email email;
    private final ValidateInfo validateInfo;
    private Prevalidation(final Email email, final ValidateInfo validateInfo){
        this.email = validate(email);
        this.validateInfo = validate(validateInfo);
    }
    private Email validate(final Email email){
        if(email==null){
            throw new InvalidConstructionException("email은 필수조건입니다.");
        }
        return email;
    }
    private ValidateInfo validate(final ValidateInfo validateInfo){
        if(validateInfo==null){
            throw new InvalidConstructionException("validateInfo는 필수조건입니다.");
        }
        return validateInfo;
    }
    public boolean isEmpty(){
        return this.email.isEmpty() || this.validateInfo.isEmpty();
    }

    public Prevalidation update(final Prevalidation request){
        if(request==null){
            throw new InvalidConstructionException("입력이 존재하지 않습니다.");
        }
        if(this.email.isEmpty()){
            throw new InvalidConstructionException("이메일이 초기화되지 않았습니다.");
        }
        final ValidateInfo resultValidateInfo = this.validateInfo.update(request.validateInfo);
        return new Prevalidation(this.email,resultValidateInfo);
    }
    public static Prevalidation of(final Email email,final ValidateInfo validateInfo){
        return new Prevalidation(email,validateInfo);
    }
}
