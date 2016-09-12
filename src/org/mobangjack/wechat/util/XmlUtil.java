package org.mobangjack.wechat.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XML工具类
 * 
 */
public class XmlUtil {

	public static String parseXml(InputStream inputStream)
			throws Exception {
		InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String str = null;
		while((str=br.readLine())!=null){
			sb.append(str);
		}
		br.close();
		br = null;
		isr.close();
		isr = null;
		inputStream.close();
		inputStream = null;
		return sb.toString();
	}

	public static Map<String, String> XmlToMap(String xml)
			throws Exception {
		Map<String, String> xmlMap = new HashMap<String, String>();
		StringReader sr = new StringReader(xml);
		InputSource is = new InputSource(sr);
		SAXReader reader = new SAXReader();
		Document document = reader.read(is);
		Element root = document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> elements = root.elements();
		for (Element e : elements) {
			xmlMap.put(e.getName(), e.getText());
		}
		return xmlMap;
	}
	
	public static String toXml(Object o,XStream xstream) {
		xstream.processAnnotations(o.getClass());
		return xstream.toXML(o);
	}

	public static String toXml(Object o) {
		XStream xstream = new XStream(new XppDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					
					boolean cdata = true;

					@Override
					public void startNode(String name,
							@SuppressWarnings("rawtypes") Class clazz) {
						if (name.equals("CreateTime")
								|| name.equals("ArticleCount"))
							cdata = false;
						else
							cdata = true;
						super.startNode(name, clazz);
					}

					@Override
					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		return toXml(o,xstream);
	}
	
	public static Object toBean(String xml,XStream xstream) {
		return xstream.fromXML(xml);
	}
	
	public static Object toBean(InputStream input,XStream xstream) {
		return xstream.fromXML(input);
	}
	
	public static Object toBean(Reader xml,XStream xstream) {
		return xstream.fromXML(xml);
	}
	
}
