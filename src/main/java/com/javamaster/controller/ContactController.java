package com.javamaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.dto.ModelAddFriend;
import com.javamaster.entity.Contact;
import com.javamaster.entity.Member;
import com.javamaster.service.IContactService;

@RestController
@CrossOrigin
@RequestMapping("/contacts")
public class ContactController {
	@Autowired
	private IContactService contactService;
	
	
	@PostMapping(value = {"", "/"}, consumes = {
            "application/json",
            "application/x-www-form-urlencoded"
    })
    public Contact addFriend(@RequestBody ModelAddFriend modelAddFriend) {
		Contact result;
        try {
        	result = contactService.addContact(modelAddFriend);
        } catch (Exception e) {
            return null;
        }
        return result;
    }
	
	@GetMapping("/user/{userId}")
	public List<Contact> getContactsbyUserId(@PathVariable String userId) {
		try {
			List<Contact> contacts = contactService.getContactsByUserId(userId);
			return contacts;
		} catch (Exception e) {
			return null;
		}
	}
}
