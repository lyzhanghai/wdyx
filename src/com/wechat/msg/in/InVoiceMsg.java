package com.wechat.msg.in;

import java.util.Map;

import com.wechat.msg.InMsg;

/**
	接收语音消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>1357290913</CreateTime>
		<MsgType><![CDATA[voice]]></MsgType>
			<MediaId><![CDATA[media_id]]></MediaId>
			<Format><![CDATA[Format]]></Format>
			<Recognition><![CDATA[腾讯微信团队]]></Recognition>
			<MsgId>1234567890123456</MsgId>
	</xml>
*/
public class InVoiceMsg extends InMsg {
	
	private String mediaId;
	private String format;
	private String recognition;
	private String msgId;
	
	public InVoiceMsg(Map<String, String> xmlMap) {
		super(xmlMap);
		mediaId = xmlMap.get("MediaId");
		format = xmlMap.get("Format");
		recognition = xmlMap.get("Recognition");
		msgId = xmlMap.get("MsgId");
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}





