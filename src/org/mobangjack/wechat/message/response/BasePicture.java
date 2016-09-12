package org.mobangjack.wechat.message.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class BasePicture {

	@XStreamAlias("ToUserName")
	private String ToUserName;

	@XStreamAlias("FromUserName")
	private String FromUserName;

	@XStreamAlias("CreateTime")
	private String CreateTime;

	@XStreamAlias("MsgType")
	private String MsgType;

	@XStreamAlias("Image")
	private Picture Picture;

	public Picture getPicture() {
		return Picture;
	}

	public void setPicture(Picture picture) {
		Picture = picture;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
