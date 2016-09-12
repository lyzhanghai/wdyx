package org.mobangjack.db;

import java.sql.Connection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

public class DbPro {

	private static final ThreadLocal<Connection> tlCon = new ThreadLocal<Connection>();
	
	public static boolean start(Connection con){
		if(tlCon.get()==null){
			tlCon.set(con);
			return false;
		}else {
			Db.close(con);
			return true;
		}
		
	}
	
	public static CachedRowSet query(String sql,Object...params){
		return DbUtil.query(tlCon.get(), sql, params);
	}
	
	public static CachedRowSet query(String sql,List<Object> params){
		return DbUtil.query(tlCon.get(), sql, params);
	}
	
	public static CachedRowSet query(String sql){
		return DbUtil.query(tlCon.get(), sql);
	}
	
	public static boolean update(String sql,Object...params){
		return DbUtil.update(tlCon.get(), sql, params);
	}
	
	public static boolean update(String sql,List<Object> params){
		return DbUtil.update(tlCon.get(), sql, params);
	}
	
	public static boolean update(String sql){
		return DbUtil.update(tlCon.get(), sql);
	}
	
	public static Object updateAndRetrieveKey(String sql,Object...params){
		return DbUtil.updateAndRetrieveKey(tlCon.get(), sql, params);
	}
	
	public static Object updateAndRetrieveKey(String sql,List<Object> params){
		return DbUtil.updateAndRetrieveKey(tlCon.get(), sql, params);
	}
	
	public static Object updateAndRetrieveKey(String sql){
		return DbUtil.updateAndRetrieveKey(tlCon.get(), sql);
	}
	
	public static boolean end() {
		Connection con = tlCon.get();
		if(con!=null){
			tlCon.remove();
			Db.close(con);
			return false;
		}
		return true;
	}

}
