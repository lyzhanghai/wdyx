package org.mobangjack.db;

import java.util.List;

import javax.sql.rowset.CachedRowSet;

/**
 * One database process per thread.
 * @author 帮杰
 *
 */
public class TxPro {

	public static CachedRowSet query(String sql,Object...params){
		return DbUtil.query(Tx.getThreadLocalTx().getConnection(), sql, params);
	}
	
	public static CachedRowSet query(String sql,List<Object> params){
		return DbUtil.query(Tx.getThreadLocalTx().getConnection(), sql, params);
	}
	
	public static CachedRowSet query(String sql){
		return DbUtil.query(Tx.getThreadLocalTx().getConnection(), sql);
	}
	
	public static boolean update(String sql,Object...params){
		return DbUtil.update(Tx.getThreadLocalTx().getConnection(), sql, params);
	}
	
	public static boolean update(String sql,List<Object> params){
		return DbUtil.update(Tx.getThreadLocalTx().getConnection(), sql, params);
	}
	
	public static boolean update(String sql){
		return DbUtil.update(Tx.getThreadLocalTx().getConnection(), sql);
	}
	
	public static Object updateAndRetrieveKey(String sql,Object...params){
		return DbUtil.updateAndRetrieveKey(Tx.getThreadLocalTx().getConnection(), sql, params);
	}
	
	public static Object updateAndRetrieveKey(String sql,List<Object> params){
		return DbUtil.updateAndRetrieveKey(Tx.getThreadLocalTx().getConnection(), sql, params);
	}
	
	public static Object updateAndRetrieveKey(String sql){
		return DbUtil.updateAndRetrieveKey(Tx.getThreadLocalTx().getConnection(), sql);
	}
	
}