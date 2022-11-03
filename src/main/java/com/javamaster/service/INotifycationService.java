package com.javamaster.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.AddFriendRequest;

@Service
public interface INotifycationService {
	AddFriendRequest createAddFriendRequest(AddFriendRequest addFriendRequest) throws InterruptedException, ExecutionException;
	List<AddFriendRequest> getAllAddFriendRequestByUserId(String userId) throws InterruptedException, ExecutionException;
	int deleteAddFriendRequest(String id) throws InterruptedException, ExecutionException;
}
