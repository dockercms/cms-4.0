package com.leimingtech.wechat.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 微信消息加密方式枚举
 * 
 * @author 谢进伟
 * 
 * @date 2015-8-16 下午2:59:59
 */
public enum WechatEncodeModeEnum {
	/**
	 * 明文模式
	 */
	plaintext,
	/**
	 * 兼容模式
	 */
	compatible,
	/**
	 * 安全模式
	 */
	safety;
	
	/**
	 * 枚举中文释义
	 */
	public static Map<WechatEncodeModeEnum , String> chineseMap = new TreeMap<WechatEncodeModeEnum , String>();
	public static Map<WechatEncodeModeEnum , String> remarkMap = new TreeMap<WechatEncodeModeEnum , String>();
	static {
		// 初始化枚举值中文释义
		chineseMap.put(plaintext , "明文模式");
		chineseMap.put(compatible , "兼容模式");
		chineseMap.put(safety , "安全模式");
		// 初始化枚举值中备注
		remarkMap.put(plaintext , "维持现有模式，没有适配加解密新特性，消息体明文收发，默认设置为明文模式");
		remarkMap.put(compatible , "公众平台发送消息内容将同时包括明文和密文，消息包长度增加到原来的3倍左右；公众号回复明文或密文均可，不影响现有消息收发；开发者可在此模式下进行调试");
		remarkMap.put(safety , "公众平台发送消息体的内容只含有密文，公众账号回复的消息体也为密文，建议开发者在调试成功后使用此模式收发消息");
	}
	
	/**
	 * 判断指定字符串是否为枚举成员
	 * 
	 * @param element
	 *            需要判断的字符串
	 * @return
	 */
	public static boolean contains(String element) {
		boolean result = false;
		if(!StringUtils.isEmpty(element)) {
			WechatEncodeModeEnum [] values = WechatEncodeModeEnum.values();
			for(WechatEncodeModeEnum baseEnum : values) {
				if(baseEnum.toString().equals(element)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 将枚举元素转换为中文释义
	 * 
	 * @param element
	 *            需要转换的枚举元素
	 * @return
	 */
	public static String convertChinese(String element) {
		String chinese = null;
		if(WechatEncodeModeEnum.contains(element)) {
			chinese = chineseMap.get(WechatEncodeModeEnum.valueOf(element));
		}
		return chinese;
	}
}
