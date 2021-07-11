package com.salgam.transaction.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Member {
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int id;
	@Column(name = "member_id")
	private String name;
	
	Member() {}
	Member(int id,String name) {
		this.id 		= id;
		this.name 		= name;
	}
	
}
