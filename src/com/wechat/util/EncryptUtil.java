package com.wechat.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;


/**
 * message encrypt tool
 * @author 帮杰
 *
 */
public class EncryptUtil {
	
	public static String encrypt(String token,String encodingAesKey,String appId,String msg, String timestamp, String nonce) {
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			return pc.encryptMsg(msg, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String decrypt(String token,String encodingAesKey,String appId,String encryptedMsg, String timestamp, String nonce) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(encryptedMsg);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			
			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encryptor");
			NodeList nodelist2 = root.getElementsByTagName("MsgSignature");
			
			String encrypt = nodelist1.item(0).getTextContent();
			String msgSignature = nodelist2.item(0).getTextContent();
			
			String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encryptor><![CDATA[%1$s]]></Encryptor></xml>";
			String fromXML = String.format(format, encrypt);
			
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			return pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}



