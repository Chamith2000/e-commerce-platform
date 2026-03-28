package com.ecommerce.notificationservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data @NoArgsConstructor @AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String recipientEmail;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private Channel channel = Channel.EMAIL;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private String referenceId;   // e.g. orderId, paymentId
    private String referenceType; // e.g. "ORDER", "PAYMENT"

    private String failureReason;

    public enum NotificationType {
        ORDER_PLACED, ORDER_CONFIRMED, ORDER_SHIPPED, ORDER_DELIVERED,
        PAYMENT_SUCCESS, PAYMENT_FAILED, LOW_STOCK_ALERT, WELCOME, GENERAL
    }
    public enum Channel { EMAIL, SMS, PUSH }
    public enum Status { PENDING, SENT, FAILED }

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
}
