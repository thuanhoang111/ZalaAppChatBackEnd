package com.javamaster.service.impl;

import java.util.ArrayList;
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
import com.javamaster.entity.Account;
import com.javamaster.entity.Contact;
import com.javamaster.firebase.FirebaseConfig;
import com.javamaster.service.IAccountService;
import com.javamaster.service.IUserService;

@Service
public class AccountServiceImpl implements IAccountService{
	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "accounts";
	
	@Autowired
	private IUserService userService;

	@Override
	public Account createAccount(Account account) throws InterruptedException, ExecutionException {
		try {
			
			Firestore dbFireStore = FirestoreClient.getFirestore();
			
			String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();
			
			account.setId(autoId);
			
			ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId)
					.set(account);
			collectionAPIFuture.get().getUpdateTime().toString();
			
			return account;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Account getAccountByPhoneNumber(String phoneNumber) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("phoneNumber", phoneNumber);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

		Account account = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        	account = document.toObject(Account.class);
        }
		return account;
	}
	@Override
	public int updateAccount(Account account) {
		try {
			
			Firestore dbFireStore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(account.getId())
					.set(account);
			collectionAPIFuture.get().getUpdateTime().toString();
			
			return 0;
		} catch (Exception e) {
			return 1;
		}
	}

}
