package com.javamaster.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.dto.ModelAddFriend;
import com.javamaster.entity.Contact;

@Service
public interface IContactService {
	Contact addContact(ModelAddFriend modelAddFriend) throws InterruptedException, ExecutionException;
	List<Contact> getContactsByUserId(String userId) throws InterruptedException, ExecutionException;
}
