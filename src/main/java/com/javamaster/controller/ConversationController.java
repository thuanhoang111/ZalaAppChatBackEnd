package com.javamaster.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.dto.AddMemberInGroup;
import com.javamaster.dto.DeleteMemberInGroup;
import com.javamaster.entity.Conversation;
import com.javamaster.entity.Member;
import com.javamaster.entity.Message;
import com.javamaster.entity.User;
import com.javamaster.service.IConversationService;
import com.javamaster.service.IMemberService;
import com.javamaster.service.IUserService;
import com.javamaster.storage.ConversationStorage;

@RestController
@CrossOrigin
@RequestMapping("/conversations")
public class ConversationController {

	@Autowired
	private IConversationService conversationService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IMemberService memberService;

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

	@PostMapping("/addMemberInGroup")
	public Conversation addMemberInGroup(@RequestBody AddMemberInGroup addMemberInGroup)
			throws InterruptedException, ExecutionException, TimeoutException {
		Conversation conversation = conversationService.addFriendInConversation(
				addMemberInGroup.getConversationId().trim(), addMemberInGroup.getUserId().trim());
//		if(conversation!=null) {
//			userService.addConversationInUser(addMemberInGroup.getUserId().trim(), addMemberInGroup.getConversationId().trim());
//		}

		return conversation;
	}
	
	@PostMapping("/addConversationInUser")
	public User addConversationInUser(@RequestBody AddMemberInGroup addMemberInGroup)
			throws InterruptedException, ExecutionException, TimeoutException {
		
		return userService.addConversationInUser(addMemberInGroup.getUserId().trim(), addMemberInGroup.getConversationId().trim());
		
	}
	

	@PostMapping("/leaveConversation")
	public Conversation leaveConversation(@RequestBody DeleteMemberInGroup deleteMemberInGroup)
			throws InterruptedException, ExecutionException, TimeoutException {
		Conversation conversation = conversationService.leaveConversation(
				deleteMemberInGroup.getConversationId().trim(), deleteMemberInGroup.getMemberId().trim(),
				deleteMemberInGroup.getUserId().trim());
		if(conversation!=null) {
//			userService.deleteConversationInUser(deleteMemberInGroup.getUserId().trim(), deleteMemberInGroup.getConversationId().trim());
			userService.deleteMemberInUser(deleteMemberInGroup.getUserId().trim(), deleteMemberInGroup.getMemberId().trim());
			memberService.deleteMemberById(deleteMemberInGroup.getMemberId().trim());			
		}
		//		userService.deleteMemberInUser(deleteMemberInGroup.getUserId().trim(),
//				deleteMemberInGroup.getMemberId().trim());

		return conversation;
	}
	
	
	@PostMapping("/leaveConversationInUser")
	public User leaveConversationInUser(@RequestBody DeleteMemberInGroup deleteMemberInGroup)
			throws InterruptedException, ExecutionException, TimeoutException {
		
		return	userService.deleteConversationInUser(deleteMemberInGroup.getUserId().trim(), deleteMemberInGroup.getConversationId().trim());
			
		//		userService.deleteMemberInUser(deleteMemberInGroup.getUserId().trim(),
//				deleteMemberInGroup.getMemberId().trim());

		
	}
	
//	@PostMapping("/delete/{conversationId}")
//	public int deleteConversation(@PathVariable String conversationId) {
//		try {
//			conversationService.deleteConversation(conversationId);
//			return 1;
//		} catch (Exception e) {
//			return 0;
//		}
//	}
	
	@PutMapping("/delete/{conversationId}")
	public int disbandingTheGroup(@PathVariable String conversationId) {
		try {
			conversationService.disbandingTheGroup(conversationId);
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}
	
	
	@PutMapping("/updateAdmin")
	public Conversation updateConversation(@RequestBody Conversation conversation) {
		try {
			Conversation result = conversationService.updateConvertation(conversation);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
