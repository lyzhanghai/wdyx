package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;


/**
 * 请求方视频消息
 * @author 帮杰
 *
 */
public class VideoMsg extends BaseMsg {
	
	// 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;
	// 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		super.parseRequestXML(xmlMap);
		this.setMediaId(xmlMap.get("MediaId"));
		this.setMsgType(xmlMap.get("ThumbMediaId"));
	}
}
