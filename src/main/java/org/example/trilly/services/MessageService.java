package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.message.MessagesResponseDTO;
import org.example.trilly.models.Message;
import org.example.trilly.repositories.ChatRepository;
import org.example.trilly.repositories.MessageRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    public MessagesResponseDTO getMessagesByChatId(Long chatId){
        List<String> messages = new ArrayList<>();
        List<LocalDateTime> times = new ArrayList<>();
        messageRepository.getMessagesByChatId(chatId).forEach(message -> {
            messages.add(message.getText());
            times.add(message.getTime());
        });
        return MessagesResponseDTO.builder()
                .messages(messages)
                .times(times).build();
    }

    public Message sendMessage(Long chatId, String text, String username){  //Message response will change on dto

        return messageRepository.save(
                Message.builder().text(text)
                        .time(LocalDateTime.now())
                        .chat(chatRepository.findById(chatId).get())
                        .sender(userRepository.findByUsername(username))
                        .build());
    }

    public Message changeMessage(Long messageId,String text){ //Remake to DTO too
        Message m = messageRepository.findById(messageId).get();
        m.setText(text);
        return messageRepository.save(m);
    }

    public void deleteMessage(Long messageId){
        messageRepository.deleteById(messageId);
    }
}
