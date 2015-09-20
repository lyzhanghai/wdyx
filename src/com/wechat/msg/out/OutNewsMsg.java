package com.wechat.msg.out;

import java.util.ArrayList;
import java.util.List;

import com.wechat.msg.in.InMsg;

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

public class OutNewsMsg extends OutMsg {
	
	private List<Article> articles = new ArrayList<Article>();
	
	public OutNewsMsg() {
		this.msgType = "news";
	}
	
	public OutNewsMsg(InMsg inMsg) {
		super(inMsg);
		this.msgType = "news";
	}
	
	public Integer getArticleCount() {
		return articles.size();
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(List<Article> articles) {
		if (articles != null)
			this.articles = articles;
	}
	
	public OutNewsMsg addNews(List<Article> articles) {
		if (articles != null)
			this.articles.addAll(articles);
		return this;
	}
	
	public OutNewsMsg addNews(String title, String description, String picUrl, String url) {
		this.articles.add(new Article(title, description, picUrl, url));
		return this;
	}
	
	public OutNewsMsg addNews(Article article) {
		this.articles.add(article);
		return this;
	}
	
	@Override
	public String toString() {
		String t = "<xml><ToUserName><![CDATA[toUserName]]></ToUserName><FromUserName><![CDATA[fromUserName]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>articleCount</ArticleCount><Articles>";
		t = t.replace("toUserName", toUserName)
			 .replace("fromUserName", fromUserName)
			 .replace("createTime", ""+createTime)
			 .replace("articleCount",""+articles.size());
		for(Article article:articles){
			t+=article;
		}
		t+="</Articles></xml>";
		return t;
	}
}








