package com.wdyx.weixin.web.bbs;

import java.sql.Timestamp;

public class BbsUser {
	private int id;
	private String openid;
	private String avatar;
	private String nickname;
	private int sex;
	private String address;
	private Timestamp lastActiveTime;
	private int forbid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Timestamp getLastActiveTime() {
		return lastActiveTime;
	}
	public void setLastActiveTime(Timestamp lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	public int getForbid() {
		return forbid;
	}
	public void setForbid(int forbid) {
		this.forbid = forbid;
	}
	

}
