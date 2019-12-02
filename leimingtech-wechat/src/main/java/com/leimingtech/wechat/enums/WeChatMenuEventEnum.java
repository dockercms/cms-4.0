package com.leimingtech.wechat.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 微信菜单事件枚举
 * 
 * @author 谢进伟
 * 
 * @date 2015-8-12 上午10:42:54
 */
public enum WeChatMenuEventEnum {
	
	/**
	 * <b>点击推事件</b><br>
	 * 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event
	 * 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
	 */
	click,
	/**
	 * <b>跳转URL</b><br>
	 * 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
	 */
	view,
	/**
	 * <b>扫码推事件</b><br>
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，
	 * 开发者可以下发消息,仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，
	 * 开发者也不能正常接收到事件推送。
	 */
	scancode_push,
	/**
	 * <b>扫码推事件且弹出“消息接收中”提示框</b><br>
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，
	 * 随后可能会收到开发者下发的消息,仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应
	 * ，开发者也不能正常接收到事件推送。
	 */
	scancode_waitmsg,
	/**
	 * <b>弹出系统拍照发图</b><br>
	 * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，
	 * 随后可能会收到开发者下发的消息,仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应
	 * ，开发者也不能正常接收到事件推送。
	 */
	pic_sysphoto,
	/**
	 * <b>弹出拍照或者相册发图</b><br>
	 * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程,仅支持微信iPhone5.4.1
	 * 以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送。
	 */
	pic_photo_or_album,
	/**
	 * <b>弹出微信相册发图器</b><br>
	 * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，
	 * 随后可能会收到开发者下发的消息,仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应
	 * ，开发者也不能正常接收到事件推送。
	 */
	pic_weixin,
	/**
	 * <b>弹出地理位置选择器</b><br>
	 * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，
	 * 随后可能会收到开发者下发的消息,仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应
	 * ，开发者也不能正常接收到事件推送。
	 */
	location_select,
	/**
	 * <b>下发消息（除文本消息）</b><br>
	 * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。
	 * 请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id,专门给第三方平台旗下未微信认证（具体而言，是资质认证未通过）
	 * 的订阅号准备的事件类型，它们是没有事件推送的，能力相对受限，其他类型的公众号不必使用。
	 */
	media_id,
	/**
	 * <b>跳转图文消息URL</b><br>
	 * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。
	 * 请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id,专门给第三方平台旗下未微信认证（具体而言，是资质认证未通过）
	 * 的订阅号准备的事件类型，它们是没有事件推送的，能力相对受限，其他类型的公众号不必使用。
	 */
	view_limited;
	
	/**
	 * 枚举中文释义
	 */
	public static Map<WeChatMenuEventEnum , String> chineseMap = new TreeMap<WeChatMenuEventEnum , String>();
	public static Map<WeChatMenuEventEnum , String> remarkMap = new TreeMap<WeChatMenuEventEnum , String>();
	static {
		// 初始化枚举值中文释义
		chineseMap.put(click , "点击推事件");
		chineseMap.put(view , "跳转URL");
		chineseMap.put(scancode_push , "扫码推事件");
		chineseMap.put(scancode_waitmsg , "扫码推事件且弹出“消息接收中”提示框");
		chineseMap.put(pic_sysphoto , "弹出系统拍照发图");
		chineseMap.put(pic_photo_or_album , "弹出拍照或者相册发图");
		chineseMap.put(pic_weixin , "弹出微信相册发图器");
		chineseMap.put(location_select , "弹出地理位置选择器");
		chineseMap.put(media_id , "下发消息（除文本消息）");
		chineseMap.put(view_limited , "跳转图文消息URL");
		// 初始化枚举值中备注
		remarkMap.put(click , "用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互");
		remarkMap.put(view , "用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息");
		remarkMap.put(scancode_push , "用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息");
		remarkMap.put(scancode_waitmsg , "用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息");
		remarkMap.put(pic_sysphoto , "用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息");
		remarkMap.put(pic_photo_or_album , "用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程");
		remarkMap.put(pic_weixin , "用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息");
		remarkMap.put(location_select , "用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息");
		remarkMap.put(media_id , "用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id");
		remarkMap.put(view_limited , "用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id");
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
			WeChatMenuEventEnum [] values = WeChatMenuEventEnum.values();
			for(WeChatMenuEventEnum baseEnum : values) {
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
		if(WeChatMenuEventEnum.contains(element)) {
			chinese = chineseMap.get(WeChatMenuEventEnum.valueOf(element));
		}
		return chinese;
	}
}
