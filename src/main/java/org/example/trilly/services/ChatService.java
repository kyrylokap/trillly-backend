package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.chat.ChatResponseDTO;
import org.example.trilly.models.Chat;
import org.example.trilly.models.User;
import org.example.trilly.repositories.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatResponseDTO getChatById(Long chatId){
        Chat c = chatRepository.getChatById(chatId);
        return mapToDTO(c);
    }

    public List<ChatResponseDTO> getChatByUsername(String finder, String found){
        List<Chat> c = chatRepository.getChatByFinderAndFound(finder, found);
        List<ChatResponseDTO> chatsAfterMapping = new ArrayList<>();
        c.forEach( chat -> {
            chatsAfterMapping.add(mapToDTO(chat));
        });
        return chatsAfterMapping;
    }

    public List<ChatResponseDTO> getMyAllChats(String username){
        return chatRepository
                .findAllByMemberUsername(username).stream()
                .map(this::mapToDTO).toList();
    }

    private ChatResponseDTO mapToDTO(Chat c){
        String lastMessage = c.getMessages().isEmpty() ?
                            "" : c.getMessages().get(c.getMessages().size() - 1).getText();
        List<String> usernames = c.getMembers().stream()
                .map(User::getUsername).toList();

        return ChatResponseDTO.builder()
                .chatId(c.getId())
                .lastMessage(lastMessage)
                .usernames(usernames)
                .build();
    }

}
