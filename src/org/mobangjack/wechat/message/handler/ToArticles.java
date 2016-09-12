package org.mobangjack.wechat.message.handler;

import java.util.Date;
import java.util.List;

import org.mobangjack.wechat.message.BaseMsg;
import org.mobangjack.wechat.message.response.Article;
import org.mobangjack.wechat.message.response.BaseArticle;

public class ToArticles implements MessageHandler {

	private List<Article> articles;

	public ToArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public Object handleMessage(BaseMsg baseMsg) {
		BaseArticle baseArticle = new BaseArticle();
		baseArticle.setCreateTime(new Date().getTime() + "");
		baseArticle.setMsgType("news");
		baseArticle.setFromUserName(baseMsg.getToUserName());
		baseArticle.setToUserName(baseMsg.getFromUserName());
		baseArticle.setArticleCount(articles.size() + "");
		baseArticle.setArticles(articles);
		return baseArticle;
	}

}
