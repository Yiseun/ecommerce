package com.ecommerce.notification;

import com.ecommerce.notification.dto.SendMessageRequest;
import com.ecommerce.notification.dto.SubscribeRequest;
import com.ecommerce.notification.dto.UnsubscribeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(final UnsubscribeRequest request){
        notificationService.unsubscribe(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(final SubscribeRequest request){
        notificationService.subscribe(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(final SendMessageRequest request){
        notificationService.sendMessage(request);
        return ResponseEntity.ok().build();
    }
}
