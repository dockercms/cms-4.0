package com.leimingtech.api.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.entity.member.MemberEntity;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.SmsServiceI;
import com.leimingtech.core.util.EncryptionUtil;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.SendMailUtil;
import com.leimingtech.mobile.service.feedback.ForgetPasswordServiceI;

/**
 * 忘记密码
 * @author baorui
 *
 */
@Controller
@RequestMapping("/front/forgetPasswordApi")
public class ForgetPasswordApi {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ForgetPasswordApi.class);
	
	private String message;
	@Autowired
	private SmsServiceI smsService;
	@Autowired
	private MemberServiceI memberService;
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	@Autowired
	private ForgetPasswordServiceI forgetPasswordService;
	
	/**
	 * 验证用户名，邮箱以及发送邮件
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPassword")
	@ResponseBody
	public JSONObject getPassword(HttpServletRequest request){
		String username = request.getParameter("username");
		String useremail = request.getParameter("useremail");
		JSONObject json = forgetPasswordService.getPassword(username, useremail);
		
		return json;
	}
	/**
	 * 验证用户是否存在
	 * @return
	 */
	@RequestMapping(value = "/checkUserExits")
	@ResponseBody
	public JSONObject checkUserExits(){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String username = request.getParameter("username");
		BeanApi beanApi = new BeanApi();
		JSONObject json = new JSONObject();
		
		MemberEntity member = new MemberEntity();
		
		try {
			 username = URLDecoder.decode(username,"utf-8");
		     member=memberService.getMemberByUsername(username);
			if(member!=null){
				beanApi.setResultCode("1");
				beanApi.setResultMsg("用户名存在");
				beanApi.setUserId(String.valueOf(member.getId()));
				beanApi.setUserName(member.getUsername());
				beanApi.setFaceImg(member.getFaceImg());
				beanApi.setEmail(member.getEmail()==null?"":member.getEmail());
				beanApi.setTel(member.getCellphone()==null?"":member.getCellphone());
				beanApi.setItems(null);
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
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
		System.out.println(json.toString());
		return json;
		
	}
	
	
	/**
	 * 验证邮箱是否存在
	 * @return
	 */
	@RequestMapping(value = "/checkEmailExits")
	@ResponseBody
	public JSONObject checkEmailExits(){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String username = request.getParameter("username");
		String useremail = request.getParameter("useremail");
		BeanApi beanApi = new BeanApi();
		JSONObject json = new JSONObject();
		
		MemberEntity member = new MemberEntity();
		
		try {
			 username = URLDecoder.decode(username,"utf-8");
		     member=memberService.getMemberByUsername(username);
			if(member!=null){
				
				if(useremail.equals(member.getEmail())){
					beanApi.setResultCode("1");
					beanApi.setResultMsg("邮箱存在");
					beanApi.setUserId(String.valueOf(member.getId()));
					beanApi.setUserName(member.getUsername());
					beanApi.setFaceImg(member.getFaceImg());
					beanApi.setEmail(member.getEmail()==null?"":member.getEmail());
					beanApi.setTel(member.getCellphone()==null?"":member.getCellphone());
					beanApi.setItems(null);
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}else{
					beanApi.setResultCode("0");
					beanApi.setResultMsg("邮箱不存在");
					beanApi.setUserId(String.valueOf(member.getId()));
					beanApi.setUserName(member.getUsername());
					beanApi.setFaceImg(member.getFaceImg());
					beanApi.setEmail(member.getEmail()==null?"":member.getEmail());
					beanApi.setTel(member.getCellphone()==null?"":member.getCellphone());
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
		System.out.println(json.toString());
		return json;
		
	}
	/**
	 * 手机找回  --暂留(未完成)
	 * @return
	 */
	@RequestMapping(value = "/phoneForge")
	@ResponseBody
	public JSONObject phoneForge(){
		
		String message="效验码："+"ABC12"+"(请勿泄漏5分钟内有效),若非本人操作,请勿理会。【雷铭CMS】";
	//	Date date=new Date();
	//	SimpleDateFormat simple=new SimpleDateFormat("");
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String tel = request.getParameter("tel");
		BeanApi beanApi = new BeanApi();
		JSONObject json = new JSONObject();
		
		MemberEntity member = new MemberEntity();
		
		try {
			if(StringUtil.isNotEmpty(tel)){
				tel = URLDecoder.decode(tel,"utf-8");
			}
			if(tel!=null || !"".equals(tel)){
				String  result = smsService.sendSMS(tel,message,"");
				beanApi.setResultCode("1");
				beanApi.setResultMsg(result);
				beanApi.setItems(null);
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				beanApi.setResultCode("0");
				beanApi.setResultMsg("手机号为空,发送失败，请保证手机号码存在.");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}
			
			
		} catch (Exception e) {
			beanApi.setResultCode("0");
			beanApi.setResultMsg("短信发送失败，请稍后重试");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		System.out.println(json.toString());
		return json;
		
	}
	
	
	
	/**
	 * 发送邮件
	 * @return
	 */
	@RequestMapping(value = "sendemail")
	@ResponseBody
	public JSONObject sendEmail(HttpServletRequest request){
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String domain = PlatFormUtil.getDomail();
		JSONObject j = new JSONObject();
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
			message = "邮件已发送！";
			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "MemberAct.do?sendEmailSuccess&email=" + email);
		} catch (Exception e) {
			e.printStackTrace();
			message = "邮件发送失败！";
			j.accumulate("isSuccess", false);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "MemberAct.do?findpassword");
		}
		String str = j.toString();
		return j;
	}
	
	
	public static void main(String[] args) {
		ForgetPasswordApi  passwordApi=new ForgetPasswordApi();
		JSONObject json =passwordApi.checkUserExits();
		System.out.println(json.toString());
	}
	

}
