package com.wechat.msg.out;

import com.wechat.msg.InMsg;
import com.wechat.msg.OutMsg;


/**
	回复音乐消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[music]]></MsgType>
			<Music>
				<Title><![CDATA[TITLE]]></Title>
				<Description><![CDATA[DESCRIPTION]]></Description>
				<MusicUrl><![CDATA[MUSIC_Url]]></MusicUrl>
				<HQMusicUrl><![CDATA[HQ_MUSIC_Url]]></HQMusicUrl>
				<ThumbMediaId><![CDATA[media_id]]></ThumbMediaId>
			</Music>
	</xml>
*/

public class OutMusicMsg extends OutMsg {
	
	private String title;		// 不是必须
	private String description;	// 不是必须
	private String musicUrl;	// 不是必须
	private String hqMusicUrl;	// 不是必须
	private String thumbMediaId;
	
	public OutMusicMsg() {
		this.msgType = "music";
	}
	
	public OutMusicMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "music";
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
	
	public String getMusicUrl() {
		return musicUrl;
	}
	
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	
	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	@Override
	public String toString() {
		String t = "<xml><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[music]]></MsgType><Music><Title><![CDATA[title]]></Title><Description><![CDATA[description]]></Description><MusicUrl><![CDATA[musicUrl]]></MusicUrl><HQMusicUrl><![CDATA[hqMusicUrl]]></HQMusicUrl><ThumbMediaId><![CDATA[thumbMediaId]]></ThumbMediaId></Music></xml>";
		t = t.replace("toUserName", toUserName)
			 .replace("fromUserName", fromUserName)
			 .replace("createTime", createTime+"")
			 .replace("title", title==null?"":title)
			 .replace("description", description==null?"":description)
			 .replace("musicUrl", musicUrl==null?"":musicUrl)
			 .replace("hqMusicUrl", hqMusicUrl==null?"":hqMusicUrl)
			 .replace("thumbMediaId", thumbMediaId);
		return t;
	}

	
}






