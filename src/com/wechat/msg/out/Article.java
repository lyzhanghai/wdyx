package com.wechat.msg.out;


/**
	回复图文消息
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[news]]></MsgType>
			<ArticleCount>2</ArticleCount>
			<Articles>
				<item>
					<Title><![CDATA[title1]]></Title> 
					<Description><![CDATA[description1]]></Description>
					<PicUrl><![CDATA[picurl]]></PicUrl>
					<Url><![CDATA[url]]></Url>
				</item>
				
				<item>
					<Title><![CDATA[title]]></Title>
					<Description><![CDATA[description]]></Description>
					<PicUrl><![CDATA[picurl]]></PicUrl>
					<Url><![CDATA[url]]></Url>
				</item>
			</Articles>
	</xml>
*/

public class Article {
	
	private String title;		// 不是必须
	
	private String description;	// 不是必须
	
	private String picUrl;		// 不是必须
	
	private String url;			// 不是必须
	
	public Article(String title, String description, String picUrl, String url) {
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}
	
	public Article() {
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		String t = "<item><Title><![CDATA[title]]></Title> <Description><![CDATA[description]]></Description><PicUrl><![CDATA[picUrl]]></PicUrl><Url><![CDATA[url]]></Url></item>";
		t = t.replace("title", title==null?"":title)
			 .replace("description", description==null?"":description)
			 .replace("picUrl", picUrl==null?"":picUrl)
			 .replace("url", url==null?"":url);
		return t;
	}
}
