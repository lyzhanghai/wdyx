package com.wdyx.weixin.web.art;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ArtReview {
	
	private int id;
	private String openid;
	private String title;
	private String review;
	private String time;
	private int artid;
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
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
	public int getArtid() {
		return artid;
	}
	public void setArtid(int artid) {
		this.artid = artid;
	}
	public String toString() {
		return review+"("+time+")";
	}
}
