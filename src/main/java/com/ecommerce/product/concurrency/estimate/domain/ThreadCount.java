package com.ecommerce.product.concurrency.estimate.domain;

import lombok.Getter;

@Getter
public class ThreadCount {
    private static final long DEFAULT_THREAD_COUNT = 0;
    private static final long DEFAULT_THREAD_COUNT_CHANGE_AT_ONCE = 1;
    private final Long activeThreadCount;
    private ThreadCount(final Long value){
        this.activeThreadCount = value;
    }

    public boolean isConcurrency(){
        return this.activeThreadCount>DEFAULT_THREAD_COUNT;
    }

    public ThreadCount update(final boolean isIncrease){
        if(isIncrease){
            return new ThreadCount(activeThreadCount+DEFAULT_THREAD_COUNT_CHANGE_AT_ONCE);
        }
        return new ThreadCount(activeThreadCount-DEFAULT_THREAD_COUNT_CHANGE_AT_ONCE);
    }

    public static ThreadCount init(){
        return new ThreadCount(DEFAULT_THREAD_COUNT);
    }

    public static ThreadCount createEmpty(){
        return new ThreadCount(null);
    }

    public static ThreadCount from(final Long value){
        return new ThreadCount(value);
    }
}
