package com.wdyx.weixin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.wdyx.weixin.service.commons.HttpUtil;

/**
 * 武汉大学信息门户服务
 * 功能：
 * 1、校园卡余额、状态查询服务
 * 2、信息交流
 * @author 帮杰
 *
 */
public class InfoPortalService {
	
	private String username = null;
	private String password = null;
	
	private Map<String,String> BasicUserInfoMap = null;
	
	private CloseableHttpClient httpclient = null;
	
	public InfoPortalService(String username,String password){
		this.username = username;
		this.password = password;
		this.httpclient = HttpClients.createDefault();
	}
	
	/**
	 * 登录
	 * @return
	 */
	public boolean login(){
		boolean flag = true;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("Login.Token1",username));
			nvps.add(new BasicNameValuePair("Login.Token2",password));
			nvps.add(new BasicNameValuePair("goto","http://my.whu.edu.cn/loginSuccess.portal"));
			nvps.add(new BasicNameValuePair("gotoOnFail","http://my.whu.edu.cn/loginFailure.portal"));
			HttpPost post = new HttpPost("http://my.whu.edu.cn/userPasswordValidate.portal");
			post.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			flag = EntityUtils.toString(entity).contains("用户不存在或密码错误")?false:true;
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public String getShortMsg() {
		String url = "http://my.whu.edu.cn/pnull.portal?.pen=pe169&.ia=false&action=informationCenterAjax&.pmn=view&.f=f1298";
		String msg = "";
		try {
			msg = HttpUtil.getHtml(url, "UTF-8", httpclient);
			//System.out.println(msg);
		} catch (ClientProtocolException e) {
			System.out.println("Error : getShortMsg");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error : getShortMsg");
			e.printStackTrace();
		}
		try {
			JSONArray json = JSONArray.fromObject(msg);
			msg = json.getJSONObject(1).getString("description").replace("<span>", ":").replace("</span>", "");
		} catch (Exception e) {
			System.out.println("Error : getShortMsg");
			e.printStackTrace();
			msg = "";
		}
		return msg;
	}
	/**
	 * 得到用户基本信息页
	 * @return
	 */
	private String getBasicUserInfoHtml(){
		String html = null;
		if(login())
			try {
				HttpUtil.getHtml("http://ykt.whu.edu.cn:8000/whuportalHome.action", "UTF-8", httpclient);
				html = HttpUtil.getHtml("http://ykt.whu.edu.cn:8000/accountcardUser.action", "UTF-8", httpclient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return html;
	}
	/**
	 * 得到用户基本信息Map
	 * @return
	 */
	private void getBasicUserInfoMap(){
		String html = getBasicUserInfoHtml();
		if(html!=null){
			Document doc = Jsoup.parse(html);
			Elements tds = doc.select("td.neiwen");
			BasicUserInfoMap = new LinkedHashMap<String,String>();
			int size = tds.size();
			String space = Jsoup.parse("&nbsp;").text();
			for(int i=0;i<size-1;i+=2){
				String tmp_key = tds.get(i).text().replace(space, " ");
				String tmp_val = tds.get(i+1).text().replace(space, " ");
				if(tmp_key.length()>0)
					BasicUserInfoMap.put(tmp_key, tmp_val);
				else{
					i--;
				}
			}
		}
	}
	/**
	 * 得到姓名
	 * @return
	 */
	public String getUserName(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("姓    名：");
	}
	/**
	 * 得到性别
	 * @return
	 */
	public String getGender(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("性    别：");
	}
	/**
	 * 得到国籍
	 * @return
	 */
	public String getNationality(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("国    籍：");
	}
	/**
	 * 得到民族
	 * @return
	 */
	public String getNation(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("民    族：");
	}
	/**
	 * 得到籍贯
	 * @return
	 */
	public String getNativePlace(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("籍    贯：");
	}
	/**
	 * 得到政治面貌
	 * @return
	 */
	public String getPoliticsStatus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("政治面貌：");
	}
	/**
	 * 得到所属部门
	 * @return
	 */
	public String getDepartment(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("所属部门：");
	}
	/**
	 * 得到所属校区
	 * @return
	 */
	public String getCampus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("所属校区：");
	}
	/**
	 * 得到校内地址
	 * @return
	 */
	public String getAddress(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("校内地址：");
	}
	/**
	 * 得到电子邮箱
	 * @return
	 */
	public String getEmail(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("电子邮箱：");
	}
	/**
	 * 得到余额
	 * @return
	 */
	public String getBalance(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("余    额：");
	}
	/**
	 * 得到账号
	 * @return
	 */
	public String getECardNumber(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("帐    号：");
	}
	/**
	 * 得到学工号
	 * @return
	 */
	public String getUserNumber(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("学 工 号：");
	}
	/**
	 * 得到身份类型(普通本科生or其他?)
	 * @return
	 */
	public String getStatus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("身份类型：");
	}
	/**
	 * 得到证件类型(居民身份证or其他?)
	 * @return
	 */
	public String getIDType(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("证件类型：");
	}
	/**
	 * 得到证件号码
	 * @return
	 */
	public String getIDNumber(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("证件号码：");
	}
	/**
	 * 得到出生年月
	 * @return
	 */
	public String getBirthday(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("出生年月：");
	}
	/**
	 * 得到卡 状 态
	 * @return
	 */
	public String getECardStatus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("卡 状 态：");
	}
	/**
	 * 得到检查状态
	 * @return
	 */
	public String getCheckStatus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("检查状态：");
	}
	/**
	 * 得到冻结状态
	 * @return
	 */
	public String getFreezeStatus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("冻结状态：");
	}
	/**
	 * 得到挂失状态
	 * @return
	 */
	public String getLossReportingStatus(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("挂失状态：");
	}
	/**
	 * 得到进校日期
	 * @return
	 */
	public String getEnteryDay(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("进校日期：");
	}
	/**
	 * 得到邮    编
	 * @return
	 */
	public String getPostcode(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("邮    编：");
	}
	/**
	 * 得到电    话
	 * @return
	 */
	public String getPhoneNumber(){
		if(BasicUserInfoMap==null)getBasicUserInfoMap();
		return BasicUserInfoMap.get("电    话：");
	}
	/**
	 * 得到用户头像，以学号命名
	 * 路径：WebContent/images/service/ips/user_portrait/filename.jpg
	 */
	public boolean getUserPortrait(){
		boolean flag = true;
		try {
			String folderName = "WebContent/images/service/ips/user_portrait/";
			File file =new File(folderName);    
			if  (!file .exists()  && !file .isDirectory())          
			    file .mkdirs();    
			String fileName = getUserNumber()+".jpg";
			String url = "http://ykt.whu.edu.cn:8000/getPhoto.action";
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity = response.getEntity();
			FileOutputStream fos = new FileOutputStream(folderName+fileName);
			entity.writeTo(fos);
			fos.close();
			EntityUtils.consume(entity);
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args){
		String username = "2013301200227";
		String password = "******";
		InfoPortalService IPS = new InfoPortalService(username,password);
		System.out.println(IPS.login());
		System.out.println(IPS.getShortMsg());
		/*
		System.out.println(IPS.getUserName());
		System.out.println(IPS.getAddress());
		System.out.println(IPS.getBalance());
		System.out.println(IPS.getBirthday());
		System.out.println(IPS.getCampus());
		System.out.println(IPS.getCheckStatus());
		System.out.println(IPS.getDepartment());
		System.out.println(IPS.getECardNumber());
		System.out.println(IPS.getECardStatus());
		System.out.println(IPS.getEmail());
		System.out.println(IPS.getEnteryDay());
		System.out.println(IPS.getFreezeStatus());
		System.out.println(IPS.getGender());
		System.out.println(IPS.getIDNumber());
		System.out.println(IPS.getIDType());
		System.out.println(IPS.getLossReportingStatus());
		System.out.println(IPS.getNation());
		System.out.println(IPS.getNationality());
		System.out.println(IPS.getNativePlace());
		System.out.println(IPS.getPhoneNumber());
		System.out.println(IPS.getPoliticsStatus());
		System.out.println(IPS.getPostcode());
		System.out.println(IPS.getStatus());
		//System.out.println(IPS.getUserPortrait());
		 * *
		 */
	}
}
