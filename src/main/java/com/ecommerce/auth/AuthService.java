package com.ecommerce.auth;

import com.ecommerce.auth.domain.PrevalidationSessionData;
import com.ecommerce.auth.dto.PrevalidationSessionDto;
import com.ecommerce.auth.dto.SendVerifyMailRequest;
import com.ecommerce.auth.dto.VerifyAccessCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AccessCodeCreator accessCodeCreator;
    @Transactional
    public PrevalidationSessionDto createPrevalidation(final PrevalidationSessionDto serverData, final SendVerifyMailRequest request){
        final PrevalidationSessionData requestPrevalidationData = request.toPrevalidationSessionData(accessCodeCreator);
        final PrevalidationSessionData serverPrevalidationData = serverData.toPrevalidationSessionData();
        final PrevalidationSessionData resultPrevalidationSessionData = serverPrevalidationData.substitute(requestPrevalidationData);
        request.getClient().sendMessage(resultPrevalidationSessionData.getPrevalidation());
        return PrevalidationSessionDto.from(resultPrevalidationSessionData);
    }

    public PrevalidationSessionDto updatePrevalidation(final PrevalidationSessionDto serverData, final VerifyAccessCodeRequest request){
        final PrevalidationSessionData requestPrevalidationSessionData = request.toPrevalidationSessionData();
        final PrevalidationSessionData serverPrevalidationSessionData = serverData.toPrevalidationSessionData();
        return PrevalidationSessionDto.from(serverPrevalidationSessionData.update(requestPrevalidationSessionData));
    }
}
