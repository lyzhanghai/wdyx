package com.wdyx.weixin.web.vote;

public class Vote {
	
	private int id;
	private String title;
	private String cover;
	private String[] candidate;
	private String[] picurl;
	private String[] description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String[] getCandidate() {
		return candidate;
	}
	public void setCandidate(String[] candidate) {
		this.candidate = candidate;
	}
	public String[] getPicurl() {
		return picurl;
	}
	public void setPicurl(String[] picurl) {
		this.picurl = picurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String[] getDescription() {
		return description;
	}
	public void setDescription(String[] description) {
		this.description = description;
	}

}
