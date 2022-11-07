package com.javamaster.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.User;

@Service
public interface IUserService {
	User addUser(User user) throws InterruptedException, ExecutionException;

	User getUser(String id) throws InterruptedException, ExecutionException;

	User findUserByPhoneNumber(String phoneNumber) throws InterruptedException, ExecutionException;

	User updateUser(User user) throws InterruptedException, ExecutionException;

	//Author: NHH
	User deleteMemberInUser(String userId, String memberId) throws InterruptedException, ExecutionException;
	User deleteConversationInUser(String userId,String conversationId) throws InterruptedException, ExecutionException;
	User addConversationInUser(String userId,String conversationId) throws InterruptedException, ExecutionException;
}
