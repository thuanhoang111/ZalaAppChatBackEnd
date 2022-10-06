package com.javamaster.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.entity.Contact;
import com.javamaster.entity.Conversation;
import com.javamaster.entity.Member;
import com.javamaster.entity.Message;
import com.javamaster.service.IConversationService;
import com.javamaster.storage.ConversationStorage;
import com.javamaster.storage.UserStorage;

@RestController
@CrossOrigin
@RequestMapping("/conversations")
public class ConversationController {
	
	@Autowired
	private IConversationService conversationService;

	@PostMapping(value = { "", "/" }, consumes = { "application/json", "application/x-www-form-urlencoded" })
	public Conversation registeConversation(@RequestBody Conversation model) {
		Conversation conversation;
		try {
			conversation = conversationService.createConversation(model);
			
		} catch (Exception e) {
			return null;
		}
		return conversation;
	}

	@GetMapping(value = { "", "/" })
	public Set<Conversation> fetchAllConversations() {
		return ConversationStorage.getInstance().getConversations();
	}
	
	@GetMapping("/user/{userId}")
	public List<Conversation> getConversationsByUserId(@PathVariable String userId) {
		try {
			List<Conversation> conversations = conversationService.getConversationsByUserId(userId);
			return conversations;
		} catch (Exception e) {
			return Arrays.asList();
		}
	}
	
	@GetMapping("/{conversationId}")
	public Conversation getConversationsById(@PathVariable String conversationId) {
		try {
			Conversation conversation = conversationService.getConversationById(conversationId);
			return conversation;
		} catch (Exception e) {
			return null;
		}
	}
	
	@GetMapping("/chat/{conversationId}")
	public List<Message> getAllMessagesInConversation(@PathVariable String conversationId) {
		try {
			List<Message> messages = conversationService.getAllMessageInConversation(conversationId);
			return messages;
		} catch (Exception e) {
			return Arrays.asList();
		}
	}
	
	@GetMapping("/members/{conversationId}")
	public List<Member> getAllMembersInConversation(@PathVariable String conversationId) {
		try {
			List<Member> members = conversationService.getAllMemberInConversation(conversationId);
			return members;
		} catch (Exception e) {
			return Arrays.asList();
		}
	}
}
