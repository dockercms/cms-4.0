package com.leimingtech.api.controller;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.PasswordUtil;
import com.leimingtech.core.util.browser.BrowserUtils;
import com.leimingtech.core.util.date.DataUtils;
import com.leimingtech.member.entity.membermng.MemberGroupEntity;
import com.leimingtech.member.service.membermng.MemberGroupServiceI;
import com.leimingtech.ucenter.fivestars.interfaces.bbs.client.Client;
import com.leimingtech.ucenter.fivestars.interfaces.bbs.util.XMLHelper;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/front/loginMobileApi")
public class LoginMobileApi extends BaseController {
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
	private MobileChannelServiceI mobileChannelService;
	@Autowired
	private MemberGroupServiceI memberGroupService;
	/** 站点管理接口 */
	@Autowired
	private SiteServiceI siteService;

	/**
	 * 手机端会员登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public JSONObject login(HttpServletRequest request) {

		String username = request.getParameter("username");
		String logintype = request.getParameter("logintype");
		try {
			username = URLDecoder.decode(username, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String password = request.getParameter("password");

		// 站点中是否开启ucenter
		if (siteService.ucenterIsOpen()) {
			return ucenterLogin(username, password, logintype);// ucenter登录方式
		}

		// 系统登录方式
		return systemLogin(username, password, logintype);
	}

	/**
	 * 会员系统登录方式
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param logintype
	 *            登录方式，有第三方登录方式
	 * @return
	 */
	private JSONObject systemLogin(String username, String password,
			String logintype) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();

