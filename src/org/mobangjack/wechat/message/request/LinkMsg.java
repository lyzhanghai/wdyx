package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;

/**
 * 请求方链接消息
 * @author 帮杰
 *
 */
public class LinkMsg extends BaseMsg {
	// 消息标题
	private String Title;
	// 消息描述
	private String Description;
	// 消息链接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		// TODO Auto-generated method stub
		super.parseRequestXML(xmlMap);
		this.setTitle(xmlMap.get("Title"));
		this.setDescription(xmlMap.get("Description"));
		this.setUrl(xmlMap.get("Url"));
	}

}
