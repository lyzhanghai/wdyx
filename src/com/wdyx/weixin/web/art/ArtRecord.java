package com.wdyx.weixin.web.art;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class ArtRecord {
	private int id;
	private String title;
	private String description;
	private String time;
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
	public String getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
	public String toString() {
		return "《"+title.substring(title.lastIndexOf("】")+1)+"》"+"  "+description+"("+time+")";
	}
}
