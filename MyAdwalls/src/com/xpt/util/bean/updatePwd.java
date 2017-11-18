package com.xpt.util.bean;

public class updatePwd {
	int  id;
	String  username;
	String  password;
	String  repassword;
	public  updatePwd(int id, String username,String password,String  repassword){
		this.id=id;
		this.username=username;
		this.password=password;
		this.repassword=repassword;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	@Override
	public String toString() {
		return "updatePwd [id=" + id + ", username=" + username + ", password="
				+ password + ", repassword=" + repassword + "]";
	}

	
}
