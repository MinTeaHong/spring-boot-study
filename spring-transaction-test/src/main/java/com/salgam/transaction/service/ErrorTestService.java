package com.salgam.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salgam.transaction.domain.Member;
import com.salgam.transaction.repository.JdbcMemberTemplate;
import com.salgam.transaction.repository.MemberRepository;

@Service
public class ErrorTestService {
	
	private final MemberRepository memberRepository;
	private final JdbcMemberTemplate jdbcMemberTemplate;
	
	
			
	ErrorTestService(MemberRepository memberRepository,JdbcMemberTemplate jdbcMemberTemplate) {
		this.memberRepository = memberRepository;
		this.jdbcMemberTemplate = jdbcMemberTemplate;
		
	}
	
	//저장됨.
	@Transactional(readOnly=false) 
	public Member addMember1(String name) {
		Member member = Member.builder().name(name).build();
		memberRepository.save(member);
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
	
	//저장안됨
	@Transactional(readOnly=false) 
	public Member addMember2(String name) {
		Member member = Member.builder().name(name).build();
		memberRepository.save(member);
		throw new RuntimeException("Runtime error");
	}
	
	
	//저장안됨이 예상 되었는데 저장됨(try catch에서 이미 에러를 넘겨버림)
	@Transactional(readOnly=false,rollbackFor=Exception.class) 
	public Member addMember3(String name) {
		Member member = Member.builder().name(name).build();
		memberRepository.save(member);
		try {
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
	
	@Transactional(readOnly=false,rollbackFor=Exception.class) 
//	@Transactional(readOnly=false) 
	public Member addMember3_Throw(String name)  throws Exception {
		Member member = Member.builder().name(name).build();
		memberRepository.save(member);
		throw new Exception();
	}
	
	
//	@Transactional(readOnly=false,rollbackFor=Exception.class) 
//	public Member addMember3_jdbc(String name) {
//		Member member = Member.builder().name(name).build();
//		jdbcMemberTemplate.save(member);
//		try {
//			throw new Exception();
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		return member;
//	}
	
	//저장됨
	@Transactional(readOnly=false,noRollbackFor=RuntimeException.class) 
	public Member addMember4(String name) {
		Member member = Member.builder().name(name).build();
		memberRepository.save(member);
		throw new RuntimeException("Runtime error");
	}
	
	
}
