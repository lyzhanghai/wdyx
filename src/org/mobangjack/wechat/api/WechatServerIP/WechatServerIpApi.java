package org.mobangjack.wechat.api.WechatServerIP;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.mobangjack.wechat.util.HttpsUtil;

/**
 * WechatServerIpApi</br>
 * 用于获取微信服务器IP地址
 * @author 帮杰
 *
 */
public class WechatServerIpApi {

	public static final String URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	
	@SuppressWarnings("unchecked")
	public static List<String> getWeChatServerIP(String accessToken){
		JSONObject jsonObject = HttpsUtil.getJsonObject(URL.replace("ACCESS_TOKEN", accessToken), "GET", null);
		JSONArray jsonArray = jsonObject.getJSONArray("ip_list");
		return jsonArray;
	}
}
