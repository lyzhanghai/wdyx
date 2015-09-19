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
package com.wechat.api;

import com.json.JsonObject;
import com.wechat.access.AccessToken;
import com.wechat.util.http.ApiRequest;

/**
 * 全局属性：AccessToken的获取API
 * @author 帮杰
 *
 */
public class AccessTokenApi {

	public static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * {"access_token":"ACCESS_TOKEN","expires_in":7200}
	 * @param appid
	 * @param appsecret
	 * @return AccessToken
	 * @throws Exception 
	 */
	public static AccessToken getAccessToken(String appId,String appSecret) {
		String url = URL.replace("APPID", appId).replace("APPSECRET", appSecret);
		JsonObject jsonObject = new ApiRequest(url).doGet().getResourceAsJsonObject();
		String access_token = jsonObject.getString("access_token");
		Integer expires_in = jsonObject.getInteger("expires_in");
		AccessToken accessToken = new AccessToken(access_token, expires_in);
		return accessToken;
	}
	
}
