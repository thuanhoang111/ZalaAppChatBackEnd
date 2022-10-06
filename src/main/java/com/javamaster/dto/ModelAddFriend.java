package com.javamaster.dto;

public class ModelAddFriend {
	private String userId;
	private String nameUser;
	private String friendId;
	private String nameFriend;
	private String requestId;
	public ModelAddFriend() {
		super();
	}
	public ModelAddFriend(String userId) {
		super();
		this.userId = userId;
	}
	public ModelAddFriend(String userId, String nameUser, String friendId, String nameFriend, String requestId) {
		super();
		this.userId = userId;
		this.nameUser = nameUser;
		this.friendId = friendId;
		this.nameFriend = nameFriend;
		this.requestId = requestId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getNameFriend() {
		return nameFriend;
	}
	public void setNameFriend(String nameFriend) {
		this.nameFriend = nameFriend;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	@Override
	public String toString() {
		return "ModelAddFriend [userId=" + userId + ", nameUser=" + nameUser + ", friendId=" + friendId
				+ ", nameFriend=" + nameFriend + ", requestId=" + requestId + "]";
	}
	
	
	
	
}
