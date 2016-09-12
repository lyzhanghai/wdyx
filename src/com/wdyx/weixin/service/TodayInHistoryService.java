package com.wdyx.weixin.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wdyx.weixin.service.commons.HttpUtil;
  
/**
 * 历史上的今天 服务
 * @author 帮杰
 *
 */
public class TodayInHistoryService {  
 
	public static final String URL = "http://www.rijiben.com";
	
	private String todayInHistoryInfo = "";
  
	public TodayInHistoryService(){
		StringBuffer buffer = new StringBuffer();
		String html = "";
		try {
			html = HttpUtil.getHtml(URL);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(html!=null&&!html.trim().equals("")){
			Document doc = Jsoup.parse(html);
	        Elements elements = doc.select("div.listren").select("a");
	        for(Element element : elements){
	        	buffer.append(element.text()).append("\n");
	        }
	        String space = Jsoup.parse("&nbsp;").text();
			todayInHistoryInfo = buffer.substring(0, buffer.lastIndexOf("\n")).replace(space, " ");
		}
	}
	public String getTodayInHistoryInfo(){
		return todayInHistoryInfo;
	}
    public static void main(String[] args){   
        System.out.println(new TodayInHistoryService().getTodayInHistoryInfo());  
    }  
}  
