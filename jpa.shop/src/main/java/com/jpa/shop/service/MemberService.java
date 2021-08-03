package com.jpa.shop.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.shop.domain.Member;
import com.jpa.shop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

// 동기화 문제 :https://www.inflearn.com/questions/183033
// 결국 db에 유니크 걸어서 캐치하는 방법이 가장 깔끔 할 듯 싶다.
@Service
@Transactional(readOnly=true) // 아무거나 쓰는게 아닌 스프링꺼 써야한다. 또한 readonley = true는 읽기 성능을 회적화 한다.
@RequiredArgsConstructor // final 필드만 생성자 만들어준다.
public class MemberService {
	
	//대충 10분 부터 왜 요즘 생성자 주입하는지 설명함
	//final은 바꾸지 말고 사용 + 컴파일 체크 가능하게 해준다. 
	private final MemberRepository memberRepository;
	
//	@Autowired
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	//회원 가입
	@Transactional(readOnly=false) // 기본적으로 false인데 클래스 레벨에서 true 선언해서 바꿔줘야한다
	public Long join( Member member ) {
		validateDuplicateMember( member );
		memberRepository.save(member);
		return member.getId();
	}
	
	//회원 검증
	private void validateDuplicateMember( Member member ) {
		List<Member>  findMembers = memberRepository.findByName( member.getName() );
		if ( !findMembers.isEmpty() ) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}
	
	//회원 전체 조회 //(readOnly=true) 는 기본적으로 최적화다 . 
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	public Member findOne( Long memberId ) {
		return memberRepository.findOne(memberId);
	}
	
}
