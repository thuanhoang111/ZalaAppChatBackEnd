package com.javamaster.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.Conversation;
import com.javamaster.entity.Member;
import com.javamaster.entity.Message;

@Service
public interface IConversationService {
	Conversation createConversation(Conversation conversation) throws InterruptedException, ExecutionException;
/**
 * 
 * @param conversationId
 * @param userId
 * @return 
 * @throws InterruptedException
 * @throws ExecutionException
 */
	Conversation addFriendInConversation(String conversationId,String userId) throws InterruptedException, ExecutionException;
	String deleteConversation (String conversationId) throws InterruptedException, ExecutionException;	
	
	Conversation leaveConversation(String conversationId, String memberId, String userId) throws InterruptedException, ExecutionException;
	List<Conversation> getConversationsByUserId(String userId) throws InterruptedException, ExecutionException;
	Conversation getConversationById(String conversationId) throws InterruptedException, ExecutionException;
	List<Message> getAllMessageInConversation(String conversationId) throws InterruptedException, ExecutionException;
	List<Member> getAllMemberInConversation(String conversationId) throws InterruptedException, ExecutionException;
	Conversation updateConvertation(Conversation conversation) throws InterruptedException, ExecutionException;
	int disbandingTheGroup (String conversationIdDelete) throws InterruptedException, ExecutionException;
}
