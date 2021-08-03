package com.jpa.shop.repository;

import com.jpa.shop.domain.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class OrderSearch {

	private String memberName;
	private OrderStatus orderStatus;
	
}
