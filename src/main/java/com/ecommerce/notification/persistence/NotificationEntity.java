package com.ecommerce.notification.persistence;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity {
    private Long notificationId;
    private String memberId;
    private String topic;
    private String token;
    private String state;

    public boolean isSubscribe(){
        return this.state.equals("subscribe");
    }

    public NotificationEntity pending(){
        return new NotificationEntity(this.notificationId,this.memberId,this.topic,this.token,"pending");
    }

    public NotificationEntity delete(){
        return new NotificationEntity(this.notificationId,this.memberId,this.topic,this.token,"unsubscribe");
    }

    public static NotificationEntity of(final String memberId,final String topic,final String token){
        return new NotificationEntity(null,memberId,topic,token,null);
    }

    public static NotificationEntity init(final String memberId,final String topic,final String token){
        return new NotificationEntity(null,memberId,topic,token,"subscribe");
    }
}
