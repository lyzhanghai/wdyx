package org.mobangjack.wechat.message.handler;

import java.util.Date;

import org.mobangjack.wechat.message.BaseMsg;
import org.mobangjack.wechat.message.response.BaseSound;
import org.mobangjack.wechat.message.response.Sound;

public class ToSound implements MessageHandler {

	private Sound sound;

	public ToSound(Sound sound) {
		this.sound = sound;
	}

	@Override
	public Object handleMessage(BaseMsg baseMsg) {
		// TODO Auto-generated method stub
		BaseSound baseSound = new BaseSound();
		baseSound.setCreateTime(new Date().getTime() + "");
		baseSound.setMsgType("voice");
		baseSound.setFromUserName(baseMsg.getToUserName());
		baseSound.setToUserName(baseMsg.getFromUserName());
		baseSound.setSound(sound);
		return baseSound;
	}

}
