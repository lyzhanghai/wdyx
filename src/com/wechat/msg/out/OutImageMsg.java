package com.wechat.msg.out;

import com.wechat.msg.in.InMsg;


/**
	回复图片消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[image]]></MsgType>
			<Image>
				<MediaId><![CDATA[media_id]]></MediaId>
			</Image>
	</xml>
 */

public class OutImageMsg extends OutMsg {
	
	private String mediaId;
	
	public OutImageMsg() {
		this.msgType = "image";
	}
	
	public OutImageMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "image";
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@Override
	public String toString() {
		String t = "<xml><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[mediaId]]></MediaId></Image></xml>";
		t = t.replace("toUserName", toUserName)
			 .replace("fromUserName", fromUserName)
			 .replace("createTime", createTime+"")
			 .replace("mediaId", mediaId);
		return t;
	}
	
}