		MemberEntity member = new MemberEntity();
		member.setUsername(username);
		member.setPassword(password);
		member = memberService.checkUserExits(member);
		if (StringUtils.isEmpty(logintype)) {
			if (member != null) {
				beanApi.setResultCode("1");
				beanApi.setUserId(member.getId());
				beanApi.setUserName(member.getRealname());
				beanApi.setFaceImg(member.getFaceImg());
				if (StringUtils.isEmpty(member.getCardPath())) {
					try {
						memberService.createMemberCard(member);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				beanApi.setCardPath(member.getCardPath());
				return json.fromObject(beanApi,
						mobileChannelService.getJsonConfig());
			} else {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("用户名或密码错误");
				HttpServletRequest request = ContextHolderUtils.getRequest();
				LogUtil.warning("会员登录失败，用户名或密码错误，操作者ip："+IpUtil.getIpAddr(request)+",浏览器："+BrowserUtils.checkBrowse(request)+",当前时间:"+DataUtils.formatTime());
				return json.fromObject(beanApi,
						mobileChannelService.getJsonConfig());
			}
		} else {

			String $email = username + "@qq.com";

			if (member == null) {
				if (memberService.checkUserExits(username)) {
					MemberEntity localMember = memberService
							.findUniqueByProperty(MemberEntity.class,
									"username", username);
					if (localMember != null) {
						member = createNewMember(username, password, $email,
								localMember);
					}
				} else {
					member = createNewMember(username, password, $email,
							new MemberEntity());
					try {
						memberService.createMemberCard(member);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			beanApi.setResultCode("1");
			beanApi.setResultMsg("登录成功");
			beanApi.setUserId(member.getId());
			beanApi.setUserName(member.getRealname());
			beanApi.setFaceImg(member.getFaceImg());
			beanApi.setCardPath(member.getCardPath());
			return json.fromObject(beanApi,
					mobileChannelService.getJsonConfig());
		}
	}

	/**
	 * ucenter登录方式
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param logintype
	 *            登录类型，有第三方登录方式
	 * @return
	 */
	private JSONObject ucenterLogin(String username, String password,
			String logintype) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();

		LinkedList<String> rs = new LinkedList<String>();
		Client e = new Client();
		String result = "";
		if (StringUtils.isEmpty(logintype)) {
			rs.add(1 + "");
			rs.add(username);
			rs.add(password);
			rs.add(username + "@qq.com");
		} else {
			result = e.uc_user_login(username, password);
			rs = XMLHelper.uc_unserialize(result);
		}

		if (rs.size() > 0) {
			int $uid = Integer.parseInt(rs.get(0));
			String $username = rs.get(1);
			String $password = rs.get(2);
			String $email = rs.get(3);
			if ($uid > 0) {
				String $ucsynlogin = e.uc_user_synlogin($uid);

				MemberEntity member = new MemberEntity();
				member.setUsername(username);
				member.setPassword(password);
				member = memberService.checkUserExits(member);
				if (StringUtils.isEmpty(logintype)) {
					if (member != null) {
						beanApi.setResultCode("1");
						beanApi.setResultMsg("登录成功");
						beanApi.setUserId(member.getId());
						beanApi.setUserName(member.getRealname());
						beanApi.setFaceImg(member.getFaceImg());
						if (StringUtils.isEmpty(member.getCardPath())) {
							try {
								memberService.createMemberCard(member);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						beanApi.setCardPath(member.getCardPath());
						beanApi.setItems(null);
						return json.fromObject(beanApi,
								mobileChannelService.getJsonConfig());
					} else {
						beanApi.setResultCode("0");
						beanApi.setResultMsg("用户名或密码错误");
						beanApi.setItems(null);
						return json.fromObject(beanApi,
								mobileChannelService.getJsonConfig());
					}
				} else {
					if (member == null) {
						if (memberService.checkUserExits(username)) {
							List<MemberEntity> memberList = memberService
									.findByProperty(MemberEntity.class,
											"username", username);
							if (memberList != null && memberList.size() > 0) {
								MemberEntity localMember = memberList.get(0);
								member = createNewMember(username, password,
										$email, localMember);
							}
						} else {
							member = createNewMember(username, password,
									$email, new MemberEntity());
							try {
								memberService.createMemberCard(member);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
					beanApi.setResultCode("1");
					beanApi.setResultMsg("登录成功");
					beanApi.setUserId(String.valueOf(member.getId()));
					beanApi.setCardPath(member.getCardPath());
					beanApi.setUserName(member.getRealname());
					beanApi.setFaceImg(member.getFaceImg());
					beanApi.setItems(null);
					beanApi.setUcsynlogin($ucsynlogin);
					return json.fromObject(beanApi,
							mobileChannelService.getJsonConfig());
				}

			} else if ($uid == -1) {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("用户不存在,或者被删除 ");
				beanApi.setItems(null);
				return json.fromObject(beanApi,
						mobileChannelService.getJsonConfig());
			} else if ($uid == -2) {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("密码错误 ");
				beanApi.setItems(null);
				return json.fromObject(beanApi,
						mobileChannelService.getJsonConfig());
			} else {
				beanApi.setResultCode("0");
				beanApi.setResultMsg("未定义");
				beanApi.setItems(null);
				return json.fromObject(beanApi,
						mobileChannelService.getJsonConfig());
			}
		} else {
			beanApi.setResultCode("0");
			beanApi.setResultMsg("登陆UCenter失败 " + result);
			beanApi.setItems(null);
			return json.fromObject(beanApi,
					mobileChannelService.getJsonConfig());
		}

	}

	/**
	 * 创建新用户
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param localMember
	 */
	private MemberEntity createNewMember(String username, String password,
			String email, MemberEntity localMember) {
		String pwd = PasswordUtil.encrypt(username, password,
				PasswordUtil.getStaticSalt());
		localMember.setUsername(username);
		localMember.setPassword(pwd);
		localMember.setEmail(email);
		localMember.setIs_cheked(1);
		localMember.setCreatetime(new Date());
		// 会员默认级别
		MemberGroupEntity mg = memberGroupService.getDefaultLevel();
		if (mg != null) {
			localMember.setMembergroupsid(mg.getId());
			localMember.setMemberlevel(mg.getUsergroupsname());

			// 添加会员个数
			if (null != mg.getNumbers()) {
				mg.setNumbers(mg.getNumbers() + 1);
			} else {
				mg.setNumbers(1);
			}
			memberGroupService.saveOrUpdate(mg);
		} else {
			localMember.setMembergroupsid(1 + "");
			// FIXME Membergroupsid("1"); 2015年5月19日17:12:18
			localMember.setMemberlevel("普通会员");
		}
		if (localMember.getId() == null) {
			memberService.save(localMember);
		} else {
			memberService.saveOrUpdate(localMember);
		}
		return localMember;
	}

}
