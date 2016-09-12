package com.wdyx.weixin.user;

import net.sf.json.JSONObject;

import org.mobangjack.wechat.api.AccessToken.AccessTokenMgr;
import org.mobangjack.wechat.api.UserMgr.UserMgrApi;
import org.mobangjack.wechat.ext.Const;

public class UserInfo {

	private String openid;
	private String nickname;
	private String city;
	private String province;
	private String headimgurl;
	
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public static UserInfo getUserInfo(String openid) {
		JSONObject json = UserMgrApi.getUserInfo(AccessTokenMgr.getAccessToken("wdyx").toString(), openid);
		UserInfo user = new UserInfo();
		user.setOpenid(openid);
		String headimgurl = json.optString("headimgurl",Const.EXT_CONFIG.get("BASE_WEB_VIEW_PIC_URL")+"avatar.gif");
		headimgurl = headimgurl.endsWith("0")?headimgurl.substring(0,headimgurl.lastIndexOf('0'))+"132":headimgurl;
		user.setHeadimgurl(headimgurl);
		user.setNickname(json.optString("nickname", "Anonymous"));
		user.setProvince(json.optString("province"));
		user.setCity(json.optString("city"));
		return user;
	}
}
