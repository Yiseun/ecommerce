package com.ecommerce.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0/10 * * * *")
    public void retryFailedTask() throws FirebaseMessagingException {
        List<Long> pendingTaskIds = notificationService.findAllPendingTasks();
        for(Long pendingTaskId : pendingTaskIds){
            notificationService.unsubscribeForFailTask(pendingTaskId);
        }
    }
}
