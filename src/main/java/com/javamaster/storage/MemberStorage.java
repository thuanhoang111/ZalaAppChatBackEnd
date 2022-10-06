package com.javamaster.storage;

import java.util.HashSet;
import java.util.Set;

import com.javamaster.entity.Member;

public class MemberStorage {
	private static MemberStorage instance;
    private Set<Member> members;
    
    private MemberStorage() {
    	members = new HashSet<>();
    }

    public static synchronized MemberStorage getInstance() {
        if (instance == null) {
            instance = new MemberStorage();
        }
        return instance;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setMember(Member member) throws Exception {
        if (members.contains(member)) {
            throw new Exception("Member aready exists with userName: " + member);
        }
        members.add(member);
    }
}
