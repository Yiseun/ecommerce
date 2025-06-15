package com.ecommerce.product.concurrency.pending.domain;

import com.ecommerce.product.concurrency.exception.application.domain.InvalidConstructionException;

public enum PendingTaskStatus {
    INITIAL_SEND_COMPLETE {
        @Override
        public PendingTaskStatus update(PendingTaskStatus requestStatus) {
            if(this.equals(requestStatus)){
                return LISTENER_ACCEPT;
            }
            return this;
        }
    },
    LISTENER_ACCEPT {
        @Override
        public PendingTaskStatus update(PendingTaskStatus requestStatus) {
            if(this.equals(requestStatus)){
                return TASK_COMPLETE;
            }
            if(requestStatus.equals(INITIAL_SEND_COMPLETE)){
                return DUPLICATED;
            }
            return TASK_FAILED;
        }
    },
    DUPLICATED {
        @Override
        public PendingTaskStatus update(PendingTaskStatus requestStatus) {
            return this;
        }
    },
    TASK_FAILED {
        @Override
        public PendingTaskStatus update(PendingTaskStatus requestStatus) {
            return this;
        }
    },
    TASK_COMPLETE {
        @Override
        public PendingTaskStatus update(PendingTaskStatus requestStatus) {
            return this;
        }
    };
    public abstract PendingTaskStatus update(final PendingTaskStatus requestStatus);

    public boolean isApplyable(){
        return this.equals(LISTENER_ACCEPT);
    }

    public static PendingTaskStatus from(final String value){
        try{
            return PendingTaskStatus.valueOf(value);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new InvalidConstructionException("올바르지않은 입력입니다.");
        }
    }

    public static PendingTaskStatus init(){
        return PendingTaskStatus.INITIAL_SEND_COMPLETE;
    }

}
