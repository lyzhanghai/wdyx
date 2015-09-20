package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * XML工具类
 * 
 */
public class XmlUtil {

	public static String read(InputStream inputStream) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer();
		String str = null;
		while((str=bufferedReader.readLine())!=null){
			sb.append(str);
		}
		bufferedReader.close();
		bufferedReader = null;
		inputStreamReader.close();
		inputStreamReader = null;
		return sb.toString();
	}

	public static Map<String, String> xmlToMap(String xml) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Map<String, String> xmlMap = new HashMap<String, String>();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getChildNodes();
		for(int i=0;i<nodeList.getLength();i++){
			xmlMap.put(nodeList.item(i).getNodeName(), nodeList.item(i).getTextContent());
		}
		return xmlMap;
	}
	
	public static void main(String[] args) throws Exception {
		String xml = "<xml><ToUserName><![CDATA[gh_6c7ca91f3334]]></ToUserName><FromUserName><![CDATA[onbOrtzigRl1yEPuKNfRgiLrqH4Q]]></FromUserName><CreateTime>1442723779</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[11]]></EventKey></xml>";
		Map<String, String> xmlMap = xmlToMap(xml);
		System.out.println(xmlMap);
	}
}
