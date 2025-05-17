package com.ecommerce.auth.domain.sessiondata;

import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.exception.application.domain.business.BusinessLogicException;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.Getter;

@Getter
public class ValidationSessionData {
    private final Validation validation;
    private final Constraint constraint;

    private ValidationSessionData(final Validation validation, final Constraint constraint){
        this.validation = validate(validation);
        this.constraint = validate(constraint);
    }
    private Validation validate(final Validation validation){
        if(validation ==null){
            throw new InvalidConstructionException("prevalidation이 null일수 없습니다.");
        }
        return validation;
    }
    private Constraint validate(final Constraint constraint){
        if(constraint==null){
            throw new InvalidConstructionException("constraint가 null일수 없습니다.");
        }
        return constraint;
    }

    public boolean isComplete(){
        return this.validation.isComplete();
    }

    public ValidationSessionData substitute(final ValidationSessionData request){
        if(request==null){
            throw new InvalidConstructionException("갱신할 정보가 비어있습니다.");
        }
        if(request.validation.isEmpty()){
            throw new BusinessLogicException("요청정보가 존재하지 않습니다.");
        }
        final Constraint resultConstraint = this.constraint.update(request.getConstraint());
        return new ValidationSessionData(request.validation,resultConstraint);
    }

    public ValidationSessionData update(final ValidationSessionData request){
        if(request==null){
            throw new InvalidConstructionException("입력이 존재하지 않습니다.");
        }
        if(this.constraint.isEmpty()){
            throw new InvalidConstructionException("제약조건이 초기화되지 않았습니다.");
        }
        final Validation resultValidation = this.validation.update(request.validation);
        return new ValidationSessionData(resultValidation,this.constraint);
    }

    public static ValidationSessionData of(final Validation validation, final Constraint constraint){
        return new ValidationSessionData(validation, constraint);
    }
}
