package com.javamaster.entity;

import java.util.List;

import org.springframework.cloud.gcp.data.firestore.Document;
@Document(collectionName = "conversations")
public class Conversation {
	private String id;
	private String groupName;
	private List<String> memberInGroup;
	private List<String> messages;
	private boolean typeChat;
	private String admin;
	public Conversation(String id, String groupName, List<String> memberInGroup, List<String> messages) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.memberInGroup = memberInGroup;
		this.messages = messages;
	}
	
	public Conversation(String id, String groupName, List<String> memberInGroup, List<String> messages,
			boolean typeChat) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.memberInGroup = memberInGroup;
		this.messages = messages;
		this.typeChat = typeChat;
	}
	

	public Conversation(String id, String groupName, List<String> memberInGroup, List<String> messages,
			boolean typeChat, String admin) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.memberInGroup = memberInGroup;
		this.messages = messages;
		this.typeChat = typeChat;
		this.admin = admin;
	}

	public Conversation() {
		super();
	}
	public Conversation(String id) {
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getMemberInGroup() {
		return memberInGroup;
	}
	public void setMemberInGroup(List<String> memberInGroup) {
		this.memberInGroup = memberInGroup;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	

	public boolean isTypeChat() {
		return typeChat;
	}

	public void setTypeChat(boolean typeChat) {
		this.typeChat = typeChat;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Conversation [id=" + id + ", groupName=" + groupName + ", memberInGroup=" + memberInGroup
				+ ", messages=" + messages + ", typeChat=" + typeChat + ", admin=" + admin + "]";
	}
	

	
	
	
	
	
	
	
}
