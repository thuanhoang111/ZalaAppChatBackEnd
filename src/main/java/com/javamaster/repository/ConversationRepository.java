package com.javamaster.repository;

import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

import com.javamaster.entity.Conversation;
@Repository
public interface ConversationRepository extends FirestoreReactiveRepository<Conversation> {
}
