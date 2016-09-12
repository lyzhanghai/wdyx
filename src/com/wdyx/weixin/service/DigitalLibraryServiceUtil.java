package com.wdyx.weixin.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

import com.wdyx.weixin.user.User;

/**
 * 数字图书馆数据平滑过渡
 * @author 帮杰
 *
 */
public class DigitalLibraryServiceUtil {

	private static final Logger logger = Logger.getLogger(DigitalLibraryServiceUtil.class);
	
	public static boolean initDB(){
		String sql = "create table if not exists dls("
				+ "openid varchar(28) not null default '',"
				+ "borrow text not null default '',"
				+ "history text not null default '',"
				+ "primary key(openid)) "
				+ "comment='DigitalLibraryService' default character set utf8 collate utf8_general_ci";
		return MysqlUtil.update(sql);
	}
	
	public static Map<String,String> updateDB(String openid){
		Map<String,String> data = null;
		User user = User.getUser(openid);
		DigitalLibraryService DLS = new DigitalLibraryService(user.getUsername(), user.getDLSPassword());
		if (DLS.login()) {
			String borrow = DLS.getBorrowInfoStr();
			String history = DLS.getBorrowHistoryStr();
			data = new HashMap<String, String>();
			data.put("borrow", borrow);
			data.put("history", history);
			String sql = "replace dls (`openid`,`borrow`,`history`,`time`) values ('"+openid+"','"+borrow+"','"+history+"',current_timestamp)";
			MysqlUtil.update(sql);
		}
		return data;
	}
	
	public static Map<String,String> getDataFromDB(String openid){
		Map<String,String> data = null;
		CachedRowSet crs = null;
		try {
			String query = "SELECT borrow,history FROM dls WHERE openid="+"'"+openid+"'";
			crs = MysqlUtil.query(query);
			if (crs.next()) {
				data = new HashMap<String, String>();
				data.put("borrow", crs.getString("borrow"));
				data.put("history", crs.getString("history"));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return data;
	}
	public static Map<String, String> getDataFluently(String openid){
		Map<String,String> data = getDataFromDB(openid);
		return data==null?updateDB(openid):data;
	}
	
}
