package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trilly.dto.message.MessageDTO;
import org.example.trilly.dto.message.MessagesResponseDTO;
import org.example.trilly.services.MessageService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/chats")
public class MessageController{
    private MessageService messageService;
    @GetMapping("/{chatId}/messages")
    public MessagesResponseDTO getMessages(@PathVariable Long chatId){
        return messageService.getMessagesByChatId(chatId);
    }
    @PostMapping("/{chatId}/messages")
    public ResponseEntity<Void> sendMessage(@PathVariable Long chatId,
                                               @RequestBody MessageDTO message){
        messageService.sendMessage(message, chatId);
        return  ResponseEntity.ok().build();
    }
    @PutMapping("/{chatId}/messages")
    public ResponseEntity<Void> changeMessage(@PathVariable Long chatId,
                                                 @RequestBody MessageDTO message){
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