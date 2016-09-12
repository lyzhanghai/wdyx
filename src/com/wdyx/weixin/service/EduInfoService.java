package com.wdyx.weixin.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mobangjack.ocr.plugins.LibMgr;
import org.mobangjack.ocr.plugins.OCR;

import com.wdyx.weixin.service.commons.HttpUtil;

/**
 * 教务信息服务，用于获取课表、成绩、考场、以及评教 
 * @author 帮杰
 *
 */

public class EduInfoService {
	
	private static final String HOST = "http://210.42.121.241";
	private static final int MAX_RETRYING_LOGING_TIMES = 40;
	
	private String username = null;
	private String password = null;
	
	private CloseableHttpClient httpclient = null;
	
	public EduInfoService(String username,String password) {
		this.username = username;
		this.password = password;
		this.httpclient = HttpClients.createDefault();
	}
	
    /**
     * 登陆
     */
    public boolean login() {
    	//jxpg_confirmEnterForm.jsp
    	boolean flag = false;
    	int retrying_times = 0;
    	while(!flag&&retrying_times<MAX_RETRYING_LOGING_TIMES){
    		try {
    			retrying_times++;
            	//get and recognize identify code
            	String icode_url = HOST + "/servlet/GenImg";
            	HttpGet httpGet = new HttpGet(icode_url);
    			HttpResponse response = httpclient.execute(httpGet);
    			HttpEntity entity = response.getEntity();
    			//String icode = new OCR().ocr(ImageIO.read(entity.getContent()),EduInfoServiceUtil.getIcodeList());
    			String icode = new OCR().ocr(ImageIO.read(entity.getContent()),new LibMgr().list());
    			System.out.println("icode:"+icode);
    			//post data
            	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        		nvps.add(new BasicNameValuePair("id",username));
        		nvps.add(new BasicNameValuePair("pwd",password));
        		nvps.add(new BasicNameValuePair("xdvfb",icode));
                String post_url = HOST +"/servlet/Login";
                HttpPost post = new HttpPost(post_url);
    			post.setEntity(new UrlEncodedFormEntity(nvps));
    			response = httpclient.execute(post);
    			entity = response.getEntity();
    			String html = EntityUtils.toString(entity);
    			flag = html.contains("验证码错误")?false:true;
    			if(html.contains("用户名/密码错误")){
    				flag = false;
    				break;
    			}
    			EntityUtils.consume(entity);
    		} catch (UnsupportedEncodingException e) {
    			flag = false;
    			e.printStackTrace();
    		}catch (ClientProtocolException e) {
    			flag = false;
    			e.printStackTrace();
    		} catch (IOException e) {
    			flag = false;
    			e.printStackTrace();
    		}
    	}
        
		return flag;
    }  
    
    public Map<String,List<String>> getCourseTable() throws ClientProtocolException, IOException{
    	String url = "http://210.42.121.241/stu/stu_course_parent.jsp";
    	String html = HttpUtil.getHtml(url, httpclient);
    	Document doc = Jsoup.parse(html);
    	url = "http://210.42.121.241" + doc.select("iframe#iframe0").first().attr("src").replace("上", URLEncoder.encode("上","gb2312")).replace("下", URLEncoder.encode("下","gb2312"));
    	html = HttpUtil.getHtml(url, httpclient);
    	doc = Jsoup.parse(html);
    	Elements trs = doc.select("table").select("tr");
    	Elements ths = trs.first().select("th");
    	List<String> thList = new ArrayList<String>();
    	for(Element th : ths){
    		thList.add(th.text());
    	}
    	Map<String,List<String>> courseTable = new LinkedHashMap<String,List<String>>();
    	for(int i=0;i<ths.size();i++){
    		List<String> tdList = new ArrayList<String>();
    		for(int j=1;j<trs.size();j++){
    			tdList.add(trs.get(j).select("td").get(i).text());
    		}
    		courseTable.put(thList.get(i), tdList);
    	}
		return courseTable;
    }
    
    public Map<String,String> getCourseTablePrecisely() throws ClientProtocolException, IOException{
    	Map<String,List<String>> table1 = getCourseTable();
    	Map<String,String> table2 = new LinkedHashMap<String,String>();
    	List<String> courseName = table1.get("课程名");
    	List<String> courseTime = table1.get("上课时间");
    	List<String> weeks = new ArrayList<String>();
    	weeks.add("周一");
    	weeks.add("周二");
    	weeks.add("周三");
    	weeks.add("周四");
    	weeks.add("周五");
    	weeks.add("周六");
    	weeks.add("周日");
    	int size = courseName.size();
    	for(String week : weeks){
    		for(int i=0;i<size;i++){
    			String time = courseTime.get(i);
    			if(time.contains(week)){
    				table2.put(courseName.get(i), time);
    			}
        	}
    	}
    	return table2;
    }
    
    public Map<String,List<String>> getScore() throws ClientProtocolException, IOException{
    	String url = "http://210.42.121.241/servlet/Svlt_QueryStuScore?year=0&term=&learnType=&scoreFlag=0";
    	String html = HttpUtil.getHtml(url, httpclient);
    	Document doc = Jsoup.parse(html);
    	Elements trs = doc.select("table").select("tr");
    	Elements ths = trs.first().select("th");
    	List<String> thList = new ArrayList<String>();
    	for(Element th : ths){
    		thList.add(th.text());
    	}
    	Map<String,List<String>> scoreMap = new LinkedHashMap<String,List<String>>();
    	for(int i=0;i<ths.size();i++){
    		List<String> tdList = new ArrayList<String>();
    		for(int j=1;j<trs.size();j++){
    			tdList.add(trs.get(j).select("td").get(i).text());
    		}
    		scoreMap.put(thList.get(i), tdList);
    	}
		return scoreMap;
    }
    
