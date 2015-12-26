package com.example.domain.dto;

public class UserDTO {

	private String username;
	private int age;
	
	public UserDTO() {
	}
	public UserDTO(String username, int age) {
		super();
		this.username = username;
		this.age = age;
	}
	
	// GetSet
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
