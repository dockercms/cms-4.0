package com.leimingtech.wechat.timetask;

import com.leimingtech.wechat.service.wechatbutton.WechatButtonServiceI;
import com.leimingtech.wechat.service.wechatuser.WechatUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 定时获取微信用户的access_token
 * 
 * @author 谢进伟
 * 
 * @date 2015-8-12 下午11:30:10
 */
@Component
public class WechatTimeTask {
	
	@Autowired
	private WechatUserServiceI wechatUserServiceI;
	@Autowired
	private WechatButtonServiceI sechatButtonServiceI;
	
	/**
	 * <b>自动同步微信号access_token</b><br>
	 * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。<br>
	 * access_token的存储至少要保留512个字符空间<br>
	 * 。access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。<br>
	 * 公众平台的API调用所需的access_token的使用及生成方式说明：<br>
	 * 1、为了保密appsecrect，第三方需要一个access_token获取和刷新的中控服务器。<br>
	 * 而其他业务逻辑服务器所使用的access_token均来自于该中控服务器，不应该各自去刷新，否则会造成access_token覆盖而影响业务；<br>
	 * 2、目前access_token的有效期通过返回的expire_in来传达，目前是7200秒之内的值。<br>
	 * 中控服务器需要根据这个有效时间提前去刷新新access_token<br>
	 * 。在刷新过程中，中控服务器对外输出的依然是老access_token，此时公众平台后台会保证在刷新短时间内<br>
	 * ，新老access_token都可用，这保证了第三方业务的平滑过渡；<br>
	 * 3、access_token的有效时间可能会在未来有调整，所以中控服务器不仅需要内部定时主动刷新，
	 * 还需要提供被动刷新access_token的接口<br>
	 * 
	 * ，这样便于业务服务器在API调用获知access_token已超时的情况下，可以触发access_token的刷新流程。<br>
	 */
	//FIXME 微信定时任务
//	@Scheduled(fixedDelay = 1000)
//	public void AutoGetWechatUserAccessToken() {
//		wechatUserServiceI.autoGetWechatUserAccessToken();
//	}
	
	/**
	 * 每天晚上12整,自动同步微信服务器端的菜单
	 * TODO 此功能待完成
	 */
//	@Scheduled(cron = "0 0 12 * * ?")
//	public void AutogetWechatServerMenus() {
//		sechatButtonServiceI.autoGetWechatServerAllMenus();
//	}
}
