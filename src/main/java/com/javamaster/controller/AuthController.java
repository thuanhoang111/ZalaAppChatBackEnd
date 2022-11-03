package com.javamaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javamaster.dto.ModelLogin;
import com.javamaster.dto.ModelRegisterAccount;
import com.javamaster.entity.Account;
import com.javamaster.model.UpdateAccount;
import com.javamaster.service.IAuthService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private IAuthService authService;
	
	@PostMapping("/register")
	private Account register(@RequestBody ModelRegisterAccount model) {
		try {
			Account result = authService.registerAccount(model);
			return result;			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	@PostMapping("/login")
	private Account register(@RequestBody ModelLogin model) {
		try {
			Account result = authService.login(model);
			System.out.println(result.toString());
			return result;			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	@PutMapping("/update/{phoneNumber}")
	private int update(@RequestBody UpdateAccount model,@PathVariable String phoneNumber) {
		try {
			int result = authService.updateAccount(model,phoneNumber);
			return result;			
		} catch (Exception e) {
			// TODO: handle exception
			return 1;
		}
	}
}
