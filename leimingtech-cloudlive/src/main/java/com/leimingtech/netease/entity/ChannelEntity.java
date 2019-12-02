package com.leimingtech.netease.entity;

/**
 * 网易云直播 创建频道完成返回信息
 * 
 * @author liuzhen
 * 
 */
public class ChannelEntity {

	private String cid;// 频道ID，32位字符串

	private long ctime;// 创建频道的时间戳

	private String pushUrl;// 推流地址

	private String httpPullUrl;// http拉流地址

	private String hlsPullUrl;// hls拉流地址

	private String rtmpPullUrl;// rtmp拉流地址

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public String getHttpPullUrl() {
		return httpPullUrl;
	}

	public void setHttpPullUrl(String httpPullUrl) {
		this.httpPullUrl = httpPullUrl;
	}

	public String getHlsPullUrl() {
		return hlsPullUrl;
	}

	public void setHlsPullUrl(String hlsPullUrl) {
		this.hlsPullUrl = hlsPullUrl;
	}

	public String getRtmpPullUrl() {
		return rtmpPullUrl;
	}

	public void setRtmpPullUrl(String rtmpPullUrl) {
		this.rtmpPullUrl = rtmpPullUrl;
	}

}
