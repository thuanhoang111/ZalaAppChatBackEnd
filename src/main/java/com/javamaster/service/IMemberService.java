package com.javamaster.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.Member;

import reactor.core.publisher.Flux;

@Service
public interface IMemberService {
	Member createMember(Member member) throws InterruptedException, ExecutionException;

	Member getMemberById(String memberId) throws InterruptedException, ExecutionException;
//Author:NHH
	Flux<Member> getMemberByConversationId(String conversationId) throws InterruptedException, ExecutionException;

	Member updateMember(Member member) throws InterruptedException, ExecutionException;

//Author:NHH
	String deleteMemberById(String memberId) throws InterruptedException, ExecutionException;
//Author:NHH
	int deleteMemberInUser(String memberId, String userId) throws InterruptedException, ExecutionException;
} 
