package com.leimingtech.member.controller.member;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * CMS前台用户
 * 
 * @author liuzhen 2014年6月4日 20:07:37
 * 
 */
@Controller
@RequestMapping("/member/MemberAct")
public class MemberAction extends BaseController {

	@Autowired
	private MemberServiceI memberMng;

	@Autowired
	private SystemService systemMng;

	private String message;

	/**
	 * 个人信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "personal")
	public ModelAndView personal(HttpServletRequest reqeust) {
		MemberEntity member = memberMng.getSessionMember();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", member);
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		m.put("sitePath", memberMng.getSitePath());
		return new ModelAndView("member/personalinfo", m);
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "passwordModify")
	public ModelAndView passwordModify(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		m.put("sitePath", memberMng.getSitePath());
		return new ModelAndView("member/passwordmodify", m);
	}

	/**
	 * 保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "savePassword")
	@ResponseBody
	public String savePassword(HttpServletRequest requset, String keyword, String password, String repassword) {
		MemberEntity m = memberMng.getSessionMember();

		JSONObject j = new JSONObject();
		if (StringUtil.isEmpty(keyword)) {
			j.accumulate("isSuccess", false);
			message = "原密码不能为空";
		} else if (StringUtil.isEmpty(password) || StringUtil.isEmpty(repassword) || !password.equals(repassword)) {
			j.accumulate("isSuccess", false);
			message = "两次密码不一致";
		}

		String oldpwd = PasswordUtil.encrypt(m.getUsername(), keyword, PasswordUtil.getStaticSalt());
		System.out.println(oldpwd+"以前密码");
		if (oldpwd.equals(m.getPassword())) {
			message = "密码修改成功，重新登陆时生效！";
			try {
				m.setPassword(PasswordUtil.encrypt(m.getUsername(), password, PasswordUtil.getStaticSalt()));
				memberMng.saveOrUpdate(m);
				j.accumulate("isSuccess", true);
				systemMng.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				j.accumulate("isSuccess", false);
				message = "密码修改失败";
			}
		} else {
			j.accumulate("isSuccess", false);
			message = "原密码不正确！";
		}
		j.accumulate("msg", message);
		j.accumulate("toUrl", "MemberAct.do?passwordModify");
		String str = j.toString();
		return str;
	}

	/**
	 * 会员修改个人信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "updateInfo")
	@ResponseBody
	public String updateInfo(MemberEntity user, HttpServletRequest reqeust) {
		MemberEntity member = memberMng.getSessionMember();

		JSONObject j = new JSONObject();
		try {
			user.setUsername(null);
			MyBeanUtils.copyBeanNotNull2Bean(user, member);
			memberMng.saveOrUpdate(member);
			j.accumulate("isSuccess", true);
			message = "信息更新成功";
		} catch (Exception e) {
			e.printStackTrace();
			j.accumulate("isSuccess", false);
			message = "信息更新失败";
		}

		j.accumulate("msg", message);
		j.accumulate("toUrl", "MemberAct.do?personal");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 忘记密码
	 * @return
	 */
	@RequestMapping(params = "findpassword")
	public ModelAndView findPassword(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		m.put("sitePath", memberMng.getSitePath());
		m.put("title", "找回密码");
		return new ModelAndView("member/findpassword", m);
	}
	
	/**
	 * 验证用户名与邮箱是否为同一个用户的
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "checkNameAndEmail")
	@ResponseBody
	public boolean checkNameAndEmail(HttpServletRequest req, HttpServletResponse response) {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		boolean haveuser = memberMng.checkNameAndEmail(username, email);
		return haveuser;
	}
	
	/**
	 * 发送邮件
	 * @return
	 */
	@RequestMapping(params = "sendemail")
	@ResponseBody
	public String sendEmail(HttpServletRequest request){
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
		return str;
	}
	
	/**
	 * 邮件发送成功
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "sendEmailSuccess")
	public ModelAndView sendEmailSuccess(HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		String email = request.getParameter("email");
		String[] str = email.split("@");
		String emailexe = str[1];
		m.put("email", email);
		m.put("title", "找回密码");
		if(SendMailUtil.emailMap.containsKey(emailexe)){
			m.put("mailurl", SendMailUtil.emailMap.get(emailexe));
		}
		
		return new ModelAndView("member/sendemailsuccess", m);
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(params = "editPassword")
	public ModelAndView editPassword(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		String s = request.getParameter("s");
		if(StringUtils.isEmpty(s)){  // 参数没有
			m.put("title", "错误提示");
			m.put("errortitle", "找回密码错误");
			m.put("errotcontent", "非法链接地址，请重新找回。<a href='MemberAct.do?findpassword'>点击重新找回</a>");
			return new ModelAndView("member/error", m);
		}
		String str = EncryptionUtil.authcode(s, "DECODE", "", 0);
		String[] array = StringUtils.split(str, ",");
		if(array.length != 2){       // 参数不对
			m.put("title", "错误提示");
			m.put("errortitle", "找回密码错误");
			m.put("errotcontent", "验证字符串不正确，请重新找回。<a href='MemberAct.do?findpassword'>点击重新找回</a>");
			return new ModelAndView("member/error", m);
		}
		String username = array[0];
		MemberEntity member = memberMng.getMemberByUsername(username);
		if(null == member){          // 用户不存在
			m.put("title", "错误提示");
			m.put("errortitle", "找回密码错误");
			m.put("errotcontent", "验证字符串不正确，请重新找回。<a href='MemberAct.do?findpassword'>点击重新找回</a>");
			return new ModelAndView("member/error", m);
		}
		long time = Long.parseLong(array[1]);
		long nowtime = System.currentTimeMillis();
		int h = 1000 * 60 * 60 * 24;
		if(nowtime - time > h){      // 地址过期
			m.put("title", "错误提示");
			m.put("errortitle", "找回密码错误");
			m.put("errotcontent", "地址已过期，请重新找回。<a href='MemberAct.do?findpassword'>点击重新找回</a>");
			return new ModelAndView("member/error", m);
		}
		m.put("username", username);
		m.put("sitePath", memberMng.getSitePath());
		m.put("title", "修改密码");
		m.put("site",  PlatFormUtil.getFrontSessionSite());
		return new ModelAndView("member/editpassword", m);
	}
	
	/**
	 * 保存修改密码
	 * @return
	 */
	@RequestMapping(params = "saveEditPassword")
	public ModelAndView saveEditPassword(HttpServletRequest requset) {
		Map<String, Object> map = new HashMap<String, Object>();
		String password = requset.getParameter("password");
		String username = requset.getParameter("username");
		MemberEntity m = memberMng.getMemberByUsername(username);
		try {
			m.setPassword(PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt()));
			memberMng.saveOrUpdate(m);
			message = "密码修改成功";
			systemMng.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			map.put("title", "提示");
			map.put("errortitle", "密码修改成功");
			map.put("errotcontent", "密码成功找回，请<a href='loginAct.do?login'>登录</a>");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("title", "提示");
			map.put("errortitle", "密码修改失败");
			map.put("errotcontent", "密码找回失败，请点击<a href='MemberAct.do?findpassword'>重新找回</a>");
		}
		return new ModelAndView("member/error", map);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public static void main(String[] args) {
		System.out.println(PasswordUtil.encrypt("kviuff", "666666", PasswordUtil.getStaticSalt()));
	}

}
