package com.javamaster.repository;

import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

import com.javamaster.entity.Member;

import reactor.core.publisher.Flux;
@Repository
public interface MemberRepository  extends FirestoreReactiveRepository<Member>{
	Flux<Member> findAllByConversationId(String conversationId);
}
