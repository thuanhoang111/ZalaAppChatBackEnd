package com.javamaster.model;

public class UpdateAccount {
	private String phoneNumber;
	private String fullName;
	private String avatar;
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public UpdateAccount(String phoneNumber, String fullName, String avatar) {
		super();
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
		this.avatar = avatar;
	}
	public UpdateAccount() {
		super();
	}
	@Override
	public String toString() {
		return "UpdateAccount [phoneNumber=" + phoneNumber + ", fullName=" + fullName + ", avatar=" + avatar + "]";
	}

}
