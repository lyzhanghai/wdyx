/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wechat.util.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * MultipartFormUploadRequest
 * @author 帮杰
 *
 */
public class MultipartFormUploadRequest extends HttpsRequest {

	private Map<String, Object> formDataMap;
	
	public MultipartFormUploadRequest(String url) {
		super(url);
	}
	
	public MultipartFormUploadRequest(String url,Map<String, Object> formDataMap) {
		super(url);
		this.formDataMap = formDataMap;
	}
	
	public void setFormData(Map<String, Object> formDataMap) {
		this.formDataMap = formDataMap;
	}
	
	@Override
	public void doRequest() throws Exception {
		TrustManager[] trustManager = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, trustManager, new SecureRandom());
		SSLSocketFactory factory = sslContext.getSocketFactory();

		URL requestUrl = new URL(null,getUrl(),new sun.net.www.protocol.https.Handler());
		connection = (HttpsURLConnection) requestUrl.openConnection();
		connection.setHostnameVerifier(new MyHostnameVerifier());
		connection.setSSLSocketFactory(factory);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setRequestMethod(method);
		connection.setRequestProperty("Connection", "Keep-Alive");  
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		String BOUNDARY = "---------------------------" + System.currentTimeMillis();
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);  
		
		if (formDataMap != null) {
			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			for (Map.Entry<String, Object> e : formDataMap.entrySet()) {
				String k = e.getKey();
				Object v = e.getValue();
				if (v instanceof String) {
					StringBuffer sb = new StringBuffer();
					sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					sb.append("Content-Disposition: form-data; name=\"" + k + "\"\r\n\r\n");
					sb.append(v);
					out.write(sb.toString().getBytes(encoding));
					System.out.println(sb);
				} else if (v instanceof byte[]) {
					byte[] bytes = (byte[])v;
					StringBuffer sb = new StringBuffer();
					String contentType = "application/octet-stream";
					sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					sb.append("Content-Disposition: form-data; name=\"" + k + "\"\r\n\r\n");
					sb.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(sb.toString().getBytes(encoding));
					out.write(bytes);
				} else if (v instanceof InputStream) {
					InputStream in = (InputStream)v;
					StringBuffer sb = new StringBuffer();
					String contentType = "application/octet-stream";
					sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					sb.append("Content-Disposition: form-data; name=\"" + k + "\"\r\n\r\n");
					sb.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(sb.toString().getBytes(encoding));
					byte[] buf = new byte[4096];
					int len;
					while ((len=in.read(buf))>0) {
						out.write(buf, 0, len);
					}
					in.close();
					in = null;
				} else if (v instanceof File) {
					StringBuffer sb = new StringBuffer();
					File file = (File) v;
					String filename = file.getName();
					String contentType = new MimetypesFileTypeMap().getContentType(file);
					sb.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					sb.append("Content-Length:");
					sb.append("Content-Disposition: form-data; name=\"" + k + "\"; filename=\"" + filename + "\"\r\n");
					sb.append("Content-Type:" + contentType + "\r\n\r\n");
					out.write(sb.toString().getBytes(encoding));
					System.out.println(sb);
					FileInputStream in = new FileInputStream(file);
					byte[] buf = new byte[4096];
					int len;
					while ((len=in.read(buf))>0) {
						out.write(buf, 0, len);
					}
					in.close();
					in = null;
				}
			}
			String eof = BOUNDARY + "--";
			out.write(eof.getBytes(encoding));
			out.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		/*
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=xohaaockdFH0VC1tGyAHq63URTDClG2LCkzuBWIA_hHgF3lpPm60ZV69dh4GR8DUDEE9VX7nesgrkLaYfshMSZ8XJG0rWqHpsa2aIz5TZ4k&type=image";
		Map<String, Object> formDataMap = new HashMap<String, Object>();
		//formDataMap.put("access_token", "xohaaockdFH0VC1tGyAHq63URTDClG2LCkzuBWIA_hHgF3lpPm60ZV69dh4GR8DUDEE9VX7nesgrkLaYfshMSZ8XJG0rWqHpsa2aIz5TZ4k");
		formDataMap.put("type", "image");
		File file = new File("G:/优米可/素材/red bull.jpg");
		formDataMap.put("media", file);
		MultipartFormUploadRequest request = new MultipartFormUploadRequest(url,formDataMap);
		request.setMethod(METHOD_POST);
		request.doRequest();
		String response = request.getResponseAsString();
		System.out.println(response);
		request.close();
		*/
		String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=xohaaockdFH0VC1tGyAHq63URTDClG2LCkzuBWIA_hHgF3lpPm60ZV69dh4GR8DUDEE9VX7nesgrkLaYfshMSZ8XJG0rWqHpsa2aIz5TZ4k&media_id=Exc32M1eHI3_tCS28SsK0PosRxkkrhN1SjcxL-KNbsg9UdGbi9LqtWA28vWMOWn3";
		File dest = new File("redbull.jpg");
		ApiRequest request = new ApiRequest(url).doGet();
		System.out.println(request.downloadFile(dest));
		
	}
}
