package com.wdyx.weixin.web.bbs;


public class MsgBlock {
	private int id;
	private String openid;
	private String headimgurl;
	private String spokesman;
	private String msg;
	private String timestamp;
	
	public String getSpokesman() {
		return spokesman;
	}
	public void setSpokesman(String spokesman) {
		this.spokesman = spokesman;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
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
}
