package org.mobangjack.db.jodb.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.mobangjack.common.util.ReflectUtil;
import org.mobangjack.common.util.StrUtil;
import org.mobangjack.db.jodb.Jodb;
import org.mobangjack.db.jodb.table.MetaTable;
import org.mobangjack.db.jodb.table.column.MetaField;

/**
 * The DefaultDialect is actually MysqlDialect.
 * @author 帮杰
 *
 */
public class DefaultDialect implements Dialect {

	public DefaultDialect() {}

	@Override
	public String createTable(MetaTable metaTable) {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sql.append(metaTable.getName()+"(");
		for(MetaField metaField:metaTable.getMetaFields()){
			sql.append(metaField.getName()+" ");
			sql.append(metaField.getType()+" ");
			sql.append(metaField.isNullAllowed()?"":"NOT NULL ");
			sql.append(StrUtil.isNOB(metaField.getDefaultVal())?"":"DEFAULT "+metaField.getDefaultVal()+" ");
			sql.append(metaField.isPrimary()?"PRIMARY KEY ":"");
			sql.append((!metaField.isPrimary())&&metaField.isUnique()?"UNIQUE KEY ":"");
			sql.append(",");
		}
		sql = sql.replace(sql.lastIndexOf(","), sql.length(), ")");
		return sql.toString();
	}
	
	@Override
	public <M> String insert(M model, boolean replace, List<Object> params) {
		
		Class<?> clazz = model.getClass();
		
		String k = "";
		String v = "";
		for(Entry<String, String> entry:Jodb.getFieldMap(clazz).entrySet()){
			k += entry.getValue()+",";
			Object val = ReflectUtil.getFieldVal(model, entry.getKey());
			v += handleEncrypt(clazz,entry.getValue(),val==null?null:val.toString())+",";
			params.add(val);
		}
		k = k.substring(0, k.lastIndexOf(","));
		v = v.substring(0, v.lastIndexOf(","));
		
		StringBuilder sql = new StringBuilder();
		if(replace){
			sql.append("REPLACE INTO ");
		}else{
			sql.append("INSERT INTO ");
		}
		sql.append(Jodb.getMetaTable(clazz).getName())
		   .append("(")
		   .append(k)
		   .append(")")
		   .append(" VALUES(")
		   .append(v)
		   .append(")");
		
		return sql.toString();
	}

	@Override
	public <M> String delete(M model, List<Object> params) {
		
		Class<?> clazz = model.getClass();
		
		String wh = "";
		for(Entry<String, String> entry:Jodb.getFieldMap(clazz).entrySet()){
			Object val = ReflectUtil.getFieldVal(model,entry.getKey());
			if(val!=null){
				wh += entry.getValue()+"="+handleEncrypt(clazz,entry.getValue(),val==null?null:val.toString())+" AND ";
				params.add(val);
			}
		}
		wh = StrUtil.isNOB(wh)?"1":wh.substring(0, wh.lastIndexOf("AND"));
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append(" WHERE ")
		   .append(wh);
		
		return sql.toString();
	}

	@Override
	public <M> String update(M target, M replacement, List<Object> params) {
		
		Class<?> clazz = target.getClass();
		
		String kv = "";
		String wh = "";
		List<Object> wheres = new ArrayList<Object>();
		for(Entry<String, String> entry:Jodb.getFieldMap(clazz).entrySet()){
			Object val = ReflectUtil.getFieldVal(replacement,entry.getKey());
			Object whe = ReflectUtil.getFieldVal(target,entry.getKey());
			if(val!=null){
				kv += entry.getValue()+"="+handleEncrypt(target.getClass(),entry.getValue(),val.toString())+",";
				params.add(val);
			}
			if(whe!=null){
				wh += entry.getValue()+"="+handleEncrypt(target.getClass(),entry.getValue(),whe.toString())+" AND ";
				wheres.add(whe);
			}
		}
		kv = kv.substring(0, kv.lastIndexOf(","));
		wh = StrUtil.isNOB(wh)?"1":wh.substring(0, wh.lastIndexOf("AND"));
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append(" SET ")
		   .append(kv)
		   .append(" WHERE ")
		   .append(wh);
		params.addAll(wheres);
		
		return sql.toString();
	}

	@Override
	public <M> String select(String select, M model, List<Object> params, String constrains) {
		
		Class<?> clazz = model.getClass();
		
		String se = "";
		String wh = "";
		for(Entry<String, String> entry:Jodb.getFieldMap(clazz).entrySet()){
			Object val = ReflectUtil.getFieldVal(model,entry.getKey());
			if(val!=null){
				wh += entry.getValue()+"="+handleEncrypt(clazz,entry.getValue(),val.toString())+" AND ";
				params.add(val);
			}else{
				se += handleDecrypt(clazz, entry.getValue(), null)+",";
			}
		}
		se = StrUtil.isNOB(se)?"*":se.substring(0, se.lastIndexOf(","));
		wh = StrUtil.isNOB(wh)?"1":wh.substring(0, wh.lastIndexOf("AND"));
		select = StrUtil.isNOB(select)?se:select;
		constrains = StrUtil.isNOB(constrains)?"":constrains;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ")
		   .append(select)
		   .append(" FROM ")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append(" WHERE ")
		   .append(wh)
		   .append(" ")
		   .append(constrains);
		
		return sql.toString();
	}

	@Override
	public String handleEncrypt(Class<?> clazz, String metaFieldName,
			String defaultKey) {
		return "?";
	}

	@Override
	public String handleDecrypt(Class<?> clazz, String metaFieldName,
			String defaultKey) {
		return metaFieldName;
	}

}
