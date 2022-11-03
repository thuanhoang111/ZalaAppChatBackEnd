package com.javamaster.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.Message;

@Service
public interface IMessageService {
	Message addMessage(Message message) throws InterruptedException, ExecutionException;
	List<Message> getMessagesByConversationId(String conversationId) throws InterruptedException, ExecutionException;
	Message getMessageById(String MessageId) throws InterruptedException, ExecutionException;
	Message sendMessage(Message message) throws InterruptedException, ExecutionException;
	Message updateMessage(Message message) throws InterruptedException, ExecutionException;
	String DeleteMessage(String id) throws InterruptedException, ExecutionException;
}
