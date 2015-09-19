package com.wechat.msg.in;

import java.util.Map;

import com.wechat.msg.InMsg;

/**
	接收文本消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName> 
		<CreateTime>1348831860</CreateTime>
		<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[this is a test]]></Content>
			<MsgId>1234567890123456</MsgId>
	</xml>
 */
public class InTextMsg extends InMsg {
	
	private String content;
	private String msgId;
	
	public InTextMsg(Map<String, String> xmlMap) {
		super(xmlMap);
		content = xmlMap.get("Content");
		msgId = xmlMap.get("MsgId");
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}




