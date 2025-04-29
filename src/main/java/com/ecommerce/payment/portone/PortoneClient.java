package com.ecommerce.payment.portone;

import com.ecommerce.payment.dto.FindPortoneResponse;
import com.ecommerce.payment.dto.internal.InternalPaymentPurchaseRequest;
import com.ecommerce.payment.dto.request.CreatePaymentSessionRequest;
import com.ecommerce.payment.portone.exception.ConnectionFailureException;
import com.ecommerce.payment.portone.exception.http.HttpStatus;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PortoneClient {

    private final IamportClient iamportClient;
    public void prepare(final CreatePaymentSessionRequest request){
        final PortonePrepareData portonePrepareData = PortonePrepareData.from(request);
        try {
            iamportClient.postPrepare(portonePrepareData.getPrepareData());
        } catch (IOException e) {
            throw new ConnectionFailureException(e.getMessage(),e.getCause());
        } catch (IamportResponseException e) {
            throw HttpStatus.createHttpStatusExceptionBy(e.getHttpStatusCode());
        }
    }

    public FindPortoneResponse findBy(final InternalPaymentPurchaseRequest request){
        try {
            return FindPortoneResponse.from(iamportClient.paymentByImpUid(request.getImpUid()));
        } catch (IamportResponseException e) {
            throw HttpStatus.createHttpStatusExceptionBy(e.getHttpStatusCode());
        } catch (IOException e) {
            throw new ConnectionFailureException(e.getMessage(),e.getCause());
        }
    }
}
