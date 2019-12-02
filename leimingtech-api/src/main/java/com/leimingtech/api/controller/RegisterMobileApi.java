package com.leimingtech.api.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.util.PasswordUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;
import com.leimingtech.member.service.membermng.MemberGroupServiceI;
import com.leimingtech.ucenter.fivestars.interfaces.bbs.client.Client;

@Controller
@RequestMapping("/front/registerMobileApi")
public class RegisterMobileApi extends BaseController {
	private String message = null;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Autowired
	private MemberServiceI memberService;
	@Autowired
	private MemberGroupServiceI mgMng;
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	/**站点管理接口*/
	@Autowired
	private SiteServiceI siteService;

	/**
	 * 常规手机端会员注册
	 * 
	 * @param request
	 * @return
	 */
	public JSONObject generalRegister(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String logintype = request.getParameter("logintype");
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		MemberEntity member = new MemberEntity();
		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			beanApi.setResultCode("0");
			beanApi.setResultMsg("注册信息未填写完整");
			beanApi.setItems(null);
			json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
		} else {
			try {
				username = URLDecoder.decode(username, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			boolean cemail = memberService.checkUserEmail(email);
			boolean ishave = memberService.checkUserExits(username);
			if (ishave) {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("用户名被占用");
				beanApi.setItems(null);
				json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
			} else {
				if (cemail) {
					beanApi.setResultCode("0");
					beanApi.setResultMsg("该邮箱已绑定账号，请重新填写");
					beanApi.setItems(null);
					json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
				} else {
					String pwd = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());
					member.setUsername(username);
					member.setPassword(pwd);
					member.setEmail(email);
					member.setIs_cheked(1);
					member.setCreatetime(new Date());
					// 会员默认级别
					MemberGroupEntity mg = mgMng.getDefaultLevel();
					if (mg != null) {
						member.setMembergroupsid(mg.getId());
						member.setMemberlevel(mg.getUsergroupsname());

						// 添加会员个数
						if (null != mg.getNumbers()) {
							mg.setNumbers(mg.getNumbers() + 1);
						} else {
							mg.setNumbers(1);
						}
						mgMng.saveOrUpdate(mg);
					} else {
						member.setMembergroupsid(1+"");
						//FIXME Membergroupsid("1");  2015年5月19日17:12:18
						member.setMemberlevel("普通会员");
					}

					MemberEntity m = null;
					if (StringUtils.isNotEmpty(logintype)) {
						m = memberService.getMemberByUsername(username);
						if (null != m) {
							member.setThirdLogin_uid(username);
							member.setThirdType(logintype);
							memberService.save(member);
						}
					} else {
						memberService.save(member);
					}

					beanApi.setResultCode("1");
					beanApi.setResultMsg("注册成功");
					beanApi.setUserId(String.valueOf(member.getId()));
					beanApi.setUserName(member.getRealname());
					beanApi.setFaceImg(member.getFaceImg());

					try {
						memberService.createMemberCard(member);// 生成会员卡图
					} catch (IOException e) {
						e.printStackTrace();
					}

					beanApi.setCardPath(member.getCardPath());
					json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
				}
			}
		}
		return json;
	}

