项目说明：微信公众平台急速开发框架

使用方法：

1、创建一个处理器类。方法很简单，继承org.mobangjack.wechat.core.WechatRequestHandlerImpl或org.mobangjack.wechat.core.WechatRequestHandler，复写以下方法即可：
	protected abstract Object handleEventMsg(EventMsg eventMsg);
	protected abstract Object handleImageMsg(ImageMsg imageMsg);
	protected abstract Object handleLinkMsg(LinkMsg linkMsg);
	protected abstract Object handleLocationMsg(LocationMsg locationMsg);
	protected abstract Object handleTextMsg(TextMsg textMsg);
	protected abstract Object handleVideoMsg(VideoMsg videoMsg);
	protected abstract Object handleVoiceMsg(VoiceMsg voiceMsg);
	protected abstract Object onSubscribe(EventMsg eventMsg);
	protected abstract Object onUnsubscribe(EventMsg eventMsg);
（注：org.mobangjack.wechat.core.WechatRequestHandlerImpl与org.mobangjack.wechat.core.WechatRequestHandler的区别：前者直接继承后者，后者是一抽象类，如果继承前者就不用复写以上全部方法。）

2、同样的方法，继承org.mobangjack.wechat.core.ConfigMgr，复写其中的public abstract void configMap(WechatConfigMap wechatConfigMap)方法，配置API和处理器，并映射到url。支持多个公众号。

配置样例：
public class ConfigMaster extends ConfigMgr {

	/**
	 * 多公众号配置管理
	 */
	@Override
	public void configMap(WechatConfigMap map) {
		/**************************公众号1************************/
		//配置API
		ApiConfig apiConfig1 = new ApiConfig()
			.setToken("WX_TOKEN")//token
			.setAppId("WX_APPID")//appid
			.setAppSecret("WX_APPSECRET")//appsecret
			.setEncodingAesKey("WX_ENCODING_AES_KEY")//加密密匙
			.setMsgEncrypted(false);//消息是否加密
		//配置处理器
		WeChatMsgHandler handler1 = new WeChatMsgHandler1();
		//生成配置
		WechatConfig wechatConfig1 = new WechatConfig(apiConfig1,handler1);
		//路由映射
		map.add("/wechat/xxx1", wechatConfig);
		/**************************公众号1************************/
		
		/**************************公众号2************************/
		//配置API
		ApiConfig apiConfig2 = new ApiConfig()
			.setToken("WX_TOKEN")//token
			.setAppId("WX_APPID")//appid
			.setAppSecret("WX_APPSECRET")//appsecret
			.setEncodingAesKey("WX_ENCODING_AES_KEY")//加密密匙
			.setMsgEncrypted(false);//消息是否加密
		//配置处理器
		WeChatMsgHandler handler2 = new WeChatMsgHandler2();
		//生成配置
		WechatConfig wechatConfig2 = new WechatConfig(apiConfig2,handler2);
		//路由映射
		map.add("/wechat/xxx2", wechatConfig2);
		/**************************公众号2************************/
		
	}

}

3、配置web.xml。
 <filter>
    <filter-name>WechatFilter</filter-name>
    <filter-class>org.mobangjack.wechat.core.WechatFilter</filter-class>
    <init-param>
      <param-name>WechatConfigMgrClass</param-name>
      <param-value>[第一步所创建的处理器的位置，如org.mobangjack.wechat.WechatConfigMgr]</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>WechatFilter</filter-name>
    <url-pattern>[url映射，如欲配置多个API、多个处理器、多个公众号，则可以这样配置：/wechat/*，相应的，路由映射应该这样填：/wechat/xxxx]</url-pattern>
  </filter-mapping>

4、登陆微信开放平台，点击开发者服务，点击自定义配置，填写URL为http://yourdomain/wechat/xxx，token为xxx公众号的ApiConfig中的token。

author:mobangjack
Email:mobangjack@foxmail.com
CSDN：http://blog.csdn.net/momojie12345


智慧凝结汗水，灵感来之不易，请尊重作者原创。