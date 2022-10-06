package com.javamaster.storage;

import java.util.HashSet;
import java.util.Set;

import com.javamaster.entity.Conversation;


public class ConversationStorage {
	private static ConversationStorage instance;
    private Set<Conversation> conversations;

    private ConversationStorage() {
    	conversations = new HashSet<>();
    }

    public static synchronized ConversationStorage getInstance() {
        if (instance == null) {
            instance = new ConversationStorage();
        }
        return instance;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversation(Conversation newConversation) throws Exception {
        if (conversations.contains(newConversation)) {
            throw new Exception("Conversation aready exists with userName: " + newConversation.toString());
        }
        conversations.add(newConversation);
    }
}
