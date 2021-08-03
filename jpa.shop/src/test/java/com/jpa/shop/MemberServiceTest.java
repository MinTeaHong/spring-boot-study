package com.jpa.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.shop.domain.Member;
import com.jpa.shop.repository.MemberRepository;
import com.jpa.shop.service.MemberService;

@SpringBootTest
@Transactional
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository; 
	
	@Autowired
	EntityManager em;
	
	@Test
//	@Rollback(false) // 이게 없으면 insert가 되지 않는다. 혹은 아래처럼 em.flush를 한다.
	// 기본적으로 테스트는 롤백이라 주의해야한다. 그래서 transaction이 롤백이라 jpa는 애초에 아무 설정없으면 insert조차 하지 않는다.
	public void 회원가입() throws Exception {
		
		//given 
		Member member = new Member();
		member.setName("kim");
		
		//when 
		Long savedId = memberService.join(member);
		
		//then --> jpa이기 때문에 같아야한다.
		em.flush();
		assertEquals(member, memberRepository.findOne(savedId));
		
	}
	
	
	@Test
	public void 중복_회원_예외() throws Exception {
		//given 
		Member member1 = new Member();
		member1.setName("kim1");
		
		Member member2 = new Member();
		member2.setName("kim1");
		
		//when 
		memberService.join(member1);
		try {
			memberService.join(member2);
		} catch (IllegalStateException e) {
			return;
		}
		
		//then
		fail("예외가 발생해야한다");
	}
	
	
}
