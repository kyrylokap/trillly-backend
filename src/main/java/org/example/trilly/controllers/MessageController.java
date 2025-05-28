package org.example.trilly.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.trilly.dto.message.MessagesResponseDTO;
import org.example.trilly.models.Message;
import org.example.trilly.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class MessageController{
    private MessageService messageService;
    @GetMapping("/chats/{chatId}/messages")
    public MessagesResponseDTO messagesByChatId(@PathVariable Long chatId){
        return messageService.getMessagesByChatId(chatId);
    }
    @PostMapping("/chats/{chatId}/{username}/sendMessage/{text}")
    public ResponseEntity<Message> sendMessage(@PathVariable Long chatId, //Message response will change on dto
                                               @PathVariable String text,
                                               @PathVariable String username){
        return ResponseEntity.ok(messageService.sendMessage(chatId, text,username));
    }
    @PutMapping("/chats/{chatId}/{username}/changeMessage/{messageId}/{text}")
    public ResponseEntity<Message> changeMessage(@PathVariable Long chatId,
                                                 @PathVariable String username,
                                                 @PathVariable Long messageId,
                                                 @PathVariable String text){
        return ResponseEntity.ok(messageService.changeMessage(chatId,username,messageId,text));
    }

    @DeleteMapping("/chats/{chatId}/{username}/deleteMessage/{messageId}")
    public void deleteMessage(@PathVariable Long chatId,
                              @PathVariable String username,
                              @PathVariable Long messageId){
        messageService.deleteMessage(messageId);
    }
}