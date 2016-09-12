package org.mobangjack.wechat.core;

import org.mobangjack.wechat.message.request.EventMsg;
import org.mobangjack.wechat.message.request.ImageMsg;
import org.mobangjack.wechat.message.request.LinkMsg;
import org.mobangjack.wechat.message.request.LocationMsg;
import org.mobangjack.wechat.message.request.TextMsg;
import org.mobangjack.wechat.message.request.VideoMsg;
import org.mobangjack.wechat.message.request.VoiceMsg;
/**
 * Hierarchy WeChatRequestHandler so that any handler extends this one doesn't have to implement all of the abstract methods.
 * @author 帮杰
 *
 */
public class WechatRequestHandlerImpl extends WechatRequestHandler{

	public WechatRequestHandlerImpl(){
		super();
	}
	
	@Override
	protected Object handleEventMsg(EventMsg eventMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleImageMsg(ImageMsg imageMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleLinkMsg(LinkMsg linkMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleLocationMsg(LocationMsg locationMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleTextMsg(TextMsg textMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleVideoMsg(VideoMsg videoMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleVoiceMsg(VoiceMsg voiceMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object onSubscribe(EventMsg eventMsg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object onUnsubscribe(EventMsg eventMsg) {
		// TODO Auto-generated method stub
		return null;
	}

}
