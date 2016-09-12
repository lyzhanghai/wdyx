package org.mobangjack.db.jodb.dialect;

import java.util.List;

import org.mobangjack.db.jodb.table.MetaTable;


public interface Dialect {
	
	public String createTable(MetaTable metaTable);
	
	public <M> String insert(M model,boolean replace,List<Object> params);
	
	public <M> String delete(M model,List<Object> params);
	
	public <M> String update(M target,M replacement,List<Object> params);
	
	public <M> String select(String select,M model,List<Object> params,String constrains);
	
	public String handleEncrypt(Class<?> clazz,String column,String defaultKey);
	
	public String handleDecrypt(Class<?> clazz,String column,String defaultKey);

}
