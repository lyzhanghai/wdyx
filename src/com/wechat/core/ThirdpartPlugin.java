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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.core.ServiceContext;
import com.wechat.util.SignUtil;

/**
 * 第三方接口插件
 * @author 帮杰
 *
 */
public abstract class ThirdpartPlugin {

	public abstract String getToken();
	public abstract String getUrl();
	
	public void service(ServiceContext serviceContext) throws ServletException, IOException {
		HttpServletRequest request = serviceContext.getRequest();
		HttpServletResponse response = serviceContext.getResponse();
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = SignUtil.generateSignature(getToken(), timestamp, nonce);
		StringBuilder url = new StringBuilder(getUrl());
		url.append("?").append("signature=").append(signature)
		   .append("&").append("timestamp=").append(timestamp)
		   .append("&").append("nonce=").append(nonce);
		request.getRequestDispatcher(url.toString()).forward(request, response);
	}
	
}
