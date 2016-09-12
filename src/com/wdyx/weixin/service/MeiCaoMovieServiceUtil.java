package com.wdyx.weixin.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.wdyx.weixin.service.commons.HttpUtil;

/**
 * 梅操电影相关服务
 * @author 帮杰
 *
 */
public class MeiCaoMovieServiceUtil {

	private static final Logger logger = Logger.getLogger(MeiCaoMovieServiceUtil.class);
	
	public static String URL = "http://op.juhe.cn/onebox/movie/video?key=f81e4b4ff915a76611adf5223cfd35de&dtype=TYPE&q=QUERY";
	
	public static boolean initDB(){
		String sql = "create table if not exists movie("
				+ "raw varchar(128) not null default '',"
				+ "data1 varchar(6000) not null default '',"
				+ "data2 varchar(6000) not null default '',"
				+ "time timestamp not null default current_timestamp,"
				+ "primary key(raw)) "
				+ "comment='MeiCaoMovieService' default character set utf8 collate utf8_general_ci";
		return MysqlUtil.update(sql);
	}
	public static Map<String,String> updateDB(){
		String raw = new MeiCaoMovieService().getMovieInfo();
		if(raw==null||raw.trim().equals(""))return null;
		Map<String,String> data = new HashMap<String, String>();
		String film1 = raw.substring(raw.indexOf("《")+1, raw.indexOf("》")).trim();
		String film2 = raw.substring(raw.lastIndexOf("《")+1, raw.lastIndexOf("》")).trim();
		String data1 = searchMovie(film1,"json");
		String data2 = data1;
		if(!film1.equals(film2)){
			data1 = searchMovie(film2,"json");
		}
		
		data.put("raw",raw);
		data.put("data1",data1);
		data.put("data2",data2);
		data.put("time", new SimpleDateFormat("MM-dd").format(new Date()));
		Connection con = MysqlUtil.getConnection();
		Statement sm = null;
		try {
			sm = con.createStatement();
			sm.addBatch("DELETE FROM movie");
			sm.addBatch("INSERT INTO movie (`raw`,`data1`,`data2`,`time`) VALUES ('"+raw+"','"+data1+"','"+data2+"',CURRENT_TIMESTAMP)");
			sm.executeBatch();
		} catch (SQLException e) {
			logger.error("MeiCaoMovieServiceUtil.updateDB");
			e.printStackTrace();
		} finally{
			MysqlUtil.close(null, sm, con);
		}
		return data;
	}
	public static Map<String,String> getDataFromDB(){
		Map<String,String> data = null;
		String query = "SELECT * FROM `movie` WHERE 1";
		CachedRowSet crs = null;
		try {
			crs = MysqlUtil.query(query);
			if (crs.next()) {
				data = new HashMap<String,String>();
				data.put("raw",crs.getString("raw"));
				data.put("data1",crs.getString("data1"));
				data.put("data2",crs.getString("data2"));
				data.put("time", new SimpleDateFormat("MM-dd").format(crs.getTimestamp("time")));
			}
		} catch (SQLException e) {
			logger.error("MeiCaoMovieServiceUtil.getDataFromDB");
			e.printStackTrace();
		}
		return data;
	}
	
	public static String searchMovie(String movieName,String dataType){
		String data = "";
		try {
			data = HttpUtil.httpRequest(URL.replace("TYPE", dataType).replace("QUERY", URLEncoder.encode(movieName,"utf-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("MeiCaoMovieServiceUtil.searchMovie");
			e.printStackTrace();
		}
		return data;
	}
	
	public static Map<String,String> getDataFluently(){
		Map<String,String> data = getDataFromDB();
		data = data==null||!data.get("time").equals(new SimpleDateFormat("MM-dd").format(new Date()))?updateDB():data;
		return data;
	}
	public static void main(String[] args){
		String raw = new MeiCaoMovieService().getMovieInfo();
		String data1 = searchMovie(raw.substring(raw.indexOf("《")+1, raw.indexOf("》")).trim(),"json");
		String data2 = searchMovie(raw.substring(raw.lastIndexOf("《")+1, raw.lastIndexOf("》")).trim(),"json");
		System.out.println(data1);
		System.out.println(data2);
	}
}
