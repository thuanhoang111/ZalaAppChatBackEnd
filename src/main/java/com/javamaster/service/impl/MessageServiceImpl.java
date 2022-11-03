package com.javamaster.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.javamaster.entity.Contact;
import com.javamaster.entity.Conversation;
import com.javamaster.entity.Member;
import com.javamaster.entity.Message;
import com.javamaster.entity.User;
import com.javamaster.firebase.FirebaseConfig;
import com.javamaster.service.IConversationService;
import com.javamaster.service.IMemberService;
import com.javamaster.service.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "messages";
	private static final String COLLECTION_NAME_CONVERSATIONS = "conversations";
	private static final String COLLECTION_NAME_MEMBERS = "members";
	
	
	@Autowired
	private IConversationService conversationService;
	
	@Autowired 
	private IMemberService memberService;

	@Override
	public Message addMessage(Message message) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();

		String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();

		message.setId(autoId);
		message.setCreateAt(new Date());
		

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId)
				.set(message);
		collectionAPIFuture.get().getUpdateTime().toString();
		
		
		//update conversation
		Conversation conversation = conversationService.getConversationById(message.getConversationId());
		List<String> messages = conversation.getMessages();
		messages.add(autoId);
		conversation.setMessages(messages);
		ApiFuture<WriteResult> collectionAPIFuture2 = dbFireStore.collection(COLLECTION_NAME_CONVERSATIONS).document(message.getConversationId())
				.set(conversation);
		collectionAPIFuture2.get().getUpdateTime().toString();
		
		//update member
		Member member = memberService.getMemberById(message.getSender());
		List<String> messagesMember = member.getMessages();
		messagesMember.add(autoId);
		member.setMessages(messagesMember);
		ApiFuture<WriteResult> collectionAPIFuture3 = dbFireStore.collection(COLLECTION_NAME_MEMBERS).document(message.getSender())
				.set(member);
		collectionAPIFuture3.get().getUpdateTime().toString();
		
		return message;
	}

	@Override
	public List<Message> getMessagesByConversationId(String conversationId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME).whereEqualTo("conversationId", conversationId);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Message> messages = new ArrayList<>();
        Message message = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        	message = document.toObject(Message.class);
        	messages.add(message);
        }
        
        return messages;
	}

	@Override
	public Message getMessageById(String messageId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(messageId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Message message = null;
        return message = document.toObject(Message.class);
	}

	@Override
	public Message sendMessage(Message message) throws InterruptedException, ExecutionException {
		Message result = addMessage(message);
		return result;
	}
	@Override
	public Message updateMessage(Message message) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		
		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(message.getId())
				.set(message);
		collectionAPIFuture.get().getUpdateTime().toString();
		return message;
	}
	
	@Override
	public String DeleteMessage(String id) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(id).delete();
		return "Delete Message Id:" + id;
	}

}
