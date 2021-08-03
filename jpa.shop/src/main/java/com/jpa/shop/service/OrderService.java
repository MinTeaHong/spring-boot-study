package com.jpa.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.shop.domain.Delivery;
import com.jpa.shop.domain.Member;
import com.jpa.shop.domain.Order;
import com.jpa.shop.domain.OrderItem;
import com.jpa.shop.domain.item.Item;
import com.jpa.shop.repository.ItemRepository;
import com.jpa.shop.repository.MemberRepository;
import com.jpa.shop.repository.OrderRepository;
import com.jpa.shop.repository.OrderSearch;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor 
public class OrderService {

	private final OrderRepository 	orderRepository;
	private final MemberRepository 	memberRepository;
	private final ItemRepository 	itemRepository;
	
	/**
	 * 주문 단일 주문만 처리함 -> 추후에  다수 주문으로 바꿔보셈
	 */
	@Transactional
	public Long order(Long memberId,Long itemId,int count) {
		
		//맴버조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		//주문 상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		
		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);
		//주문 저장 cascade 떄문에 다른것도 저장이 된다.
		// cascade의 lifecycle이 order랑 동일하게 관리가 될 떄는 이렇게 사용한다.
		// 만약 delrvery가 중요하게 다른 곳에서 쓰이면 cascade로 쓰면 안된다.
		orderRepository.save(order);
		
		return order.getId();
	}
	
	/**
	 * 주문 취소 
	 */
	@Transactional
	public void cancelOrder(Long orderId) {
		//주문 엔터티 조회
		Order order = orderRepository.findOne(orderId);
		//주문 취소
		order.cancel();
	}
	//검색
	public List<Order> findOrders( OrderSearch orderSearch ) {
		return orderRepository.findAllByString(orderSearch);
		
	}
}
