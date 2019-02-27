package com.by.wechat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息
 * Created by yiqr on 2017/6/12.
 */
public class NewsMessage extends BaseMessage{
	/** 图文消息个数，限制为8条以内 **/
	public int ArticleCount;
	/** 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应 **/
	private List<News> item = new ArrayList<News>();
	public NewsMessage() {
		super();
		setMsgType("news");
	}
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getItem() {
		return item;
	}
	public void setItem(List<News> item) {
		this.item = item;
	}
}
