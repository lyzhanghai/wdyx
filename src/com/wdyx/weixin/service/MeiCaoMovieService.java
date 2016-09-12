package com.wdyx.weixin.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 这个类用于获取最新梅操电影资讯
 * @author 帮杰
 *
 */
public class MeiCaoMovieService {

	private static final String BASIC_URL = "http://vhost.whu.edu.cn/gh/";
	private static final String URL = "http://vhost.whu.edu.cn/gh/xywh.php?Class_Type=0&Class_ID=42";
	private String movieInfo = "";
	public MeiCaoMovieService(){
		try {
			Document doc = Jsoup.parse(new URL(URL), 5000);
			String href = doc.select("td.k2").select("a.a1").first().attr("href");
			doc = Jsoup.parse(new URL(BASIC_URL+href), 5000);
			Elements elements = doc.select("p");
			StringBuffer buffer = new StringBuffer();
			for(Element e : elements){
				buffer.append(e.text()).append("\n\n");
			}
			String space = Jsoup.parse("&nbsp;").text();
			movieInfo = buffer.substring(0,buffer.lastIndexOf("\n\n")).replace(space, " ");
		} catch (MalformedURLException e) {
			System.out.println("MeiCaoMovieService:fail to fetch data");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("MeiCaoMovieService:fail to fetch data");
			e.printStackTrace();
		}
	}
	public String getMovieInfo(){
		return movieInfo;
	}
	//测试
    public static void main(String[] args) {  
    	//System.out.println(new MeiCaoMovieService().getMovieInfo());
    	try {
			Document doc = Jsoup.parse(new File("C:\\Users\\帮杰\\Desktop\\sma.duapp.com _ sqld.duapp.com _ dLBorePIIFHdHnWahyVo _ phpMyAdmin 3.4.8_files\\db_structure.htm"), "UTF-8");
			if(doc!=null){
				//Elements es = doc.select("optgroup").select("option");
				Elements es = doc.select("optgroup");
				Set<String> charset = new HashSet<String>();
				for(Element e:es){
					String text = e.text();
					if(text.equals("-"))continue;
					if(text.contains("_")){
						charset.add(text.substring(0, text.indexOf("_")));
					}else {
						charset.add(text);
					}
					//System.out.println("public static final String "+text+" = \""+text+"\";");
					//System.out.println(text+"(\""+text+"\");");
				}
				for(String s:charset){
					System.out.println("public static final String "+s+" = \""+s+"\";");
				}
				//System.out.println(charset);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
