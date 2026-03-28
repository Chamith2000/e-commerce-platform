package com.ecommerce.notificationservice.repository;

import com.ecommerce.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByStatus(Notification.Status status);
    List<Notification> findByType(Notification.NotificationType type);
    List<Notification> findByReferenceIdAndReferenceType(String referenceId, String referenceType);
}
