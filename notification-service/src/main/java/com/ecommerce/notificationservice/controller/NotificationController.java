package com.ecommerce.notificationservice.controller;

import com.ecommerce.notificationservice.dto.NotificationDTO;
import com.ecommerce.notificationservice.model.Notification;
import com.ecommerce.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Service", description = "Sends and manages user notifications via Email, SMS, or Push")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID")
    public ResponseEntity<?> getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all notifications for a user")
    public ResponseEntity<List<Notification>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get notifications by status", description = "Status: PENDING, SENT, FAILED")
    public ResponseEntity<List<Notification>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(notificationService.getNotificationsByStatus(status));
    }

    @PostMapping("/send")
    @Operation(summary = "Send a notification",
            description = "Types: ORDER_PLACED, ORDER_CONFIRMED, ORDER_SHIPPED, ORDER_DELIVERED, PAYMENT_SUCCESS, PAYMENT_FAILED, LOW_STOCK_ALERT, WELCOME, GENERAL")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody NotificationDTO dto) {
        try {
            Notification sent = notificationService.sendNotification(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(sent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/retry")
    @Operation(summary = "Retry a failed notification")
    public ResponseEntity<?> retryNotification(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(notificationService.retryNotification(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification record")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.ok(Map.of("message", "Notification deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
