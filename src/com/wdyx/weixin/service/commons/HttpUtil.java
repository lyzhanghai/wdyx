package com.wdyx.weixin.service.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * HTTP小工具
 * @author 帮杰
 *
 */
public class HttpUtil {

	public static String getHtml(String url) throws ClientProtocolException, IOException {
		String html = "";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = HttpClients.createDefault().execute(httpget);
		HttpEntity entity = new BufferedHttpEntity(response.getEntity());
		String charset = HtmlUtil.getCharset(EntityUtils.toString(entity));
        html = EntityUtils.toString(entity,charset);
        EntityUtils.consume(entity);
		return html;
	}
	
	public static String getHtml(String url,String charset) throws ClientProtocolException, IOException {
		String html = "";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = HttpClients.createDefault().execute(httpget);
		HttpEntity entity = response.getEntity();
        html = EntityUtils.toString(entity,charset);
        EntityUtils.consume(entity);
		return html;
	}
	
	public static String getHtml(String url,CloseableHttpClient httpclient) throws ClientProtocolException, IOException {
		String html = "";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = new BufferedHttpEntity(response.getEntity());
		String charset = HtmlUtil.getCharset(EntityUtils.toString(entity));
        html = EntityUtils.toString(entity,charset);
        EntityUtils.consume(entity);
		return html;
	}
	
	public static String getHtml(String url,String charset,CloseableHttpClient httpclient) throws ClientProtocolException, IOException {
		String html = "";
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
        html = EntityUtils.toString(entity,charset);
        EntityUtils.consume(entity);
		return html;
	}
	  
    public static String httpRequest(String requestUrl,String charset) {  
        StringBuffer buffer = null;  
  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            buffer = new StringBuffer();  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            httpUrlConn.disconnect();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer.toString();  
    }  
    
}
