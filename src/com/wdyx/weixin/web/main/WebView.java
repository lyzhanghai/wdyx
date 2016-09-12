package com.wdyx.weixin.web.main;

import java.util.List;

public class WebView {
	private int id;
	private String title;
	private String description;
	private String picUrl;
	private List<WebItem> items;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public List<WebItem> getItems() {
		return items;
	}

	public void setItems(List<WebItem> items) {
		this.items = items;
	}
	

}
