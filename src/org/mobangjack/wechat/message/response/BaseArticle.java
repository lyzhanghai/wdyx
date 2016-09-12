package org.mobangjack.wechat.message.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class BaseArticle {

	@XStreamAlias("ToUserName")
	private String ToUserName;

	@XStreamAlias("FromUserName")
	private String FromUserName;

	@XStreamAlias("CreateTime")
	private String CreateTime;

	@XStreamAlias("MsgType")
	private String MsgType;

	@XStreamAlias("ArticleCount")
	private String ArticleCount;

	@XStreamAlias("Articles")
	private List<Article> Articles;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
