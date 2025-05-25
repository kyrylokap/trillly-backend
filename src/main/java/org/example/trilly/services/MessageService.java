package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.message.MessageResponseDTO;
import org.example.trilly.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageResponseDTO getMessagesByChatId(Long chatId){
        List<String> messages = new ArrayList<>();
        List<LocalDateTime> times = new ArrayList<>();
        messageRepository.getMessagesByChatId(chatId).forEach(message -> {
            messages.add(message.getText());
            times.add(message.getTime());
        });
        return MessageResponseDTO.builder()
                .messages(messages)
                .times(times).build();
    }



}
