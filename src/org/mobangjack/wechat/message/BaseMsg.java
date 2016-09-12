package org.mobangjack.wechat.message;

import java.util.Map;

/**
 * 请求方所有信息的基类
 * @author 帮杰
 *
 */

public class BaseMsg {
	protected String ToUserName; // 开发者微信号
	protected String FromUserName; // 发送方帐号（一个OpenID）
	protected String CreateTime; // 消息创建时间 （整型）
	protected String MsgType; // text
	protected String MsgId; // 消息id，64位整型

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public void parseRequestXML(Map<String, String> xmlMap) {
		this.setToUserName(xmlMap.get("ToUserName"));
		this.setCreateTime(xmlMap.get("CreateTime"));
		this.setFromUserName(xmlMap.get("FromUserName"));
		this.setMsgType(xmlMap.get("MsgType"));
		this.setMsgId(xmlMap.get("MsgId"));
	}

}
