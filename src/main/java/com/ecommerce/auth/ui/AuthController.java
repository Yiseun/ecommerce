package com.ecommerce.auth.ui;

import com.ecommerce.auth.AuthService;
import com.ecommerce.auth.dto.PrevalidationSessionDto;
import com.ecommerce.auth.dto.SendVerifyMailRequest;
import com.ecommerce.auth.dto.SendVerifyMailRequestBody;
import com.ecommerce.auth.port.AuthClientRegistry;
import com.ecommerce.auth.ui.session.PrevalidationSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthClientRegistry authClientRegistry;
    private final AuthService authService;
    @PostMapping("/sendMail")
    public ResponseEntity<Void> sendVerifyMail(final PrevalidationSession prevalidationSession, @RequestBody final SendVerifyMailRequestBody body){
        final PrevalidationSessionDto response = authService.createPrevalidation(prevalidationSession.getPrevalidationSessionData(), SendVerifyMailRequest.of(authClientRegistry.getCreatePrevalidationClient(),body));
        prevalidationSession.update(response);
        return ResponseEntity.ok().build();
    }
}
