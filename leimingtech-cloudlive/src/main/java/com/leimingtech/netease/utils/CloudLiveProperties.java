package com.leimingtech.netease.utils;

import java.util.ResourceBundle;

/**
 * 云直播配置
 * 
 * @author liuzhen
 * 
 */
public class CloudLiveProperties {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("cloudlive");

	/************************************* 网易云直播 **********************************/

	/** 网易云直播 appkay */
	public static final String APPKEY = bundle.getString("appKey");
	/** 网易云直播 appSecret */
	public static final String APPSECRET = bundle.getString("appSecret");

	/************************************ 百度云直播 **********************************/

	/** 百度云直播 ACCESS_KEY_ID */
	public static final String ACCESS_KEY_ID = bundle
			.getString("ACCESS_KEY_ID");
	/** 百度云直播 SECRET_ACCESS_KEY */
	public static final String SECRET_ACCESS_KEY = bundle
			.getString("SECRET_ACCESS_KEY");

}
