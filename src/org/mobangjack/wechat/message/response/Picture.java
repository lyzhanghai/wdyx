package org.mobangjack.wechat.message.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Picture")
public class Picture {

	@XStreamAlias("MediaId")
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}