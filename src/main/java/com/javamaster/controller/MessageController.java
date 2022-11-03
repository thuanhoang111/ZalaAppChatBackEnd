package com.javamaster.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.entity.Message;
import com.javamaster.service.IMessageService;
import com.javamaster.storage.ConversationStorage;

import io.grpc.netty.shaded.io.netty.handler.timeout.TimeoutException;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private IMessageService messageService;
    
    
    @MessageMapping("/chat/{conversationId}")
    public void sendMessage(@DestinationVariable String conversationId, Message message) throws InterruptedException, ExecutionException {
        System.out.println("handling send message: " + message + " to: " + conversationId);
          
        Message result = messageService.sendMessage(message);
        System.out.println(ConversationStorage.getInstance().getConversations().toString()); 
        
        simpMessagingTemplate.convertAndSend("/topic/messages/" + conversationId, result);
        
    }
   
    
    
    
	@PostMapping(value = {"messages", "/messages"}, consumes = {
            "application/json",
            "application/x-www-form-urlencoded"
    })
    public Message addMessage(@RequestBody Message model) {
		Message result;
        try {
        	result = messageService.addMessage(model);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
	
	
	@GetMapping("/messages/conversations/{conversationId}")
	public List<Message> getMessagesByConversationId(@PathVariable String conversationId) {
		try {
			List<Message> messages = messageService.getMessagesByConversationId(conversationId);
			return messages;
		} catch (Exception e) {
			return null;
		}
	}
	@DeleteMapping("/chatMessages/{id}")
    public String DeleteChatMessage(@PathVariable String id) throws InterruptedException, ExecutionException, TimeoutException {
        return messageService.DeleteMessage(id);
    }
    
    
    
    
    
    

//    @MessageMapping("/chat/{conversationId}")
//    public void sendMessage(@DestinationVariable String conversationId, MessageModel message) {
//        System.out.println("handling send message: " + message + " to: " + conversationId);
////        boolean isExists = UserStorage.getInstance().getUsers().contains(conversationId);
//        Set<String> users = UserStorage.getInstance().getUsers();
//        
//        MessageGroup resMessGroup = new MessageGroup(conversationId, message.getFromLogin(), message.getMessage());
//        
//        users.forEach(user -> {
//        	simpMessagingTemplate.convertAndSend("/topic/messages/" + user, resMessGroup);
//        });
//        
//    }
    
    
    
    
    
    
    
    

    
}
