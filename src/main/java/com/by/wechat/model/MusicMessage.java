package com.by.wechat.model;

/**
 * 音乐消息
 * Created by yiqr on 2017/6/12.
 */
public class MusicMessage extends BaseMessage{
	private Music Music = new Music();

	public MusicMessage() {
		super();
		setMsgType("music");
	}

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