    public Map<String,String> getScorePrecisely() throws ClientProtocolException, IOException{
    	Map<String,String> PreciseScoreMap = new LinkedHashMap<String,String>();
    	Map<String,List<String>> scoreMap = getScore();
    	List<String> courseName = scoreMap.get("课程名称");
    	List<String> score = scoreMap.get("成绩");
    	for(int i = 0;i<score.size();i++){
    		String tmp = score.get(i);
    		if(tmp.length()>0){
    			PreciseScoreMap.put(courseName.get(i), score.get(i));
    		}else{
    			break;
    		}
    	}
    	return PreciseScoreMap;
    }
    
    /**
     * 得到考场信息
     * @return
     */
    public String getExamSpot(){
		return null;
    	
    }
    
    /**
     * 一键评教
     * @return
     */
    public String oneKeyJudge(){
    	return null;
    }
    
    public Map<String,List<String>> getCredit() throws ClientProtocolException, IOException{
    	String url = "http://210.42.121.241/stu/stu_score_credit_statics.jsp";
    	String html = HttpUtil.getHtml(url, httpclient);
    	Document doc = Jsoup.parse(html);
    	Elements trs = doc.select("table.listTable").select("tr");
    	Elements ths = trs.first().select("th");
    	List<String> thList = new ArrayList<String>();
    	for(Element th : ths){
    		thList.add(th.text());
    	}
    	Map<String,List<String>> creditMap = new LinkedHashMap<String,List<String>>();
    	for(int i=0;i<ths.size();i++){
    		List<String> tdList = new ArrayList<String>();
    		for(int j=1;j<trs.size();j++){
    			tdList.add(trs.get(j).select("td").get(i).text());
    		}
    		creditMap.put(thList.get(i), tdList);
    	}
		return creditMap;
    }
    
    public String getGPA() throws ClientProtocolException, IOException{
    	List<String> tmp = getCredit().get("已修学分");
    	return tmp.get(tmp.size()-1);
    }
    
    public String getWeightedAverage() throws ClientProtocolException, IOException{
    	Map<String,List<String>> scoreMap = getScore();
    	List<String> credit = scoreMap.get("学分");
    	List<String> score = scoreMap.get("成绩");
    	float sum_score = 0f;
    	float sum_credit = 0f;
    	for(int i = 0;;i++){
    		String tmp = score.get(i);
    		if(tmp.length()>0){
    			float tmp_credit = Float.parseFloat(credit.get(i));
    			sum_credit+=tmp_credit;
    			sum_score+=Float.parseFloat(tmp)*tmp_credit;
    		}else{
    			break;
    		}
    	}
    	String average ="" + sum_score/sum_credit;
    	return average;
    }
    
    public String getCourseStr(){
    	StringBuffer buffer = new StringBuffer();
    	try {
			Map<String,String> map = getCourseTablePrecisely();
			Set<String> keySet = map.keySet();
			for(String key : keySet){
				buffer.append(key).append("  ").append(map.get(key)).append("\n\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return buffer.substring(0, buffer.lastIndexOf("\n\n"));
    }
   
    public static float getGPByScore(float score) {
    	float gpa = 0;
		if(score>=90&&score<=100){
			gpa = 4.0f;
		}else if (score>=85&&score<=89) {
			gpa = 3.7f;
		}else if (score>=82&&score<=84) {
			gpa = 3.3f;
		}else if (score>=78&&score<=81) {
			gpa = 3.0f;
		}else if (score>=75&&score<=77) {
			gpa = 2.7f;
		}else if (score>=72&&score<=74) {
			gpa = 2.3f;
		}else if (score>=68&&score<=71) {
			gpa = 2.0f;
		}else if (score>=64&&score<=67) {
			gpa = 1.5f;
		}else if (score>=60&&score<=63) {
			gpa = 1.0f;
		}else{
			gpa = 0.0f;
		}
		return gpa;
	}
    public String getScoreStr(){
    	StringBuffer buffer = new StringBuffer();
    	try {
			Map<String, List<String>> map = getScore();
			List<String> courseName = map.get("课程名称");
			List<String> courseCredit = map.get("学分");
	    	List<String> score = map.get("成绩");
	    	float weightedAver = 0;
	    	float totalCredits = 0;
	    	float gpa = 0;
	    	for(int i = 0;i<score.size();i++){
	    		String tmp = score.get(i);
	    		if(tmp.length()>0){
	    			//credit
	    			float credits = Float.parseFloat(courseCredit.get(i));
	    			//score
	    			float scores = Float.parseFloat(score.get(i));
	    			//grade point
	    			float gp = getGPByScore(scores);
	    			weightedAver+=credits*scores;
	    			totalCredits+=credits;
	    			gpa+=credits*gp;
	    			buffer.append(courseName.get(i)).append("  ").append(score.get(i)).append("\n");
	    		}else{
	    			break;
	    		}
	    	}
	    	weightedAver/=totalCredits;
	    	gpa/=totalCredits;
			
	    	buffer.append("已修学分").append("  ").append(totalCredits).append("\n");
			buffer.append("加权平均").append("  ").append(weightedAver).append("\n");
			buffer.append("GPA").append("  ").append(gpa).append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return buffer.substring(0, buffer.lastIndexOf("\n"));
    }
    //测试
  	public static void main(String[] args) {
  		
  		String username = "2013301200227";
  		String password = "173514";
  		EduInfoService edu = new EduInfoService(username,password);
  		if(edu.login()){
  			System.out.println("---------------------------------课程表------------------------------------");
  	  		System.out.println(edu.getCourseStr());
  	  		System.out.println("---------------------------------成绩表------------------------------------");
  	  		System.out.println(edu.getScoreStr());
  		}
  	}

}
