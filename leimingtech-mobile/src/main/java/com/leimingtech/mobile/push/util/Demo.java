/**
 * 
 */
package com.leimingtech.mobile.push.util;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.leimingtech.mobile.push.utl.enums.AfterOpenEnum;
import com.leimingtech.mobile.push.utl.enums.MobilePhoneSystemEnum;


/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-1-7 下午2:29:49
 */
public class Demo {
	
	/**
	 * Android广播测试——方法一
	 * */
	@Test
	public void test() {
		try {
			long nanoTime=System.nanoTime();
			String ticker = "雷铭消息推送测试"+nanoTime;
			System.out.println(ticker);
			String title = "今日头条"+nanoTime;
			String text = "【导语】：在昨天公开的90余条意见中，对于公交、地铁票价“涨不涨”，呈现出四类观点，第一类认为应维持现状，不应调价；第二";
			AfterOpenEnum after_open=AfterOpenEnum.GO_APP;
			boolean production_mode=false;
			
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("type" ,"broadcast");
			params.put("ticker" ,ticker);
			params.put("title" ,title);
			params.put("text" ,text);
			params.put("after_open" ,after_open.toString().toLowerCase());
			params.put("production_mode" ,production_mode);
			params.put("play_lights" ,true);
			params.put("play_sound" ,true);
			params.put("play_vibrate" ,true);
			params.put("description" ,title);
			
			boolean result = PushUtil.instance(MobilePhoneSystemEnum.ANDROID).generalAndroidSendPush(params , MobilePhoneSystemEnum.ANDROID,true);//sendAndroidBroadcast(ticker , title , text , after_open , objective , production_mode);
			System.out.println(result ? "测试成功" : "测试失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Android广播测试——方法二
	 * */
	@Test
	public void test01() {
		try {
			long nanoTime=System.nanoTime();
			String ticker = "雷铭消息推送测试"+nanoTime;
			System.out.println(ticker);
			String title = "今日头条"+nanoTime;
			String text = "【导语】：在昨天公开的90余条意见中，对于公交、地铁票价“涨不涨”，呈现出四类观点，第一类认为应维持现状，不应调价；第二";
			AfterOpenEnum after_open=AfterOpenEnum.GO_APP;
			Object objective=null;
			boolean production_mode=false;
			boolean result = PushUtil.instance(MobilePhoneSystemEnum.ANDROID).sendAndroidBroadcast(ticker , title , text , after_open , objective , production_mode,null);
			System.out.println(result ? "测试成功" : "测试失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Android单播测试
	 * */
	@Test
	public void test02() {
		try {
			long nanoTime=System.nanoTime();
			String ticker = "雷铭消息推送测试"+nanoTime;
			System.out.println(ticker);
			String title = "今日头条"+nanoTime;
			String text = "在去年四月，缅甸宣布禁止原木出口，以减少森林砍伐，以及遏制完全依赖高附加值产品出口的人造板工业的恶性发展。";
			AfterOpenEnum after_open=AfterOpenEnum.GO_APP;
			Object objective=null;
			boolean production_mode=false;
			String device_token="Ao4W0LVEAtT7cga2Req_g1ywPelRFB3f6SHwddHaXkVn";
			boolean result = PushUtil.instance(MobilePhoneSystemEnum.ANDROID).sendAndroidUnicast(ticker , title , text , after_open , objective , production_mode , device_token,null);
			System.out.println(result ? "测试成功" : "测试失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * IOS单播测试
	 * */
	@Test
	public void test03() {
		try {
			long nanoTime=System.nanoTime();
			String alert = "雷铭消息推送测试"+nanoTime;
			System.out.println(alert);
			boolean production_mode=false;
			String device_token="Ao4W0LVEAtT7cga2Req_g1ywPelRFB3f6SHwddHaXkVn";
			boolean result = PushUtil.instance(MobilePhoneSystemEnum.IOS).sendIOSUnicast(alert , production_mode , device_token,null);
			System.out.println(result ? "测试成功" : "测试失败");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
