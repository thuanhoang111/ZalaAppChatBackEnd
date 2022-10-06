package com.javamaster.entity;

public class Account {
	private String id;
	private String phoneNumber;
	private String password;
	private String userId;
	public Account(String id, String phoneNumber, String password, String userId) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.userId = userId;
	}
	public Account() {
		super();
	}
	public Account(String id) {
		super();
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Accout [id=" + id + ", phoneNumber=" + phoneNumber + ", password=" + password + ", userId=" + userId
				+ "]";
	}
	
	
}
