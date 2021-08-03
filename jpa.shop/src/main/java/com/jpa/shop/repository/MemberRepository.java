package com.jpa.shop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.springframework.stereotype.Repository;

import com.jpa.shop.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor //EntityManager도 스프링 최신버전에서 PersistenceContext로 찾아줌
public class MemberRepository {

//	@PersistenceContext
	private final EntityManager em;
	
	//참고 : 엔터티 팩토리 
//	@PersistenceUnit
//	private EntityManagerFactory emf;
	
	public void save( Member member ) {
		em.persist(member);
	}
	
	public Member findOne( Long id ) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery( "select m from Member m" , Member.class)
				.getResultList();
	}
	
	public List<Member> findByName( String name ) {
		return em.createQuery( "select m from Member m where m.name = :name" , Member.class)
				.setParameter("name", name)
				.getResultList();
	}
	
	
}
