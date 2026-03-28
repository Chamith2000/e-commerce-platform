package com.ecommerce.notificationservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class NotificationDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Email(message = "Valid email required")
    private String recipientEmail;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    @NotBlank(message = "Notification type is required")
    private String type; // ORDER_PLACED, ORDER_CONFIRMED, PAYMENT_SUCCESS, etc.

    private String channel = "EMAIL"; // EMAIL, SMS, PUSH

    private String referenceId;
    private String referenceType;
}
