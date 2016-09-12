package com.wdyx.weixin.web.bbs;

import java.util.Date;

import org.mobangjack.common.util.DateUtil;


public class Bbs {
	private int id;
	private String openid;
	private String msg;
	private String time;
	private int msgid;
	private int readflag;
	
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public int getReadflag() {
		return readflag;
	}
	public void setReadflag(int readflag) {
		this.readflag = readflag;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setTime(Date time) {
		this.time = DateUtil.format(time, DateUtil.FORMAT_DATETIME);
	}
}
