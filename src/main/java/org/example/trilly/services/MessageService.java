package org.example.trilly.services;


import lombok.AllArgsConstructor;
import org.example.trilly.dto.message.MessageDTO;
import org.example.trilly.dto.message.MessagesResponseDTO;
import org.example.trilly.dto.message.SocketMessageDTO;
import org.example.trilly.models.Message;
import org.example.trilly.repositories.ChatRepository;
import org.example.trilly.repositories.MessageRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        List<String> times = new ArrayList<>();
        List<String> senders = new ArrayList<>();
        List<String> types = new ArrayList<>();
        messageRepository.getMessagesByChatId(chatId).forEach(message -> {
            messages.add(message.getText());
            types.add(message.getType());
            String time = message.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            times.add(time);
            senders.add(message.getSender().getUsername());
        });
        return MessagesResponseDTO.builder()
                .messages(messages)
                .times(times)
                .types(types)
                .senders(senders).build();
    }



    public SocketMessageDTO sendMessage(SocketMessageDTO message, Long chatId){
        LocalDateTime time = LocalDateTime.now();
        messageRepository.save(
                Message.builder().text(message.getText())
                        .time(time)
                        .chat(chatRepository.findById(chatId).get())
                        .type(message.getType())
                        .sender(userRepository.findByUsername(message.getSender()))
                        .build());
        message.setTime(time.format(DateTimeFormatter.ofPattern("HH:mm")));
        return message;
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
