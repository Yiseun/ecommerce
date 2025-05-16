package com.ecommerce.auth.domain.sessiondata;

import com.ecommerce.auth.exception.application.domain.business.BusinessLogicException;
import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Constraint {
    private static final int DEFAULT_TRY_COUNT = 30;
    private static final int MINIMUM_TRY_COUNT = 0;
    private static final int TRY_COUNT_REDUCED_PER_TRY  = 1;
    private final Integer tryCount;
    private Constraint(final Integer tryCount){
        this.tryCount = tryCount;
    }

    public Constraint update(final Constraint constraint){
        if(this.tryCount==null){
            throw new InvalidConstructionException("tryCount가 초기화되지 않았습니다.");
        }
        if(constraint==null){
            throw new InvalidConstructionException("요청값이 비어있습니다.");
        }
        final Integer resultTryCount = this.tryCount-TRY_COUNT_REDUCED_PER_TRY;
        if(resultTryCount<MINIMUM_TRY_COUNT){
            throw new BusinessLogicException("더이상 시도할수없습니다. 잠시후 다시 시도해주세요");
        }
        return new Constraint(resultTryCount);
    }

    public boolean isEmpty(){
        return this.tryCount == null;
    }

    private static Constraint createDafaultConstraint(){
        return new Constraint(DEFAULT_TRY_COUNT);
    }
    public static Constraint from(final Integer value){
        if(value==null){
           return Constraint.createDafaultConstraint();
        }
        return new Constraint(value);
    }
    public static Constraint createEmpty(){
        return new Constraint(null);
    }

}
