package com.leimingtech.platform.entity.message.resp;
/** 
 * 类说明 音乐消息
 */
public class MusicMessage extends BaseMessage{
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}
