package com.wdyx.weixin.web.vote;

import java.util.List;

import org.mobangjack.db.Tx;
import org.mobangjack.db.jodb.Jodb;
import org.mobangjack.db.jodb.table.Table;
import org.mobangjack.db.jodb.table.column.Column;

@Table
public class Candidate {

	@Column(type = "varchar(100)")
	private String title;
	
	@Column(type = "int(11)",primary=true,auto_increment=true)
	private int id;
	
	@Column(type = "varchar(32)")
	private String name;
	
	@Column(type = "varchar(200)")
	private String pic;
	
	@Column(type = "varchar(2048)")
	private String remark;
	
	@Column(type = "int(11)")
	private int votes;
	
	public Candidate() {}

	public Candidate(int id) {
		this.id = id;
	}
	
	public Candidate(String title) {
		this.title = title;
	}
	
	public Candidate(String name,String pic,String remark) {
		this.name = name;
		this.pic = pic;
		this.remark = remark;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getId() {
		return id;
	}

	public Candidate setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Candidate setName(String name) {
		this.name = name;
		return this;
	}

	public String getRemark() {
		return remark;
	}

	public Candidate setRemark(String remark) {
		this.remark = remark;
		return this;
	}

	public String getPic() {
		return pic;
	}

	public Candidate setPic(String pic) {
		this.pic = pic;
		return this;
	}
	
	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public static Candidate getCandidate(int id) {
		Candidate candidate = new Candidate(id);
		List<Candidate> candidates = Jodb.select(candidate);
		return candidates==null?null:candidates.get(0);
	}
	
	public static List<Candidate> getCandidate(String title) {
		Candidate candidate = new Candidate(title);
		List<Candidate> candidates = Jodb.select(candidate);
		return candidates;
	}
	
	public boolean addVotes(int id) {
		return false;
		
	}

}
