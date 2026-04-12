package com.ecommerce.notification;

import com.ecommerce.notification.dto.*;
import com.ecommerce.notification.exception.NotificationException;
import com.ecommerce.notification.exception.UnmanagedDependencyException;
import com.ecommerce.notification.persistence.NotificationEntity;
import com.ecommerce.notification.persistence.NotificationRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Retryable(retryFor = {UnmanagedDependencyException.class})
    @Transactional
    public void unsubscribe(final UnsubscribeRequest request){
        NotificationEntity requestNotification = NotificationEntity.of(request.getMemberId(),request.getTopicId(),request.getToken());
        NotificationEntity serverNotification = notificationRepository.findByMemberIdAndTopic(requestNotification.getMemberId(),requestNotification.getTopic());
        NotificationEntity resultNotification = serverNotification.delete();
        notificationRepository.save(resultNotification);
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(request.getToken()), request.getTopicId());
        } catch (FirebaseMessagingException e) {
            throw new UnmanagedDependencyException(e.getMessage());
        }
    }

    @Recover
    @Transactional
    public void unsubscribeRecover(final UnmanagedDependencyException e, final UnsubscribeRequest request){
        NotificationEntity requestNotification = NotificationEntity.of(request.getMemberId(), request.getTopicId(), request.getToken());
        NotificationEntity serverNotification = notificationRepository.findByMemberIdAndTopic(requestNotification.getMemberId(),requestNotification.getTopic());
        NotificationEntity resultNotification = serverNotification.pending();
        notificationRepository.save(resultNotification);
    }

    @Transactional
    public List<Long> findAllPendingTasks(){
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByState("pending");
        return notificationEntities.stream().map(i->i.getNotificationId()).toList();
    }

    @Transactional
    public void unsubscribeForFailTask(final Long notifiactionId) throws FirebaseMessagingException {
        NotificationEntity notification = notificationRepository.findById(notifiactionId).orElseThrow(()->new NotificationException("잠시후 다시 시도해주세요."));
        NotificationEntity resultNotification = notification.delete();
        notificationRepository.save(resultNotification);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(notification.getToken()), notification.getTopic());
    }

    @Transactional
    public void subscribe(final SubscribeRequest request){
        try {
            NotificationEntity requestNotification = NotificationEntity.init(request.getMemberId(),request.getTopicId(),request.getToken());
            notificationRepository.save(requestNotification);
            FirebaseMessaging.getInstance().subscribeToTopic(
                    List.of(request.getToken()), request.getTopicId());
        } catch (FirebaseMessagingException e) {
            throw new NotificationException("잠시후 다시 시도해주세요.");
        }
    }

    @Transactional
    public void sendMessage(final SendMessageRequest request){
        final int pageSize = 500;
        int pageNumber = 0;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Slice<NotificationEntity> sliceResults;
        do{
            sliceResults = notificationRepository.findByTopic(request.getTopicId(), pageRequest);
            List<String> targetTokens = sliceResults.stream().filter(i->i.isSubscribe())
                                                             .map(i->i.getToken())
                                                             .toList();
            MulticastMessage message = MulticastMessage.builder().addAllTokens(targetTokens)
                                                                 .setNotification(Notification.builder()
                                                                                              .setTitle(request.getTitle())
                                                                                              .setBody(request.getBody())
                                                                                              .build())
                                                                 .build();

            try {
                FirebaseMessaging.getInstance().sendMulticast(message);
            } catch (FirebaseMessagingException e) {
                throw new NotificationException("알림전송에 실패했습니다.");
            }

        }while(sliceResults.hasNext());
    }
}
