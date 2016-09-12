package org.mobangjack.wechat.message.handler;

import java.util.Date;

import org.mobangjack.wechat.message.BaseMsg;
import org.mobangjack.wechat.message.response.BaseMusic;
import org.mobangjack.wechat.message.response.Music;

public class ToMusic implements MessageHandler {

	private Music music;

	public ToMusic(Music music) {
		this.music = music;
	}

	@Override
	public Object handleMessage(BaseMsg baseMsg) {
		// TODO Auto-generated method stub
		BaseMusic baseMusic = new BaseMusic();
		baseMusic.setCreateTime(new Date().getTime() + "");
		baseMusic.setMsgType("music");
		baseMusic.setFromUserName(baseMsg.getToUserName());
		baseMusic.setToUserName(baseMsg.getFromUserName());
		baseMusic.setMusic(music);
		return baseMusic;
	}

}
