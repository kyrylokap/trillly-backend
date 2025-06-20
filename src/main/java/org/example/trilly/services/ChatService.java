package org.example.trilly.services;

import lombok.AllArgsConstructor;
import org.example.trilly.dto.chat.ChatResponseDTO;
import org.example.trilly.models.Chat;
import org.example.trilly.models.Message;
import org.example.trilly.models.User;
import org.example.trilly.models.enums.RelationStatus;
import org.example.trilly.repositories.ChatRepository;
import org.example.trilly.repositories.RelationRepository;
import org.example.trilly.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final RelationRepository relationRepository;
    private final UserRepository userRepository;

    public ChatResponseDTO createAndGetChat(String firstUsername, String secondUsername){
        var chat = chatRepository.getChatByFinderAndFound(firstUsername, secondUsername);
        return chat == null ? createChat(firstUsername, secondUsername) : mapToDTO(chat, "");
    }
    private ChatResponseDTO createChat(String firstUsername, String secondUsername){
        var user1 = userRepository.findByUsername(firstUsername);
        var user2 = userRepository.findByUsername(secondUsername);

        List<User> initialChatMembers = List.of(user1, user2);

        var chat = Chat.builder().members(initialChatMembers).messages(new ArrayList<>()).build();

        user1.getChats().add(chat);
        user2.getChats().add(chat);
        chatRepository.save(chat);
        return mapToDTO(chat, "");
    }

    public ChatResponseDTO getChatById(Long chatId){
        Chat c = chatRepository.getChatById(chatId);
        return mapToDTO(c, "");
    }

    public List<ChatResponseDTO> getChatsByUsername(String finder, String found){
        return  chatRepository.getChatsByFinderAndFound(finder, found).stream().filter(chat ->
                        chat.getMembers().stream().noneMatch(member ->
                                relationRepository.isBlocked(finder, member.getUsername(),
                                       RelationStatus.BLOCKED))
                        )

                .map(chat -> mapToDTO(chat, "")).toList();
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
