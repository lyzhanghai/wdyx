package org.mobangjack.db.jodb.table;

import org.mobangjack.db.consts.mysql.Charset;
import org.mobangjack.db.consts.mysql.Collate;
import org.mobangjack.db.consts.mysql.Engine;
import org.mobangjack.db.jodb.table.column.MetaField;

/**
 * MetaTable that describe the table structure.
 * @author 帮杰
 *
 */
public class MetaTable {
	
	private String name = "";
	private MetaField[] metaFields;
	private String comment = "";
	private String engine = Engine.InnoDB;
	private Integer auto_increment = null;
	private String charset = Charset.utf8;
	private String collate = Collate.utf8_general_ci;
	
	
	public MetaTable() {}

	public MetaTable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public MetaTable setName(String name) {
		this.name = name;
		return this;
	}

	public MetaField[] getMetaFields() {
		return metaFields;
	}

	public MetaTable setMetaFields(MetaField[] metaFields) {
		this.metaFields = metaFields;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public MetaTable setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getEngine() {
		return engine;
	}

	public MetaTable setEngine(String engine) {
		this.engine = engine;
		return this;
	}

	public Integer getAuto_increment() {
		return auto_increment;
	}

	public MetaTable setAuto_increment(Integer auto_increment) {
		this.auto_increment = auto_increment;
		return this;
	}

	public String getCharset() {
		return charset;
	}

	public MetaTable setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public String getCollate() {
		return collate;
	}

	public MetaTable setCollate(String collate) {
		this.collate = collate;
		return this;
	}

	public String getPrimaryKey() {
		for(MetaField mf:metaFields){
			if(mf.isPrimary())
				return mf.getName();
		}
		return null;
	}
}
