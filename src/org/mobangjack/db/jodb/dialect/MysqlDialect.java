package org.mobangjack.db.jodb.dialect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.mobangjack.common.util.ReflectUtil;
import org.mobangjack.common.util.StrUtil;
import org.mobangjack.db.consts.mysql.Encrypt;
import org.mobangjack.db.jodb.Jodb;
import org.mobangjack.db.jodb.table.MetaTable;
import org.mobangjack.db.jodb.table.column.MetaField;

/**
 * MysqlDialect
 * @author 帮杰
 *
 */
public class MysqlDialect extends DefaultDialect {
	
	public MysqlDialect(){}
	
	@Override
	public String createTable(MetaTable metaTable) {
		StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sql.append("`"+metaTable.getName()+"`(");
		for(MetaField metaField:metaTable.getMetaFields()){
			sql.append("`"+metaField.getName()+"` ");
			sql.append(metaField.getType());
			sql.append(metaField.isNullAllowed()?"":" NOT NULL");
			sql.append(StrUtil.isNOB(metaField.getDefaultVal())?"":" DEFAULT "+metaField.getDefaultVal());
			sql.append(metaField.isAuto_increment()?" AUTO_INCREMENT ":"");
			sql.append(StrUtil.isNOB(metaField.getCharset())?"":" CHARACTER SET "+metaField.getCharset());
			sql.append(StrUtil.isNOB(metaField.getCollate())?"":" COLLATE "+metaField.getCollate());
			sql.append(metaField.isPrimary()?" PRIMARY KEY":"");
			sql.append((!metaField.isPrimary())&&metaField.isUnique()?" UNIQUE KEY":"");
			sql.append(StrUtil.isNOB(metaField.getComment())?"":"COMMENT='"+metaField.getComment()+"'");
			sql.append(",");
		}
		sql = sql.replace(sql.lastIndexOf(","), sql.length(), ")");
		sql.append(StrUtil.isNOB(metaTable.getComment())?"":"COMMENT='"+metaTable.getComment()+"' ");
		sql.append(StrUtil.isNOB(metaTable.getEngine())?"":"ENGINE="+metaTable.getEngine()+" ");
		sql.append(metaTable.getAuto_increment()==null?"":"AUTO_INCREMENT="+metaTable.getAuto_increment()+" ");
		sql.append(StrUtil.isNOB(metaTable.getCharset())?"":"DEFAULT CHARSET="+metaTable.getCharset()+" ");
		sql.append(StrUtil.isNOB(metaTable.getCollate())?"":"DEFAULT COLLATE="+metaTable.getCollate());
		return sql.toString();
	}
	
	@Override
	public <M> String insert(M model, boolean replace, List<Object> params) {
		
		Class<?> clazz = model.getClass();
		
		String k = "";
		String v = "";
		for(Entry<String, String> entry:Jodb.getFieldMap(clazz).entrySet()){
			k += "`"+entry.getValue()+"`,";
			Object val = ReflectUtil.getFieldVal(model, entry.getKey());
			System.out.println(entry.getValue()+"="+val);
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
		sql.append("`")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append("`")
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
				wh += "`"+entry.getValue()+"`="+handleEncrypt(clazz,entry.getValue(),val.toString())+" AND ";
				params.add(val);
			}
		}
		wh = StrUtil.isNOB(wh)?"1":wh.substring(0, wh.lastIndexOf("AND"));
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ")
		   .append("`")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append("`")
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
				kv += "`"+entry.getValue()+"`="+handleEncrypt(target.getClass(),entry.getValue(),val.toString())+",";
				params.add(val);
			}
			if(whe!=null){
				wh += "`"+entry.getValue()+"`="+handleEncrypt(target.getClass(),entry.getValue(),whe.toString())+" AND ";
				wheres.add(whe);
			}
		}
		kv = kv.substring(0, kv.lastIndexOf(","));
		wh = StrUtil.isNOB(wh)?"1":wh.substring(0, wh.lastIndexOf("AND"));
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ")
		   .append("`")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append("`")
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
				wh += "`"+entry.getValue()+"`="+handleEncrypt(clazz,entry.getValue(),val.toString())+" AND ";
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
		   .append("`")
		   .append(Jodb.getMetaTable(clazz).getName())
		   .append("`")
		   .append(" WHERE ")
		   .append(wh)
		   .append(" ")
		   .append(constrains);
		
		return sql.toString();
	}

	@Override
	public String handleEncrypt(Class<?> clazz, String metaFieldName,
			String defaultKey) {
		String encryptStr = "?";
		MetaField metaField = Jodb.getMetaFieldByName(clazz, metaFieldName);
		String encrypt = metaField.getEncrypt();
		if(StrUtil.isNOB(encrypt))
			return encryptStr;
		String key = metaField.getEncryptKey();
		key = StrUtil.isNOB(key)?defaultKey:key;
		if(encrypt.equalsIgnoreCase(Encrypt.AES_ENCRYPT)||encrypt.equalsIgnoreCase(Encrypt.ENCODE)){
			encryptStr = encrypt.toUpperCase()+"(?,'"+key+"')";
		}else if (encrypt.equalsIgnoreCase(Encrypt.MD5)||encrypt.equalsIgnoreCase(Encrypt.PASSWORD)||encrypt.equalsIgnoreCase(Encrypt.SHA)){
			encryptStr = encrypt.toUpperCase()+"(?)";
		}else{
			encryptStr = "?";
		}
		return encryptStr;
	}

	@Override
	public String handleDecrypt(Class<?> clazz, String metaFieldName,
			String defaultKey) {
		String decryptStr = "`"+metaFieldName+"`";
		MetaField metaField = Jodb.getMetaFieldByName(clazz, metaFieldName);
		String encrypt = metaField.getEncrypt();
		if(StrUtil.isNOB(encrypt))
			return decryptStr;
		String key = metaField.getEncryptKey();
		key = StrUtil.isNOB(key)?defaultKey:key;
		if(encrypt.equalsIgnoreCase(Encrypt.AES_ENCRYPT)||encrypt.equalsIgnoreCase(Encrypt.ENCODE)){
			if(encrypt.equalsIgnoreCase(Encrypt.AES_ENCRYPT)){
				decryptStr = "AES_DECRYPT";
			}else{
				decryptStr = "DECODE";
			}
			decryptStr += "(`"+metaFieldName+"`,'"+key+"') AS `"+metaFieldName+"`";
		}else {
			decryptStr = "`"+metaFieldName+"`";
		}
		return decryptStr;
	}

}
