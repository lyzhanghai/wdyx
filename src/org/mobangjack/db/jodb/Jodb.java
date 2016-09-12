package org.mobangjack.db.jodb;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.ReflectUtil;
import org.mobangjack.common.util.StrUtil;
import org.mobangjack.db.Db;
import org.mobangjack.db.Database;
import org.mobangjack.db.DbPro;
import org.mobangjack.db.DbUtil;
import org.mobangjack.db.Tx;
import org.mobangjack.db.TxPro;
import org.mobangjack.db.jodb.table.MetaTable;
import org.mobangjack.db.jodb.table.Table;
import org.mobangjack.db.jodb.table.column.Column;
import org.mobangjack.db.jodb.table.column.MetaField;

/**
 * Java Object database.
 * 
 * @author 帮杰
 *
 */
public class Jodb {
	
	public static boolean debug = false;
	
	private static final Logger logger = Logger.getLogger(Jodb.class);
			
	private static final Map<Class<?>, JodbConfig> jodbConfigMap = new HashMap<Class<?>, JodbConfig>();
	
	private Jodb(){}
	
	public static JodbConfig getJodbConfig(Class<?> clazz){
		JodbConfig jodbConfig = jodbConfigMap.get(clazz);
		if(jodbConfig==null){
			List<MetaField> metaFieldList = new ArrayList<MetaField>();
			Map<String, String> fieldMap = new HashMap<String, String>();
			Class<?> claz = ReflectUtil.cloneClass(clazz);
			while(claz!=null){
				Field[] fields = claz.getDeclaredFields();
				for(int i=0;i<fields.length;i++){
					Column column = fields[i].getAnnotation(Column.class);
					if(column!=null){
						MetaField metaField = new MetaField()
								.setName(StrUtil.isNOB(column.name())?fields[i].getName():column.name())
								.setType(column.type())
								.setNullAllowed(column.nullAllowed())
								.setDefaultVal(column.defaultVal())
								.setAuto_increment(column.auto_increment())
								.setCharset(column.charset())
								.setCollate(column.collate())
								.setUnique(column.unique())
								.setPrimary(column.primary())
								.setComment(column.comment())
								.setEncrypt(column.encrypt())
								.setEncryptKey(column.encryptKey());
						metaFieldList.add(metaField);
						fieldMap.put(fields[i].getName(), metaField.getName());
					}
				}
				claz = claz.getSuperclass();
			}
			if(metaFieldList.isEmpty())
				throw new IllegalArgumentException("Jodb initializing error!Your must use JodbTable(Optional) and JodbColumn annotation in your bean class '"+clazz.getName()+"'");
			Table table = clazz.getAnnotation(Table.class);
			MetaTable metaTable = new MetaTable()
				.setName(table==null||StrUtil.isNOB(table.name())?clazz.getSimpleName():table.name())
				.setMetaFields(metaFieldList.toArray(new MetaField[0]))
				.setComment(table==null?"":table.comment())
				.setEngine(table==null?"":table.engine())
				.setAuto_increment(table==null?1:table.auto_increment())
				.setCharset(table==null?"":table.charset())
				.setCollate(table==null?"":table.collate());
			Database database = clazz.getAnnotation(Database.class);
			Db db = database==null||StrUtil.isNOB(database.value())?Db.useDefault():Db.use(database.value());
			jodbConfig = new JodbConfig(db,metaTable, fieldMap);
			if(!createTable(jodbConfig))
				throw new RuntimeException("Jodb initializing error!Fail to create table.");
			jodbConfigMap.put(clazz, jodbConfig);
		}
		return jodbConfig;
	}

	private static boolean createTable(JodbConfig jodbConfig){
		boolean flag = true;
		Tx tx = Tx.getThreadLocalTx();
		boolean isTxMode = (tx!=null);
		Db db = isTxMode?tx.getDb():jodbConfig.getDb();
		String sql = db.getDialect().createTable(jodbConfig.getMetaTable());
		if(debug)
			System.out.println(sql);
		if(isTxMode){
			flag = TxPro.update(sql);
		}else {
			DbPro.start(db.getConnection());
			flag = DbPro.update(sql);
			DbPro.end();
		}
		return flag;
	}
	
	public static Db getDb(Class<?> clazz){
		return getJodbConfig(clazz).getDb();
	}
	
	public static MetaTable getMetaTable(Class<?> clazz){
		return getJodbConfig(clazz).getMetaTable();
	}
	
	public static Map<String, String> getFieldMap(Class<?> clazz){
		return getJodbConfig(clazz).getFieldMap();
	}
	
	public static MetaField getMetaFieldByName(Class<?> clazz,String metaFieldName){
		for(MetaField e:getMetaTable(clazz).getMetaFields()){
			if(e.getName().equals(metaFieldName)){
				return e;
			}
		}
		return null;
	}
	
	public static String getFieldNameByMetaFieldName(Class<?> clazz,String metaFieldName){
		for(Map.Entry<String, String> e:getFieldMap(clazz).entrySet()){
			if(e.getValue().equals(metaFieldName)){
				return e.getKey();
			}
		}
		return null;
	}
	
