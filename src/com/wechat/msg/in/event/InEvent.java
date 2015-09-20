package com.wechat.msg.in.event;

import java.util.Map;

import com.wechat.msg.in.InMsg;

public class InEvent extends InMsg {

	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_LOCATION = "LOCATION";
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";
	public static final String EVENT_SCAN = "SCAN";
	
	private String event;

	public InEvent(Map<String, String> xmlMap) {
		super(xmlMap);
		event = xmlMap.get("Event");
	}
	
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
