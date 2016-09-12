package com.wdyx.weixin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wdyx.weixin.service.commons.HtmlUtil;
import com.wdyx.weixin.service.commons.HttpUtil;

/**
 * 数字图书馆服务
 * 功能：查询个人信息、借阅信息、借阅历史，以及进行续借操作
 * @author 帮杰
 *
 */

public class DigitalLibraryService {
	
	private static final String HOST = "http://metalib.lib.whu.edu.cn";
	
	private String mainHtml = null;
	private String username = null;
	private String password = null;
	
	private CloseableHttpClient httpclient = null;
	
	public DigitalLibraryService(String username,String password) {
		this.username = username;
		this.password = password;
		this.httpclient = HttpClients.createDefault();
	}

    /**
     * 登录  
     * @return 
     */
    public boolean login() {
    	boolean flag = false;
    	try{ 
    		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    		nvps.add(new BasicNameValuePair("bor_id",username));
    		nvps.add(new BasicNameValuePair("bor_verification",password));
    		nvps.add(new BasicNameValuePair("func","login"));
    		nvps.add(new BasicNameValuePair("calling_system","idp_proxy"));
    		nvps.add(new BasicNameValuePair("term1","short"));
    		nvps.add(new BasicNameValuePair("selfreg",""));
    		nvps.add(new BasicNameValuePair("institute","WHU"));
    		nvps.add(new BasicNameValuePair("url","http://apps.lib.whu.edu.cn/idp_proxy/bor_auth_agent.asp?"
    				+ "goto=http%3A%2F%2Fwww.lib.whu.edu.cn%2Fweb%2Fdefault.asp&"
    				+ "sp=http%3A%2F%2Fwww.hub.calis.edu.cn%3A8090%2Famconsole%"
    				+ "2FAuthServices%2F242010%3Fverb%3Dsplogin&idp=242010"));
            HttpPost post = new HttpPost("http://metalib.lib.whu.edu.cn:80/pds");
            post.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            mainHtml = EntityUtils.toString(entity,"UTF-8");
            flag = mainHtml.contains("relocate")?true:false;
            if(flag){
            	String mainUrl = HOST + HtmlUtil.getLinks(mainHtml).get(0);
                mainHtml = HttpUtil.getHtml(mainUrl,"GB2312",httpclient);
            }
            EntityUtils.consume(entity);
    	}catch(ClientProtocolException e){
    		flag = false;
    		e.printStackTrace();
    	}catch(IOException e){
    		flag = false;
    		e.printStackTrace();
    	}
        return flag;  
    }  

