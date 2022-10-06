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
import com.google.cloud.firestore.Internal;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
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
import com.javamaster.service.IUserService;
import com.javamaster.storage.ConversationStorage;

@Service
public class ConversationServiceImpl implements IConversationService {

	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "conversations";
	private static final String COLLECTION_NAME_MEMBERS = "members";
	private static final String COLLECTION_NAME_USERS = "users";
	private static final String COLLECTION_NAME_MESSAGES = "messages";

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IMessageService messageService;

	@Override
	public Conversation createConversation(Conversation conversation) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();

		conversation.setId(autoId);

		List<String> memberInGroup = new ArrayList<String>();

		for (String userId : conversation.getMemberInGroup()) {
			User user = userService.getUser(userId);
			Member member = new Member();
			member.setUserId(userId);
			member.setNameUser(user.getFullName());
			member.setConversationId(autoId);
			member.setMessages(Arrays.asList());
			Member memberTemp = memberService.createMember(member);
			memberInGroup.add(memberTemp.getId());

			// update conversations in user
			List<String> conversationOfUser = user.getConversations();
			conversationOfUser.add(conversation.getId());
			user.setConversations(conversationOfUser);
			System.out.println(user.getFullName() + "   "+user.getConversations());
			ApiFuture<WriteResult> collectionAPIFuture2 = dbFireStore.collection(COLLECTION_NAME_USERS)
					.document(user.getId()).set(user);
			collectionAPIFuture2.get().getUpdateTime().toString();
			
			//update members in user
			List<String> membersOfUser = user.getMembers();
			membersOfUser.add(member.getId());
			user.setMembers(membersOfUser);
			ApiFuture<WriteResult> collectionAPIFuture3 = dbFireStore.collection(COLLECTION_NAME_USERS)
					.document(user.getId()).set(user);
			collectionAPIFuture3.get().getUpdateTime().toString();

		}

		conversation.setMemberInGroup(memberInGroup);
		conversation.setMessages(Arrays.asList());
		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId)
				.set(conversation);
		
		collectionAPIFuture.get().getUpdateTime().toString();

		return conversation;
	}

	@Override
	public List<Conversation> getConversationsByUserId(String userId) throws InterruptedException, ExecutionException {

		User user = userService.getUser(userId);

		List<Conversation> conversations = new ArrayList<Conversation>();
		Conversation conversation = null;
		for (String conversationId : user.getConversations()) {
			conversation = getConversationById(conversationId);
			conversations.add(conversation);
		}

		return conversations;
	}

	@Override
	public Conversation getConversationById(String conversationId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(conversationId);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();
		Conversation conversation = null;
		return conversation = document.toObject(Conversation.class);
	}

	@Override
	public List<Message> getAllMessageInConversation(String conversationId)
			throws InterruptedException, ExecutionException {
		
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME_MESSAGES).whereEqualTo("conversationId", conversationId);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Message> messages = new ArrayList<>();
        Message message = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        	message = document.toObject(Message.class);
        	messages.add(message);
        }

//		Conversation conversation = getConversationById(conversationId);
//
//		List<Message> messagesOfConversation = new ArrayList<Message>();
//		Message message = null;
//		for (String messageId : conversation.getMessages()) {
//			message = messageService.getMessageById(messageId);
//			messagesOfConversation.add(message);
//		}

		return messages;
	}

	@Override
	public List<Member> getAllMemberInConversation(String conversationId) throws InterruptedException, ExecutionException {
		
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query query = dbFirestore.collection(COLLECTION_NAME_MEMBERS).whereEqualTo("conversationId", conversationId);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<Member> memberOfConversation = new ArrayList<>();
        Member member = null;
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        	member = document.toObject(Member.class);
        	memberOfConversation.add(member);
        }
//		Conversation conversation = getConversationById(conversationId);
//
//		List<Member> memberOfConversation = new ArrayList<Member>();
//		Member member = null;
//		for (String memberId : conversation.getMemberInGroup()) {
//			member = memberService.getMemberById(memberId);
//			memberOfConversation.add(member);
//		}

		return memberOfConversation;
	}
	@Override
	public Conversation updateConvertation(Conversation conversation) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		
		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(conversation.getId())
				.set(conversation);
		collectionAPIFuture.get().getUpdateTime().toString();
		return conversation;
	}

}
