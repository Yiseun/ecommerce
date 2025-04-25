package com.ecommerce.payment.domain;

import com.ecommerce.payment.exception.domain.FailedCreationException;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public enum PgProvider {
    KAKAO,
    KICC,
    NAVER;

    private static Map<PayMethod, List<PgProvider>> enablePgProvier = new HashMap<>();

    static{
        enablePgProvier.put(PayMethod.CARD,List.of(PgProvider.KAKAO,PgProvider.KICC,PgProvider.NAVER));
        enablePgProvier.put(PayMethod.MOBILE,List.of(PgProvider.KAKAO,PgProvider.KICC,PgProvider.NAVER));
        enablePgProvier.put(PayMethod.TRANSFER,List.of(PgProvider.KAKAO,PgProvider.KICC,PgProvider.NAVER));
        enablePgProvier.put(PayMethod.VIRTURAL_ACCOUNT,List.of(PgProvider.KAKAO,PgProvider.KICC,PgProvider.NAVER));
    }


    public static PgProvider from(final String value){
        try {
            return PgProvider.valueOf(value);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new FailedCreationException("올바르지않은 PgProvider입니다.");
        }
    }

    public static PgProvider createDefault(final PayMethod payMethod){
        final List<PgProvider> pgProviders = enablePgProvier.get(payMethod);
        if(pgProviders==null){
            throw new FailedCreationException("적절한 PgProvider를 선택할수 없습니다.");
        }
        return pgProviders.stream().findFirst().orElseThrow(()->new FailedCreationException("적절한 PgProvider를 선택할수 없습니다."));
    }

}
