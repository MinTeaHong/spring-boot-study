package com.salgam.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.salgam.transaction.domain.Member;
import com.salgam.transaction.repository.MemberRepository;

@Service
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final long TEMP_SLEEP_TIME_SECOND = 5;
	
	MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Transactional(readOnly=false)
	public Member addMember(String name) {
		Member member = Member.builder().name(name).build();
		memberRepository.save(member);
		try {
			Thread.sleep( TEMP_SLEEP_TIME_SECOND * 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return member;
	}
	
	@Transactional(readOnly=true,isolation=Isolation.READ_UNCOMMITTED)
	public List<Member> getAllMember_READ_UNCOMMITTED() {
		return memberRepository.selectAllMember();
	}
	
	@Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED)
	public List<Member> getAllMember_READ_COMMITTED() {
		List<Member> beforeList = memberRepository.selectAllMember();
		beforeList.forEach( m -> m.setName( m.getName() + "_before" )  );
		
		try {
			Thread.sleep( TEMP_SLEEP_TIME_SECOND * 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Member> afterList = memberRepository.selectAllMember();
		afterList.forEach( m -> m.setName( m.getName() + "_after" )  );
		beforeList.addAll(afterList);
		
		return beforeList;
	}

	@Transactional(readOnly=true,isolation=Isolation.REPEATABLE_READ)
	public List<Member> getAllMember_REPEATABLE_READ() {
		
		List<Member> beforeList = memberRepository.selectAllMember();
		beforeList.forEach( m -> m.setName( m.getName() + "_before" )  );
		
		try {
			Thread.sleep( TEMP_SLEEP_TIME_SECOND * 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		List<Member> afterList = memberRepository.selectAllMember();
		afterList.forEach( m -> m.setName( m.getName() + "_after" )  );
		beforeList.addAll(afterList);
		
		return beforeList;
	}
	
	
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public Member getMember_SERIALIZABLE( int id ) {
		Member member = memberRepository.findMemberById(id);
		try {
			Thread.sleep( TEMP_SLEEP_TIME_SECOND * 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	@Transactional(readOnly=false)
	public Member updateMember( int id , String name ) {
		return memberRepository.updateMemberById(id, name);
	}
	
}
