package com.ecommerce.notificationservice.service;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.model.Notification;
import com.ecommerce.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getNotificationsByStatus(String status) {
        return notificationRepository.findByStatus(Notification.Status.valueOf(status.toUpperCase()));
    }

    public Notification sendNotification(NotificationDTO dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setRecipientEmail(dto.getRecipientEmail());
        notification.setSubject(dto.getSubject());
        notification.setMessage(dto.getMessage());
        notification.setType(Notification.NotificationType.valueOf(dto.getType().toUpperCase()));
        notification.setChannel(Notification.Channel.valueOf(
                dto.getChannel() != null ? dto.getChannel().toUpperCase() : "EMAIL"));
        notification.setReferenceId(dto.getReferenceId());
        notification.setReferenceType(dto.getReferenceType());

        // Simulate sending the notification
        try {
            log.info("[NOTIFICATION] Sending {} via {} to userId={} | Subject: {}",
                    notification.getType(), notification.getChannel(),
                    notification.getUserId(), notification.getSubject());

            // In a real system, this would call an SMTP / SMS / Push gateway
            notification.setStatus(Notification.Status.SENT);
            notification.setSentAt(LocalDateTime.now());
        } catch (Exception e) {
            notification.setStatus(Notification.Status.FAILED);
            notification.setFailureReason(e.getMessage());
        }

        return notificationRepository.save(notification);
    }

    public Notification retryNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
        if (notification.getStatus() != Notification.Status.FAILED) {
            throw new RuntimeException("Only FAILED notifications can be retried");
        }
        log.info("[NOTIFICATION] Retrying notification id={}", id);
        notification.setStatus(Notification.Status.SENT);
        notification.setSentAt(LocalDateTime.now());
        notification.setFailureReason(null);
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
