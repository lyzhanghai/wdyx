package com.wechat.msg.in.event;

import java.util.Map;


/**
	接收 关注/取消关注事件
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[FromUser]]></FromUserName>
		<CreateTime>123456789</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[subscribe]]></Event>
	</xml>
*/

/**
	关注实测数据结果： 比官方文档多出一个 EventKey 标记
	<xml>
		<ToUserName><![CDATA[gh_e21b62f685f4]]></ToUserName>
		<FromUserName><![CDATA[o5Ljujn78A_S0uk_WvAM_fKFEXm4]]></FromUserName>
		<CreateTime>1411785252</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[subscribe]]></Event>
			<EventKey><![CDATA[]]></EventKey>
	</xml>
	取消关注实测数据结果：比官方文档多出一个 EventKey 标记
	<xml>
		<ToUserName><![CDATA[gh_e21b62f685f4]]></ToUserName>
		<FromUserName><![CDATA[o5Ljujn78A_S0uk_WvAM_fKFEXm4]]></FromUserName>
		<CreateTime>1411785559</CreateTime>
		<MsgType><![CDATA[event]]></MsgType>
			<Event><![CDATA[unsubscribe]]></Event>
			<EventKey><![CDATA[]]></EventKey>
	</xml>
*/
public class InFollowEvent extends InEvent {
	
	public InFollowEvent(Map<String, String> xmlMap) {
		super(xmlMap);
	}
	
}






