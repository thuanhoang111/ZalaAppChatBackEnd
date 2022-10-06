package com.javamaster.entity;

import java.util.List;

public class Conversation {
	private String id;
	private String groupName;
	private List<String> memberInGroup;
	private List<String> messages;
	public Conversation(String id, String groupName, List<String> memberInGroup, List<String> messages) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.memberInGroup = memberInGroup;
		this.messages = messages;
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
	@Override
	public String toString() {
		return "Conversation [id=" + id + ", groupName=" + groupName + ", memberInGroup=" + memberInGroup
				+ ", messages=" + messages + "]";
	}
	
	
	
	
	
}
