package org.mobangjack.wechat.api.config;

public class ThreadLocalApiConfig {

	private static final ThreadLocal<ApiConfig> threadLocalApiConfig = new ThreadLocal<ApiConfig>();
	
	public static ApiConfig get() {
		return threadLocalApiConfig.get();
	}
	
	public static void set(ApiConfig apiConfig) {
		threadLocalApiConfig.set(apiConfig);
	}
	
	public static void remove() {
		threadLocalApiConfig.remove();
	}

}
