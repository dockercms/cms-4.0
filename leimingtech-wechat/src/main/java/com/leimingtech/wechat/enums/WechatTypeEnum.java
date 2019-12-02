package com.leimingtech.wechat.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 微信号类型枚举
 * 
 * @author 谢进伟
 * 
 * @date 2015-8-12 下午12:05:39
 */
public enum WechatTypeEnum {
	
	/**
	 * 订阅号
	 */
	subscribe,
	/**
	 * 服务号
	 */
	service,
	/**
	 * 企业号
	 */
	//enterprise,
	/**
	 * 认证服务号
	 */
	certificationServic,
	/**
	 * 认证订阅号
	 */
	certificationSubscribe;
	
	/**
	 * 枚举中文释义
	 */
	public static Map<WechatTypeEnum , String> chineseMap = new TreeMap<WechatTypeEnum , String>();
	public static Map<WechatTypeEnum , String> remarkMap = new TreeMap<WechatTypeEnum , String>();
	static {
		// 初始化枚举值中文释义
		chineseMap.put(subscribe , "订阅号");
		chineseMap.put(service , "服务号");
//		chineseMap.put(enterprise , "企业号");
		chineseMap.put(certificationServic , "认证服务号");
		chineseMap.put(certificationSubscribe , "认证订阅号");
		// 初始化枚举值中备注
		remarkMap.put(subscribe , "订阅号主要适用于个人、媒体、企业、政府或其他有需求的组织,主要是为用户提供各类信息和资讯，让我们第一时间获得更多的信息量。还有就是订阅号是每天可以群发一条消息。但是订阅号发给用户的消息，只会显示在用户的订阅号文件夹之中，不会出现在聊天的界面里，也不会收到即时提醒消息。用户要看必须进入订阅号文件夹中。");
		remarkMap.put(service , "服务号主要适用于媒体、企业、政府或其他有需求的组织,最大的特点可以自定义菜单功能，也就是可以在手机底部进行自定义菜单,它最终目的是为用户提供服务，重在服务。而且服务号一个月内只可以群发一条消息，但是有一点好处就是服务号发给用户的消息，它会直接显示在用户的聊天列表之中，很明显，还即时的给用户消息提醒，让用户能第一时间看到。");
//		remarkMap.put(enterprise , "企业号主要适用于企业、政府、事业单位或其他有需求的组织");
		remarkMap.put(certificationServic , "认证服务号是指每年向微信官方交300元认证费的公众号");
		remarkMap.put(certificationSubscribe , "认证订阅号");
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
			WechatTypeEnum [] values = WechatTypeEnum.values();
			for(WechatTypeEnum baseEnum : values) {
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
		if(WechatTypeEnum.contains(element)) {
			chinese = chineseMap.get(WechatTypeEnum.valueOf(element));
		}
		return chinese;
	}
}
