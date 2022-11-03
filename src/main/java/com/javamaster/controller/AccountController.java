package com.javamaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.entity.Account;
import com.javamaster.entity.User;
import com.javamaster.service.IAccountService;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private IAccountService accountService;
	
	@GetMapping("/filter")
	public Account findAccountByPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber) {
		Account account;
		
		try {
			account = accountService.getAccountByPhoneNumber('+'+phoneNumber);
		} catch (Exception e) {
			return null;
		}
		return account;
	}
	
	@PutMapping("")
	public int updateAccount(@RequestBody Account model) {
		
		try {
			int status = accountService.updateAccount(model);
			return status;
		} catch (Exception e) {
			return 1;
		}
	}
}
