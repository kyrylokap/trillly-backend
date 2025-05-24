package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.message.MessageResponseDTO;
import org.example.trilly.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageResponseDTO getMessagesByChatId(Long chatId){

        return MessageResponseDTO.builder()
                .messages(messageRepository
                        .getAllByChatId(chatId)).build();
    }
}
