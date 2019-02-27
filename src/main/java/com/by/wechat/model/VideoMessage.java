package com.by.wechat.model;

/**
 * 视频消息
 * Created by yiqr on 2017/6/12.
 */
public class VideoMessage extends BaseMessage{
	private Video Video = new Video();

	public VideoMessage() {
		super();
		setMsgType("video");
	}

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
