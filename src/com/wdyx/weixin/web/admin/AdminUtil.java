package com.wdyx.weixin.web.admin;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

/**
 * Admin Utils
 * @author 帮杰
 *
 */
public class AdminUtil {

	private static final Logger logger = Logger.getLogger(AdminUtil.class);
	
	public static boolean login (String openid,String username,String password) {
		boolean flag = true;
		CachedRowSet crs = null;
		try {
			String query = "select username,password from admin where username='"+username+"' and password='"+password+"'";
			crs = MysqlUtil.query(query);
			if(crs.next()){
				if(setAdminID(openid)){
					flag = true;
				}else {
					flag = false;
					logger.error("setAdminID success but login failed");
				}
				
			}
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
			logger.error("error : admin login", e);
		}
		return flag;
	}
	public static String login (String username,String password) {
		String openid = "";
		CachedRowSet crs = null;
		try {
			String query = "select openid from admin where username='"+username+"' and password='"+password+"'";
			crs = MysqlUtil.query(query);
			if(crs.next()){
				openid=crs.getString("openid");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("error : admin login", e);
		}
		return openid;
	}
	public static boolean setAdminID(String openid){
		String sql = "update admin set openid='"+openid+"', time=current_timestamp where 1";
		return MysqlUtil.update(sql);
	}
	public static String getAdminID(){
		String openid = "";
		CachedRowSet crs = null;
		try {
			String query = "select openid from admin where 1";
			crs = MysqlUtil.query(query);
			if(crs.next()){
				openid = crs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("error : getAdminID", e);
		}
		return openid;
	}
	public static boolean setAdminOperation(String operation){
		String sql = "update admin set operation='"+operation+"', time=current_timestamp where 1";
		return MysqlUtil.update(sql);
	}
	public static String getAdminOperation(){
		String operation = "";
		CachedRowSet crs = null;
		try {
			String query = "select operation from admin where 1";
			crs = MysqlUtil.query(query);
			if(crs.next()){
				operation = crs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("error : getAdminOperation", e);
		}
		return operation;
	}
	public static boolean isAdmin(String openid){
		boolean flag = true;
		CachedRowSet crs = null;
		try {
			String query = "select * from admin where openid='"+openid+"'";
			crs = MysqlUtil.query(query);
			if(crs.next()){
				flag = true;
			}else{
				flag = false;
			}
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
			logger.error("error : setAdminID", e);
		}
		return flag;
	}
	
	public static boolean logout(String openid){
		boolean flag = true;
		if(isAdmin(openid)){
			String sql = "update admin set openid='',time=current_timestamp where openid='"+openid+"'";
			flag = MysqlUtil.update(sql); 
		}
		return flag;
	}
}
