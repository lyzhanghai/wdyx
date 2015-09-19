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

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.util.EncryptUtil;
import com.wechat.util.XmlUtil;

/**
 * 微信处理器
 * @author 帮杰
 *
 */
public class WechatHandler {
	
	private WechatMsgHandler wechatMsgHandler;
	
	public WechatHandler() {}
	
	void handle(ServiceContext serviceContext) throws IOException{
		wechatMsgHandler.setServiceContext(serviceContext);
		HttpServletRequest request = serviceContext.getRequest();
		HttpServletResponse response = serviceContext.getResponse();
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String inXml = XmlUtil.read(request.getInputStream());
		WechatApiConfig wechatApiConfig = serviceContext.getWechatApi().getWechatApiConfig();
		inXml = wechatApiConfig.encryptMsg()?EncryptUtil.decrypt(wechatApiConfig.getToken(), wechatApiConfig.getEncodingAesKey(), wechatApiConfig.getAppId(), inXml, timestamp, nonce):inXml;
		String outXml = wechatMsgHandler.handle(inXml);
		if (!response.isCommitted()) {
			PrintWriter writer = response.getWriter();
			writer.write(outXml);
			writer.flush();
			writer.close();
			writer = null;
		}
	}

	WechatMsgHandler getWechatMsgHandler() {
		return wechatMsgHandler;
	}

	public void setWechatMsgHandler(WechatMsgHandler wechatMsgHandler) {
		this.wechatMsgHandler = wechatMsgHandler;
	}
	
}
