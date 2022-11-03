package com.javamaster.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.entity.AddFriendRequest;
import com.javamaster.entity.Message;
import com.javamaster.service.INotifycationService;
import com.javamaster.storage.ConversationStorage;

@RestController
@CrossOrigin
@RequestMapping("/notifycation")
public class NotifycationController {
	
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private INotifycationService notifycationService;
	
	@PostMapping(value = {"/addFriend"}, consumes = {
            "application/json",
            "application/x-www-form-urlencoded"
    })
	public AddFriendRequest createAddFriendRequest(@RequestBody AddFriendRequest model) {
		AddFriendRequest result = null;
		
		try {
			result = notifycationService.createAddFriendRequest(model);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	@GetMapping("/addFriendRequest/{userId}")
	public List<AddFriendRequest> getAllAddFriendRequestByUserId(@PathVariable String userId) {
		try {
			List<AddFriendRequest> listAddFriendRequest = notifycationService.getAllAddFriendRequestByUserId(userId);
			return listAddFriendRequest;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Arrays.asList();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Arrays.asList();
		}
		
	}
	
	@MessageMapping("/notifycation/{userId}")
    public void sendMessage(@DestinationVariable String userId, AddFriendRequest addFriendRequest) throws InterruptedException, ExecutionException {
        System.out.println("handling notify: " + addFriendRequest.toString() + " to: " + userId);
        AddFriendRequest result = null;
        result = notifycationService.createAddFriendRequest(addFriendRequest);
        
		simpMessagingTemplate.convertAndSend("/topic/notifycation/" + userId, result);
        
    }
	@DeleteMapping("/deleteFriendRequest/{id}")
	int deleteFriendRequest(@PathVariable String id) {
		try {
			notifycationService.deleteAddFriendRequest(id);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

}
