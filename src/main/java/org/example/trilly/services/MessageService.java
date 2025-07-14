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

    public void deleteMessageSocket(SocketMessageDTO socketMessage){
        messageRepository.deleteById(socketMessage.getId());

    }
    public MessagesResponseDTO getMessagesByChatId(Long chatId){
        List<String> messages = new ArrayList<>();
        List<String> times = new ArrayList<>();
        List<String> senders = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        messageRepository.getMessagesByChatId(chatId).forEach(message -> {
            messages.add(message.getText());
            types.add(message.getType());
            String time = message.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            times.add(time);
            senders.add(message.getSender().getUsername());
            ids.add(message.getId());
        });
        return MessagesResponseDTO.builder()
                .messages(messages)
                .times(times)
                .types(types)
                .ids(ids)
                .senders(senders).build();
    }

    public SocketMessageDTO changeMessageSocket(SocketMessageDTO message){
        Message m = messageRepository.findMessageByIdAndChatId(message.getId(), message.getChatId());
        m.setText(message.getText());
        messageRepository.save(m);

        return SocketMessageDTO.builder()
                .id(message.getId())
                .text(message.getText())
                .chatId(message.getChatId())
                .type(m.getType())
                .time(m.getTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }



    public SocketMessageDTO sendMessageSocket(SocketMessageDTO message, Long chatId){
        LocalDateTime time = LocalDateTime.now();
        var msg = messageRepository.save(
                Message.builder().text(message.getText())
                        .time(time)
                        .chat(chatRepository.findById(chatId).get())
                        .type(message.getType())
                        .sender(userRepository.findByUsername(message.getSender()))
                        .build());
        message.setTime(time.format(DateTimeFormatter.ofPattern("HH:mm")));
        message.setId(msg.getId());
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
