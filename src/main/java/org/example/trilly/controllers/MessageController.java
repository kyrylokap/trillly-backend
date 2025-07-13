package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trilly.CustomUserDetails;
import org.example.trilly.dto.message.MessageDTO;
import org.example.trilly.dto.message.MessagesResponseDTO;
import org.example.trilly.dto.message.SocketMessageDTO;
import org.example.trilly.dto.message.TypingDTO;
import org.example.trilly.jwt.JWTService;
import org.example.trilly.services.MessageService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/chats")
public class MessageController{
    private MessageService messageService;
    @GetMapping("/{chatId}/messages")
    public ResponseEntity<MessagesResponseDTO> getMessages(@PathVariable Long chatId){
        return ResponseEntity.ok(messageService.getMessagesByChatId(chatId));
    }
    /*@PostMapping("/{chatId}/messages")        If need REST messaging
    public ResponseEntity<Void> sendMessage(@PathVariable Long chatId,
                                               @RequestBody MessageDTO message){
        message.setSender(SecurityContextHolder.getContext().getAuthentication().getName());
        messageService.sendMessage(message, chatId);
        return  ResponseEntity.ok().build();
    }*/

    @MessageMapping("/chat.typing")
    @SendTo("/topic/typing")
    public TypingDTO typingStatus(@Payload TypingDTO typingDTO, Principal principal) {
        typingDTO.setUsername(principal.getName());
        return typingDTO;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public SocketMessageDTO sendMessage(SocketMessageDTO message, Principal principal) {
        String sender = principal.getName();
        message.setSender(sender);
        return messageService.sendMessageSocket(message, message.getChatId());
    }

    @MessageMapping("/chat.change")
    @SendTo("/topic/change")
    public SocketMessageDTO changeMessageSocket(SocketMessageDTO message) {

        return messageService.changeMessageSocket(message);
    }


    @PutMapping("/{chatId}/messages")
    public ResponseEntity<Void> changeMessage(@PathVariable Long chatId,
                                                 @RequestBody MessageDTO message){
        message.setSender(SecurityContextHolder.getContext().getAuthentication().getName());
        messageService.changeMessage(message, chatId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long chatId,
                              @PathVariable Long messageId){
        messageService.deleteMessage(messageId, chatId);
        return ResponseEntity.ok().build();
    }
}