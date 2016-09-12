package org.mobangjack.wechat.message.request;

import java.util.Map;

import org.mobangjack.wechat.message.BaseMsg;


/**
 * 请求方语音消息
 * @author 帮杰
 *
 */
public class VoiceMsg extends BaseMsg {
	
	// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;
	// 语音格式，如amr，speex等
	private String Format;
	//开启语音识别后的识别结果
	private String Recognition;
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	@Override
	public void parseRequestXML(Map<String, String> xmlMap) {
		this.setFormat(xmlMap.get("Format"));
		this.setMediaId(xmlMap.get("MediaId"));
		this.setRecognition(xmlMap.get("Recognition"));
		super.parseRequestXML(xmlMap);
	}

}
