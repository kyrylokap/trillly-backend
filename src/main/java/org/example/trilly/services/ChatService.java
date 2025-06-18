package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.chat.ChatResponseDTO;
import org.example.trilly.models.Chat;
import org.example.trilly.models.Message;
import org.example.trilly.models.User;
import org.example.trilly.repositories.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatResponseDTO getChatById(Long chatId){
        Chat c = chatRepository.getChatById(chatId);
        return mapToDTO(c, "");
    }

    public List<ChatResponseDTO> getChatByUsername(String finder, String found){
        List<Chat> c = chatRepository.getChatByFinderAndFound(finder, found);
        return c.stream().map(chat -> mapToDTO(chat, "")).toList();
    }

    public List<ChatResponseDTO> getMyAllChats(String username){
        return chatRepository
                .findAllByMemberUsername(username).stream()
                .map(chat -> mapToDTO(chat, username)).toList();
    }

    private ChatResponseDTO mapToDTO(Chat c, String username){
        List<Message> messages = c.getMessages().stream().sorted(Comparator.comparing(Message::getTime)).toList();

        String lastMessage = messages.isEmpty() ? "" : messages.get(messages.size() - 1).getText();
        lastMessage = lastMessage.length() > 10 ? lastMessage.substring(0,10) + "..." : lastMessage;

        List<String> usernames = c.getMembers().stream().filter(user -> !user.getUsername().equals(username))
                .map(User::getUsername).toList();

        return ChatResponseDTO.builder()
                .chatId(c.getId())
                .lastMessage(lastMessage)
                .usernames(usernames)
                .build();
    }

}
