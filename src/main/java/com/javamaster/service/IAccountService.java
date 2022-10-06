package com.javamaster.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.Account;

@Service
public interface IAccountService {
	int createAccount(Account account) throws InterruptedException, ExecutionException;
	Account getAccountByPhoneNumber(String phoneNumber) throws InterruptedException, ExecutionException;
	int updateAccount(Account account);
}
