package com.ecommerce.auth.domain.sessiondata.prevalidation;

import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import com.ecommerce.auth.exception.application.domain.business.OutOfTryCountException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class ValidateInfo {
    private static final int DEFAULT_TRY_COUNT = 10;
    private static final int MINIMUM_TRY_COUNT = 0;
    private static final int TRY_COUNT_REDUCED_PER_TRY = 1;
    private final AccessCode accessCode;
    private final Integer tryCount;
    private final ValidateState validateState;

    private ValidateInfo(final AccessCode accessCode, final Integer tryCount, final ValidateState validateState){
        this.accessCode = validate(accessCode);
        this.tryCount = tryCount;
        this.validateState = validateState;
    }
    private AccessCode validate(final AccessCode accessCode){
        if(accessCode==null){
            throw new InvalidConstructionException("accessCode는 필수조건입니다.");
        }
        return accessCode;
    }
    public boolean isEmpty(){
        return this.accessCode.isEmpty();
    }
    public String getValidateState(){
        if(this.validateState==null){
            return null;
        }
        return this.validateState.name();
    }

    public boolean isComplete(){
        if(this.validateState==null){
            return false;
        }
        return this.validateState.equals(ValidateState.SUCCESS);
    }
    public ValidateInfo update(final ValidateInfo validateInfo){
        if(validateInfo==null){
            throw new InvalidConstructionException("요청은 null일수 없습니다.");
        }
        if(this.accessCode.isEmpty()||this.tryCount==null||this.validateState==null){
            throw new InvalidConstructionException("검증정보가 초기화되지 않았습니다.");
        }
        if(this.validateState.equals(ValidateState.SUCCESS)){
            return new ValidateInfo(this.accessCode,this.tryCount,this.validateState);
        }
        if(this.tryCount<=MINIMUM_TRY_COUNT){
            throw new OutOfTryCountException("더이상 시도할수 없습니다.");
        }
        final Integer resultTryCount = this.tryCount-TRY_COUNT_REDUCED_PER_TRY;
        if(this.accessCode.equals(validateInfo.accessCode)){
            return new ValidateInfo(this.accessCode,resultTryCount,ValidateState.SUCCESS);
        }
        return new ValidateInfo(this.accessCode,resultTryCount,ValidateState.FAIL);
    }
    public static ValidateInfo init(final AccessCode accessCode){
        return new ValidateInfo(accessCode,DEFAULT_TRY_COUNT,ValidateState.PREPARE);
    }

    public static ValidateInfo of(final AccessCode accessCode,final Integer tryCount,final String validateState){
        return new ValidateInfo(accessCode, tryCount, ValidateState.from(validateState));
    }

    private enum ValidateState{
        PREPARE,
        FAIL,
        SUCCESS;

        public static ValidateState from(final String value){
            if(value==null){
                return null;
            }
            try{
                return ValidateState.valueOf(value);
            }catch (IllegalArgumentException e){
                throw new InvalidConstructionException("존재하지 않는 값입니다.");
            }
        }
    }
}
