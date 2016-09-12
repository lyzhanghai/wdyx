package org.mobangjack.wechat.message.handler;

import java.util.Date;

import org.mobangjack.wechat.message.BaseMsg;
import org.mobangjack.wechat.message.response.BasePicture;
import org.mobangjack.wechat.message.response.Picture;

public class ToPicture implements MessageHandler {
	
	private Picture picture;
	
	public ToPicture(Picture picture){
		this.picture = picture;
	}

	@Override
	public Object handleMessage(BaseMsg baseMsg) {
		// TODO Auto-generated method stub
		BasePicture basePicture = new BasePicture();
		basePicture.setCreateTime(new Date().getTime() + "");
		basePicture.setMsgType("image");
		basePicture.setFromUserName(baseMsg.getToUserName());
		basePicture.setToUserName(baseMsg.getFromUserName());
		basePicture.setPicture(picture);
		return basePicture;
	}
	

}
