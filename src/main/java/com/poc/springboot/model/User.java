package com.poc.springboot.model;

import org.hibernate.validator.constraints.NotEmpty;

public class User {

	@NotEmpty(message="Enter User name.")
	private String userName;
	
	@NotEmpty(message="Enter password.")
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
