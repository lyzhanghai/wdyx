package org.mobangjack.wechat.message.handler;

import org.mobangjack.wechat.message.BaseMsg;

public interface MessageHandler {
	
	public Object handleMessage(BaseMsg baseMsg);

}
