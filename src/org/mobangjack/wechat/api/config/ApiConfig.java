package org.mobangjack.wechat.api.config;

/**
 * API配置
 * @author 帮杰
 *
 */
public class ApiConfig {
	
	private String token;
	private String appId;
	private String appSecret;
	private boolean encryptMsg = false;
	private String encodingAesKey;
	
	public ApiConfig(String token,String appId,String appSecret,boolean encryptMsg,String encodingAesKey) {
		this.token = token;
		this.appId = appId;
		this.appSecret = appSecret;
		this.encryptMsg = encryptMsg;
		this.encodingAesKey = encodingAesKey;
	}
	
	public String getToken() {
		return token;
	}
	
	public ApiConfig setToken(String token) {
		this.token = token;
		return this;
	}
	
	public String getAppId() {
		return appId;
	}
	
	public ApiConfig setAppId(String appId) {
		this.appId = appId;
		return this;
	}
	
	public String getAppSecret() {
		return appSecret;
	}
	
	public ApiConfig setAppSecret(String appSecret) {
		this.appSecret = appSecret;
		return this;
	}
	
	public String getEncodingAesKey() {
		return encodingAesKey;
	}
	
	public ApiConfig setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
		return this;
	}

	public boolean getEncryptMsg() {
		return encryptMsg;
	}

	public ApiConfig setEncryptMsg(boolean encryptMsg) {
		this.encryptMsg = encryptMsg;
		return this;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("------------------ApiConfig------------------\n");
		sb.append("token:").append(token).append("\n")
		  .append("appId:").append(appId).append("\n")
		  .append("appSecret:").append(appSecret).append("\n")
		  .append("encryptMsg:").append(encryptMsg).append("\n")
		  .append("encodingAesKey:").append(encodingAesKey).append("\n")
		  .append("------------------ApiConfig------------------\n");
		return sb.toString();
	}

}


