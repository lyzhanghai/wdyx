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

import com.wechat.util.SignUtil;



/**
 * WechatApi
 * @author 帮杰
 *
 */
public abstract class WechatApi {

	private WechatApiConfig wechatApiConfig = new WechatApiConfig();
	private WechatHandler wechatHandler = new WechatHandler();
	
	public WechatApi() {
		configWechatApi(wechatApiConfig);
		configWechatHandler(wechatHandler);
	}

	protected abstract void configWechatApi(WechatApiConfig wechatApiConfig);
	protected abstract void configWechatHandler(WechatHandler wechatHandler);
	
	public WechatApiConfig getWechatApiConfig() {
		return wechatApiConfig;
	}

	public WechatHandler getWechatRequestHandler() {
		return wechatHandler;
	}

	public void doHandshake(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		if(SignUtil.checkSignature(wechatApiConfig.getToken(),signature, timestamp, nonce)){
			PrintWriter out = response.getWriter();
			out.write(echostr);
			out.flush();
			out.close();
			out = null;
		}
	}
	
	public void doService(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/xml");
		ServiceContext serviceContext = new ServiceContext(this, request, response);
		wechatHandler.handle(serviceContext);
	}
}
