package org.mobangjack.wechat.api.UserMgr;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.mobangjack.wechat.util.HttpsUtil;

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
	
	public boolean setUserRemark(String accessToken,String openid,String remark) {
		String url = SET_USER_REMARK_URL.replace("ACCESS_TOKEN", accessToken);
		String data = "{\"openid\":\""+openid+"\",\"remark\":\""+remark+"\"}";
		JSONObject json = HttpsUtil.getJsonObject(url, "post", data);
		return json.getInt("errcode")==0;
	}
	
	public static JSONObject getUserInfo(String accessToken,String openid) {
		String url = GET_USER_INFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		return HttpsUtil.getJsonObject(url, "GET", null);
	}

	public static UserInfo parseUserInfo(JSONObject jsonObject) {
		return (UserInfo) JSONObject.toBean(jsonObject, UserInfo.class);
	}
	
	public static boolean sendMsgToUser(String accessToken,String openid,String msg) {
		String url = SEND_MSG_TO_USER_URL.replace("ACCESS_TOKEN", accessToken);
		String data = "{\"touser\":\""+openid+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+msg+"\"}}";
		JSONObject json = HttpsUtil.getJsonObject(url, "POST", data);
		return json.getInt("errcode")==0;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static List<String> getUserList(String accessToken){
		List<String> openids = null;
		String url = GET_USER_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", "");
		JSONObject obj = HttpsUtil.getJsonObject(url, "GET", null);
		openids = JSONArray.toList(obj.getJSONObject("data").getJSONArray("openid"));
		String next_openid = obj.optString("next_openid");
		while(!next_openid.equals("")){
			url = GET_USER_LIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", next_openid);
			obj = HttpsUtil.getJsonObject(url, "GET", null);
			JSONObject data = obj.optJSONObject("data");
			if(data!=null){
				JSONArray jArray = data.optJSONArray("openid");
				if(jArray!=null){
					List<String> tmpList = JSONArray.toList(jArray);
					openids.addAll(tmpList);
					next_openid = obj.optString("next_openid");
				}
			}else {
				break;
			}
		}
		return openids;
	}
}
