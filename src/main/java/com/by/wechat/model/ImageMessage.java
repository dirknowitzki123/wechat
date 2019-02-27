package com.by.wechat.model;

/**
 * 图片消息
 * Created by yiqr on 2017/6/12.
 */
public class ImageMessage extends BaseMessage{
	public Image Image = new Image();
	
	public ImageMessage() {
		super();
		setMsgType("image");
	}

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
}
