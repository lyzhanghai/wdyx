package com.wechat.msg.out;

import com.wechat.msg.InMsg;
import com.wechat.msg.OutMsg;


/**
	回复视频消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[video]]></MsgType>
			<Video>
				<MediaId><![CDATA[media_id]]></MediaId>
				<Title><![CDATA[title]]></Title>
				<Description><![CDATA[description]]></Description>
			</Video>
	</xml>
 */

public class OutVideoMsg extends OutMsg {
	
	private String mediaId;
	private String title;		// 不是必须
	private String description;	// 不是必须
	
	public OutVideoMsg() {
		this.msgType = "video";
	}
	
	public OutVideoMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "video";
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		String t = "<xml><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[mediaId]]></MediaId><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description></Video></xml>";
		t = t.replace("toUserName", toUserName)
			 .replace("fromUserName", fromUserName)
			 .replace("createTime", ""+createTime)
			 .replace("mediaId", mediaId)
			 .replace("title", title==null?"":title)
			 .replace("description", description==null?"":title);
		return t;
	}
}







