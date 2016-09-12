package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;

/**
 * 请求方事件消息
 * @author 帮杰
 *
 */
public class EventMsg extends BaseMsg {
	private String Event; // 事件类型，VIEW
	private String EventKey; // 事件KEY值，设置的跳转URL

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		super.parseRequestXML(xmlMap);
		this.setEvent(xmlMap.get("Event"));
		this.setEventKey(xmlMap.get("EventKey"));
	}

}
