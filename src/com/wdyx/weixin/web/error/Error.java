package com.wdyx.weixin.web.error;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.mobangjack.common.util.MysqlUtil;

public class Error {

	public static boolean initDB() {
		String sql = "create table if not exists error("
				+ "id int(11) not null auto_increment,"
				+ "openid varchar(28) not null default '',"
				+ "code varchar(16) not null default '0',"
				+ "remark varchar(512) not null default '',"
				+ "time timestamp not null default current_timestamp,primary key(id)) "
				+ "comment='error report' default character set utf8 collate utf8_general_ci";
		return MysqlUtil.update(sql);
	}
	public static boolean report(String openid,String code,String remark) {
		if(!MysqlUtil.hasTable("error")){
			initDB();
		}
		String sql = "insert into error (openid,code,remark,time) values ('"+openid+"','"+code+"','"+remark+"',current_timestamp)";
		return MysqlUtil.update(sql);
	}
	public static List<String> getErrors() {
		String query = "select remark,time from error where 1 order by id desc limit 0,30";
		CachedRowSet crs = MysqlUtil.query(query);
		List<String> errors = new ArrayList<String>();
		try {
			while(crs.next()){
				errors.add(crs.getString(1)+"("+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(crs.getTimestamp(2))+")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return errors.size()==0?null:errors;
	}
}
