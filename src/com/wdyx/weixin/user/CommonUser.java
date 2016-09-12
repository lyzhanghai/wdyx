package com.wdyx.weixin.user;

import java.util.List;

import org.mobangjack.common.util.StrUtil;
import org.mobangjack.db.jodb.Jodb;
import org.mobangjack.db.jodb.table.Table;
import org.mobangjack.db.jodb.table.column.Column;
import org.mobangjack.wechat.api.AccessToken.AccessTokenMgr;
import org.mobangjack.wechat.api.UserMgr.UserMgrApi;

@Table
public class CommonUser {
	
	@Column(type = "varchar(28)",primary = true)
	private String openid;
	
	@Column(type = "varchar(10)",defaultVal = "'"+CommonUser.Mode.Normal+"'")
	private String mode;
	
	public String getOpenid() {
		return openid;
	}
	
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public static class Mode {
		public static final String Normal = "普通";
		public static final String LiuYan = "留言";
		public static final String DianGe = "点歌";
		public static final String SimSim = "聊天";
	}
	
	public static String getMode(String openid){
		CommonUser user = new CommonUser();
		user.setOpenid(openid);
		List<CommonUser> users = Jodb.select(user);
		return users==null?null:users.get(0).getMode();
	}
	
	public static boolean setMode(String openid, String mode) {
		CommonUser target = new CommonUser();
		target.setOpenid(openid);
		
		CommonUser replacement = new CommonUser();
		replacement.setMode(mode);
		
		return Jodb.update(target,replacement);
	}
	
	public static void grabCommonUser() {
		List<String> openids = UserMgrApi.getUserList(AccessTokenMgr.getAccessToken("wdyx").toString());
		for(String openid:openids){
			CommonUser user = new CommonUser();
			user.setOpenid(openid);
			Jodb.insert(user, true);
		}
	}
	
	public static boolean validate(String openid) {
		if(StrUtil.isNOB(openid))
			return false;
		CommonUser user = new CommonUser();
		user.setOpenid(openid);
		return Jodb.validate(user);
	}
	
	public static boolean addCommonUser(String openid) {
		CommonUser user = new CommonUser();
		user.setOpenid(openid);
		return Jodb.insert(user, true);
	}
	
	public static boolean removeCommonUser(String openid) {
		CommonUser user = new CommonUser();
		user.setOpenid(openid);
		return Jodb.delete(user);
	}
}
