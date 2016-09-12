package test;

import org.mobangjack.db.jodb.table.column.Column;

public class SuperAdmin {

	@Column(type = "int(11)",primary=true)
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public SuperAdmin setId(Integer id) {
		this.id = id;
		return this;
	}
	
}
