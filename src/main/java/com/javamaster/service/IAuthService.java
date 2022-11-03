package com.javamaster.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.dto.ModelLogin;
import com.javamaster.dto.ModelRegisterAccount;
import com.javamaster.entity.Account;
import com.javamaster.model.UpdateAccount;

@Service
public interface IAuthService {
	Account registerAccount(ModelRegisterAccount modelRegisterAccount) throws InterruptedException, ExecutionException;
	Account login(ModelLogin modelLogin) throws InterruptedException, ExecutionException;
	int updateAccount(UpdateAccount updateAccount, String phoneNumber) throws InterruptedException, ExecutionException;
}
