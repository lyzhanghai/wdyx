package com.wdyx.weixin.service.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * HTML小工具
 * @author 帮杰
 *
 */
public class HtmlUtil {

	public static final String GBK = "GBK";
	public static final String GB_2312 = "GB2312";
	public static final String UTF_8 = "UTF-8";
	public static final String UTF_16 = "UTF-16";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String ISO_8859_2 = "ISO-8859-2";
	public static final String ISO_8859_3 = "ISO-8859-3";
	public static final String ISO_8859_4 = "ISO-8859-4";
	public static final String ISO_8859_5 = "ISO-8859-5";
	public static final String ISO_8859_6 = "ISO-8859-6";
	public static final String ISO_8859_7 = "ISO-8859-7";
	public static final String ISO_8859_8 = "ISO-8859-8";
	public static final String ISO_8859_9 = "ISO-8859-9";
	public static final String ISO_8859_10 = "ISO-8859-10";
	public static final String ISO_8859_15 = "ISO-8859-15";
	public static final String ISO_2022_JP = "ISO-2022-JP";
	public static final String ISO_2022_JP_2 = "ISO-2022-JP-2";
	public static final String ISO_2022_KR = "ISO-2022-KR";
	
	/**
	 * 列举所有字符编码
	 * @return List<String>
	 */
	public static List<String> listCharset(){
		List<String> charsetList = new ArrayList<String>();
		charsetList.add(GBK);
		charsetList.add(GB_2312);
		charsetList.add(UTF_8);
		charsetList.add(UTF_16);
		charsetList.add(ISO_8859_1);
		charsetList.add(ISO_8859_2);
		charsetList.add(ISO_8859_3);
		charsetList.add(ISO_8859_4);
		charsetList.add(ISO_8859_5);
		charsetList.add(ISO_8859_6);
		charsetList.add(ISO_8859_7);
		charsetList.add(ISO_8859_8);
		charsetList.add(ISO_8859_9);
		charsetList.add(ISO_8859_10);
		charsetList.add(ISO_8859_15);
		charsetList.add(ISO_2022_JP);
		charsetList.add(ISO_2022_JP_2);
		charsetList.add(ISO_2022_KR);
		return charsetList;
	}
	
	/**
	 * 获取HTML页面的字符集
	 * @param html
	 * @return
	 */
	public static String getCharset(String html){
		String charSet = "utf-8";
		try{
			Document doc = Jsoup.parse(html);
			String content = doc.getElementsByTag("meta").select("[http-equiv=Content-Type]").attr("content").toUpperCase();
			List<String> charSetList = listCharset();
			for(String charset : charSetList){
				if(content.contains(charset)){
					return charset;
				}
			}
		}catch(Exception e){
			charSet = "utf-8";
		}
		return charSet;
	}
	
	public static List<String> getLinks(String html){
		List<String> links = new ArrayList<String>();
		try{
			Document doc = Jsoup.parse(html);
			Elements elements = doc.select("a[href]");
			for(Element element : elements){
				links.add(element.attr("href"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return links;
	}
	
	public static List<String> getSrc(Document doc){
		List<String> src = new ArrayList<String>();
		String base = "";
		if(doc.select("base")!=null){
			base = doc.select("base").attr("href");
			base = base.endsWith("/")?base:base+"/";
		}
		Elements elements = doc.select("[src]");
		for(Element element : elements){
			src.add(base+element.attr("src"));
		}
		return src;
	}
	
    public static List<HashMap<String, String>> getFormData(Document doc) {
    	Elements es = doc.getElementsByTag("form").select("[method=post]");
    	List<HashMap<String, String>> parmaList = new ArrayList<HashMap<String, String>>();  
        for (Element e : es) {  
        	HashMap<String, String> parmas = new HashMap<String, String>();
        	parmas.put("action", e.attr("action"));
        	for(Element tmp : e.select("input[name]")){
        		parmas.put(tmp.attr("name"), tmp.attr("value"));
        	}
        	parmaList.add(parmas);
        }  
        return parmaList; 
    }
}
