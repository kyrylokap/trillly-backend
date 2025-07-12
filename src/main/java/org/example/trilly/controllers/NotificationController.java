package org.example.trilly.controllers;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.notification.NotificationResponseDTO;
import org.example.trilly.models.Notification;
import org.example.trilly.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/user/notifications")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(notificationService.getAllByUsername(username));
    }

    @MessageMapping("/notifications.send")
    @SendTo("/topic/notifications")
    public NotificationResponseDTO sendNotification(NotificationResponseDTO notification, Principal principal){
        notification.setFrom(principal.getName());
        notification.setText(notification.getFrom() + notification.getText());
        return notificationService.sendNotification(notification);
    }


}
