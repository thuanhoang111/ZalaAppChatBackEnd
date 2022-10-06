package com.javamaster.entity;

public class AddFriendRequest {
	private String id;
	private String userId;
	private String nameUser;
	private String friendId;
	private String nameFriend;
	private boolean status;
	public AddFriendRequest(String id, String userId, String nameUser, String friendId, String nameFriend,
			boolean status) {
		super();
		this.id = id;
		this.userId = userId;
		this.nameUser = nameUser;
		this.friendId = friendId;
		this.nameFriend = nameFriend;
		this.status = status;
	}
	public AddFriendRequest() {
		super();
	}
	public AddFriendRequest(String id) {
		super();
		this.id = id;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AddFriendRequest [id=" + id + ", userId=" + userId + ", nameUser=" + nameUser + ", friendId=" + friendId
				+ ", nameFriend=" + nameFriend + ", status=" + status + "]";
	}
	
	
	
	
}
