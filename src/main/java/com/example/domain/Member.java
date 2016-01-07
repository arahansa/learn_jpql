package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name="Member")
@EqualsAndHashCode(of="id")
@ToString(exclude={"orders"})
@NamedQueries({
		@NamedQuery(
				name = "Member.findByUsername",
				query = "select m from Member m where m.username= :username"
		),
		@NamedQuery(
				name = "Member.count",
				query = "select count(m) from Member m "
		)
})
@SqlResultSetMapping(name="memberWithOrderCount",
entities = {@EntityResult(entityClass = Member.class)},
columns = {@ColumnResult(name = "ORDER_COUNT")}
)
public class Member {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="name")
	private String username;
	private int age;
	
	@OneToMany(mappedBy="member")
	private List<Order> orders = new ArrayList<>();
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Team team;
	
	public void setTeam(Team team){
		this.team = team;
		team.getMembers().add(this);
	}

	public Member() {
	}
	public Member(String username) {
		super();
		this.username = username;
	}
	public Member(int age) {
		super();
		this.age = age;
	}
	public Member(String username, Team team){
		super();
		this.username = username;
		setTeam(team);
	}
	public Member(String username, Team team, int age){
		super();
		this.username = username;
		this.age = age;
		setTeam(team);
	}

	public Member(String username, int age) {
		super();
		this.username = username;
		this.age = age;
	}
	
	public Member(int age, Team team) {
		super();
		this.age = age;
		setTeam(team);
	}
	

	
}
