package com.wdyx.weixin.web.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;

public class WebUtil {

	private static final Logger logger = Logger.getLogger(WebUtil.class);
	
	public static List<WebItem> getWebItem(String type,String title){
		List<WebItem> webItems = new ArrayList<WebItem>();
		CachedRowSet crs = null;
		try {
			String query = "select id,keyname,keyvalue,picurl from webitem "
					+ "where type='"+type+"' and title='"+title+"' order by id asc";
			crs = MysqlUtil.query(query);
			while(crs.next()){
				WebItem webItem = new WebItem();
				webItem.setId(crs.getInt(1));
				webItem.setTitle(crs.getString(2));
				webItem.setDetail(crs.getString(3));
				String pic = crs.getString(4);
				String[] pics = (pic==null||pic.trim().equals(""))?null:pic.split(";");
				webItem.setPicUrl(pics);
				webItems.add(webItem);
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return webItems;
	}
	
	public static WebView getWebView(String type,String id){
		WebView webView = null;
		CachedRowSet crs = null;
		try {
			String query = "select id,title,description,picurl from webview "
					+ "where type='"+type+"' order by id desc limit 0,1";
			if(id!=null&&!id.trim().equals("")){
				query = "select id,title,description,picurl from webview "
						+ "where type='"+type+"' and id="+id;
			}
			crs = MysqlUtil.query(query);
			if(crs.next()){
				webView = new WebView();
				webView.setId(crs.getInt(1));
				webView.setTitle(crs.getString(2));
				webView.setDescription(crs.getString(3));
				webView.setPicUrl(crs.getString(4));
				webView.setItems(getWebItem(type,webView.getTitle()));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return webView;
	}
}
