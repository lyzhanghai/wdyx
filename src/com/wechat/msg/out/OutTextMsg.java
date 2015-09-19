package com.wechat.msg.out;

import com.wechat.msg.InMsg;
import com.wechat.msg.OutMsg;


/**
	回复文本消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[text]]></MsgType>
			<Content><![CDATA[你好]]></Content>
	</xml>
 */

public class OutTextMsg extends OutMsg {
	
	private String content;
	
	public OutTextMsg() {
		this.msgType = "text";
	}
	
	public OutTextMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "text";
	}
	
	public String getContent() {
		return content;
	}
	
	public OutTextMsg setContent(String content) {
		this.content = content;
		return this;
	}
	
	@Override
	public String toString() {
		String t = "<xml><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[content]]></Content></xml>";
		t = t.replace("toUserName", toUserName)
			 .replace("fromUserName", fromUserName)
			 .replace("createTime", ""+createTime)
			 .replace("content", content);
		return t;
	}
}


