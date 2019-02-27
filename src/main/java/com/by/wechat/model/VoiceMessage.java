package com.by.wechat.model;

/**
 * 语音消息
 * Created by yiqr on 2017/6/12.
 */
public class VoiceMessage extends BaseMessage{
	private Voice Voice = new Voice();
	
	public VoiceMessage() {
		super();
		setMsgType("voice");
	}

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
