package com.wechat.msg.in.event;

import java.util.Map;


/**
	自定义菜单事件
	1： 点击菜单拉取消息时的事件推送
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[CLICK]]></Event>
			<EventKey><![CDATA[EVENTKEY]]></EventKey>
	</xml>
	
	2： 点击菜单跳转链接时的事件推送
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[VIEW]]></Event>
			<EventKey><![CDATA[www.qq.com]]></EventKey>
	</xml>
 */
public class InMenuEvent extends InEvent {
	
	private String eventKey;
	
	public InMenuEvent(Map<String, String> xmlMap) {
		super(xmlMap);
		eventKey = xmlMap.get("EventKey");
	}
	
	public String getEventKey() {
		return eventKey;
	}
	
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}



