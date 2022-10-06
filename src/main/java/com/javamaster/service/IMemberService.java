package com.javamaster.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.javamaster.entity.Member;

@Service
public interface IMemberService {
	Member createMember(Member member) throws InterruptedException, ExecutionException;
	Member getMemberById(String memberId) throws InterruptedException, ExecutionException;
	Member updateMember(Member member) throws InterruptedException, ExecutionException;
}
