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

    public List<MessagesResponseDTO> changeSeenMark(Long chatId, String username){

        List<Message> messages = messageRepository.getMessagesByChatId(chatId);

        messages.stream().filter(m -> !m.getSender().equals(username)).forEach(m-> m.setSeen(true));

        messageRepository.saveAll(messages);

        return messages.stream()
                .map(this::mapMsg).toList();
    }


    public List<MessagesResponseDTO> getMessagesByChatId(Long chatId){

        return messageRepository.getMessagesByChatId(chatId)
                .stream().map((this::mapMsg))
                .toList();
    }

    private MessagesResponseDTO mapMsg(Message m){

        return MessagesResponseDTO.builder()
                .seen(m.isSeen())
                .message(m.getText())
                .id(m.getId())
                .time(m.getTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")))
                .sender(m.getSender().getUsername())
                .type(m.getType())
                .edited(m.isEdited())
                .build();
    }

    public SocketMessageDTO changeMessageSocket(SocketMessageDTO message){
        Message m = messageRepository.findMessageByIdAndChatId(message.getId(), message.getChatId());
        m.setText(message.getText());
        m.setEdited(true);
        messageRepository.save(m);

        return SocketMessageDTO.builder()
                .id(message.getId())
                .text(message.getText())
                .chatId(message.getChatId())
                .type(m.getType())
                .time(m.getTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")))
                .seen(m.isSeen())
                .edited(m.isEdited())
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
        message.setTime(time.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")));
        message.setId(msg.getId());
        message.setSeen(msg.isSeen());
        return message;
    }

    public void changeMessage(MessageDTO message, Long chatId){
        Message m = messageRepository.findMessageByIdAndChatId(message.getId(), chatId);
        m.setText(message.getText());
        messageRepository.save(m);
    }

    /*public void deleteMessage(Long messageId, Long chatId){
        messageRepository.deleteByIdAndChatId(messageId, chatId);
    }*/
}
