package org.mobangjack.db.jodb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

import org.mobangjack.common.util.ReflectUtil;
import org.mobangjack.common.util.StrUtil;
import org.mobangjack.db.consts.mysql.Encrypt;
import org.mobangjack.db.jodb.Jodb;
import org.mobangjack.db.jodb.table.column.MetaField;

public class TypeHandler {

	public static Object handleType(Class<?> modelClass, String fieldName,
			String metaFieldName, Object val) {
		Field field = ReflectUtil.getFieldByName(modelClass, fieldName);
		if(field.getType().equals(val.getClass()))
			return val;
		MetaField metaField = Jodb.getMetaFieldByName(modelClass,metaFieldName);
		String encrypt = metaField.getEncrypt();
		String type = metaField.getType().toLowerCase();
		if(!StrUtil.isNOB(encrypt)&&encrypt.equalsIgnoreCase(Encrypt.AES_ENCRYPT)){
			val = new String((byte[])val);
		}else if(type.contains("blob")){
			val = handleBlob((Blob)val);
		}else if(type.contains("clob")||type.contains("nclob")){
			val = handleClob((Clob)val);
		}
		return val;
	}
	
	public static byte[] handleBlob(Blob blob) {
		byte[] data = null;
		InputStream is = null;
		try {
			is = blob.getBinaryStream();
			data = new byte[(int)blob.length()];		// byte[] data = new byte[is.available()];
			is.read(data);
			is.close();
			return data;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public static String handleClob(Clob clob) {
		String data = null;
		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			char[] buffer = new char[(int)clob.length()];
			reader.read(buffer);
			data = new String(buffer);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

}
