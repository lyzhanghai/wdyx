/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wechat.core;

import java.util.Map;

import com.wechat.msg.OutMsg;
import com.wechat.msg.in.InImageMsg;
import com.wechat.msg.in.InLinkMsg;
import com.wechat.msg.in.InLocationMsg;
import com.wechat.msg.in.InTextMsg;
import com.wechat.msg.in.InVideoMsg;
import com.wechat.msg.in.InVoiceMsg;
import com.wechat.msg.in.event.InEvent;
import com.wechat.msg.in.event.InFollowEvent;
import com.wechat.msg.in.event.InLocationEvent;
import com.wechat.msg.in.event.InMenuEvent;
import com.wechat.msg.in.event.InQrCodeEvent;
import com.wechat.util.XmlUtil;

/**
 * 微信消息（明文）处理器
 * @author 帮杰
 *
 */
public abstract class WechatMsgHandler {

	public static final String IN_TEXT_MSG = "text";
	public static final String IN_LINK_MSG = "link";
	public static final String IN_LOCATION_MSG = "location";
	public static final String IN_IMAGE_MSG = "image";
	public static final String IN_VOICE_MSG = "voice";
	public static final String IN_VIDEO_MSG = "shortvideo";
	public static final String IN_EVENT_MSG = "event";
	
	private ServiceContext serviceContext;
	
	public WechatMsgHandler() {}
	
	public String handle(String inXml){
		Map<String, String> xmlMap = XmlUtil.xmlToMap(inXml);
		OutMsg outMsg = null;
		String msgType = xmlMap.get("MsgType");
		if(msgType.equals(IN_TEXT_MSG)) {
			InTextMsg inTextMsg = new InTextMsg(xmlMap);
			outMsg = handleInTextMsg(inTextMsg);
		}else if(msgType.equals(IN_VOICE_MSG)) {
			InVoiceMsg inVoiceMsg = new InVoiceMsg(xmlMap);
			outMsg = handleInVoiceMsg(inVoiceMsg);
		}else if(msgType.equals(IN_IMAGE_MSG)) {
			InImageMsg inImageMsg = new InImageMsg(xmlMap);
			outMsg = handleInImageMsg(inImageMsg);
		}else if(msgType.equals(IN_VIDEO_MSG)) {
			InVideoMsg inVideoMsg = new InVideoMsg(xmlMap);
			outMsg = handleInVideoMsg(inVideoMsg);
		}else if(msgType.equals(IN_LINK_MSG)) {
			InLinkMsg inLinkMsg = new InLinkMsg(xmlMap);
			outMsg = handleInLinkMsg(inLinkMsg);
		}else if(msgType.equals(IN_LOCATION_MSG)) {
			InLocationMsg inLocationMsg = new InLocationMsg(xmlMap);
			outMsg = handleInLocationMsg(inLocationMsg);
		}else if(msgType.equals(IN_EVENT_MSG)) {
			InEvent inEvent = new InEvent(xmlMap);
			String event = inEvent.getEvent();
			if(event.equalsIgnoreCase(InEvent.EVENT_SUBSCRIBE)||event.equalsIgnoreCase(InEvent.EVENT_UNSUBSCRIBE)) {
				InFollowEvent inFollowEvent = new InFollowEvent(xmlMap);
				outMsg = handleInFollowEvent(inFollowEvent);
			}else if(event.equalsIgnoreCase(InEvent.EVENT_LOCATION)) {
				InLocationEvent inLocationEvent = new InLocationEvent(xmlMap);
				outMsg = handleInLocationEvent(inLocationEvent);
			}else if(event.equalsIgnoreCase(InEvent.EVENT_CLICK)||event.equalsIgnoreCase(InEvent.EVENT_VIEW)) {
				InMenuEvent inMenuEvent = new InMenuEvent(xmlMap);
				outMsg = handleInMenuEvent(inMenuEvent);
			}else if(event.equalsIgnoreCase(InEvent.EVENT_SCAN)) {
				InQrCodeEvent inQrCodeEvent = new InQrCodeEvent(xmlMap);
				outMsg = handleInQrCodeEvent(inQrCodeEvent);
			}
		}else {
			System.err.println("WechatMsgHandler:Unrecognized msg type:"+inXml);
		}
		
		return outMsg==null?"":outMsg.toString();
		
	}
	
	public ServiceContext getServiceContext() {
		return serviceContext;
	}

	void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}

	protected abstract OutMsg handleInTextMsg(InTextMsg inTextMsg);
	protected abstract OutMsg handleInVoiceMsg(InVoiceMsg inVoiceMsg);
	protected abstract OutMsg handleInImageMsg(InImageMsg inImageMsg);
	protected abstract OutMsg handleInVideoMsg(InVideoMsg inVideoMsg);
	protected abstract OutMsg handleInLinkMsg(InLinkMsg inLinkMsg);
	protected abstract OutMsg handleInLocationMsg(InLocationMsg inLocationMsg);
	protected abstract OutMsg handleInFollowEvent(InFollowEvent inFollowEvent);
	protected abstract OutMsg handleInLocationEvent(InLocationEvent inLocationEvent);
	protected abstract OutMsg handleInMenuEvent(InMenuEvent inMenuEvent);
	protected abstract OutMsg handleInQrCodeEvent(InQrCodeEvent inQrCodeEvent);

}
