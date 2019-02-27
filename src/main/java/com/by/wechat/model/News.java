package com.by.wechat.model;

public class News {
	/** 图文消息标题 **/
	public int Title;
	/** 图文消息描述 **/
	public int Description;
	/** 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 **/
	public int PicUrl;
	/** 点击图文消息跳转链接 **/
	public int Url;
	public int getTitle() {
		return Title;
	}
	public void setTitle(int title) {
		Title = title;
	}
	public int getDescription() {
		return Description;
	}
	public void setDescription(int description) {
		Description = description;
	}
	public int getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(int picUrl) {
		PicUrl = picUrl;
	}
	public int getUrl() {
		return Url;
	}
	public void setUrl(int url) {
		Url = url;
	}
}
