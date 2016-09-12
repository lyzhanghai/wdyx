package org.mobangjack.db.jodb;

import java.util.Map;

import org.mobangjack.db.Db;
import org.mobangjack.db.jodb.table.MetaTable;

public class JodbConfig {

	private Db db;
	private MetaTable metaTable;
	private Map<String, String> fieldMap;
	
	public JodbConfig() {}
	
	public JodbConfig(Db db,MetaTable metaTable,Map<String, String> fieldMap) {
		this.db = db;
		this.metaTable = metaTable;
		this.fieldMap = fieldMap;
	}
	
	public JodbConfig(MetaTable metaTable,Map<String, String> fieldMap) {
		this.db = Db.useDefault();
		this.metaTable = metaTable;
		this.fieldMap = fieldMap;
	}

	public Db getDb() {
		return db;
	}

	public void setDb(Db db) {
		this.db = db;
	}

	public MetaTable getMetaTable() {
		return metaTable;
	}

	public void setMetaTable(MetaTable metaTable) {
		this.metaTable = metaTable;
	}

	public Map<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}


}
