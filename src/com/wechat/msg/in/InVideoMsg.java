package com.wechat.msg.in;

import java.util.Map;

import com.wechat.msg.InMsg;

/**
	接收视频消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>1357290913</CreateTime>
		<MsgType><![CDATA[video]]></MsgType>
			<MediaId><![CDATA[media_id]]></MediaId>
			<ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
			<MsgId>1234567890123456</MsgId>
	</xml>
*/
public class InVideoMsg extends InMsg {
	
	private String mediaId;
	private String thumbMediaId;
	private String msgId;
	
	public InVideoMsg(Map<String, String> xmlMap) {
		super(xmlMap);
		mediaId = xmlMap.get("MediaId");
		thumbMediaId = xmlMap.get("ThumbMediaId");
		msgId = xmlMap.get("MsgId");
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}



