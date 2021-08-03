package com.jpa.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.jpa.shop.domain.item.Item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	// 여기는 왜 반대쪽 설정을 안 하냐?
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	private int orderPrice;
	
	private int count;
	
	// 유지 보수를 위해 막는다.
//	protected OrderItem() {
//		
//	}
	
	//생성 메서드 -> 뉴하고 set하면 유지 보수에 좋지  않으므로 다음 처럼 행동하기.
	public static OrderItem createOrderItem( Item item, int orderPrice, int count ) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setOrderPrice(orderPrice);
		orderItem.setCount(count);
		
		item.removeStock(count);
		return orderItem;
	}
	
	//비지니스 로직
	public void cancel() {
		getItem().addStock(count);
	}
	
	//조회 로직
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
	
}
