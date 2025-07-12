package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.notification.NotificationResponseDTO;
import org.example.trilly.models.Notification;
import org.example.trilly.models.User;
import org.example.trilly.repositories.NotificationRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public List<NotificationResponseDTO> getAllByUsername(String username){
        return notificationRepository.getAllByUserUsernameOrderByTimeDesc(username)
                .stream().map(this::mapToDTO).toList();
    }

    private NotificationResponseDTO mapToDTO(Notification notification){
        return NotificationResponseDTO.builder()
                .time(notification.getTime().format(DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy")))
                .text(notification.getNotificationText())
                .build();
    }

    public NotificationResponseDTO sendNotification(NotificationResponseDTO notification){
        LocalDateTime lc = LocalDateTime.now();
        notification.setTime(lc.format(DateTimeFormatter.ofPattern("HH:mm dd:MM:yyyy")));
        User user = userRepository.findByUsername(notification.getTo());

        notificationRepository.save(Notification.builder()
                .user(user).time(lc)
                .notificationText(notification.getText()).build());
        return notification;
    }

}
