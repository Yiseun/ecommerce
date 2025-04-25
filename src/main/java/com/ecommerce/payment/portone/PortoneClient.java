package com.ecommerce.payment.portone;

import com.ecommerce.payment.dto.CreatePaymentSessionRequest;
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
}
