package org.mobangjack.wechat.api.AccessToken;

import net.sf.json.JSONObject;

/**
 * 全局属性：AccessToken的获取API
 * @author 帮杰
 *
 */
public class AccessTokenApi {

	public static final String URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public static AccessToken getAccessToken(String appid,String appsecret) {
		String url = URL.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject result = org.mobangjack.wechat.util.HttpsUtil.getJsonObject(url, "GET", null);
		AccessToken accessToken = null;
		if (result != null) {
			accessToken = new AccessToken(result.getString("access_token"),Integer.parseInt(result.getString("expires_in")));
		}
		return accessToken;
	}
	
}
