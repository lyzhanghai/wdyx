package com.wdyx.weixin.service;

import java.util.List;

import com.wdyx.weixin.sniffer.CurseSniffer;

/**
 * Let me play with you.
 * @author 帮杰
 *
 */
public class SimsimServiceUtil {

	public static String filteredResponse(String q) {
		List<String> curseList = CurseSniffer.getCurseList();
		String response = "";
		for(int i=0;i<10;i++){
			boolean isCurse = false;
			response = SimsimService.response(q);
			for(String s:curseList){
				if (response.contains(s)) {
					isCurse = true;
					break;
				}
			}
			if(!isCurse)
				return response;
		}
		return response;
	}

	public static void main(String[] args) {
		String q = "我爱你";
		for(int i=0;i<20;i++){
			q = filteredResponse(q);
			System.out.println(q);
		}
	}
}
