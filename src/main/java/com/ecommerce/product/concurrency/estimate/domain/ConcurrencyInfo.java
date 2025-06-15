package com.ecommerce.product.concurrency.estimate.domain;

import com.ecommerce.product.concurrency.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class ConcurrencyInfo {
    private final Long concurrencyId;
    @EqualsAndHashCode.Include
    private final Long productId;
    private final ThreadCount threadCount;

    private ConcurrencyInfo(final Long concurrencyId,
                            final Long productId,
                            final ThreadCount activeThreadCount){
        this.concurrencyId = concurrencyId;
        this.productId = validate(productId);
        this.threadCount = validate(activeThreadCount);
    }

    private Long validate(final Long productId){
        if(productId==null){
            throw new InvalidConstructionException( "productId는 필수입력입니다.");
        }
        return productId;
    }

    private ThreadCount validate(final ThreadCount threadCount){
        if(threadCount==null){
            throw new InvalidConstructionException("threadCount는 필수입력입니다.");
        }
        return threadCount;
    }

    public boolean isConcurrency(){
        return this.threadCount.isConcurrency();
    }

    public ConcurrencyInfo update(final ConcurrencyInfo serverConcurrencyInfo, final boolean isIncrease){
        if(serverConcurrencyInfo==null){ // 이거 Entity에서 ThreadCount를 만들어도 똑같이 이렇게된다
            final ThreadCount resultThreadCount = ThreadCount.init().update(isIncrease);
            return new ConcurrencyInfo(this.concurrencyId,this.productId,resultThreadCount);
        }
        final ThreadCount resultThreadCount = serverConcurrencyInfo.threadCount.update(isIncrease);
        return new ConcurrencyInfo(this.concurrencyId,this.productId,resultThreadCount);
    }

    public static ConcurrencyInfo from(final Long productId){
        return new ConcurrencyInfo(null,productId,ThreadCount.createEmpty());
    }
    public static ConcurrencyInfo of(final Long concurrencyId,final Long productId,final ThreadCount threadCount){
        return new ConcurrencyInfo(concurrencyId,productId,threadCount);
    }
}
