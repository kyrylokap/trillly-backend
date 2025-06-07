package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.message.MessageDTO;
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
        List<String> senders = new ArrayList<>();
        messageRepository.getMessagesByChatId(chatId).forEach(message -> {
            messages.add(message.getText());
            times.add(message.getTime());
            senders.add(message.getSender().getUsername());
        });
        return MessagesResponseDTO.builder()
                .messages(messages)
                .times(times).senders(senders).build();
    }

    public void sendMessage(MessageDTO message, Long chatId){

        messageRepository.save(
                Message.builder().text(message.getText())
                        .time(LocalDateTime.now())
                        .chat(chatRepository.findById(chatId).get())
                        .sender(userRepository.findByUsername(message.getSender()))
                        .build());
    }

    public void changeMessage(MessageDTO message, Long chatId){
        Message m = messageRepository.findMessageByIdAndChatId(message.getId(), chatId);
        m.setText(message.getText());
        messageRepository.save(m);
    }

    public void deleteMessage(Long messageId, Long chatId){
        messageRepository.deleteByIdAndChatId(messageId, chatId);
    }
}
