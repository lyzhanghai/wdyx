package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;


/**
 * 请求方图片消息
 * @author 帮杰
 *
 */
public class ImageMsg extends BaseMsg {
	// 图片链接
	private String PicUrl;

	// 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		this.MediaId = mediaId;
	}

	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		this.setPicUrl(xmlMap.get("PicUrl"));
		this.setMediaId(xmlMap.get("MediaId"));
		super.parseRequestXML(xmlMap);
	}

}
