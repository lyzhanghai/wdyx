package org.mobangjack.wechat.api.menu;

import net.sf.json.JSONObject;

import org.mobangjack.wechat.util.HttpsUtil;

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
	
	public static boolean createMenu(String accessToken,String jsonMenu) {
		return HttpsUtil.getJsonObject(CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken), "POST",jsonMenu).getInt("errcode")==0;
	}

	public static JSONObject getMenu(String accessToken) {
		return HttpsUtil.getJsonObject(GET_MENU_URL.replace("ACCESS_TOKEN", accessToken), "GET", null);
	}
	
	public static boolean deleteMenu(String accessToken) {
		return HttpsUtil.getJsonObject(DELETE_MENU_URL.replace("ACCESS_TOKEN", accessToken), "GET", null).getInt("errcode")==0;
	}
	
	public static JSONObject getMenuConfig(String accessToken) {
		return HttpsUtil.getJsonObject(GET_MENU_CONFIG_URL.replace("ACCESS_TOKEN", accessToken), "GET", null);
	}
}
