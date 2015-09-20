package com.wechat.msg.out;

import com.wechat.msg.in.InMsg;


/**
	回复语音消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[voice]]></MsgType>
			<Voice>
				<MediaId><![CDATA[media_id]]></MediaId>
			</Voice>
	</xml>
 */

public class OutVoiceMsg extends OutMsg {
	
	private String mediaId;
	
	public OutVoiceMsg() {
		this.msgType = "voice";
	}
	
	public OutVoiceMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "voice";
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@Override
	public String toString() {
		String t = "<xml><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[voice]]></MsgType><Voice><MediaId><![CDATA[mediaId]]></MediaId></Voice></xml>";
		t = t.replace("toUserName", toUserName)
			 .replace("fromUserName", fromUserName)
			 .replace("createTime", ""+createTime)
			 .replace("mediaId", mediaId);
		return t;
	}
}