	/**
	 * UCenter手机端会员注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register")
	@ResponseBody
	public JSONObject register(HttpServletRequest request) {
		if(siteService.ucenterIsOpen()){
			return ucenterRegister(request);
		}
		
		return generalRegister(request);
	}
	
	/**
	 * UCenter手机端会员注册
	 * 
	 * @param request
	 * @return
	 */
	public JSONObject ucenterRegister(HttpServletRequest request) {
		String username = request.getParameter("username");
		String logintype = request.getParameter("logintype");
		try {
			username = URLDecoder.decode(username, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		MemberEntity member = new MemberEntity();

		int $uid = 1;
		if (StringUtils.isEmpty(logintype)) {
			Client uc = new Client();

			// setcookie('Example_auth', '', -86400);
			// 生成同步退出的代码
			String $returns = uc.uc_user_register(username, password, email);
			$uid = Integer.parseInt($returns);
		}
		if ($uid <= 0) {
			if ($uid == -1) {
				beanApi.setResultMsg("用户名不合法");
			} else if ($uid == -2) {
				beanApi.setResultMsg("包含要允许注册的词语");
			} else if ($uid == -3) {
				beanApi.setResultMsg("用户名已经存在");
			} else if ($uid == -4) {
				beanApi.setResultMsg("Email 格式有误");
			} else if ($uid == -5) {
				beanApi.setResultMsg("Email 不允许注册");
			} else if ($uid == -6) {
				beanApi.setResultMsg("该 Email 已经被注册");
			} else {
				beanApi.setResultMsg("未定义");
			}
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
		} else {
			if (org.apache.commons.lang3.StringUtils.isEmpty(logintype)) {
				//非第三方登陆
				// System.out.println("OK:"+$returns);
				String pwd = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());
				member.setUsername(username);
				member.setPassword(pwd);
				member.setEmail(email);
				member.setIs_cheked(1);
				member.setCreatetime(new Date());
				// 会员默认级别
				MemberGroupEntity mg = mgMng.getDefaultLevel();
				if (mg != null) {
					member.setMembergroupsid(mg.getId());
					member.setMemberlevel(mg.getUsergroupsname());

					// 添加会员个数
					if (null != mg.getNumbers()) {
						mg.setNumbers(mg.getNumbers() + 1);
					} else {
						mg.setNumbers(1);
					}
					mgMng.saveOrUpdate(mg);
				} else {
					member.setMembergroupsid(1+"");
					//FIXME Membergroupsid("1");  2015年5月19日17:12:18
					member.setMemberlevel("普通会员");
				}

				MemberEntity m = null;
				if (StringUtils.isNotEmpty(logintype)) {
					m = memberService.getMemberByUsername(username);
					if (null == m) {
						member.setThirdLogin_uid(username);
						member.setThirdType(logintype);
						memberService.save(member);
					} else {
						member.setId(m.getId());
					}
				} else {
					memberService.save(member);
				}

				beanApi.setResultCode("1");
				beanApi.setResultMsg("注册成功");
				beanApi.setUserId(String.valueOf(member.getId()));
				beanApi.setUserName(member.getRealname());
				beanApi.setFaceImg(member.getFaceImg());
				beanApi.setItems(null);

				try {
					memberService.createMemberCard(member);// 生成会员卡图
				} catch (IOException e) {
					e.printStackTrace();
				}

				beanApi.setCardPath(member.getCardPath());
				json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
			} else {
				//第三方登陆
				if(memberService.checkUserExits(username)){
					beanApi.setResultMsg("用户名已经存在");
					beanApi.setResultCode("0");
					beanApi.setItems(null);
					json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
				}else{
					member=createNewMember(username,password,email,new MemberEntity());
					try {
						memberService.createMemberCard(member);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					beanApi.setResultCode("1");
					beanApi.setResultMsg("注册成功");
					beanApi.setUserId(String.valueOf(member.getId()));
					beanApi.setUserName(member.getUsername());
					beanApi.setFaceImg(member.getFaceImg());
					beanApi.setItems(null);
					beanApi.setCardPath(member.getCardPath());
					json = json.fromObject(beanApi, mobileChannelService.getJsonConfig());
				}
			}
			
		}

		return json;
	}
	
	/**
	 *  创建新用户
	 * @param username
	 * @param password
	 * @param email
	 * @param localMember
	 */
	private MemberEntity createNewMember(String username,String password,String email,MemberEntity localMember){
		String pwd = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());
		localMember.setUsername(username);
		localMember.setPassword(pwd);
		localMember.setEmail(email);
		localMember.setIs_cheked(1);
		localMember.setCreatetime(new Date());
		// 会员默认级别
		MemberGroupEntity mg = mgMng.getDefaultLevel();
		if (mg != null) {
			localMember.setMembergroupsid(mg.getId());
			localMember.setMemberlevel(mg.getUsergroupsname());
			
			// 添加会员个数
			if(null!=mg.getNumbers()){
				mg.setNumbers(mg.getNumbers() + 1);
			}else{
				mg.setNumbers(1);
			}
			mgMng.saveOrUpdate(mg);
		} else {
			localMember.setMembergroupsid(1+"");
			//FIXME Membergroupsid("1");  2015年5月19日17:12:18
			localMember.setMemberlevel("普通会员");
		}
		if(localMember.getId()==null){
			memberService.save(localMember);
		}else{
			memberService.saveOrUpdate(localMember);
		}
		return localMember;
	}

}
