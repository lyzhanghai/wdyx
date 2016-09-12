/**
 * 
 */
package org.mobangjack.db.jodb.table.column;

import org.mobangjack.db.consts.mysql.Charset;
import org.mobangjack.db.consts.mysql.Collate;


/**
 * Meta-field that describing the field structure.
 * @author 帮杰
 *
 */
public class MetaField {

	private String name = "";
	private String type = "";
	private boolean nullAllowed = true;
	private String defaultVal = "";
	private boolean auto_increment = false;
	private String charset = Charset.utf8;
	private String collate = Collate.utf8_general_ci;
	private boolean unique = false;
	private boolean primary = false;
	private String comment = "";
	private String encrypt = null;
	private String encryptKey = null;
	
	public MetaField() {}

	public MetaField(String name) {
		this.name = name;
	}

	public MetaField(String name,String type) {
		this(name);
		this.type = type;
	}
	
	public MetaField(String name,String type,String defaultVal) {
		this(name,type);
		this.defaultVal = defaultVal;
	}
	
	public String getName() {
		return name;
	}

	public MetaField setName(String name) {
		this.name = name;
		return this;
	}

	public String getType() {
		return type;
	}

	public MetaField setType(String type) {
		this.type = type;
		return this;
	}

	public boolean isNullAllowed() {
		return nullAllowed;
	}

	public MetaField setNullAllowed(boolean nullAllowed) {
		this.nullAllowed = nullAllowed;
		return this;
	}
	
	public String getDefaultVal() {
		return defaultVal;
	}

	public MetaField setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
		return this;
	}

	public boolean isAuto_increment() {
		return auto_increment;
	}

	public MetaField setAuto_increment(boolean auto_increment) {
		this.auto_increment = auto_increment;
		return this;
	}

	public boolean isUnique() {
		return unique;
	}

	public MetaField setUnique(boolean unique) {
		this.unique = unique;
		return this;
	}

	public boolean isPrimary() {
		return primary;
	}

	public MetaField setPrimary(boolean primary) {
		this.primary = primary;
		return this;
	}
	
	public String getCharset() {
		return charset;
	}

	public MetaField setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public String getCollate() {
		return collate;
	}

	public MetaField setCollate(String collate) {
		this.collate = collate;
		return this;
	}
	
	public String getComment() {
		return comment;
	}

	public MetaField setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getEncrypt() {
		return encrypt;
	}

	public MetaField setEncrypt(String encrypt) {
		this.encrypt = encrypt;
		return this;
	}
	
	public String getEncryptKey() {
		return encryptKey;
	}

	public MetaField setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
		return this;
	}
	
}
