package com.wdyx.weixin.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.wdyx.weixin.service.commons.HttpUtil;

public class SimsimService {
	private static final String API = "http://api.mrtimo.com/Simsimi.ashx?parm=PARM";
	//private static final String API = "http://www.xiaodoubi.com/bot/api.php?chat=PARM";
	
	public static final String[] curses = {"我操","卧槽","我草","尼玛","你妈","你妹","屎",
		"狗日","shit","射","死","加微信","爽","日","逼","sharejokes","插死","骚","http","html","www","href","开房","水水"};
	
	public static String response(String parm) {
		try {
			parm = URLEncoder.encode(parm, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String response = "";
		for(int i=0;i<10;i++){
			response = HttpUtil.httpRequest(API.replace("PARM", parm), "utf-8");
			for(String curse:curses){
				if(!response.contains(curse))
					return response;
			}
		}
		return response;
	}

	public static void main(String[] args) {
		String parm = "我爱你";
		for(int i=0;i<20;i++){
			System.out.println(parm=response(parm));
		}
	}
}
