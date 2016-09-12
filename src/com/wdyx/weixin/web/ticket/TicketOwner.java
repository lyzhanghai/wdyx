package com.wdyx.weixin.web.ticket;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TicketOwner {

	private int id;
	private String openid;
	private String title;
	private String type;
	private String time;
	private String state;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.time = format.format(time);
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
