package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;


/**
 * 请求方文本消息
 * @author 帮杰
 *
 */
public class TextMsg extends BaseMsg {
	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		super.parseRequestXML(xmlMap);
		this.setContent(xmlMap.get("Content"));
	}



}
