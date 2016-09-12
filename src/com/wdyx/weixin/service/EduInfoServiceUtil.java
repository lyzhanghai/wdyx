package com.wdyx.weixin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

import com.wdyx.weixin.user.User;

/**
 * 教务信息服务的工具类，用于数据的平滑过渡
 * @author 帮杰
 *
 */
public class EduInfoServiceUtil {
	
	private static Logger logger = Logger.getLogger(EduInfoServiceUtil.class);
	
	public static boolean initDB(){
		String sql = "create table if not exists eis("
				+ "openid varchar(28) not null,"
				+ "course varchar(2048),"
				+ "score varchar(2048),"
				+ "primary key(openid)) "
				+ "comment='EduInfoService' default character set utf8 collate utf8_general_ci";
		return MysqlUtil.update(sql);
	}
	
	public static List<String> getIcodeList(){
		String icodeLib = null;
		List<String> list = null;
		String query = "select `code` from Icode";
		CachedRowSet crs = null;
		try {
			crs = MysqlUtil.query(query);
			if(crs.next()){
				icodeLib = crs.getString(1);
			}else{
				logger.error("Icode library is not ready.");
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		if(icodeLib!=null||icodeLib!=""){
			list = new ArrayList<String>();
			String[] group = icodeLib.split("\r\n");
			for(int i=0;i<group.length;i++){
				list.add(group[i]);
			}
		}
		return list;
	}
	
	public static Map<String,String> updateDB(String openid){
		Map<String,String> data = null;
		User user = User.getUser(openid); 
		EduInfoService EIS = new EduInfoService(user.getUsername(),user.getEISPassword());
		if(EIS.login()){
			String course = EIS.getCourseStr();
			String score = EIS.getScoreStr();
			data = new HashMap<String, String>();
			data.put("course", course);
			data.put("score", score);
			String sql = "replace eis (`openid`,`course`,`score`,`time`) values ('"+openid+"','"+course+"','"+score+"',current_timestamp)";
			MysqlUtil.update(sql);
		}
		return data;
	}
	
	public static Map<String,String> getDataFromDB(String openid){
		Map<String,String> data = null;
		CachedRowSet crs = null;
		try {
			String query = "SELECT course,score FROM eis WHERE openid="+"'"+openid+"'";
			crs = MysqlUtil.query(query);
			if (crs.next()) {
				data = new HashMap<String, String>();
				data.put("course", crs.getString("course"));
				data.put("score", crs.getString("score"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return data;
	}
	
	public static Map<String,String> getDataFluently(String openid){
		Map<String,String> data = getDataFromDB(openid);
		data = data==null?updateDB(openid):data;
		return data;
	}
}
