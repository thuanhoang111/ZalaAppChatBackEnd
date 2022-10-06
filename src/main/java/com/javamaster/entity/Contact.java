package com.javamaster.entity;

public class Contact {
	private String id;
	private String userId;
	private String friendId;
	private String conversationId;
	private String nameFriend;
	public Contact() {
		super();
	}
	public Contact(String id, String userId, String friendId, String conversationId, String nameFriend) {
		super();
		this.id = id;
		this.userId = userId;
		this.friendId = friendId;
		this.conversationId = conversationId;
		this.nameFriend = nameFriend;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getConversationId() {
		return conversationId;
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	public String getNameFriend() {
		return nameFriend;
	}
	public void setNameFriend(String nameFriend) {
		this.nameFriend = nameFriend;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", userId=" + userId + ", friendId=" + friendId + ", conversationId="
				+ conversationId + ", nameFriend=" + nameFriend + "]";
	}
	
	
	
}
