package com.wdyx.weixin.web.art;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

import com.wdyx.weixin.web.main.WebUtil;

public class ArtUtil {
	
	private static final Logger logger = Logger.getLogger(ArtUtil.class);
			
	public static Art getArt(String artid){
		Art art = null;
		CachedRowSet crs = null;
		try {
			String query = "select id,title,description,picurl from webview "
					+ "where type='34' order by id desc limit 0,1";
			if(artid!=null&&!artid.trim().equals("")){
				query = "select id,title,description,picurl from webview "
						+ "where type='34' and id="+artid;
			}
			crs = MysqlUtil.query(query);
			if(crs.next()){
				art = new Art();
				art.setId(crs.getInt(1));
				art.setTitle(crs.getString(2));
				art.setDescription(crs.getString(3));
				art.setPicUrl(crs.getString(4));
				art.setItems(WebUtil.getWebItem("34",art.getTitle()));
				art.setReviews(getArtReview(art.getId()));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return art;
	}
	public static List<ArtReview> getArtReview(int artid) {
		String query = "select * from ArtReview where artid="+artid+" order by id desc limit 0,30";
		CachedRowSet crs = MysqlUtil.query(query);
		List<ArtReview> artReviewList = new ArrayList<ArtReview>();
		try {
			while (crs.next()) {
				ArtReview artReview = new ArtReview();
				artReview.setId(crs.getInt("id"));
				artReview.setOpenid(crs.getString("openid"));
				artReview.setTitle(crs.getString("title"));
				artReview.setReview(crs.getString("review"));
				artReview.setTime(crs.getTimestamp("time"));
				artReview.setArtid(artid);
				artReviewList.add(artReview);
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return artReviewList;
	}

	public static boolean reviewArt(String openid,String title,String review,String artid) {
		String sql = "insert into ArtReview(openid,title,review,time,artid) "
				+ "values ('"+openid+"','"+title+"','"+review+"',current_timestamp,"+artid+")";
		return MysqlUtil.update(sql);
	}
	
	public static List<ArtRecord> getArtRecord() {
		String query = "select id,title,description,time from webview where type='34' order by id desc limit 0,30";
		CachedRowSet crs = MysqlUtil.query(query);
		List<ArtRecord> artRecords = new ArrayList<ArtRecord>();
		try {
			while(crs.next()){
				ArtRecord artRecord = new ArtRecord();
				artRecord.setId(crs.getInt(1));
				artRecord.setTitle(crs.getString(2));
				artRecord.setDescription(crs.getString(3));
				artRecord.setTime(crs.getTimestamp(4));
				artRecords.add(artRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artRecords;
	}
}
