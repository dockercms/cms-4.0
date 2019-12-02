package com.leimingtech.mobile.service.impl.feedback;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.EncryptionUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.SendMailUtil;
import com.leimingtech.mobile.service.feedback.ForgetPasswordServiceI;
@Service("forgetPasswordService")
@Transactional
public class ForgetPasswordServiceImpl extends CommonServiceImpl implements ForgetPasswordServiceI{

	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	@Autowired
	private MemberServiceI memberService;
	
	@Override
	public JSONObject getPassword(String username, String useremail) {
		BeanApi beanApi = new BeanApi();
		JSONObject json = new JSONObject();
		MemberEntity member = new MemberEntity();
		try {
			 username = URLDecoder.decode(username,"utf-8");
		     member=memberService.getMemberByUsername(username);
			if(member!=null){
				if(useremail.equals(member.getEmail())){
					json = sendEmail(username,useremail);
				}else{
					beanApi.setResultCode("0");
					beanApi.setResultMsg("邮箱不存在");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
				
				
			}else{
				beanApi.setResultCode("0");
				beanApi.setResultMsg("用户名不存在");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}
		} catch (Exception e) {
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，请稍后重试");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	/**
	 * 发送Email邮件
	 * @param username
	 * @param email
	 * @return
	 */
	public JSONObject sendEmail(String username,String email){
		String domain = PlatFormUtil.getDomail();
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			String initcode = username + "," + System.currentTimeMillis();
			String url = domain + "/member/MemberAct.do?editPassword&s=" + EncryptionUtil.authcode(initcode, "ENCODE", "", 0);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("url", url);
			data.put("username", username);
			// 找回密码模板路径
			String path = this.getClass().getResource("").getPath();
			path = path.substring(0, path.indexOf("WEB-INF")) + "member/emailtemplate.html";
			
			SendMailUtil.sendMailByTemplate(email, "找回密码", path, data);
			beanApi.setResultCode("1");
			beanApi.setResultMsg("邮件已发送，请登录"+email+"查看");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		} catch (Exception e) {
			e.printStackTrace();
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，请稍后重试");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
}
