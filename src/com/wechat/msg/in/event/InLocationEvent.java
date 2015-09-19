package com.wechat.msg.in.event;

import java.util.Map;


/**
	上报地理位置事件
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[LOCATION]]></Event>
			<Latitude>23.137466</Latitude>
			<Longitude>113.352425</Longitude>
			<Precision>119.385040</Precision>
	</xml>
 */
public class InLocationEvent extends InEvent {
	
	private String latitude;
	private String longitude;
	private String precision;
	
	public InLocationEvent(Map<String, String> xmlMap) {
		super(xmlMap);
		latitude = xmlMap.get("Latitude");
		longitude = xmlMap.get("Longitude");
		precision = xmlMap.get("Precision");
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getPrecision() {
		return precision;
	}
	
	public void setPrecision(String precision) {
		this.precision = precision;
	}
}




