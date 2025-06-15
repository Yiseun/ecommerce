package com.ecommerce.product.domain;

import com.ecommerce.product.exception.application.domain.BusinessLogicException;
import com.ecommerce.product.exception.application.domain.FailedCreationException;
import lombok.Getter;

@Getter
public class Quantity {
    private static final long MINIMUM_QUANTITY = 0;
    private final Long value;
    private Quantity(){
        this.value = null;
    }

    private Quantity(final Long value){
        this.value = value;
    }
    private Quantity(final String value){
        this.value = parse(value);
    }
    private Long parse(final String value){
        try{
            final Long longValue = Long.parseLong(value);
            if(longValue<MINIMUM_QUANTITY){
                throw new FailedCreationException("수량은 "+MINIMUM_QUANTITY+"보다 작을수없습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("수량이 비어있습니다.");
        }
    }

    public void validate(final Quantity request){
        if(this.value<request.value){
            throw new BusinessLogicException("수량이 부족합니다.");
        }
    }

    public Quantity update(final Quantity request){
        if(request==null){
            return this;
        }
        final long result = this.value- request.value;
        if(result<MINIMUM_QUANTITY){
            throw new BusinessLogicException("수량이 부족합니다.");
        }
        return new Quantity(result);
    }

    public static Quantity from(final String value){
        return new Quantity(value);
    }
    public static Quantity createEmpty(){
        return new Quantity();
    }

}
