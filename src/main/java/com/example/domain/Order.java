package com.example.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name="ORDERS")
@EqualsAndHashCode(of="id")
public class Order {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private int orderAmount;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Member member;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Product product;
	
	@Embedded
	private Address address;

	public Order() {
	}

	public Order(Member member, Product product, Address address) {
		setMember(member);
		setProduct(product);
		setAddress(address);
	}

	public void setMember(Member member){
		//기존 관계 제거
		if(this.member!=null){
			this.member.getOrders().remove(this);
		}
		this.member = member;
		member.getOrders().add(this);
	}
	public void setProduct(Product product){
		if(this.product!=null){
			this.product.getOrders().remove(this);
		}
		this.product = product;
		product.getOrders().add(this);
	}

}
