package org.example.trilly.service;

import org.example.trilly.dto.message.MessageDTO;
import org.example.trilly.models.Chat;
import org.example.trilly.models.Message;
import org.example.trilly.models.User;
import org.example.trilly.repositories.ChatRepository;
import org.example.trilly.repositories.MessageRepository;
import org.example.trilly.repositories.UserRepository;
import org.example.trilly.services.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;  // или как у тебя называется класс

    @Test
    void testSendMessage_savesCorrectMessage() {
        // Подготовка данных
        Long chatId = 1L;
        String senderUsername = "testUser";

        MessageDTO dto = new MessageDTO();
        dto.setText("Hello!");
        dto.setSender(senderUsername);

        User mockUser = new User();
        mockUser.setUsername(senderUsername);

        Chat mockChat = new Chat();

        when(userRepository.findByUsername(senderUsername)).thenReturn(mockUser);
        when(chatRepository.findById(chatId)).thenReturn(Optional.of(mockChat));

        messageService.sendMessage(dto, chatId);

        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(captor.capture());

        Message savedMessage = captor.getValue();

        assertEquals("Hello!", savedMessage.getText());
        assertEquals(mockUser, savedMessage.getSender());
        assertEquals(mockChat, savedMessage.getChat());
        assertNotNull(savedMessage.getTime());
    }

    @Test
    void changeMessage(){
        Long chatId = 1L;
        MessageDTO messageDTO = MessageDTO.builder().text("hi").id(1L).sender("testSender").build();

        Message message = Message.builder().id(1L).sender(userRepository.findByUsername("testSender")).text("what?").build();

        when(messageRepository.findMessageByIdAndChatId(message.getId(), chatId)).thenReturn(message);

        when(messageRepository.save((any(Message.class)))).thenReturn(message);
        messageService.changeMessage(messageDTO, chatId);

        assertEquals("hi", message.getText());
    }
}
