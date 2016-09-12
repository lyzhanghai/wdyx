package org.mobangjack.wechat.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mobangjack.wechat.api.config.ThreadLocalApiConfig;
import org.mobangjack.wechat.message.request.EventMsg;
import org.mobangjack.wechat.message.request.ImageMsg;
import org.mobangjack.wechat.message.request.LinkMsg;
import org.mobangjack.wechat.message.request.LocationMsg;
import org.mobangjack.wechat.message.request.TextMsg;
import org.mobangjack.wechat.message.request.VideoMsg;
import org.mobangjack.wechat.message.request.VoiceMsg;
import org.mobangjack.wechat.util.EncryptUtil;
import org.mobangjack.wechat.util.XmlUtil;

/**
 * 昨夜西风凋碧树，独上高楼，望断天涯路
 * 衣带渐宽终不悔，为伊消得人憔悴
 * 众里寻他千百度，蓦然回首，那人却在，灯火阑珊处
 * @author 帮杰
 *
 */
public abstract class WechatRequestHandler {

	public static final String REQUEST_MSG_TEXT = "text";
	public static final String REQUEST_MSG_LINK = "link";
	public static final String REQUEST_MSG_LOCATION = "location";
	public static final String REQUEST_MSG_IMAGE = "image";
	public static final String REQUEST_MSG_VOICE = "voice";
	public static final String REQUEST_MSG_VIDEO = "video";
	public static final String REQUEST_MSG_EVENT = "event";
	
	protected HttpServletRequest request = null;
	protected String requestPath = "/wechat";
	
	public WechatRequestHandler() {
		
	}
	
	public final String handle(HttpServletRequest request){
		this.request = request;
		String xml = "";
		Map<String, String> xmlMap = null;
		String timestamp = null;
		String nonce = null;
		try {
			xml = XmlUtil.parseXml(request.getInputStream());
			if(ThreadLocalApiConfig.get().getEncryptMsg()){
				timestamp = request.getParameter("timestamp");
				nonce = request.getParameter("nonce");
				xml = EncryptUtil.decrypt(ThreadLocalApiConfig.get().getToken(), ThreadLocalApiConfig.get().getEncodingAesKey(), ThreadLocalApiConfig.get().getAppId(), xml, timestamp, nonce);
			}
			xmlMap = XmlUtil.XmlToMap(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object responseMsg = null;
		String responseXml = null;
		String msgType = xmlMap.get("MsgType");
		if(msgType.equals(REQUEST_MSG_EVENT)) {
			EventMsg eventMsg = new EventMsg();
			eventMsg.parseRequestXML(xmlMap);
			if(eventMsg.getEvent().equals("subscribe")){
				responseMsg = onSubscribe(eventMsg);
			}else if(eventMsg.getEvent().equals("unsubscribe")) {
				responseMsg = onUnsubscribe(eventMsg);
			}else {
				responseMsg = handleEventMsg(eventMsg);
			}
		}else if(msgType.equals(REQUEST_MSG_TEXT)) {
			TextMsg textMsg = new TextMsg();
			textMsg.parseRequestXML(xmlMap);
			responseMsg = handleTextMsg(textMsg);
		}else if(msgType.equals(REQUEST_MSG_LINK)) {
			LinkMsg linkMsg = new LinkMsg();
			linkMsg.parseRequestXML(xmlMap);
			responseMsg = handleLinkMsg(linkMsg);
		}else if(msgType.equals(REQUEST_MSG_LOCATION)) {
			LocationMsg locationMsg = new LocationMsg();
			locationMsg.parseRequestXML(xmlMap);
			responseMsg = handleLocationMsg(locationMsg);
		}else if(msgType.equals(REQUEST_MSG_IMAGE)) {
			ImageMsg imageMsg = new ImageMsg();
			imageMsg.parseRequestXML(xmlMap);
			responseMsg = handleImageMsg(imageMsg);
		}else if(msgType.equals(REQUEST_MSG_VOICE)) {
			VoiceMsg voiceMsg = new VoiceMsg();
			voiceMsg.parseRequestXML(xmlMap);
			responseMsg = handleVoiceMsg(voiceMsg);
		}else if(msgType.equals(REQUEST_MSG_VIDEO)) {
			VideoMsg videoMsg = new VideoMsg();
			videoMsg.parseRequestXML(xmlMap);
			responseMsg = handleVideoMsg(videoMsg);
		}else {
			System.err.println("Unrecognized msg type:"+xml);
		}
		responseXml = responseMsg==null?"":XmlUtil.toXml(responseMsg);
		if(ThreadLocalApiConfig.get().getEncryptMsg()){
			responseXml = EncryptUtil.encrypt(ThreadLocalApiConfig.get().getToken(), ThreadLocalApiConfig.get().getEncodingAesKey(), ThreadLocalApiConfig.get().getAppId(), responseXml, timestamp, nonce);
		}
		return responseXml;
		
	}
	
	protected abstract Object handleEventMsg(EventMsg eventMsg);
	protected abstract Object handleImageMsg(ImageMsg imageMsg);
	protected abstract Object handleLinkMsg(LinkMsg linkMsg);
	protected abstract Object handleLocationMsg(LocationMsg locationMsg);
	protected abstract Object handleTextMsg(TextMsg textMsg);
	protected abstract Object handleVideoMsg(VideoMsg videoMsg);
	protected abstract Object handleVoiceMsg(VoiceMsg voiceMsg);
	protected abstract Object onSubscribe(EventMsg eventMsg);
	protected abstract Object onUnsubscribe(EventMsg eventMsg);

	public HttpServletRequest getRequest() {
		return request;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
}
