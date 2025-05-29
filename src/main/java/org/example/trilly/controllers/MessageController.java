package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trilly.dto.message.MessageDTO;
import org.example.trilly.dto.message.MessagesResponseDTO;
import org.example.trilly.models.Message;
import org.example.trilly.services.MessageService;
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
    public ResponseEntity<Message> sendMessage(@PathVariable Long chatId, //Message response will change on dto
                                               @RequestBody MessageDTO message,
                                               @RequestParam String username){
        return ResponseEntity.ok(messageService.sendMessage(chatId, message.getText(),username));
    }
    @PutMapping("/{chatId}/messages/{messageId}")
    public ResponseEntity<Message> changeMessage(@PathVariable Long chatId,
                                                 @PathVariable Long messageId,
                                                 @RequestBody MessageDTO message){
        return ResponseEntity.ok(messageService.changeMessage(messageId, message.getText()));
    }
    @DeleteMapping("/{chatId}/messages/{messageId}")
    public void deleteMessage(@PathVariable Long chatId,
                              @PathVariable Long messageId){
        messageService.deleteMessage(messageId);
    }
}