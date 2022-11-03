package com.javamaster.service.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Internal;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.javamaster.dto.ModelAddFriend;
import com.javamaster.entity.Contact;
import com.javamaster.entity.Conversation;
import com.javamaster.firebase.FirebaseConfig;
import com.javamaster.service.IContactService;
import com.javamaster.service.IConversationService;

@Service
public class ContactServiceImpl implements IContactService {

	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "contacts";
	private static final String COLLECTION_NAME_NOTIFYCATIONS = "notifycations";
	
	@Autowired
	private IConversationService conversationService;

	@Override
	public Contact addContact(ModelAddFriend modelAddFriend) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		
		Conversation bodyNewConversation = new Conversation();
		bodyNewConversation.setGroupName("");
		bodyNewConversation.setMemberInGroup(Arrays.asList(modelAddFriend.getUserId(), modelAddFriend.getFriendId()));
		bodyNewConversation.setGroupName(modelAddFriend.getNameUser() + "-" + modelAddFriend.getNameFriend());
		
		
		Conversation conversation = conversationService.createConversation(bodyNewConversation);
		
		String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();

		Contact contact1 = new Contact();
		contact1.setId(autoId);
		contact1.setConversationId(conversation.getId());
		contact1.setUserId(modelAddFriend.getUserId());
		contact1.setFriendId(modelAddFriend.getFriendId());
		contact1.setNameFriend(modelAddFriend.getNameFriend());

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId)
				.set(contact1);
		collectionAPIFuture.get().getUpdateTime().toString();
		
		//tao cho nguoi con lai
		
		Contact contact2 = new Contact(); 
		
		String autoId2 = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();
		
		contact2.setId(autoId2);
		contact2.setConversationId(conversation.getId());
		contact2.setUserId(modelAddFriend.getFriendId());
		contact2.setFriendId(modelAddFriend.getUserId());
		contact2.setNameFriend(modelAddFriend.getNameUser());

		ApiFuture<WriteResult> collectionAPIFuture2 = dbFireStore.collection(COLLECTION_NAME).document(autoId2)
				.set(contact2);
		collectionAPIFuture2.get().getUpdateTime().toString();
		
		ApiFuture<WriteResult> collectionAPIFuture3 = dbFireStore.collection(COLLECTION_NAME_NOTIFYCATIONS).document(modelAddFriend.getRequestId()).delete();
		collectionAPIFuture.get().getUpdateTime().toString();
		
		return contact1;
	}

	@Override
	public List<Contact> getContactsByUserId(String userId) throws InterruptedException, ExecutionException {
		
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("userId", userId);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Contact> contacts = new ArrayList<>();
        Contact contact = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        	contact = document.toObject(Contact.class);
        	contacts.add(contact);
        }
        
        return contacts;
	}

}
