//package com.jpa.shop;
//
//
//import java.util.PriorityQueue;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//public class MemberRepositoryTest {
//	
//	@Autowired MemberRepository memberRepository;
//	
//	@Test
//	@Transactional // spring에서 제공하는것을 써야한다.
//	@Rollback(false) // 맨마지막에 Rolled back transaction for te 막기위해서  이거 안 하면 insert도 안된다.
//	public void testMember() throws Exception {
//		
//		//given
//		Member member = new Member();
//		member.setUsername("testname");
//		
//		//when
//		Long savedId = memberRepository.save(member);
//		Member findMember = memberRepository.find(savedId);
//		
//		//then
//		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//	}
//	
//}
