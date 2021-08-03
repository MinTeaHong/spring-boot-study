package com.jpa.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.shop.domain.Address;
import com.jpa.shop.domain.Member;
import com.jpa.shop.domain.Order;
import com.jpa.shop.domain.item.Book;
import com.jpa.shop.domain.OrderStatus;
import com.jpa.shop.domain.item.Item;
import com.jpa.shop.exception.NotEnoughStockException;
import com.jpa.shop.repository.OrderRepository;
import com.jpa.shop.service.OrderService;

@SpringBootTest
@Transactional
public class OrderServiceTest {

	@Autowired
	EntityManager em;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	
	
	@Test
	@Rollback(false) 
	public void 상품주문() throws Exception {
		//given
		Member member = createMember();
		Item book = createBook("사골 JPA",10000,10);
		//when
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
		//then
		Order getOrder = orderRepository.findOne(orderId);
		Assertions.assertEquals( OrderStatus.ORDER, getOrder.getStatus(),"상품 주문시 상태는 order");
		Assertions.assertEquals( 1, getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야한다.");
		Assertions.assertEquals( 10000*orderCount, getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다.");
		Assertions.assertEquals( 8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야한다.");
		
		
	}
	
	@Test
	public void 상품주문_재고수량초과() throws Exception {
		//given
		Member 	member 	= createMember();
		Item 	book 	= createBook("사골 JPA",10000,10);
		int orderCount = 11;
		//when
		
		try {
			orderService.order(member.getId(),book.getId(), orderCount);
		} catch (NotEnoughStockException e) {
			return;
		}
		
		//then
		fail("재고 수량 예외가 발생해야한다.");
		
	}
	
	@Test
	public void 주문취소() throws Exception {
		//given
		Member 	member 	= createMember();
		Item 	book 	= createBook("사골 JPA",10000,10);
		int orderCount = 2;
		//여기까지가 given이다
		Long orderId = orderService.order(member.getId(),book.getId(), orderCount);
		//when
		orderService.cancelOrder(orderId);
		//then
		Order getOrder =  orderRepository.findOne(orderId);
		Assertions.assertEquals( OrderStatus.CALCLE, getOrder.getStatus(),"주문 취소시 상태는 cancel이다.");
		Assertions.assertEquals( 10, book.getStockQuantity(),"주문 취소된 상품은 그만큼 재고가 증가해야한다.");
		
	}
	

	private Item createBook(String name,int price,int stockQuantity) {
		Item book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-123"));
		em.persist(member);
		return member;
	}
	
}
