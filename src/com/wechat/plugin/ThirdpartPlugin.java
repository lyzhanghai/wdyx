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
package com.wechat.plugin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wechat.core.ServiceContext;
import com.wechat.util.SignUtil;
import com.wechat.util.http.ApiRequest;

/**
 * ThirdpartPlugin
 * @author 帮杰
 *
 */
public abstract class ThirdpartPlugin {

	protected abstract String getToken();
	protected abstract String getUrl();
	
	public String service(ServiceContext serviceContext) {
		HttpServletRequest request = serviceContext.getRequest();
		HttpServletResponse response = serviceContext.getResponse();
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String signature = SignUtil.generateSignature(getToken(), timestamp, nonce);
		StringBuilder url = new StringBuilder(getUrl());
		url.append("?").append("signature=").append(signature)
		   .append("&").append("timestamp=").append(timestamp)
		   .append("&").append("nonce=").append(nonce);
		String outXml = new ApiRequest(url.toString()).setOutData(((String)request.getAttribute("inXml")).getBytes()).doPost().optString();
		try {
			response.setContentType("text/xml");
			PrintWriter writer = response.getWriter();
			writer.write(outXml);
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outXml;
	}
	
	public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_6c7ca91f3334]]></ToUserName><FromUserName><![CDATA[onbOrtzigRl1yEPuKNfRgiLrqH4Q]]></FromUserName><CreateTime>1442728462</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[boss]]></Content><MsgId>6196471561507333080</MsgId></xml>";
		String outXml = new ApiRequest("http://dxsjjb.duapp.com/wechat?signature=3d92fa406faf14eabd25abf4b6aca76ba1dddc45&timestamp=1442737177&nonce=590989547").setOutData(xml.getBytes()).doPost().optString();
		System.out.println(outXml);
	}
}
