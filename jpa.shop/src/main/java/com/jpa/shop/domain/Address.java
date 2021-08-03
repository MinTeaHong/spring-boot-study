package com.jpa.shop.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address {
	private String city;
	private String street;
	private String zipcode;
	
	//jpa의 프록시나 그런거 사용할떄 기본 생성자를 사용한다고 한다.
	protected Address() {
	}
	
	public Address(String city, String street , String zipcode) {
		this.city 		= city;
		this.street 	= street;
		this.zipcode 	= zipcode;
	}
}
