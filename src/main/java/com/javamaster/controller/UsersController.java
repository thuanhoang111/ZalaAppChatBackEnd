package com.javamaster.controller;

import com.javamaster.entity.User;
import com.javamaster.service.IUserService;
import com.javamaster.storage.ConversationStorage;
import com.javamaster.storage.UserStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private IUserService userService;

	@PostMapping(value = {"", "/"}, consumes = {
            "application/json",
            "application/x-www-form-urlencoded"
    })
    public User registerUser(@RequestBody User model) {
//        System.out.println("handling register user request: " + model.toString());\
		User result;
        try {
//            UserStorage.getInstance().setUser(model);
        	result = userService.addUser(model);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
	
	@GetMapping(value = {
            "", "/"
    })
    public Set<User> fetchAllUsers() {
        return UserStorage.getInstance().getUsers();
    }
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable String id) {
		User user;
		
		try {
			user = userService.getUser(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return user;
	}
	
	@GetMapping("/filter")
	public User findUserByPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber) {
		User user;
		
		try {
			user = userService.findUserByPhoneNumber("+"+phoneNumber);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return user;
	}
	
//	@GetMapping("/{id}")
//	public User getUserById(@PathVariable String id) {
//		try {
//			User user = UserStorage.getInstance().getUsers()
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
//	@GetMapping("/registration/conversation/{conversationId}")
//    public ResponseEntity<Void> registerConversation(@PathVariable String conversationId) {
//        System.out.println("handling register conversaion request: " + conversationId);
//        try {
//        	Conversation newConversation = new Conversation(conversationId);
//        	ConversationStorage.getInstance().setConversation(newConversation);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok().build();
//    }
	
//    @GetMapping("/fetchAllConversations")
//    public Set<Conversation> fetchAll() {
//        return ConversationStorage.getInstance().getConversations();
//    }
    
    
}
