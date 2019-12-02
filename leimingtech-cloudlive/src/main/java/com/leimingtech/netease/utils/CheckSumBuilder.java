package com.leimingtech.netease.utils;

import java.security.MessageDigest;

/**
 * 所有调用视频云服务端接口的请求都需要按此规则校验。 <br/>
 * API token校验
 * 
 * @author liuzhen
 * 
 */
public class CheckSumBuilder {

	/**
	 * 服务器认证需要,SHA1(AppSecret+Nonce+CurTime),16进制字符小写
	 * 
	 * @param appSecret
	 *            开发者平台分配的appSecret
	 * @param nonce
	 *            随机数（随机数，最大长度128个字符）
	 * @param curTime
	 *            当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的秒数
	 * @return
	 */
	public static String getCheckSum(String appSecret, String nonce,
			String curTime) {
		return encode("sha1", appSecret + nonce + curTime);
	}

	private static String encode(String algorithm, String value) {
		if (value == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(value.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}