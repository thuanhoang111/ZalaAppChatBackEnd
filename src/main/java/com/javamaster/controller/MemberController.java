package com.javamaster.controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javamaster.entity.Member;
import com.javamaster.entity.Message;
import com.javamaster.service.IMemberService;

import reactor.core.publisher.Flux;

@Controller
@CrossOrigin
@RequestMapping("/members")
public class MemberController {

	@Autowired
	private IMemberService memberService;

	@PostMapping(value = { "", "/" }, consumes = { "application/json", "application/x-www-form-urlencoded" })
	public Member addMember(@RequestBody Member model) {
		Member result;
		try {
			result = memberService.createMember(model);
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	@GetMapping("/{conversationId}")
	public Flux<Member> getAllMessagesInConversation(@PathVariable String conversationId) {

		try {
			return memberService.getMemberByConversationId(conversationId);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
