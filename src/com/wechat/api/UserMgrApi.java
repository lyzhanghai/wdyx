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
 * 用户管理API
 * @author 帮杰
 *
 */
public class UserMgrApi {

	public static final String SET_USER_REMARK_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public static final String SEND_MSG_TO_USER_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	public static final String GET_USER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	public JsonObject setUserRemark(String accessToken,String openid,String remark) {
		String url = SET_USER_REMARK_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "{\"openid\":\""+openid+"\",\"remark\":\""+remark+"\"}";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().optJsonObject();
	}
	
	public static JsonObject getUserInfo(String accessToken,String openid) {
		String url = GET_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		return new ApiRequest(url).doGet().optJsonObject();
	}

	public static JsonObject sendMsgToUser(String accessToken,String openid,String msg) {
		String url = SEND_MSG_TO_USER_URL.replace("ACCESS_TOKEN", accessToken);
		String out = "{\"touser\":\""+openid+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+msg+"\"}}";
		return new ApiRequest(url).setOutData(out.getBytes()).doPost().optJsonObject();
	}
	
	public static JsonObject getUserList(String accessToken,String nextOpenid){
		String url = GET_USER_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenid==null?"":nextOpenid);
		return new ApiRequest(url).doGet().optJsonObject();
	}
}
