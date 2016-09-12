package com.wdyx.weixin.web.gift;

import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import org.mobangjack.common.util.MysqlUtil;

public class GiftUtil {
	
	public static Gift getGift() {
		String query = "select * from gift where `use`=1 limit 0,1";
		CachedRowSet crs = MysqlUtil.query(query);
		Gift gift = null;
		try {
			if(crs.next()){
				gift = new Gift();
				gift.setId(crs.getInt("id"));
				gift.setGift(crs.getString("gift"));
				gift.setCount(crs.getInt("count"));
				gift.setTime(crs.getTimestamp("time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gift;
	}
	
	public static boolean signOwner(String openid) {
		String sql = "insert into GiftOwner (openid,gift,time,state) values ('"+openid+"','"+getGift()+"',current_timestamp,'未领')";
		return MysqlUtil.update(sql);
	}
	
	public static boolean changeOwnerState(String openid,String state) {
		String sql = "update `GiftOwner` set `time`=current_timestamp,`state`='"+state+"' where `openid`='"+openid+"' and `gift`='"+getGift()+"'";
		return MysqlUtil.update(sql);
	}
	
	public static GiftOwner getGiftOwner(String openid){
		String query = "select * from GiftOwner where openid='"+openid+"' and gift='"+getGift()+"'";;
		CachedRowSet crs = MysqlUtil.query(query);
		GiftOwner owner = null;
		try {
			if(crs.next()) {
				owner = new GiftOwner();
				owner.setId(crs.getInt("id"));
				owner.setOpenid(crs.getString("openid"));
				owner.setGift(crs.getString("gift"));
				owner.setTime(crs.getTimestamp("time"));
				owner.setState(crs.getString("state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owner;
	}

}
