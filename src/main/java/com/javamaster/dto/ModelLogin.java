package com.javamaster.dto;

public class ModelLogin {
	private String phoneNumber;
	private String password;
	public ModelLogin(String phoneNumber, String password) {
		super();
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	public ModelLogin() {
		super();
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
	@Override
	public String toString() {
		return "ModelLogin [phoneNumber=" + phoneNumber + ", password=" + password + "]";
	}
	
	
}
