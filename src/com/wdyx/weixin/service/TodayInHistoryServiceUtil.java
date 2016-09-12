package com.wdyx.weixin.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

/**
 * 这个是“历史上的今天”的工具类，主要用于数据的平滑过渡：
 * 1、将当日抓取数据写入数据库
 * 2、将当日数据从数据库中取出
 * @author 帮杰
 *
 */
public class TodayInHistoryServiceUtil {

	private static final Logger logger = Logger.getLogger(TodayInHistoryServiceUtil.class);
	
	public static boolean initDB(){
		String sql = "create table if not exists TodayInHistory("
				+ "today timestamp not null default current_timestamp,"
				+ "history varchar(2048),"
				+ "primary key(today)) "
				+ "comment='TodayInHistoryService' default character set utf8 collate utf8_general_ci";
		return MysqlUtil.update(sql);
	}
	
	public static Map<String, String> getDataFromDB(){
		Map<String, String> data = null;
		CachedRowSet crs = null;
		try {
			String query = "SELECT * FROM `TodayInHistory`";
			crs = MysqlUtil.query(query);
			if (crs.next()) {
				data = new HashMap<String, String>();
				data.put("today", crs.getString("today"));
				data.put("history", new SimpleDateFormat("MM-dd").format(crs.getTimestamp("today")));
			}
		} catch (SQLException e) {
			logger.error("TodayInHistoryServiceUtil.getDataFromDB");
			e.printStackTrace();
		}
		return data;
	}
	
	public static Map<String, String> updateDB() {
		Map<String, String> data = null;
		String history = new TodayInHistoryService().getTodayInHistoryInfo();
		if(history==null||history.trim().equals(""))return null;
		data = new HashMap<String, String>();
		data.put("today", new SimpleDateFormat("MM-dd").format(new Date()));
		data.put("history",history);
		Connection con = MysqlUtil.getConnection();
		Statement sm = null;
		try {
			sm = con.createStatement();
			sm.addBatch("DELETE FROM `TodayInHistory`");
			sm.addBatch("INSERT INTO `TodayInHistory` (`today`,`history`) VALUES (CURRENT_TIMESTAMP,'"+history+"')");
			sm.executeBatch();
		} catch (SQLException e) {
			logger.error("TodayInHistoryServiceUtil.updateDB");
			e.printStackTrace();
		} finally{
			MysqlUtil.close(null, sm, con);
		}
		return data;
	}
	
	public static Map<String, String> getDataFluently() {
		Map<String, String> data = getDataFromDB();
		data = data==null||!data.get("today").equals(new SimpleDateFormat("MM-dd").format(new Date()))?updateDB():data;
		return data;
	}
}
