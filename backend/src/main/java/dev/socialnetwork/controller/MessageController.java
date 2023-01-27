package dev.socialnetwork.controller;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.exception.AccountNotFoundError;
import dev.socialnetwork.model.ChatMessage;
import dev.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    //    @MessageMapping("/chat")
//    @SendTo("/topic/public")
//    public ChatMessage register(@Payload ChatMessage chatMessage,
//                                SimpMessageHeaderAccessor headerAccessor){
//        headerAccessor.getSessionAttributes().put("user_id", chatMessage.getSecondAccount());
//        return chatMessage;
//    }
    private final AccountService accountService;

    @Autowired
    public MessageController(AccountService accountService) {
        this.accountService = accountService;
    }

    record MessageModel(long first_account, long second_account, String text){}
    record MessageResponse(String second, String text){}
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(@Payload MessageModel messageModel) {
        Account first = accountService.findById(messageModel.first_account()).orElseThrow(AccountNotFoundError::new);
        Account second = accountService.findById(messageModel.second_account()).orElseThrow(AccountNotFoundError::new);
        ChatMessage.of(first, second, messageModel.text());
        return new MessageResponse(second.getFirstName(), messageModel.text());
    }

}
