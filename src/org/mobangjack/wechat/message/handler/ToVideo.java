package org.mobangjack.wechat.message.handler;

import java.util.Date;

import org.mobangjack.wechat.message.BaseMsg;
import org.mobangjack.wechat.message.response.BaseVideo;
import org.mobangjack.wechat.message.response.Video;

public class ToVideo implements MessageHandler {

	private Video video;

	public ToVideo(Video video) {
		this.video = video;
	}

	@Override
	public Object handleMessage(BaseMsg baseMsg) {
		// TODO Auto-generated method stub
		BaseVideo baseVideo = new BaseVideo();
		baseVideo.setCreateTime(new Date().getTime() + "");
		baseVideo.setMsgType("video");
		baseVideo.setFromUserName(baseMsg.getToUserName());
		baseVideo.setToUserName(baseMsg.getFromUserName());
		baseVideo.setVideo(video);
		return baseVideo;
	}

}
