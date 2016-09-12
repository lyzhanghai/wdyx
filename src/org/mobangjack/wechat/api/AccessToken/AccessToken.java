package org.mobangjack.wechat.api.AccessToken;

import java.util.Date;

/**
 * 全局属性：AccessToken的封装
 * @author 帮杰
 *
 */
public class AccessToken {

	private String token;
	private Date getIn;
	private int expiresIn;

	public AccessToken(){}
	
	public AccessToken(String token,int expiresIn) {
		this.token = token;
		this.getIn = new Date();
		this.expiresIn = expiresIn;
	}
	
	public AccessToken(String token,Date getIn,int expiresIn) {
		this.token = token;
		this.getIn = getIn;
		this.expiresIn = expiresIn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getGetIn() {
		return getIn;
	}

	public void setGetIn(Date getIn) {
		this.getIn = getIn;
	}
	
	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String toString() {
		return token;
	}

	public boolean isExpired() {
		Date now = new Date();
		Date lastGetIn = this.getGetIn();
		int expiredIn = (this.getExpiresIn()-200)*1000;
		return (now.getTime()>lastGetIn.getTime()+expiredIn);
	}
}
