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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WechatServlet implementation class WechatServlet
 */
public class WechatServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1000100100100122L;
    
	private WechatApi wechatApi;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WechatServlet() {
        super();
    }

	@Override
    public void init(ServletConfig config) throws ServletException {
    	String apiClass = config.getInitParameter("WechatApi");
    	try {
    		wechatApi = (WechatApi) Class.forName(apiClass).newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Could not init WechatApi '"+apiClass+"'.Cause:"+e);
		}
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		wechatApi.doHandshake(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		wechatApi.doService(request, response);
	}

}
