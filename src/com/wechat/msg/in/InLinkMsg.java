package com.wechat.msg.in;

import java.util.Map;

import com.wechat.msg.InMsg;

/**
	接收链接消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>1351776360</CreateTime>
		<MsgType><![CDATA[link]]></MsgType>
			<Title><![CDATA[公众平台官网链接]]></Title>
			<Description><![CDATA[公众平台官网链接]]></Description>
			<Url><![CDATA[url]]></Url>
			<MsgId>1234567890123456</MsgId>
	</xml>
*/
public class InLinkMsg extends InMsg {
	
	private String title;
	private String description;
	private String url;
	private String msgId;
	
	public InLinkMsg(Map<String, String> xmlMap) {
		super(xmlMap);
		title = xmlMap.get("Title");
		description = xmlMap.get("Description");
		url = xmlMap.get("Url");
		msgId = xmlMap.get("MsgId");
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}



