package com.javamaster.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.Internal;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.javamaster.entity.Message;
import com.javamaster.entity.User;
import com.javamaster.firebase.FirebaseConfig;
import com.javamaster.repository.UserRepository;
import com.javamaster.service.IUserService;
import com.javamaster.storage.UserStorage;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "users";

	@Autowired
	private UserRepository userRepository;

	@Override
	public User addUser(User user) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();

		String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();

		user.setId(autoId);
		user.setMembers(Arrays.asList());
		user.setConversations(Arrays.asList());

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId).set(user);
		collectionAPIFuture.get().getUpdateTime().toString();

//			UserStorage.getInstance().setUser(user);

		return user;
	}

	@Override
	public User getUser(String id) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();
		User users = null;
		return users = document.toObject(User.class);
	}

	@Override
	public User findUserByPhoneNumber(String phoneNumber) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("phoneNumber", phoneNumber);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		User user = null;
		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			user = document.toObject(User.class);
		}
		return user;
	}

	@Override
	public User updateUser(User user) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(user.getId())
				.set(user);
		collectionAPIFuture.get().getUpdateTime().toString();
		return user;
	}

	// Author:NHH
	@Override
	public User deleteMemberInUser(String userId, String memberId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		User user = userRepository.findById(userId).block();
		List<String> listMemberInUser = user.getMembers();
		listMemberInUser.remove(memberId);
		user.setMembers(listMemberInUser);
		ApiFuture<WriteResult> collectionAPIFuture = dbFirestore.collection(COLLECTION_NAME).document(userId).set(user);
		return user;
	}

	@Override
	public User deleteConversationInUser(String userId, String conversationId)
			throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		User user4 = userRepository.findById(userId).block();
		List<String> getListConversationInUser = user4.getConversations();
		getListConversationInUser.remove(conversationId);
		user4.setConversations(getListConversationInUser);
		System.out.println(user4.getConversations());
		ApiFuture<WriteResult> collectionAPIFuture2 = dbFirestore.collection(COLLECTION_NAME).document(userId)
				.set(user4);
		return null;
	}

	@Override
	public User addConversationInUser(String userId, String conversationId)
			throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		User user = userRepository.findById(userId).block();
		List<String> list1 = user.getConversations();
		list1.add(conversationId);
		user.setConversations(list1);
		System.out.println(user.getFullName() + "   " + user.getConversations());
		ApiFuture<WriteResult> collectionAPIFuture2 = dbFirestore.collection(COLLECTION_NAME).document(userId)
				.set(user);
		return null;
	}

}
