package com.wdyx.weixin.web.admin;

public class Admin {

	private int id;
	private String username;
	private String password;
	private String authoriry;
	
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
	public String getAuthoriry() {
		return authoriry;
	}
	public void setAuthoriry(String authoriry) {
		this.authoriry = authoriry;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
