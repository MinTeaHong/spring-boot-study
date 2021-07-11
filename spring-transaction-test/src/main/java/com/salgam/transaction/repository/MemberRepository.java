package com.salgam.transaction.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.salgam.transaction.domain.Member;

@Repository
public class MemberRepository {

	@PersistenceContext
	private final EntityManager em;
	
	MemberRepository( EntityManager em ) {
		this.em = em;
	}
	public void save(Member member) {
		em.persist(member);
	}
	
	public List<Member> selectAllMember() {
		// 조회전에 jpa 캐시 비우기위해서
		em.clear();
		return em.createQuery( "select m from Member m" , Member.class).getResultList();
	}
	
	public Member findMemberById(int id) {
		// 조회전에 jpa 캐시 비우기위해서
		em.clear();
		return em.find(Member.class, id);
	}
	
	public Member updateMemberById(int id,String name) {
		
		Member m = em.find(Member.class, id);
		m.setName(name);
		return m;
		
	}
	
}
