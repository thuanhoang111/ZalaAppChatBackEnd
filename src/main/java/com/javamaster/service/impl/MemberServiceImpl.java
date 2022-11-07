package com.javamaster.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Internal;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.javamaster.entity.Conversation;
import com.javamaster.entity.Member;
import com.javamaster.entity.User;
import com.javamaster.firebase.FirebaseConfig;
import com.javamaster.repository.MemberRepository;
import com.javamaster.repository.UserRepository;
import com.javamaster.service.IMemberService;
import com.javamaster.service.IUserService;
import com.javamaster.storage.MemberStorage;

import reactor.core.publisher.Flux;

@Service
public class MemberServiceImpl implements IMemberService {

	@Autowired
	private FirebaseConfig firebase;
	private static final String COLLECTION_NAME = "members";
	private static final String COLLECTION_NAME_USER = "users";

	@Autowired
	private IUserService userService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Member createMember(Member member) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();

		String autoId = dbFireStore.collection(COLLECTION_NAME).document(Internal.autoId()).getId();

		member.setId(autoId);

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(autoId)
				.set(member);
		collectionAPIFuture.get().getUpdateTime().toString();

		User user = userService.getUser(member.getUserId());

		List<String> membersOfUser = user.getMembers();
		membersOfUser.add(member.getId());
		user.setMembers(membersOfUser);

		ApiFuture<WriteResult> collectionAPIFuture2 = dbFireStore.collection(COLLECTION_NAME_USER)
				.document(member.getUserId()).set(user);

		return member;
	}

	@Override
	public Member getMemberById(String memberId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(memberId);
		ApiFuture<DocumentSnapshot> future = documentReference.get();
		DocumentSnapshot document = future.get();
		Member member = null;
		return member = document.toObject(Member.class);
	}

	@Override
	public Member updateMember(Member member) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();

		ApiFuture<WriteResult> collectionAPIFuture = dbFireStore.collection(COLLECTION_NAME).document(member.getId())
				.set(member);
		collectionAPIFuture.get().getUpdateTime().toString();
		return member;
	}

	// Author:NHH
	@Override
	public String deleteMemberById(String memberId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionAPIFuture = dbFirestore.collection(COLLECTION_NAME).document(memberId)
				.delete();
		return "Delete Member id: " + memberId + "-at: " + LocalDateTime.now();
	}

	@Override
	public Flux<Member> getMemberByConversationId(String conversationId)
			throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Flux<Member> member = memberRepository.findAllByConversationId(conversationId);

		return member;
	}

	@Override
	public int deleteMemberInUser(String memberId, String userId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		User user = userRepository.findById(userId).block();
		List<String> getListMemberInConversation = user.getMembers();
		getListMemberInConversation.remove(memberId);
		user.setMembers(getListMemberInConversation);
		System.out.println(user.getMembers());
		ApiFuture<WriteResult> collectionAPIFuture3 = dbFirestore.collection(COLLECTION_NAME_USER).document(userId)
				.set(user);

		return 0;
	}

}
