package com.ecommerce.auth.domain;

import com.ecommerce.auth.domain.prevalidation.Prevalidation;
import com.ecommerce.auth.exception.application.domain.BusinessLogicException;
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
    public PrevalidationSessionData substitute(final PrevalidationSessionData request){
        if(request==null||request.prevalidation.isEmpty()){
            throw new BusinessLogicException("갱신할 정보가 비어있습니다.");
        }
        final Constraint resultConstraint = this.constraint.update(request.getConstraint());
        return new PrevalidationSessionData(request.getPrevalidation(),resultConstraint);
    }

    public static PrevalidationSessionData of(final Prevalidation prevalidation,final Constraint constraint){
        return new PrevalidationSessionData(prevalidation, constraint);
    }
}
