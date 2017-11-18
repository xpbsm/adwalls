package com.xpt.util.bean;

public class userRegister {
	int  id;
	String  name;
	String  username;
	String  password;
	String  repassword;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "userRegister [username=" + username + ", password=" + password
				+ ", repassword=" + repassword + "]";
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public userRegister(int id, String name, String username, String password,
			String repassword) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.repassword = repassword;
	}

}
