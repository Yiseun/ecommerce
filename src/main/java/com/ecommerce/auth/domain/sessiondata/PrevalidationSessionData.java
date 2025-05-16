package com.ecommerce.auth.domain.sessiondata;

import com.ecommerce.auth.domain.sessiondata.prevalidation.Prevalidation;
import com.ecommerce.auth.exception.application.domain.business.BusinessLogicException;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.Getter;

@Getter
public class PrevalidationSessionData {
    private final Prevalidation prevalidation;
    private final Constraint constraint;

    private PrevalidationSessionData(final Prevalidation prevalidation,final Constraint constraint){
        this.prevalidation = validate(prevalidation);
        this.constraint = validate(constraint);
    }
    private Prevalidation validate(final Prevalidation prevalidation){
        if(prevalidation==null){
            throw new InvalidConstructionException("prevalidation이 null일수 없습니다.");
        }
        return prevalidation;
    }
    private Constraint validate(final Constraint constraint){
        if(constraint==null){
            throw new InvalidConstructionException("constraint가 null일수 없습니다.");
        }
        return constraint;
    }

    public boolean isComplete(){
        return this.prevalidation.isComplete();
    }

    public PrevalidationSessionData substitute(final PrevalidationSessionData request){
        if(request==null){
            throw new InvalidConstructionException("갱신할 정보가 비어있습니다.");
        }
        if(request.prevalidation.isEmpty()){
            throw new BusinessLogicException("요청정보가 존재하지 않습니다.");
        }
        final Constraint resultConstraint = this.constraint.update(request.getConstraint());
        return new PrevalidationSessionData(request.prevalidation,resultConstraint);
    }

    public PrevalidationSessionData update(final PrevalidationSessionData request){
        if(request==null){
            throw new InvalidConstructionException("입력이 존재하지 않습니다.");
        }
        if(this.constraint.isEmpty()){
            throw new InvalidConstructionException("제약조건이 초기화되지 않았습니다.");
        }
        final Prevalidation resultPrevalidation = this.prevalidation.update(request.prevalidation);
        return new PrevalidationSessionData(resultPrevalidation,this.constraint);
    }

    public static PrevalidationSessionData of(final Prevalidation prevalidation,final Constraint constraint){
        return new PrevalidationSessionData(prevalidation, constraint);
    }
}
