package com.ecommerce.payment.portone;

import com.ecommerce.payment.dto.request.CreatePaymentSessionRequest;
import com.ecommerce.payment.portone.exception.TypeCastException;
import com.siot.IamportRestClient.request.PrepareData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class PortonePrepareData {
    private final PrepareData prepareData;

    public static PortonePrepareData from(final CreatePaymentSessionRequest request){
        try {
            final String merchantUid = request.getBody().getOrderId();
            final BigDecimal amount = BigDecimal.valueOf(Long.parseLong(request.getBody().getTotalPrice()));
            return new PortonePrepareData(new PrepareData(merchantUid,amount));
        }catch (NumberFormatException e){
            throw new TypeCastException("요청 형식이 잘못됐습니다");
        }
    }
}
