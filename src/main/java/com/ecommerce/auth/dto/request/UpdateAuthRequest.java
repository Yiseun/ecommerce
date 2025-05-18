package com.ecommerce.auth.dto.request;

import com.ecommerce.auth.dto.request.body.UpdateAuthRequestBody;
import com.ecommerce.auth.port.UpdateAuthClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateAuthRequest {
    private final UpdateAuthRequestBody body;
    private final UpdateAuthClient client;

    public static UpdateAuthRequest of(final UpdateAuthRequestBody body,final UpdateAuthClient client){
        return new UpdateAuthRequest(body,client);
    }
}
