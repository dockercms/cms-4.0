package com.leimingtech.mobile.push.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.leimingtech.mobile.push.UmengNotification;
import com.leimingtech.mobile.push.android.AndroidBroadcast;
import com.leimingtech.mobile.push.android.AndroidCustomizedcast;
import com.leimingtech.mobile.push.android.AndroidFilecast;
import com.leimingtech.mobile.push.android.AndroidGroupcast;
import com.leimingtech.mobile.push.android.AndroidUnicast;
import com.leimingtech.mobile.push.ios.IOSBroadcast;
import com.leimingtech.mobile.push.ios.IOSCustomizedcast;
import com.leimingtech.mobile.push.ios.IOSFilecast;
import com.leimingtech.mobile.push.ios.IOSGroupcast;
import com.leimingtech.mobile.push.ios.IOSUnicast;
import com.leimingtech.mobile.push.utl.enums.AfterOpenEnum;
import com.leimingtech.mobile.push.utl.enums.MobilePhoneSystemEnum;

/**
 * 消息推送工具
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-1-7 上午10:57:18
 */
public class PushUtil {
	
	private static Logger log = Logger.getLogger(PushUtil.class);
	/**
	 * 需要接受推送的应用的唯一标识(友盟所提供)
	 */
	private static String appkey = null;
	private static String AndroidAppkey = null;
	private static String IOSAppkey = null;
	/**
	 * 友盟所提供的App Master Secret
	 */
	private static String appMasterSecret = null;
	private static String AndroidAppMasterSecret = null;
	private static String IOSAppMasterSecret = null;
	/**
	 * 时间戳，10位或者13位均可，时间戳有效期为10分钟
	 */
	private static String timestamp = null;
	private static PushUtil pu;
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle(PushUtil.class.getPackage().getName() + ".push");
		String appkey = rb.getString("AndroidAppkey");
		String appMasterSecret = rb.getString("AndroidAppMasterSecret");
		String IOSAppkey = rb.getString("IOSAppkey");
		String IOSAppMasterSecret = rb.getString("IOSAppMasterSecret");
		PushUtil.AndroidAppkey = appkey;
		PushUtil.AndroidAppMasterSecret = appMasterSecret;
		PushUtil.IOSAppkey = IOSAppkey;
		PushUtil.IOSAppMasterSecret = IOSAppMasterSecret;
		log.info("消息推送相关参数：appkey:" + appkey + "\tappMasterSecret：" + appMasterSecret);
	}
	
	private PushUtil (){
		
	}
	
	/**
	 * 初始化工具相关参数，并返回初始化参数后的工具
	 * 
	 * @return
	 */
	public static PushUtil instance(MobilePhoneSystemEnum mobilePhoneSystem) {
		if(pu == null) {
			pu = new PushUtil();
		}
		if(mobilePhoneSystem.compareTo(MobilePhoneSystemEnum.IOS) == 0) {
			appkey = IOSAppkey;
			appMasterSecret = IOSAppMasterSecret;
		} else {
			appkey = AndroidAppkey;
			appMasterSecret = AndroidAppMasterSecret;
		}
		timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		return pu;
	}
	
	/**
	 * 根据配置文件初始化参数
	 * 
	 * @param broadcast
	 *            需要初始化参数的通知对象
	 * @throws Exception
	 */
	private static void inntParam(UmengNotification broadcast) throws Exception {
		broadcast.setAppMasterSecret(appMasterSecret);
		broadcast.setPredefinedKeyValue("appkey" , appkey);
		broadcast.setPredefinedKeyValue("timestamp" , timestamp);
	}
	
	/**
	 * 设置Android广播的主体参数
	 * 
	 * @param ticker
	 *            通知栏提示文字
	 * @param title
	 *            通知标题
	 * @param text
	 *            通知文字描述
	 * @param after_open
	 *            点击"通知"的后续行为，默认为打开AfterOpenEnum.GO_APP<span style="color:red;"><br/>
	 *            可选值：<br/>
	 *            AfterOpenEnum.GO_APP: 打开应用<br/>
	 *            AfterOpenEnum.GO_URL: 跳转到URL<br/>
	 *            AfterOpenEnum.GO_ACTIVITY: 打开特定的activity<br/>
	 *            AfterOpenEnum.GO_CUSTOM:用户自定义内容。
	 *            </span>
	 * @param objective
	 *            点击"通知"的后续行为目标<span style="color:red;"><br/>
	 *            当after_opende的值为AfterOpenEnum.GO_APP时objective传入null即可(该选项为默认值
	 *            ,传入任何参数都默认打开配置文件中所配置的APP客户端应用程序)<br/>
	 *            当单after_opende的值为AfterOpenEnum.GO_URL时objective表示要跳转到URL，
	 *            要求以http或者https开头 <br/>
	 *            当after_opende的值为AfterOpenEnum.GO_ACTIVITY时objective表示：
	 *            要打开的activity;<br/>
	 *            当after_opende的值为AfterOpenEnum.GO_CUSTOM时objective表示用户自定义内容;
	 *            可以为字符串或者JSON格式
	 *            </span>
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param notivication
	 *            广播方式对象<br/>
	 * @param other
	 *            自定义参数
	 * 
	 * @throws Exception
	 */
	private static void setAndroidBodyParams(String ticker , String title , String text , AfterOpenEnum after_open , Object objective , boolean production_mode , UmengNotification notivication , Map<String , Object> other) throws Exception {
		inntParam(notivication);
		notivication.setPredefinedKeyValue("ticker" , ticker);
		notivication.setPredefinedKeyValue("title" , title);
		notivication.setPredefinedKeyValue("text" , text);
		notivication.setPredefinedKeyValue("after_open" , after_open.toString().toLowerCase());
		if(after_open != AfterOpenEnum.GO_APP) {
			String objectiveKey = null;
			if(after_open == AfterOpenEnum.GO_URL) {
				objectiveKey = "url";
			} else if(after_open == AfterOpenEnum.GO_ACTIVITY) {
				objectiveKey = "activity";
			} else if(after_open == AfterOpenEnum.GO_CUSTOM) {
				objectiveKey = "custom";
			}
			if(objectiveKey != null) {
				notivication.setPredefinedKeyValue(objectiveKey , objective);
			}
		}
		// 消息类型，值可以为:notification-通知，message-消息，此处均采用通知的形式
		notivication.setPredefinedKeyValue("display_type" , "notification");
		notivication.setPredefinedKeyValue("production_mode" , production_mode);
		if(other != null && other.size() > 0) {
			Iterator<String> iter = other.keySet().iterator();
			String key = "";
			Object value = null;
			while (iter.hasNext()) {
				key = iter.next();
				value = other.get(key);
				notivication.setExtraField(key , value);
			}
		}
	}
	
	/**
	 * 设置IOS广播的主体参数
	 * 
	 * @param alert
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param notivication
	 *            广播方式对象<br/>
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	private static void setIOSBodyParams(String alert , boolean production_mode , UmengNotification notivication , Map<String , Object> other) throws Exception {
		notivication.setPredefinedKeyValue("alert" , alert);
		notivication.setPredefinedKeyValue("sound" , "chime");
		notivication.setPredefinedKeyValue("badge" , 0);
		notivication.setPredefinedKeyValue("production_mode" , production_mode);
		notivication.setPredefinedKeyValue("description" , alert);
		if(other != null && other.size() > 0) {
			Iterator<String> iter = other.keySet().iterator();
			String key = "";
			Object value = null;
			while (iter.hasNext()) {
				key = iter.next();
				value = other.get(key);
				notivication.setExtraField(key , value);
			}
		}
	}
	
	/**
	 * 广播;向安装该Android App 客户端的所有设备发送消息(消息类型已定义通知的形式)
	 * 
	 * @param ticker
	 *            通知栏提示文字
	 * @param title
	 *            通知标题
	 * @param text
	 *            通知文字描述
	 * @param after_open
	 *            点击"通知"的后续行为，默认为打开AfterOpenEnum.GO_APP<span style="color:red;"><br/>
	 *            可选值：<br/>
	 *            AfterOpenEnum.GO_APP: 打开应用<br/>
	 *            AfterOpenEnum.GO_URL: 跳转到URL<br/>
	 *            AfterOpenEnum.GO_ACTIVITY: 打开特定的activity<br/>
	 *            AfterOpenEnum.GO_CUSTOM:用户自定义内容。
	 *            </span>
	 * @param objective
	 *            点击"通知"的后续行为目标<span style="color:red;"><br/>
	 *            当after_opende的值为AfterOpenEnum.GO_APP时objective传入null即可(该选项为默认值
	 *            ,传入任何参数都默认打开配置文件中所配置的APP客户端应用程序)<br/>
	 *            当单after_opende的值为AfterOpenEnum.GO_URL时objective表示要跳转到URL，
	 *            要求以http或者https开头 <br/>
	 *            当after_opende的值为AfterOpenEnum.GO_ACTIVITY时objective表示：
	 *            要打开的activity;<br/>
	 *            当after_opende的值为AfterOpenEnum.GO_CUSTOM时objective表示用户自定义内容;
	 *            可以为字符串或者JSON格式
	 *            </span>
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendAndroidBroadcast(String ticker , String title , String text , AfterOpenEnum after_open , Object objective , boolean production_mode , Map<String , Object> other) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast();
		setAndroidBodyParams(ticker , title , text , after_open , objective , production_mode , broadcast , other);
		return broadcast.send();
	}
	
	/**
	 * 
	 * 单播;向安装该Android App 客户端 的指定设备发送消息，包括向单个device_token或者单个alias发消息
	 * <span style="color:red"> <br/>
	 * <br/>
	 * device_token：友盟消息推送服务对设备的唯一标识。Android的device_token是44位字符串,
	 * iOS的device-token是64位。） <br/>
	 * <br/>
	 * alias:开发者自有账号, 开发者可以在SDK中调用setAlias(alias,
	 * alias_type)接口将alias+alias_type与device_token做绑定,
	 * 之后开发者就可以根据自有业务逻辑筛选出alias进行消息推送。
	 * </span>
	 * 
	 * @param ticker
	 *            通知栏提示文字
	 * @param title
	 *            通知标题
	 * @param text
	 *            通知文字描述
	 * @param after_open
	 *            点击"通知"的后续行为，默认为打开AfterOpenEnum.GO_APP<span style="color:red;"><br/>
	 *            可选值：<br/>
	 *            AfterOpenEnum.GO_APP: 打开应用<br/>
	 *            AfterOpenEnum.GO_URL: 跳转到URL<br/>
	 *            AfterOpenEnum.GO_ACTIVITY: 打开特定的activity<br/>
	 *            AfterOpenEnum.GO_CUSTOM:用户自定义内容。
	 *            </span>
	 * @param objective
	 *            点击"通知"的后续行为目标<span style="color:red;"><br/>
	 *            当after_opende的值为AfterOpenEnum.GO_APP时objective传入null即可(该选项为默认值
	 *            ,传入任何参数都默认打开配置文件中所配置的APP客户端应用程序)<br/>
	 *            当单after_opende的值为AfterOpenEnum.GO_URL时objective表示要跳转到URL，
	 *            要求以http或者https开头 <br/>
	 *            当after_opende的值为AfterOpenEnum.GO_ACTIVITY时objective表示：
	 *            要打开的activity;<br/>
	 *            当after_opende的值为AfterOpenEnum.GO_CUSTOM时objective表示用户自定义内容;
	 *            可以为字符串或者JSON格式
	 *            </span>
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param device_token
	 *            友盟消息推送服务对设备的唯一标识。Android的device_token是44位字符串
	 * 
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	
	public boolean sendAndroidUnicast(String ticker , String title , String text , AfterOpenEnum after_open , Object objective , boolean production_mode , String device_token , Map<String , Object> other) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast();
		unicast.setPredefinedKeyValue("device_tokens" , device_token);
		unicast.setPredefinedKeyValue("play_lights" , "true");
		unicast.setPredefinedKeyValue("play_vibrate" , "true");
		setAndroidBodyParams(ticker , title , text , after_open , objective , production_mode , unicast , other);
		return unicast.send();
	}
	
	/**
	 * 组播;向Android App 客户端满足特定条件的设备集合发送消息，例如:
	 * "特定版本"、"特定地域"等。友盟消息推送所支持的维度筛选和友盟统计分析所提供的数据展示维度是一致的，后台数据也是打通的
	 * 
	 * @param ticker
	 *            通知栏提示文字
	 * 
	 * @param title
	 *            通知标题
	 * @param text
	 *            通知文字描述
	 * @param after_open
	 *            点击"通知"的后续行为，默认为打开AfterOpenEnum.GO_APP<span style="color:red;"><br/>
	 *            可选值：<br/>
	 *            AfterOpenEnum.GO_APP: 打开应用<br/>
	 *            AfterOpenEnum.GO_URL: 跳转到URL<br/>
	 *            AfterOpenEnum.GO_ACTIVITY: 打开特定的activity<br/>
	 *            AfterOpenEnum.GO_CUSTOM:用户自定义内容。
	 *            </span>
	 * @param objective
	 *            点击"通知"的后续行为目标<span style="color:red;"><br/>
	 *            当after_opende的值为AfterOpenEnum.GO_APP时objective传入null即可(该选项为默认值
	 *            ,传入任何参数都默认打开配置文件中所配置的APP客户端应用程序)<br/>
	 *            当单after_opende的值为AfterOpenEnum.GO_URL时objective表示要跳转到URL，
	 *            要求以http或者https开头 <br/>
	 *            当after_opende的值为AfterOpenEnum.GO_ACTIVITY时objective表示：
	 *            要打开的activity;<br/>
	 *            当after_opende的值为AfterOpenEnum.GO_CUSTOM时objective表示用户自定义内容;
	 *            可以为字符串或者JSON格式
	 *            </span>
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param filterJson
	 *            筛选条件所组成的JSON字符串
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendAndroidGroupcast(String ticker , String title , String text , AfterOpenEnum after_open , Object objective , boolean production_mode , String filterJson , Map<String , Object> other) throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast();
		
		System.out.println(filterJson.toString());
		
		groupcast.setPredefinedKeyValue("filter" , filterJson);
		setAndroidBodyParams(ticker , title , text , after_open , objective , production_mode , groupcast , other);
		return groupcast.send();
	}
	
	/**
	 * 开发者通过自有的alias向Android APP客户端进行推送, 可以针对单个或者一批alias进行推送，也可以将alias存放到文件进行发送。
	 * <span style="color:red"> <br/>
	 * <br/>
	 * alias:开发者自有账号, 开发者可以在SDK中调用setAlias(alias,
	 * alias_type)接口将alias+alias_type与device_token做绑定,
	 * 之后开发者就可以根据自有业务逻辑筛选出alias进行消息推送。
	 * </span>
	 * 
	 * @param ticker
	 *            通知栏提示文字
	 * 
	 * @param title
	 *            通知标题
	 * @param text
	 *            通知文字描述
	 * @param after_open
	 *            点击"通知"的后续行为，默认为打开AfterOpenEnum.GO_APP<span style="color:red;"><br/>
	 *            可选值：<br/>
	 *            AfterOpenEnum.GO_APP: 打开应用<br/>
	 *            AfterOpenEnum.GO_URL: 跳转到URL<br/>
	 *            AfterOpenEnum.GO_ACTIVITY: 打开特定的activity<br/>
	 *            AfterOpenEnum.GO_CUSTOM:用户自定义内容。
	 *            </span>
	 * @param objective
	 *            点击"通知"的后续行为目标<span style="color:red;"><br/>
	 *            当after_opende的值为AfterOpenEnum.GO_APP时objective传入null即可(该选项为默认值
	 *            ,传入任何参数都默认打开配置文件中所配置的APP客户端应用程序)<br/>
	 *            当单after_opende的值为AfterOpenEnum.GO_URL时objective表示要跳转到URL，
	 *            要求以http或者https开头 <br/>
	 *            当after_opende的值为AfterOpenEnum.GO_ACTIVITY时objective表示：
	 *            要打开的activity;<br/>
	 *            当after_opende的值为AfterOpenEnum.GO_CUSTOM时objective表示用户自定义内容;
	 *            可以为字符串或者JSON格式
	 *            </span>
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param alias_type
	 *            筛选条件所组成的JSON字符串
	 * @param alias
	 *            开发者填写自己的alias<br/>
	 *            要求不超过50个alias,多个alias以英文逗号间隔
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendAndroidCustomizedcast(String ticker , String title , String text , AfterOpenEnum after_open , Object objective , boolean production_mode , Map<String , Object> other , String alias_type , String ... alias) throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
		// 设置别名,使用逗号分割他们如果有多个别名
		customizedcast.setPredefinedKeyValue("alias" , Arrays.toString(alias).replace("]" , "").replace("[" , ""));
		customizedcast.setPredefinedKeyValue("alias_type" , alias_type);
		setAndroidBodyParams(ticker , title , text , after_open , objective , production_mode , customizedcast , other);
		return customizedcast.send();
	}
	
	/**
	 * 文件播;开发者将批量的device_token或者alias存放到文件, 通过文件ID进向Android APP客户端行消息发送
	 * * <span style="color:red"> <br/>
	 * <br/>
	 * device_token：友盟消息推送服务对设备的唯一标识。Android的device_token是44位字符串,
	 * iOS的device-token是64位。） <br/>
	 * <br/>
	 * alias:开发者自有账号, 开发者可以在SDK中调用setAlias(alias,
	 * alias_type)接口将alias+alias_type与device_token做绑定,
	 * 之后开发者就可以根据自有业务逻辑筛选出alias进行消息推送。
	 * </span>
	 * 
	 * @param ticker
	 *            通知栏提示文字
	 * 
	 * @param title
	 *            通知标题
	 * @param text
	 *            通知文字描述
	 * @param after_open
	 *            点击"通知"的后续行为，默认为打开AfterOpenEnum.GO_APP<span style="color:red;"><br/>
	 *            可选值：<br/>
	 *            AfterOpenEnum.GO_APP: 打开应用<br/>
	 *            AfterOpenEnum.GO_URL: 跳转到URL<br/>
	 *            AfterOpenEnum.GO_ACTIVITY: 打开特定的activity<br/>
	 *            AfterOpenEnum.GO_CUSTOM:用户自定义内容。
	 *            </span>
	 * @param objective
	 *            点击"通知"的后续行为目标<span style="color:red;"><br/>
	 *            当after_opende的值为AfterOpenEnum.GO_APP时objective传入null即可(该选项为默认值
	 *            ,传入任何参数都默认打开配置文件中所配置的APP客户端应用程序)<br/>
	 *            当单after_opende的值为AfterOpenEnum.GO_URL时objective表示要跳转到URL，
	 *            要求以http或者https开头 <br/>
	 *            当after_opende的值为AfterOpenEnum.GO_ACTIVITY时objective表示：
	 *            要打开的activity;<br/>
	 *            当after_opende的值为AfterOpenEnum.GO_CUSTOM时objective表示用户自定义内容;
	 *            可以为字符串或者JSON格式
	 *            </span>
	 * @param device_tokens
	 *            友盟消息推送服务对设备的唯一标识
	 * @param other
	 *            自定义参数
	 * 
	 * @throws Exception
	 */
	public boolean sendAndroidFilecast(String ticker , String title , String text , AfterOpenEnum after_open , Object objective , String ... device_tokens) throws Exception {
		AndroidFilecast filecast = new AndroidFilecast();
		inntParam(filecast);
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(String device_token : device_tokens) {
			sb.append(device_token);
			if(++index < device_tokens.length) {
				sb.append("\n");
			}
		}
		filecast.uploadContents(sb.toString());
		filecast.setPredefinedKeyValue("ticker" , ticker);
		filecast.setPredefinedKeyValue("title" , title);
		filecast.setPredefinedKeyValue("text" , text);
		filecast.setPredefinedKeyValue("after_open" , after_open.toString().toLowerCase());
		filecast.setPredefinedKeyValue("display_type" , "notification");
		return filecast.send();
	}
	
	/**
	 * 向安装该IOS App 客户端的所有设备发送消息
	 * 
	 * @param alert
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendIOSBroadcast(String alert , boolean production_mode , Map<String , Object> other) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast();
		inntParam(broadcast);
		setIOSBodyParams(alert , production_mode , broadcast , other);
		return broadcast.send();
	}
	
	/**
	 * 单播;向安装该IOS App 客户端 的指定设备发送消息，包括向单个device_token或者单个alias发消息
	 * <span style="color:red"> <br/>
	 * <br/>
	 * device_token：友盟消息推送服务对设备的唯一标识。Android的device_token是44位字符串,
	 * iOS的device-token是64位。） <br/>
	 * <br/>
	 * alias:开发者自有账号, 开发者可以在SDK中调用setAlias(alias,
	 * alias_type)接口将alias+alias_type与device_token做绑定,
	 * 之后开发者就可以根据自有业务逻辑筛选出alias进行消息推送。
	 * </span>
	 * 
	 * @param alert
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param device_token
	 *            友盟消息推送服务对设备的唯一标识。iOS的device-token是64位
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendIOSUnicast(String alert , boolean production_mode , String device_token , Map<String , Object> other) throws Exception {
		IOSUnicast unicast = new IOSUnicast();
		inntParam(unicast);
		unicast.setPredefinedKeyValue("device_tokens" , device_token);
		setIOSBodyParams(alert , production_mode , unicast , other);
		return unicast.send();
	}
	
	/**
	 * 组播;向IOS App 客户端满足特定条件的设备集合发送消息，例如:
	 * "特定版本"、"特定地域"等。友盟消息推送所支持的维度筛选和友盟统计分析所提供的数据展示维度是一致的，后台数据也是打通的
	 * 
	 * @param alert
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param filterJsn
	 *            筛选条件所组成的JSON字符串
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendIOSGroupcast(String alert , boolean production_mode , Object filterJsn , Map<String , Object> other) throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast();
		inntParam(groupcast);
		
		System.out.println(filterJsn.toString());
		
		groupcast.setPredefinedKeyValue("filter" , filterJsn);
		setIOSBodyParams(alert , production_mode , groupcast , other);
		return groupcast.send();
	}
	
	/**
	 * 开发者通过自有的alias向IOS APP客户端进行推送, 可以针对单个或者一批alias进行推送，也可以将alias存放到文件进行发送。
	 * <span style="color:red"> <br/>
	 * <br/>
	 * alias:开发者自有账号, 开发者可以在SDK中调用setAlias(alias,
	 * alias_type)接口将alias+alias_type与device_token做绑定,
	 * 之后开发者就可以根据自有业务逻辑筛选出alias进行消息推送。
	 * </span>
	 * 
	 * @param alert
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param alias_type
	 *            筛选条件所组成的JSON字符串
	 * @param alias
	 *            开发者填写自己的alias<br/>
	 *            要求不超过50个alias,多个alias以英文逗号间隔
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendIOSCustomizedcast(String alert , boolean production_mode , Map<String , Object> other , String alias_type , String ... alias) throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast();
		inntParam(customizedcast);
		customizedcast.setPredefinedKeyValue("alias" , Arrays.toString(alias).replace("]" , "").replace("[" , ""));
		customizedcast.setPredefinedKeyValue("alias_type" , alias_type);
		setIOSBodyParams(alert , production_mode , customizedcast , other);
		return customizedcast.send();
	}
	
	/**
	 * 开发者将批量的device_token或者alias存放到文件, 通过文件ID进向IOS APP客户端行消息发送
	 * * <span style="color:red"> <br/>
	 * <br/>
	 * device_token：友盟消息推送服务对设备的唯一标识。Android的device_token是44位字符串,
	 * iOS的device-token是64位。） <br/>
	 * <br/>
	 * alias:开发者自有账号, 开发者可以在SDK中调用setAlias(alias,
	 * alias_type)接口将alias+alias_type与device_token做绑定,
	 * 之后开发者就可以根据自有业务逻辑筛选出alias进行消息推送。
	 * </span>
	 * 
	 * @param alert
	 * @param production_mode
	 *            正式/测试模式。<span style="color:red;"><br/>
	 *            测试模式下，只会将消息发给测试设备。<br/>
	 *            测试设备需要到web上添加。<br/>
	 *            Android:测试设备属于正式设备的一个子集
	 *            </span>
	 * @param device_tokens
	 *            友盟消息推送服务对设备的唯一标识
	 * @param other
	 *            自定义参数
	 * @throws Exception
	 */
	public boolean sendIOSFilecast(String alert , boolean production_mode , Map<String , Object> other , String ... device_tokens) throws Exception {
		IOSFilecast filecast = new IOSFilecast();
		inntParam(filecast);
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for(String device_token : device_tokens) {
			sb.append(device_token);
			if(++index < device_tokens.length) {
				sb.append("\n");
			}
		}
		filecast.uploadContents(sb.toString());
		setIOSBodyParams(alert , production_mode , filecast , other);
		return filecast.send();
	}
	
	/**
	 * 通用发送推送信息
	 * 
	 * @param params
	 *            参数，参数值中必须包含一个键值为type的键值对
	 * @param mobilePhoneSystem
	 *            手机系统
	 * @return
	 */
	public boolean generalAndroidSendPush(Map<String , Object> params , MobilePhoneSystemEnum mobilePhoneSystem , boolean production_mode) {
		try {
			Object type = params.get("type");
			UmengNotification notification = UmengNotification.createNotification(type.toString() , mobilePhoneSystem.toString());
			if(notification != null) {
				Iterator<String> iter = params.keySet().iterator();
				String key = null;
				Object value = null;
				inntParam(notification);
				while (iter.hasNext()) {
					key = iter.next();
					value = params.get(key);
					if(!key.equals("extra")) {
						notification.setPredefinedKeyValue(key , value);
						// System.out.println(key + "--" +
						// String.valueOf(value));
					} else {
						@SuppressWarnings("unchecked")
						Map<String , Object> extraMap = (Map<String , Object>)value;
						Iterator<String> iterExtra = extraMap.keySet().iterator();
						while (iterExtra.hasNext()) {
							key = iterExtra.next();
							value = extraMap.get(key);
							notification.setExtraField(key , value);
						}
					}
				}
				notification.setPredefinedKeyValue("display_type" , "notification");
				notification.setPredefinedKeyValue("production_mode" , production_mode);
				notification.setPredefinedKeyValue("description" , params.get("title"));
			}
			return notification.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @return 得到 appkey的值
	 */
	public static String getAppkey(MobilePhoneSystemEnum mobilePhoneSystem) {
		if(mobilePhoneSystem.compareTo(MobilePhoneSystemEnum.IOS) == 0) {
			return getIOSAppkey();
		} else {
			return getAndroidAppkey();
		}
	}
	
	/**
	 * @param mobilePhoneSystem
	 *            手机系统
	 * @return 得到 appMasterSecret的值
	 */
	public static String getAppMasterSecret(MobilePhoneSystemEnum mobilePhoneSystem) {
		if(mobilePhoneSystem.compareTo(MobilePhoneSystemEnum.IOS) == 0) {
			return getIOSAppMasterSecret();
		} else {
			return getAndroidAppMasterSecret();
		}
	}
	
	/**
	 * @return 得到 androidAppkey的值
	 */
	public static String getAndroidAppkey() {
		return AndroidAppkey;
	}
	
	/**
	 * @return 得到 iOSAppkey的值
	 */
	public static String getIOSAppkey() {
		return IOSAppkey;
	}
	
	/**
	 * @return 得到 androidAppMasterSecret的值
	 */
	public static String getAndroidAppMasterSecret() {
		return AndroidAppMasterSecret;
	}
	
	/**
	 * @return 得到 iOSAppMasterSecret的值
	 */
	public static String getIOSAppMasterSecret() {
		return IOSAppMasterSecret;
	}
	
}
