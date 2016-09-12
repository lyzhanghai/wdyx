package com.wdyx.weixin.web.main;

public class WebItem {

	private int id;
	private String title;
	private String detail;
	private String[] picUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail() {
		return detail.replace("\n", "<br/>");
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String[] getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String[] picUrl) {
		this.picUrl = picUrl;
	}
}