    /**
     * 得到"我的借阅信息"页
     * @return "我的借阅信息"页HTML(String)
     */
	private String getBorrowerInfoHtml(){
		String BorrowerInfoHtml = null;
		try {
			Document doc = Jsoup.parse(mainHtml);
			String BorrowerInfoUrl = doc.getElementsContainingOwnText("我的借阅信息").first().attr("href");
			String RelocatedBorrowerInfoUrl = HttpUtil.getHtml(BorrowerInfoUrl,"UTF-8",httpclient).split("\'")[1];
			String RelocatedBorrowerInfoHtml = HttpUtil.getHtml(RelocatedBorrowerInfoUrl,"UTF-8",httpclient);
			BorrowerInfoHtml = HttpUtil.getHtml(HOST + HtmlUtil.getLinks(RelocatedBorrowerInfoHtml).get(0),"UTF-8",httpclient);
			String space = Jsoup.parse("&nbsp;").text();
			BorrowerInfoHtml = BorrowerInfoHtml.replace(space, " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return BorrowerInfoHtml;
	}
	/**
     * 得到"借阅信息"页
     * @return "借阅信息"页HTML(String)
     */
	private String getBorrowInfoHtml(){
		String BorrowInfoHtml = null;
		try {
			Document doc = Jsoup.parse(getBorrowerInfoHtml());
			String BorrowInfoUrl = doc.getElementsContainingOwnText("当前借阅数：").parents().select("a").attr("href").split("\'")[1];
			String space = Jsoup.parse("&nbsp;").text();
			BorrowInfoHtml = HttpUtil.getHtml(BorrowInfoUrl,"UTF-8", httpclient).replace(space, " ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return BorrowInfoHtml;
	}
	/**
     * 得到"我的借阅信息"Map
     * @return "我的借阅信息"Map(Map<String,String>)
     */
	public Map<String,String> getBorrowerInfoMap(){
		Map<String,String> map = new LinkedHashMap<String,String>();
		try{
			List<String> key = new ArrayList<String>();
			List<String> val = new ArrayList<String>();
			Document doc = Jsoup.parse(getBorrowerInfoHtml());
			Elements elements = doc.select("table").select("tr");
			Elements col1 = elements.select("td.td4");
			Elements col2 = elements.select("td.td1");
			for(Element tmp : col1){
				key.add(tmp.text());
			}
			for(Element tmp : col2){
				val.add(tmp.text());
			}
			for(int i=0;i<key.size();i++){
				if(i!=3){
					String key_tmp = key.get(i);
					String val_tmp = val.get(i);
					if(key_tmp.length()>0&&val_tmp.length()>0)
						map.put(key_tmp, val_tmp);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	/**
     * 得到"我的借阅信息"String
     * @return "我的借阅信息"String(String)
     */
	public String getBorrowerInfoStr(){
		StringBuffer buffer = new StringBuffer();
		try{
			Map<String,String> BorrowerInfoMap = getBorrowerInfoMap();
			Set<String> keySet = BorrowerInfoMap.keySet();
			buffer.append("------您的个人信息------\n");
			for(String key : keySet){
				if(key.trim().equals("")||BorrowerInfoMap.get(key).trim().equals(""))continue;
				buffer.append(key).append(BorrowerInfoMap.get(key)).append("\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return (null == buffer) ? null : buffer.toString();
	}
	/**
     * 将数据分组，每组fieldCounts个
     * @param ObjList
     * @return List<List<String>>
     */
    private static List<List<String>> group(List<String> val,int fieldCounts){
    	if(fieldCounts<1){
    		return null;
    	}else {
    		List<List<String>> groupList = new ArrayList<List<String>>();
        	int recordCounts = val.size()/fieldCounts;
        	for(int i=0;i<fieldCounts;i++){
        		List<String> recordList = new ArrayList<String>();
        		for(int j=0;j<recordCounts;j++){
        			recordList.add(val.get(i+j*fieldCounts));
        		}
        		groupList.add(recordList);
        	}
    		return groupList;
		}
    	
    }
	/**
	 * 得到"借阅信息"Map
	 * @return "借阅信息"Map(Map<String,List<String>>)
	 */
	public Map<String,List<String>> getBorrowInfoMap(){
		Map<String,List<String>> map = null;
		try{
			Document doc = Jsoup.parse(getBorrowInfoHtml());
			Elements th = doc.select("th.text3");
			Elements td = doc.select("td.td1");
			List<String> key = new ArrayList<String>();
			List<String> val = new ArrayList<String>();
			for(Element tmp : th){
				key.add(tmp.text());
			}
			for(Element tmp : td){
				val.add(tmp.text());
			}
			int count = key.size();
			List<List<String>> group = group(val,count);
			if(group!=null){
				map = new LinkedHashMap<String,List<String>>();
				for(int i=0;i<count;i++){
					if(i!=1)
						map.put(key.get(i),group.get(i));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 得到"借阅信息"String
	 * @return "借阅信息"String(String)
	 */
	public String getBorrowInfoStr(){
		StringBuffer buffer = new StringBuffer();
		Map<String,List<String>> BorrowInfoMap = getBorrowInfoMap();
		if(BorrowInfoMap==null){
			buffer.append("您当前没有借书记录哦~\n")
				  .append("/衰武大汗牛充栋，你还没找到自己喜欢的那一本吗？");
			return buffer.toString();
		}else {
			try{
				Set<String> keySet = BorrowInfoMap.keySet();
				buffer.append("------您的借阅信息------\n");
				int itemCounts = BorrowInfoMap.get("题名").size();
				for(int i=0;i<itemCounts;i++){
					for(String key : keySet){
						if(key.trim().equals("")||BorrowInfoMap.get(key).get(i).trim().equals(""))continue;
						buffer.append(key).append("  ").append(BorrowInfoMap.get(key).get(i)).append("\n");
					}
					buffer.append("-------------------------\n");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String space = Jsoup.parse("&nbsp;").text();
			return (buffer == null)?null:buffer.substring(0, buffer.lastIndexOf("-------------------------\n")).replace(space, " ");
		}
	}
	/**
	 * 得到"借阅历史"Map
	 * @return "借阅历史"Map(Map<String,List<String>>)
	 */
	public Map<String,List<String>> getBorrowHistoryMap(){
		Map<String,List<String>> map = null;
		try{
			Document doc = Jsoup.parse(getBorrowerInfoHtml());
			String BorrowHistoryUrl = doc.getElementsContainingOwnText("借阅历史").attr("href");
			String BorrowHistoryHtml = HttpUtil.getHtml(BorrowHistoryUrl,"UTF-8", httpclient);
			doc = Jsoup.parse(BorrowHistoryHtml);
			Elements th = doc.select("th.text3");
			Elements td = doc.select("td.td1");
			List<String> key = new ArrayList<String>();
			List<String> val = new ArrayList<String>();
			for(Element tmp : th){
				key.add(tmp.text());
			}
			for(Element tmp : td){
				val.add(tmp.text());
			}
			val.remove(0);
			int count = key.size();
			List<List<String>> group = group(val,count);
			if(group!=null){
				map = new LinkedHashMap<String,List<String>>();
				for(int i=0;i<count;i++){
					map.put(key.get(i),group.get(i));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 得到"借阅历史"String
	 * @return "借阅历史"String
	 */
	public String getBorrowHistoryStr(){
		StringBuffer buffer = new StringBuffer();
		Map<String,List<String>> BorrowHistoryMap = getBorrowHistoryMap();
		if(BorrowHistoryMap==null){
			buffer.append("您当前没有借书记录哦~\n")
			  .append("/衰武大汗牛充栋，你还没找到自己喜欢的那一本吗？");
			return buffer.toString();
		}else {
			try{
				
				Set<String> keySet = BorrowHistoryMap.keySet();
				buffer.append("------您的借阅历史------\n");
				int itemCounts = BorrowHistoryMap.get("题名").size();
				for(int i=0;i<itemCounts;i++){
					for(String key : keySet){
						if(key.trim().equals("")||BorrowHistoryMap.get(key).get(i).trim().equals(""))continue;
						buffer.append(key).append("  ").append(BorrowHistoryMap.get(key).get(i)).append("\n");
					}
					buffer.append("-------------------------\n");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String space = Jsoup.parse("&nbsp;").text();
			return (buffer == null)?null:buffer.substring(0, buffer.lastIndexOf("-------------------------\n")).replace(space, " ");
		}
		
	}
	/**
	 * 续借，抽取续借结果，返回Map<String,List<String>>
	 * @return "续借结果"Map(Map<String,List<String>>)
	 */
	public Map<String,List<String>> renewBookReturnMap(){
		if(getBorrowHistoryMap()==null){
			return null;
		}else {
			Map<String,List<String>> map = new LinkedHashMap<String,List<String>>();
			try{
				Document doc = Jsoup.parse(getBorrowInfoHtml());
				String url = doc.getElementsContainingOwnText("全部续借").attr("href").split("\'")[1];
				String resultPage = HttpUtil.getHtml(url,"UTF-8",httpclient);
				doc = Jsoup.parse(resultPage);
				Elements th = doc.select("tr.tr1").select("th");
				Elements td = doc.select("td.td1");
				List<String> key = new ArrayList<String>();
				List<String> val = new ArrayList<String>();
				for(Element tmp : th){
					key.add(tmp.text());
				}
				for(Element tmp : td){
					val.add(tmp.text());
				}
				int count = key.size();
				List<List<String>> group = group(val,count);
				for(int i=0;i<count;i++){
					map.put(key.get(i),group.get(i));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return map;
		}
	}
	/**
	 * 续借，抽取续借结果，返回String
	 * @return "续借结果"String
	 */
	public String renewBookReturnStr(){
		StringBuffer buffer = new StringBuffer();
		Map<String,List<String>> BorrowInfoMap = getBorrowInfoMap();
		if(BorrowInfoMap==null){
			buffer.append("您当前没有借书记录哦~\n")
			  .append("/衰武大汗牛充栋，你还没找到自己喜欢的那一本吗？");
			return buffer.toString();
		}else {
			Map<String,List<String>> renewBookReturnMap = renewBookReturnMap();
			try{
				Set<String> keySet = renewBookReturnMap.keySet();
				buffer.append("------您的续借结果------\n");
				int itemCounts = renewBookReturnMap.get("序号").size();
				for(int i=0;i<itemCounts;i++){
					for(String key : keySet){
						String val = renewBookReturnMap.get(key).get(i).trim();
						if(val.equals("")||key.trim().equals("单册状态")||key.trim().equals("应还日期")||key.trim().equals("应还时间")||key.trim().equals("分馆")||key.trim().equals("条码"))continue;
						buffer.append(key.trim().equals("未能续借的原因")?"未能续借":key).append("  ").append(val).append("\n");
					}
					buffer.append("-------------------------\n");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			String space = Jsoup.parse("&nbsp;").text();
			return (buffer == null)?null:buffer.substring(0, buffer.lastIndexOf("-------------------------\n")).replace(space, " ");
		}
	}
	
	
	//测试
	public static void main(String[] args) {
		//String username = "2013301200225";
		//String password = "282513";
		String username = "2013301200227";
		String password = "******";
		DigitalLibraryService myDigitalLibraryService = new DigitalLibraryService(username,password);
		if(myDigitalLibraryService.login()){
			//System.out.println("---------------------------------个人信息----------------------------------");
			System.out.println(myDigitalLibraryService.getBorrowerInfoStr());
			//System.out.println(myDigitalLibraryService.getBorrowerInfoMap());
			//System.out.println("---------------------------------借阅信息----------------------------------");
			System.out.println(myDigitalLibraryService.getBorrowInfoStr());
			//System.out.println(myDigitalLibraryService.getBorrowInfoMap());
			//System.out.println("---------------------------------借阅历史----------------------------------");
			System.out.println(myDigitalLibraryService.getBorrowHistoryStr());
			//System.out.println(myDigitalLibraryService.getBorrowHistoryMap());
			//System.out.println("---------------------------------续借结果----------------------------------");
			System.out.println(myDigitalLibraryService.renewBookReturnStr());
			//System.out.println(myDigitalLibraryService.renewBookReturnMap());
		}else{
			System.out.println("登录失败！请检查用户名或密码是否正确");
		}
	}
}
