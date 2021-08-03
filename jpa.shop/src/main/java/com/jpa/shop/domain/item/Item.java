package com.jpa.shop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import com.jpa.shop.domain.Category;
import com.jpa.shop.exception.NotEnoughStockException;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Item {
	
	@Id @GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
	private int stockQuantity;
	
	@ManyToMany(mappedBy="items")
	private List<Category> categories = new ArrayList<>();
	
	//비지니스 로직
	/**
	 * stock 증가
	 */
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	
	/**
	 * stock 감소
	 */
	public void removeStock(int quantity) {
		int leftStock = this.stockQuantity - quantity;
		if (leftStock<0) {
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = leftStock;
	}
}
