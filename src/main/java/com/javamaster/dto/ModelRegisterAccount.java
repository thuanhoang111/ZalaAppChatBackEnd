package com.javamaster.dto;

import java.util.List;

public class ModelRegisterAccount {
	private String phoneNumber;
	private String password;
	private String fullName;
	public ModelRegisterAccount(String phoneNumber, String password, String fullName) {
		super();
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.fullName = fullName;
	}
	public ModelRegisterAccount() {
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Override
	public String toString() {
		return "ModelRegisterAccount [phoneNumber=" + phoneNumber + ", password=" + password + ", fullName=" + fullName
				+ "]";
	}
	
	
	
	
}
