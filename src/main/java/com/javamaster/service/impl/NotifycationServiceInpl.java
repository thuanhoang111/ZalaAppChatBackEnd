package com.javamaster.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Internal;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.javamaster.entity.AddFriendRequest;
import com.javamaster.entity.Message;
import com.javamaster.firebase.FirebaseConfig;
import com.javamaster.service.INotifycationService;

@Service
public class NotifycationServiceInpl implements INotifycationService {

	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "notifycations";

	@Override
	public AddFriendRequest createAddFriendRequest(AddFriendRequest addFriendRequest)
			throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();

		String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();

		AddFriendRequest newRequest = new AddFriendRequest();

		newRequest.setId(autoId);
		newRequest.setUserId(addFriendRequest.getFriendId());
		newRequest.setNameUser(addFriendRequest.getNameFriend());
		newRequest.setFriendId(addFriendRequest.getUserId());
		newRequest.setNameFriend(addFriendRequest.getNameUser());
		newRequest.setStatus(false);

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId)
				.set(newRequest);
		collectionAPIFuture.get().getUpdateTime().toString();
		return newRequest;
	}

	@Override
	public List<AddFriendRequest> getAllAddFriendRequestByUserId(String userId)
			throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("userId", userId);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		List<AddFriendRequest> addFriendRequestList = new ArrayList<>();
		AddFriendRequest addFriendRequest = null;
		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			addFriendRequest = document.toObject(AddFriendRequest.class);
			addFriendRequestList.add(addFriendRequest);
		}

		return addFriendRequestList;
	}

	@Override
	public int deleteAddFriendRequest(String id) throws InterruptedException, ExecutionException {
		try {
			Firestore dbFirestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

}
