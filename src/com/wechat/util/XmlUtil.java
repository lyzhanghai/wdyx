package com.wechat.util;

import java.io.BufferedReader;
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

	public static String read(InputStream inputStream) {
		try {
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
			inputStream.close();
			inputStream = null;
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public static Map<String, String> xmlToMap(String xml) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Map<String, String> xmlMap = new HashMap<String, String>();
			Element root = document.getDocumentElement();
			NodeList nodeList = root.getChildNodes();
			for(int i=0;i<nodeList.getLength();i++){
				xmlMap.put(nodeList.item(i).getNodeName(), nodeList.item(i).getNodeValue());
			}
			return xmlMap;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
