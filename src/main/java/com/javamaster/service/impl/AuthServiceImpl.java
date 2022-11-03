 package com.javamaster.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javamaster.dto.ModelLogin;
import com.javamaster.dto.ModelRegisterAccount;
import com.javamaster.entity.Account;
import com.javamaster.entity.Conversation;
import com.javamaster.entity.Member;
import com.javamaster.entity.Message;
import com.javamaster.entity.User;
import com.javamaster.model.UpdateAccount;
import com.javamaster.service.IAccountService;
import com.javamaster.service.IAuthService;
import com.javamaster.service.IConversationService;
import com.javamaster.service.IMemberService;
import com.javamaster.service.IMessageService;
import com.javamaster.service.IUserService;

@Service
public class AuthServiceImpl implements IAuthService {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IUserService userService;
	@Autowired
	private IConversationService conversationService;
	@Autowired
	private IMemberService memberService;
	@Autowired
	private IMessageService messageService;

	@Override
	public Account registerAccount(ModelRegisterAccount modelRegisterAccount)
			throws InterruptedException, ExecutionException {

		System.out.println(modelRegisterAccount.toString());

		User user = new User();
		user.setFullName(modelRegisterAccount.getFullName());
		user.setPhoneNumber(modelRegisterAccount.getPhoneNumber());

		user = userService.addUser(user);

		Account account = new Account();
		account.setPassword(modelRegisterAccount.getPassword());
		account.setPhoneNumber(modelRegisterAccount.getPhoneNumber());
		account.setUserId(user.getId());

		Account result = accountService.createAccount(account);

		return result;
	}

	@Override
	public Account login(ModelLogin modelLogin) throws InterruptedException, ExecutionException {

		Account account = accountService.getAccountByPhoneNumber(modelLogin.getPhoneNumber());
		
		if(account.getPassword().equals(modelLogin.getPassword())) {
			return account;
		} else {
			return null;
		}

	}
	@Override
	public int updateAccount(UpdateAccount updateAccount,String phoneNumber) throws InterruptedException, ExecutionException {
		
		User user = userService.findUserByPhoneNumber(phoneNumber);
		Account account = accountService.getAccountByPhoneNumber(phoneNumber);
		String Conversation_id = user.getConversations().get(0);
		Conversation conversation = conversationService.getConversationById(Conversation_id);
		List<Member> listMember = conversationService.getAllMemberInConversation(Conversation_id);
		List<Message> listMessages = conversationService.getAllMessageInConversation(Conversation_id);
		String userName = user.getFullName();
		if(user.getAvatar()!=updateAccount.getAvatar()) {
			user.setAvatar(updateAccount.getAvatar());
			for (Member member : listMember) {
				if(member.getNameUser().equals(userName) == true) {
					member.setAvatar(updateAccount.getAvatar());
					member.setNameUser(updateAccount.getFullName());
					memberService.updateMember(member);
					break;
				}
			}
			for (Message message : listMessages) {
				if(message.getSenderName().equals(userName) == true) {
					message.setAvatarSender(updateAccount.getAvatar());
					message.setSenderName(updateAccount.getFullName());
					messageService.updateMessage(message);
				}
			}
		}
		if(user.getFullName() != updateAccount.getFullName()) {
			String groupName = "";
			if(conversation.getGroupName().contains("-") == true) {
				String[] listMemberinGr = conversation.getGroupName().split("-");
				for (int i = 0; i < listMemberinGr.length; i++) {
					if(i != 0) {
						groupName +="-";
					}
					if(listMemberinGr[i].equals(userName) == true) {
						listMemberinGr[i]=updateAccount.getFullName();
					}
					groupName += listMemberinGr[i];
				}
				
			}
			System.out.println(groupName);
			conversation.setGroupName(groupName);
			
			conversationService.updateConvertation(conversation);
			user.setFullName(updateAccount.getFullName());
		}
		if(user.getPhoneNumber()!=updateAccount.getPhoneNumber()) {
			account.setPhoneNumber(updateAccount.getPhoneNumber());
			user.setPhoneNumber(updateAccount.getPhoneNumber());
		}		
//		
		System.out.println(user);
		System.out.println(account);
		User user1 =  userService.updateUser(user);
		System.out.println(user1);
		int result = accountService.updateAccount(account);

		return 0;
	}
		

}
