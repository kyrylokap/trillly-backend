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

    public ChatResponseDTO getChatByUsername(String username){
        Chat c = chatRepository.getChatByMemberUsername(username);
        return mapToDTO(c);
    }

    public List<ChatResponseDTO> getMyAllChats(Long memberId){
        return chatRepository
                .findAllByMemberId(memberId).stream()
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
