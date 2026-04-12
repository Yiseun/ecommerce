package com.ecommerce.notification.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {

    NotificationEntity findByMemberIdAndTopic(String memberId,String topic);
    List<NotificationEntity> findAllByState(String state);
    Slice<NotificationEntity> findByTopic(String Topic, Pageable pageable);
}
