package com.wdyx.weixin.menu;

import org.mobangjack.wechat.api.AccessToken.AccessTokenMgr;
import org.mobangjack.wechat.api.menu.MenuApi;


public class Menu {

	public static final String MENU = "{\"button\":["
			+ "{\"name\":\"武大\",\"sub_button\":["
			+ "{\"key\":\"11\",\"name\":\"查询成绩\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"12\",\"name\":\"查询课表\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"13\",\"name\":\"查询借阅\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"14\",\"name\":\"一键续借\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"15\",\"name\":\"查询余额\",\"type\":\"click\",\"url\":\"\"}]},"
			+ "{\"name\":\"艺协\",\"sub_button\":["
			+ "{\"key\":\"21\",\"name\":\"部门简介\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"22\",\"name\":\"造星舞台\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"23\",\"name\":\"男神女神\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"24\",\"name\":\"最新活动\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"25\",\"name\":\"招新信息\",\"type\":\"click\",\"url\":\"\"}]},"
			+ "{\"name\":\"么么哒\",\"sub_button\":["
			+ "{\"key\":\"31\",\"name\":\"梅操电影\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"32\",\"name\":\"过去现在\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"33\",\"name\":\"音乐时光\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"34\",\"name\":\"周末文苑\",\"type\":\"click\",\"url\":\"\"},"
			+ "{\"key\":\"35\",\"name\":\"艺协树洞\",\"type\":\"click\",\"url\":\"\"}]}]}";
	
	public Menu(){
		MenuApi.createMenu(AccessTokenMgr.getAccessToken("wdyx").toString(), MENU);
	}
	
	public static void main(String[] args)
	{
		new Menu();
	}

}
