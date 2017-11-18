package com.xpt.util.bean;

import java.io.Serializable;

public class User implements Serializable{
	private Integer id;
	private String userName;
	private String password;
	private String name;
	private String role;
	public User() {
		super();
	}
	public User(Integer id, String userName, String password, String name) {
		super();
		this.userName = userName;
		this.password = password;
		this.id = id;
		this.name = name;
	}

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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", id=" + id + ", name=" + name + "]";
	}
	

}
