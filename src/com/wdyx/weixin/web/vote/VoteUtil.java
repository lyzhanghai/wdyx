package com.wdyx.weixin.web.vote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

public class VoteUtil {

	private static final Logger logger = Logger.getLogger(VoteUtil.class);
	
	public static Vote getVote() {
		String query = "select * from vote where `use`=1 limit 0,1";
		CachedRowSet crs = MysqlUtil.query(query);
		Vote vote = null;
		try {
			if (crs.next()) {
				vote = new Vote();
				vote.setId(crs.getInt("id"));
				vote.setTitle(crs.getString("title"));
				vote.setCover(crs.getString("cover"));
				vote.setCandidate(crs.getString("candidate").split(";"));
				vote.setPicurl(crs.getString("picurl").split(";"));
				vote.setDescription(crs.getString("description").split(";"));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return vote;
	}
	
	public static boolean vote(String openid,String candidate) {
		String sql = "insert into voter (openid,title,candidate,time) values ('"+openid+"','"+getVote().getTitle()+"','"+candidate+"',current_timestamp)";
		return MysqlUtil.update(sql);
	}
	
	public static Voter getVoter(String openid) {
		String query = "select * from voter where openid='"+openid+"' and title='"+getVote().getTitle()+"'";
		CachedRowSet crs = MysqlUtil.query(query);
		Voter voter = null;
		try {
			if (crs.next()) {
				voter = new Voter();
				voter.setId(crs.getInt("id"));
				voter.setTitle(crs.getString("title"));
				voter.setCandidate(crs.getString("candidate"));
				voter.setTime(crs.getTimestamp("time"));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return voter;
	}
	
	public static List<String> getCandidate(String openid) {
		String query = "select candidate from voter where openid='"+openid+"' and title='"+getVote().getTitle()+"'";
		CachedRowSet crs = MysqlUtil.query(query);
		List<String> candidates = new ArrayList<String>();
		try {
			while (crs.next()) {
				candidates.add(crs.getString(1));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return candidates;
	}
	
	public static int getVotes(String candidate) {
		return MysqlUtil.getCount("voter", "candidate", candidate);
	}
}
