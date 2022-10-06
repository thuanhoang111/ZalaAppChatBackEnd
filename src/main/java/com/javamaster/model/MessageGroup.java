package com.javamaster.model;

public class MessageGroup {
	private String groupId;
	private String sender;
	private String content;
	public MessageGroup(String groupId, String sender, String content) {
		super();
		this.groupId = groupId;
		this.sender = sender;
		this.content = content;
	}
	public MessageGroup() {
		super();
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MessageGroup [groupId=" + groupId + ", sender=" + sender + ", content=" + content + "]";
	}
	
	
}