	private static <M> boolean update(Db db,M model,String sql,List<Object> params,boolean getGeneratedKey){
		Class<?> clazz = model.getClass();
		
		Tx tx = Tx.getThreadLocalTx();
		boolean isTxMode = (tx!=null);
		db = isTxMode?tx.getDb():(db==null?getDb(clazz):db);
		if(getGeneratedKey){
			Object key = null;
			if(isTxMode){
				key = TxPro.updateAndRetrieveKey(sql, params);
			}else {
				DbPro.start(db.getConnection());
				key = DbPro.updateAndRetrieveKey(sql, params);
				DbPro.end();
			}
			if(key!=null){
				String pk = getMetaTable(clazz).getPrimaryKey();
				String fd = getFieldNameByMetaFieldName(clazz, pk);
				ReflectUtil.setFieldVal(model, fd, key);
			}
			return key!=null;
		}else{
			boolean flag = true;
			if(isTxMode){
				flag = TxPro.update(sql, params);
			}else {
				DbPro.start(db.getConnection());
				flag = DbPro.update(sql, params);
				DbPro.end();
			}
			return flag;
		}
	}
	
	public static <M> boolean insert(Db db,M model,boolean replace,boolean getGeneratedKey){
		List<Object> params = new ArrayList<Object>();
		String sql = db.getDialect().insert(model, replace, params);
		if(debug)
			System.out.println(sql);
		
		return update(db, model, sql, params, getGeneratedKey);
	}
	
	public static <M> boolean insert(Db db,M model,boolean replace){
		return insert(db, model, replace, false);
	}
	
	public static <M> boolean insert(M model,boolean replace){
		return insert(getDb(model.getClass()), model, replace);
	}
	
	public static <M> boolean delete(Db db,M model){
		List<Object> params = new ArrayList<Object>();
		String sql = db.getDialect().delete(model, params);
		if(debug)
			System.out.println(sql);
		
		return update(db, model, sql, params, false);
	}
	
	public static <M> boolean delete(M model){
		return delete(getDb(model.getClass()),model);
	}
	
	public static <M> boolean update(Db db,M target,M replacement){
		List<Object> params = new ArrayList<Object>();
		String sql = db.getDialect().update(target, replacement, params);
		if(debug)
			System.out.println(sql);
		
		return update(db, target, sql, params, false);
	}
	
	public static <M> boolean update(M target,M replacement){
		return update(getDb(target.getClass()),target,replacement);
		
	}
	
	protected static <M> CachedRowSet select(Db db,String select,M model,String constrains){
		Class<?> clazz = model.getClass();
		
		Tx tx = Tx.getThreadLocalTx();
		boolean isTxMode = (tx!=null);
		db = isTxMode?tx.getDb():(db==null?getDb(clazz):db);
		
		List<Object> params = new ArrayList<Object>();
		String sql = db.getDialect().select(select, model, params, constrains);
		if(debug)
			System.out.println(sql);
		
		CachedRowSet crs = null;
		
		if(isTxMode){
			crs = TxPro.query(sql, params);
		}else {
			DbPro.start(db.getConnection());
			crs = DbPro.query(sql, params);
			DbPro.end();
		}
		return crs;
	}
	
	@SuppressWarnings("unchecked")
	protected static <M> List<M> fromCachedRowSet(M model,CachedRowSet crs){
		List<M> list = new ArrayList<M>();
		Class<?> clazz = model.getClass();
		try {
			while(crs.next()){
				M tmp = (M) clazz.newInstance();
				for(Entry<String, String> entry:getFieldMap(clazz).entrySet()){
					Object val = ReflectUtil.getFieldVal(model,entry.getKey());
					if(val!=null){
						ReflectUtil.setFieldVal(tmp, entry.getKey(), val); 
					}else{
						val = crs.getObject(entry.getValue());
						val = TypeHandler.handleType(clazz, entry.getKey() ,entry.getValue(), val);
						ReflectUtil.setFieldVal(tmp,entry.getKey(),val);
					}
				}
				list.add(tmp);
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list.isEmpty()?null:list;
	}
	
	public static <M> List<M> select(Db db,M model,String constrains){
		CachedRowSet crs = select(db, null, model, constrains);
		List<M> list = fromCachedRowSet(model,crs);
		DbUtil.close(crs, null, null);
		return list;
	}
	
	public static <M> List<M> select(M model,String constrains){
		return select(getDb(model.getClass()),model,constrains);
	}
	
	public static <M> List<M> select(M model) {
		return select(model,null);
	}
	
	public static <M> boolean validate(Db db,M model){
		CachedRowSet crs = select(db,"1",model,null);
		boolean flag = false;
		try {
			if(crs.next()){
				flag = true;
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			DbUtil.close(crs, null, null);
		}
		return flag;
	}
	
	public static <M> boolean validate(M model){
		return validate(getDb(model.getClass()),model);
	}
	
	public static <M> long count(Db db,M model){
		CachedRowSet crs = select(db,"COUNT(*)",model,null);
		long count = -1;
		try {
			if(crs.next()){
				count = crs.getLong(1);
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			DbUtil.close(crs, null, null);
		}
		return count;
	}
	
	public static <M> long count(M model){
		return count(getDb(model.getClass()),model);
	}
	
}
