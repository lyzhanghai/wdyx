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
import com.wechat.util.http.ApiRequest;

/**
 * 自定义菜单相关API
 * @author 帮杰
 *
 */
public class MenuApi {
	
	public static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public static String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	public static String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	public static String GET_MENU_CONFIG_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	public static JsonObject createMenu(String accessToken,String jsonMenu) {
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).setOutData(jsonMenu.getBytes()).doPost().getResourceAsJsonObject();
	}

	public static JsonObject getMenu(String accessToken) {
		String url = GET_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).doGet().getResourceAsJsonObject();
	}
	
	public static JsonObject deleteMenu(String accessToken) {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).doGet().getResourceAsJsonObject();
	}
	
	public static JsonObject getMenuConfig(String accessToken) {
		String url = GET_MENU_CONFIG_URL.replace("ACCESS_TOKEN", accessToken);
		return new ApiRequest(url).doGet().getResourceAsJsonObject();
	}
}
