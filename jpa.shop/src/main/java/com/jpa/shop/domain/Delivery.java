package com.jpa.shop.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
	@Id @GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy="delivery" ,fetch=FetchType.LAZY)
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status; // ORDINAL 절대 쓰지말기 --> 1. 2 ,3
}
