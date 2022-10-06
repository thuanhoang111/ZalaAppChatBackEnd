package com.javamaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javamaster.entity.Member;
import com.javamaster.entity.User;
import com.javamaster.service.IMemberService;
import com.javamaster.service.IUserService;

@Controller
@CrossOrigin
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private IMemberService memberService;

	@PostMapping(value = {"", "/"}, consumes = {
            "application/json",
            "application/x-www-form-urlencoded"
    })
    public Member addMember(@RequestBody Member model) {
		Member result;
        try {
        	result = memberService.createMember(model);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

}
