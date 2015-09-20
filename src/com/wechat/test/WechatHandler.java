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
package com.wechat.test;

import com.wechat.core.Handler;
import com.wechat.msg.in.InImageMsg;
import com.wechat.msg.in.InLinkMsg;
import com.wechat.msg.in.InLocationMsg;
import com.wechat.msg.in.InTextMsg;
import com.wechat.msg.in.InVideoMsg;
import com.wechat.msg.in.InVoiceMsg;
import com.wechat.msg.in.event.InFollowEvent;
import com.wechat.msg.in.event.InLocationEvent;
import com.wechat.msg.in.event.InMenuEvent;
import com.wechat.msg.in.event.InQrCodeEvent;
import com.wechat.msg.out.OutMsg;
import com.wechat.msg.out.OutTextMsg;

/**
 * @author 帮杰
 *
 */
public class WechatHandler extends Handler {


	@Override
	protected OutMsg handleInTextMsg(InTextMsg inTextMsg) {
		OutTextMsg outTextMsg = new OutTextMsg(inTextMsg);
		outTextMsg.setContent(inTextMsg.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInVoiceMsg(InVoiceMsg inVoiceMsg) {
		OutTextMsg outTextMsg = new OutTextMsg(inVoiceMsg);
		outTextMsg.setContent(inVoiceMsg.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInImageMsg(InImageMsg inImageMsg) {
		OutTextMsg outTextMsg = new OutTextMsg(inImageMsg);
		outTextMsg.setContent(inImageMsg.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInVideoMsg(InVideoMsg inVideoMsg) {
		OutTextMsg outTextMsg = new OutTextMsg(inVideoMsg);
		outTextMsg.setContent(inVideoMsg.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInLinkMsg(InLinkMsg inLinkMsg) {
		OutTextMsg outTextMsg = new OutTextMsg(inLinkMsg);
		outTextMsg.setContent(inLinkMsg.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInLocationMsg(InLocationMsg inLocationMsg) {
		OutTextMsg outTextMsg = new OutTextMsg(inLocationMsg);
		outTextMsg.setContent(inLocationMsg.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInFollowEvent(InFollowEvent inFollowEvent) {
		OutTextMsg outTextMsg = new OutTextMsg(inFollowEvent);
		outTextMsg.setContent(inFollowEvent.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInLocationEvent(InLocationEvent inLocationEvent) {
		OutTextMsg outTextMsg = new OutTextMsg(inLocationEvent);
		outTextMsg.setContent(inLocationEvent.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInMenuEvent(InMenuEvent inMenuEvent) {
		OutTextMsg outTextMsg = new OutTextMsg(inMenuEvent);
		outTextMsg.setContent(inMenuEvent.getMsgType());
		return outTextMsg;
	}

	@Override
	protected OutMsg handleInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
		OutTextMsg outTextMsg = new OutTextMsg(inQrCodeEvent);
		outTextMsg.setContent(inQrCodeEvent.getMsgType());
		return outTextMsg;
	}

}
