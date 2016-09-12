package org.mobangjack.wechat.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;

import org.mobangjack.common.util.InputStreamUtil;

/**
 * A tiny https util
 * @author 帮杰
 *
 */
public class HttpsUtil {

	public static String DefaultEncoding = "utf-8";
	
	private static class MyX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
	public static InputStream getInputStream(String requestURL,String requestMethod,byte[] outBytes,String encoding) {
		InputStream inputStream = null;
		TrustManager[] trustManager = { new MyX509TrustManager() };
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, trustManager, new SecureRandom());
			SSLSocketFactory factory = sslContext.getSocketFactory();

			URL url = new URL(requestURL);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(factory);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(requestMethod);
			
			if (null != outBytes) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(outBytes);
				outputStream.close();
			}
			inputStream = connection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return inputStream;
	}
	
	public static InputStream getInputStream(String requestURL,String requestMethod,byte[] outBytes) {
		return getInputStream(requestURL, requestMethod, outBytes, DefaultEncoding);
	}
	
	public static InputStream getInputStream(String requestURL,String requestMethod,String outString,String encoding) {
		return getInputStream(requestURL, requestMethod, outString.getBytes(), encoding);
	}
	
	public static InputStream getInputStream(String requestURL,String requestMethod,String outString) {
		return getInputStream(requestURL, requestMethod, outString, DefaultEncoding);
	}
	
	public static String getString(String requestURL,String requestMethod,byte[] outBytes,String encoding) {
		InputStream inputStream = getInputStream(requestURL, requestMethod, outBytes, encoding);
		return InputStreamUtil.readStr(inputStream, encoding);
	}
	
	public static String getString(String requestURL,String requestMethod,byte[] outBytes) {
		return getString(requestURL, requestMethod, outBytes, DefaultEncoding);
	}
	
	public static String getString(String requestURL,String requestMethod,String outString,String encoding) {
		return getString(requestURL, requestMethod, outString.getBytes(), encoding);
	}
	
	public static String getString(String requestURL,String requestMethod,String outString) {
		return getString(requestURL, requestMethod, outString, DefaultEncoding);
	}
	
	public static JSONObject getJsonObject(String requestURL,String requestMethod,String outString, String encoding) {
		return JSONObject.fromObject(getString(requestURL, requestMethod, outString,encoding));
	}
	
	public static JSONObject getJsonObject(String requestURL,String requestMethod,String outString) {
		return getJsonObject(requestURL, requestMethod, outString, DefaultEncoding);
	}
}
