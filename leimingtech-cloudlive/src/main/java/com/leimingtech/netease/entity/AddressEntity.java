package com.leimingtech.netease.entity;

/**
 * 网易云直播 频道推拉流地址
 * 
 * @author liuzhen
 * 
 */
public class AddressEntity {

	private String pushUrl;// 推流地址
	private String httpPullUrl;// http拉流地址
	private String hlsPullUrl;// hls拉流地址
	private String rtmpPullUrl;// rtmp拉流地址
	private String name;// 频道名称

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
