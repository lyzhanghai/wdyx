package com.wdyx.weixin.web.ticket;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.CachedRowSet;

import org.mobangjack.common.util.MysqlUtil;

public class TicketUtil {

	public static Ticket getTicket() {
		String query = "select * from ticket where `use`=1 limit 0,1";
		CachedRowSet crs = MysqlUtil.query(query);
		Ticket ticket = null;
		try {
			if(crs.next()){
				ticket = new Ticket();
				ticket.setId(crs.getInt("id"));
				ticket.setTitle(crs.getString("title"));
				ticket.setDate(crs.getDate("date"));
				ticket.setPicurl(crs.getString("picurl"));
				ticket.setNormal(crs.getInt("normal"));
				ticket.setVip(crs.getInt("vip"));
				ticket.setKey(crs.getString("key"));
				ticket.setRemark(crs.getString("remark"));
			}
		} catch (SQLException e) {
			System.out.println("Error:getTicketByTitle");
			e.printStackTrace();
		}
		return ticket;
	}
	
	public static boolean signOwner(String openid,String type) {
		String sql = "insert into TicketOwner (openid,title,type,time,state) values ('"+openid+"','"+getTicket().getTitle()+"','"+type+"',current_timestamp,'未领票')";
		return MysqlUtil.update(sql);
	}
	
	public static boolean changeOwnerState(String openid,String state) {
		String sql = "update `TicketOwner` set `time`=current_timestamp,`state`='"+state+"' where `openid`='"+openid+"' and `title`='"+getTicket().getTitle()+"'";
		return MysqlUtil.update(sql);
	}
	
	public static TicketOwner getTicketOwner(String openid){
		String query = "select * from TicketOwner where openid='"+openid+"' and title='"+getTicket().getTitle()+"'";;
		CachedRowSet crs = MysqlUtil.query(query);
		TicketOwner owner = null;
		try {
			if(crs.next()) {
				owner = new TicketOwner();
				owner.setId(crs.getInt("id"));
				owner.setOpenid(crs.getString("openid"));
				owner.setTitle(crs.getString("title"));
				owner.setType(crs.getString("type"));
				owner.setTime(crs.getTimestamp("time"));
				owner.setState(crs.getString("state"));
			}
		} catch (SQLException e) {
			System.out.println(new Date()+" error:getTicketOwner");
			e.printStackTrace();
		}
		return owner;
	}
}
