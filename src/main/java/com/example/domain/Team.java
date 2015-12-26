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

@Data
@Entity
@EqualsAndHashCode(of="id")
@ToString(exclude={"members"})
public class Team {

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="team")
	private List<Member> members = new ArrayList<>();
	
	public Team(){}
	public Team(String name){
		this.name = name;
	}
	
}
