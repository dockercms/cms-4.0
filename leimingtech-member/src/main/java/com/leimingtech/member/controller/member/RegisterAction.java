package com.leimingtech.member.controller.member;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.util.PasswordUtil;
import com.leimingtech.core.util.oConvertUtils;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;
import com.leimingtech.member.service.membermng.MemberGroupServiceI;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员注册
 * 
 * @author liuzhen 2014年5月27日 14:38:15
 * 
 */
@Controller
@RequestMapping("/member/registerAct")
public class RegisterAction extends BaseController {

	private String message;

	@Autowired
	private MemberServiceI memberMng;// 会员管理

	@Autowired
	private MemberGroupServiceI mgMng;// 会员组管理

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@RequestMapping(params = "register")
	public ModelAndView register(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sitePath", memberMng.getSitePath());
		m.put("title", "注册");
		return new ModelAndView("member/register",m);
	}

	@RequestMapping(params = "checkregister")
	@ResponseBody
	public String checkregister(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String randCode = session.getAttribute("randCode").toString();
		String username = request.getParameter("username");
		String valicode = request.getParameter("valicode");
		String reg_agreement = request.getParameter("reg_agreement");
		String confirm_password = request.getParameter("confirm_password");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		JSONObject j = new JSONObject();
		if (StringUtils.isEmpty(username)) {
			j.put("code", "1");
			j.put("info", "用户名不能为空！");
			return j.toString();
		}
		if (memberMng.checkUserExits(username)) {
			j.put("code", "1");
			j.put("info", "用户名已被注册！");
			return j.toString();
		}

		if (StringUtils.isEmpty(confirm_password) || StringUtils.isEmpty(password)) {
			j.put("code", "1");
			j.put("info", "密码不能为空！");
			return j.toString();
		}

		if (!confirm_password.equals(password)) {
			j.put("code", "1");
			j.put("info", "两次密码不一致！");
			return j.toString();
		}
		if (StringUtils.isEmpty(reg_agreement) || !reg_agreement.equals("on")) {
			j.put("code", "1");
			j.put("info", "请确认已阅读条款和协议后点击注册！");
			return j.toString();
		}

		if (StringUtils.isEmpty(valicode) || !valicode.equalsIgnoreCase(randCode)) {
			j.put("code", "1");
			j.put("info", "验证码不正确！");
			return j.toString();
		}
		MemberEntity member = new MemberEntity();
		member.setUsername(username);
		member.setCreatetime(new Date());
		member.setRegisterip(oConvertUtils.getIp());
		member.setIs_cheked(1);
		// FIXME liuzhen 2014年5月27日 18:08:37 现在不是邮箱注册先默认已验证
		member.setMp(0);
		member.setLogincount(0);
		member.setPoint(0);
		member.setName(username);
		member.setEmail(email);

		String pwd = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());
		member.setPassword(pwd);

		// 会员默认级别
		MemberGroupEntity mg = mgMng.getDefaultLevel();
		if (mg != null) {
			member.setMembergroupsid(mg.getId());
			member.setMemberlevel(mg.getUsergroupsname());

			// 添加会员个数
			mg.setNumbers(mg.getNumbers() + 1);
			mgMng.saveOrUpdate(mg);
		} else {
			member.setMembergroupsid(1+"");
			//FIXME Membergroupsid("1");  2015年5月19日17:12:18
			member.setMemberlevel("普通会员");
		}
		member.setLogincount(0);
		memberMng.save(member);
		j.put("code", "0");
		j.put("info", "恭喜你注册成功，快来登录吧！");
		j.put("url", "loginAct.do?login&username=" + username);
		return j.toString();
	}

	/**
	 * 验证用户名是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkUsername")
	@ResponseBody
	public Boolean checkUsername(HttpServletRequest request) {
		String username = request.getParameter("username");
		boolean ishave = memberMng.checkUserExits(username);
		if(ishave){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 验证验证码是否正确
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkValicode")
	@ResponseBody
	public Boolean checkValicode(HttpServletRequest request) {
		HttpSession session = ContextHolderUtils.getSession();
		String randCode = session.getAttribute("randCode").toString();
		String valicode = request.getParameter("valicode");
		if(valicode.equalsIgnoreCase(randCode)){
			return true;
		}else{
			return false;
		}
	}
	
}
