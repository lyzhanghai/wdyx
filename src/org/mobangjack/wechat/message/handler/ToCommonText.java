package org.mobangjack.wechat.message.handler;

import java.util.Date;

import org.mobangjack.wechat.message.BaseMsg;
import org.mobangjack.wechat.message.response.CommonText;

public class ToCommonText implements MessageHandler {

	private String Content;

	public ToCommonText(String content) {
		this.Content = content;
	}

	@Override
	public Object handleMessage(BaseMsg baseMsg) {
		CommonText commonText = new CommonText();
		commonText.setCreateTime(new Date().getTime() + "");
		commonText.setMsgType("text");
		commonText.setFromUserName(baseMsg.getToUserName());
		commonText.setToUserName(baseMsg.getFromUserName());
		commonText.setContent(Content);
		return commonText;
	}
}
