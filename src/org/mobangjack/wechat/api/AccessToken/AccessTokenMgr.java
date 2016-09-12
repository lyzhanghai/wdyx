package org.mobangjack.wechat.api.AccessToken;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mobangjack.wechat.core.WechatConfig;

public class AccessTokenMgr {

	private static final Map<String,AccessToken> tokenMap = new ConcurrentHashMap<String,AccessToken>();
	
	public static final AccessToken getAccessToken(String name) {
		WechatConfig wechatConfig = WechatConfig.forName(name);
		if(wechatConfig==null)
			return null;
		AccessToken token = tokenMap.get(name);
		if(token==null||token.isExpired()){
			token = AccessTokenApi.getAccessToken(wechatConfig.getApiConfig().getAppId(), wechatConfig.getApiConfig().getAppSecret());
			tokenMap.replace(name, token);
		}		
		return token;
	}

}
