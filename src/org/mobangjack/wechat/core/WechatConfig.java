package org.mobangjack.wechat.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mobangjack.common.util.StrUtil;
import org.mobangjack.wechat.api.config.ApiConfig;

/**
 * wechat configuration.
 * @author 帮杰
 *
 */
public class WechatConfig {
	
	public static final String CONFIG_XML_FILE_PATH = "wechat-config.xml";
	
	private final String name;
	
	private final ApiConfig apiConfig;
	private final WechatRequestHandler handler;
	private final String url;
	
	private static final Map<String,WechatConfig> wechatConfigs = new HashMap<String,WechatConfig>();
	
	private static final ThreadLocal<WechatConfig> current = new ThreadLocal<WechatConfig>();
	
	static {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_XML_FILE_PATH);
		if(is!=null)
			try {
				SAXReader reader = new SAXReader();
				Document document = reader.read(is);
				@SuppressWarnings("unchecked")
				List<Element> configList = document.selectNodes("/wechat-config/wechat-project");
				for(Element config:configList){
					String name = config.attributeValue("name");
					if(StrUtil.isNOB(name))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":project name can not be null or empty.");
					
					String token = config.elementText("token");
					if(StrUtil.isNOB(token))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":token can not be null or empty.");
					
					String appId = config.elementText("appId");
					if(StrUtil.isNOB(appId))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":appId can not be null or empty.");
					
					String appSecret = config.elementText("appSecret");
					if(StrUtil.isNOB(appSecret))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":appSecret can not be null or empty.");
					
					String encryptMsgStr = config.elementText("encryptMsg");
					boolean encryptMsg = StrUtil.isNOB(encryptMsgStr)?false:(encryptMsgStr.toLowerCase().trim().equals("true")?true:false);
					
					String encodingAesKey = config.elementText("encodingAesKey");
					if(StrUtil.isNOB(encodingAesKey)&&!encryptMsg)
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":encodingAesKey can not be null or empty.");
					
					String handler = config.elementText("handler");
					if(StrUtil.isNOB(handler))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":handler can not be null or empty.");
					
					WechatRequestHandler wechatRequestHandler = null;
					try {
						wechatRequestHandler = (WechatRequestHandler) Class.forName(handler).newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
					if(wechatRequestHandler==null||!(wechatRequestHandler instanceof WechatRequestHandler))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":handler can not be instantialized.");
					
					String url = config.elementText("url");
					if(StrUtil.isNOB(url))
						throw new RuntimeException(CONFIG_XML_FILE_PATH+":url can not be null or empty.");
					
					ApiConfig apiConfig = new ApiConfig(token,appId,appSecret,encryptMsg,encodingAesKey);
					
					WechatConfig wechatConfig = new WechatConfig(name,apiConfig,wechatRequestHandler,url);
					
					System.out.println(wechatConfig);
					
					wechatConfigs.put(url, wechatConfig);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		else
			System.out.println("Config file:\""+CONFIG_XML_FILE_PATH+"\" not found.");
	}
	
	public static final Map<String,WechatConfig> getWechatConfigMap(){
		return wechatConfigs;
	}
	
	public static final WechatConfig forUrl(String url){
		return wechatConfigs.get(url);
	}
	
	public static final WechatConfig forName(String name){
		for(Map.Entry<String, WechatConfig> e:wechatConfigs.entrySet()){
			if(e.getValue().getName().equals(name)){
				return e.getValue();
			}
		}
		return null;
	}
	
	protected static void setCurrent(WechatConfig current) {
		WechatConfig.current.set(current);;
	}
	
	public static WechatConfig getCurrent() {
		return current.get();
	}
	
	protected static void removeCurrent() {
		current.remove();
	}
	
	private WechatConfig(String name,ApiConfig apiConfig,WechatRequestHandler handler,String url){
		this.name = name;
		this.apiConfig = apiConfig;
		this.handler = handler;
		this.url = url;
	}
	
	public final String getName() {
		return name;
	}

	public final ApiConfig getApiConfig() {
		return apiConfig;
	}
	
	public final WechatRequestHandler getHandler() {
		return handler;
	}
	
	public final String getUrl() {
		return url;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("---------------------------WechatConfig-----------------------------\n");
		sb.append("name:").append(name).append("\n")
		  .append("apiConfig:").append(getApiConfig()).append("\n")
		  .append("handler:").append(getHandler().getClass().getName()).append("\n")
		  .append("url:").append(getUrl()).append("\n")
		  .append("---------------------------WechatConfig-----------------------------\n");
		return sb.toString();
	}

	public static void main(String[] args){
		WechatConfig wechatConfig = WechatConfig.forName("wdyx");
		System.out.println(wechatConfig);
	}
}
