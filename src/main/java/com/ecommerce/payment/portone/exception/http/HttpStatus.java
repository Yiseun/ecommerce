package com.ecommerce.payment.portone.exception.http;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public enum HttpStatus {
    INVALIDREQUEST(400,()->new InvalidRequestException("올바르지않은 요청입니다.")),
    UNAUTHORIZED(401,()->new HttpStatusException("인증정보가 올바르지않습니다.")),
    FORBIDDEN(403,()->new ForbiddenException("요청이 거절됐습니다.")),
    ALREADYPAID(409,()->new AlreadyPaidException("이미 결제된 주문입니다."));

    private final int statusCode;
    private final Supplier<HttpStatusException> exceptionSupplier;

    private static final Map<Integer, HttpStatus> httpStatusMap = new HashMap<>();

    static{
        for(HttpStatus value: HttpStatus.values()){
            httpStatusMap.put(value.statusCode,value);
        }
    }
    public static HttpStatusException createHttpStatusExceptionBy(final int statusCode){
        final HttpStatus httpStatus = httpStatusMap.get(statusCode);
        if(httpStatus==null){
            throw new UndefinedException("예상하지못한 Portone Exception이 발생했습니다.");
        }
        return httpStatus.exceptionSupplier.get();
    }
}
