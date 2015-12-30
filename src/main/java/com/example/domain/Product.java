package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@EqualsAndHashCode(of="id")
@Data
@ToString(exclude={"orders"})
public class Product {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockAmount;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="product")
	private List<Order> orders = new ArrayList<>();

	public Product() {
	}

	public Product(String name) {
		this.name = name;
	}
}
