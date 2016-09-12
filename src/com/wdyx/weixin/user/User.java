package com.wdyx.weixin.user;

import java.util.List;

import org.mobangjack.db.Bundle;
import org.mobangjack.db.Tx;
import org.mobangjack.db.jodb.Jodb;
import org.mobangjack.db.jodb.table.Table;
import org.mobangjack.db.jodb.table.column.Column;

@Table
public class User extends CommonUser{

	@Column(type = "varchar(32)")
	private String username;
	
	@Column(type = "varchar(32)")
	private String DLSPassword;
	
	@Column(type = "varchar(32)")
	private String EISPassword;
	
	@Column(type = "varchar(32)")
	private String IPSPassword;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDLSPassword() {
		return DLSPassword;
	}
	public void setDLSPassword(String dLSPassword) {
		DLSPassword = dLSPassword;
	}
	public String getEISPassword() {
		return EISPassword;
	}
	public void setEISPassword(String eISPassword) {
		EISPassword = eISPassword;
	}
	public String getIPSPassword() {
		return IPSPassword;
	}
	public void setIPSPassword(String iPSPassword) {
		IPSPassword = iPSPassword;
	}
	
	public static User getUser(String openid){
		User user = new User();
		user.setOpenid(openid);
		List<User> users = Jodb.select(user);
		return users==null?null:users.get(0);
	}
	
	public static boolean validate(String openid) {
		User user = new User();
		user.setOpenid(openid);
		return Jodb.validate(user);
	}
	
	public static boolean bindUser(User user){
		return Jodb.insert(user, true);
	}
	
	public static boolean unbindUser(String openid){
		Boolean flag = new Boolean(false);
		Bundle bundle = new Bundle();
		bundle.setAttr("openid", openid);
		bundle.setAttr("flag", flag);
		Tx tx = new Tx(bundle) {
			
			@Override
			public void run() {
				String openid = (String) getBundle().getAttr("openid");
				User user = new User();
				user.setOpenid(openid);
				boolean flag = Jodb.delete(user);
				getBundle().setAttr("flag", flag);
			}
		};
		tx.start();
		return flag;
	}
	
	public static List<User> getBoundUser(){
		User user = new User();
		List<User> users = Jodb.select(user);
		return users;
	}
}
