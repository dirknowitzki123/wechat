package com.by.wechat.model;

/**
 * 图片消息内容
 * @author yiqr
 */
public class Image {
	/** 通过素材管理中的接口上传多媒体文件，得到的id。 **/
	public String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
