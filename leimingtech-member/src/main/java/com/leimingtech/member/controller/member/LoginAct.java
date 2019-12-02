package com.leimingtech.member.controller.member;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentCatDefault;
import com.leimingtech.core.entity.MemberDepart;
import com.leimingtech.core.entity.TSDepartChannel;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.thirdlogin.ThirdloginServiceI;
import com.leimingtech.core.util.HttpUtil;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.weibo4j.Oauth;
import com.leimingtech.core.weibo4j.Users;
import com.leimingtech.core.weibo4j.http.AccessToken;
import com.leimingtech.core.weibo4j.model.User;
import com.leimingtech.core.weibo4j.model.WeiboException;
import com.leimingtech.core.weibo4j.util.WeiboConfig;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.utils.QQConnectConfig;
import com.qq.connect.utils.RandomStatusGenerator;

/**
 * 会员登录
 * 
 * @author liuzhen 2014年5月27日 16:32:43
 * 
 */
@Controller
@RequestMapping("/member/loginAct")
public class LoginAct extends BaseController {

	private String message;

	@Autowired
	private MemberServiceI memberMng;// 会员管理

	@Autowired
	private SystemService systemService;

	@Autowired
	private MemberServiceI memberService;
	@Autowired
	private ThirdloginServiceI thirdloginService;

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "login")
	public ModelAndView login(HttpServletRequest reqeust) {
		// 跳转登录页面前地址
		String addr = reqeust.getParameter("addr");
		addr = "";
		MemberEntity member = memberMng.getSessionMember();
		if (member != null)
			return new ModelAndView(new RedirectView("MemberAct.do?personal"));
		Map<String, Object> m = new HashMap<String, Object>();
		String sinapath = sinaLogin();
		String qqpath = qqLogin();
		String username = reqeust.getParameter("username");
		if (StringUtil.isNotEmpty(username)) {
			m.put("username", username);
		}
		m.put("addr", addr);
		m.put("title", "登录");
		m.put("sinapath", sinapath);
		m.put("qqpath", qqpath);
		return new ModelAndView("member/login", m);
	}

	@RequestMapping(params = "checkName")
	@ResponseBody
	public boolean checkName(MemberEntity user, HttpServletRequest req, HttpServletResponse response) {
		String username = req.getParameter("username");
		boolean haveuser = memberMng.checkUserExits(username); // 验证用户名是否存在
		return haveuser;
	}

	/**
	 * 检查用户名称
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "checkLogin")
	@ResponseBody
	public String checkLogin(MemberEntity user, HttpServletRequest req, HttpServletResponse response) {
		// 跳转前地址
		String addr = "";
		String rememberpassword = req.getParameter("rememberpassword");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		boolean haveuser = memberMng.checkUserExits(username); // 验证用户名是否存在
		JSONObject j = new JSONObject();
		if (haveuser) {
			HttpSession session = ContextHolderUtils.getSession();
			 //String randCode = session.getAttribute("randCode").toString();
			String urandcode = req.getParameter("randcode");
			MemberEntity u = memberMng.checkUserExits(user);
			if (u != null) {
				/*Subject subject = SecurityUtils.getSubject();

				boolean isrmemberName = "1".equals(rememberpassword) ? true : false;
				UsernamePasswordToken token = new UsernamePasswordToken(username,password,isrmemberName);
				subject.login(token);*/
				//为用户添加栏目权限
				List<String> defaultList = new ArrayList<String>();
				//默认权限
				List<ContentCatDefault> list = memberService.getList(ContentCatDefault.class);
				if(list.size()!=0){
					for(ContentCatDefault cat :list){
						defaultList.add(cat.getChannelId());
					}
				}
				//会员本身权限
				List<MemberDepart> memberDepartList =  memberService.findByProperty(MemberDepart.class,"member.id",u.getId());
				if(memberDepartList.size()!=0){
					for(MemberDepart mDepart:memberDepartList){
						List<TSDepartChannel> dclist = memberService.findByProperty(TSDepartChannel.class, "depart.id", mDepart.getDepart().getId());
						for(TSDepartChannel dChannel:dclist){
							defaultList.add(dChannel.getChannel().getId());
						}
					}
				}
				req.getSession().setAttribute("channel",defaultList);

				if (StringUtil.isEmpty(urandcode)) {
					message = "请输入验证码";
					j.accumulate("isSuccess", false);
					j.accumulate("msg", message);
					if (StringUtil.isNotEmpty(addr)) {
						j.accumulate("toUrl", addr);
					} 
					/*else {
						j.accumulate("toUrl", "loginAct.do?login&username=" + u.getUsername());
					}
					return j.toString();*/
				} /*
				 * else if (!urandcode.equalsIgnoreCase(randCode)) { message =
				 * "验证码错误"; j.accumulate("isSuccess", false);
				 * j.accumulate("msg", message);
				 * if(!addr.equals("")||addr!=null){ j.accumulate("toUrl",
				 * addr); }else{ j.accumulate("toUrl",
				 * "loginAct.do?login&username=" + u.getUsername()); } }
				 */

				message = "用户: " + u.getUsername() + "登录成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_LOGIN);

				MemberEntity sessionMember = new MemberEntity();
				sessionMember.setId(u.getId());
				sessionMember.setUsername(u.getUsername());

				ClientManager clientMng = ClientManager.getInstance();
				Client client = clientMng.getClient("front" + session.getId());
				if (client == null) {
					client = new Client();
				}
				client.setIp(IpUtil.getIpAddr(req));
				client.setLogindatetime(new Date());
				client.setMember(sessionMember);
				clientMng.addClinet("front" + session.getId(), client);

				u.setLastlogin(new Date());
				u.setLogincount(u.getLogincount() + 1);
				memberMng.saveOrUpdate(u);
				j.accumulate("isSuccess", true);
				j.accumulate("msg", message);
				if (StringUtil.isNotEmpty(addr)) {
					j.accumulate("toUrl", addr);
				} else {
					j.accumulate("toUrl", "MemberAct.do?personal");
				}
				HttpUtil.addCookie(response, username + "loginerror", "", 0);
				if (StringUtil.isNotEmpty(rememberpassword)) {
					HttpUtil.addCookie(response, "loginname", u.getUsername(), 365 * 24 * 60 * 60);
				} else {
					HttpUtil.addCookie(response, "loginname", "", 0);
				}

			} else {
				String loginerrornum = req.getParameter("loginerrornum"); // 登录错误次数
				message = "密码错误!";
				j.accumulate("isSuccess", false);
				j.accumulate("msg", message);
				j.accumulate("errortype", "password");
				j.accumulate("username", user.getUsername());
				j.accumulate("toUrl", "loginAct.do?login");
				j.accumulate("loginerrornum", loginerrornum);

			}

		} else {
			message = "用户名不存在!";
			j.accumulate("isSuccess", false);
			j.accumulate("errortype", "username");
			j.accumulate("msg", message);
			j.accumulate("username", user.getUsername());
			j.accumulate("toUrl", "loginAct.do?login");
		}

		String str = j.toString();
		return str;
	}

	/**
	 * 会员退出
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "exitLogin")
	@ResponseBody
	public String exitLogin(HttpServletRequest req) {
		JSONObject j = new JSONObject();
		HttpSession session = ContextHolderUtils.getSession();
		ClientManager.getInstance().removeClinet("front" + session.getId());
		message = "退出成功！";
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "loginAct.do?login");
		String str = j.toString();
		return str;
	}

	/**
	 * 会员授权登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "thirdLogin")
	@ResponseBody
	public String thirdLogin(HttpServletRequest request) {
		String reqpath = "";
		String type = request.getParameter("type");
		if ("sina".equals(type)) {
			reqpath = sinaLogin();
		} else if ("qq".equals(type)) {
			reqpath = qqLogin();
		}
		JSONObject j = new JSONObject();
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", reqpath);
		return j.toString();
	}

	/**
	 * 1.当点击qq授权登录 2.拼好路径传到前台 3.window.location.href请求qq平台 4.qq平台返回一个授权的窗口
	 * 
	 * @return
	 */
	private String qqLogin() {
		String state = RandomStatusGenerator.getUniqueState();
		String reqPath = new StringBuilder().append(QQConnectConfig.getValue("authorizeURL").trim()).append("?response_type=")
				.append("code").append("&client_id=").append(QQConnectConfig.getValue("app_ID").trim()).append("&redirect_uri=")
				.append(QQConnectConfig.getValue("redirect_URI").trim()).append("&state=").append(state).toString();
		return reqPath;
	}

	/**
	 * 1.当点击sina授权登录 2.拼好路径传到前台 3.window.location.href请求sina平台 4.sina平台返回一个授权的窗口
	 */
	private String sinaLogin() {
		String reqpath = "";
		reqpath = WeiboConfig.getValue("baseURL").trim() + "/authorize?client_id=" + WeiboConfig.getValue("client_ID").trim()
				+ "&redirect_uri=" + WeiboConfig.getValue("redirect_URI").trim();
		return reqpath;
	}

	/**
	 * 这个方法是授权成功后，返回的路径，请求的这个方法 获取qq用户的信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/qqUserInfo")
	public ModelAndView qqUserInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html,charset-utf-8"); 
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			// 授权成功后获取token
			// 在getAccessTokenByRequest()方法中有client.post()先拿到了返回来的code然后去获取token的

			com.qq.connect.javabeans.AccessToken accessTokenObj = (new com.qq.connect.oauth.Oauth()).getAccessTokenByRequest(request);
			// 对AccessToken进行判断，如果用户取消了 AccessToken就为空
			if (!accessTokenObj.getAccessToken().equals("")) {
				String accessToken = null;
				String openID = null;

				accessToken = accessTokenObj.getAccessToken();

				// 利用获取到的accessToken去获取当前用的openid
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID(); // 同一个qq号的openid是相同的
				// 利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

				// 本站会员对象，获取SINA用户信息后存入本地用户表
				MemberEntity member = new MemberEntity();
				if (userInfoBean.getRet() == 0) {
					member.setUsername(userInfoBean.getNickname()); // 昵称
					Integer sex = userInfoBean.getGender().equals("男") ? 1 : 0;
					member.setSex(sex); // 性别
					member.setLastlogin(new Date()); // 最后登录时间
					member.setThirdLogin_uid(openID); // qq用户的的唯一标示
					member.setThirdType("qq"); // 用户类型
					MemberEntity t = thirdloginService.singleResult("from MemberEntity where prop1='" + openID + "'");
					if (null != t) {
						MyBeanUtils.copyBeanNotNull2Bean(member, t);
						memberService.saveOrUpdate(t);
					} else {
						member.setCreatetime(new Date());//创建时间
						memberService.save(member);
					}

					MemberEntity sessionMember = new MemberEntity();
					sessionMember.setId(member.getId());
					sessionMember.setUsername(member.getUsername());

					HttpSession session = ContextHolderUtils.getSession();
					ClientManager clientMng = ClientManager.getInstance();
					Client client = clientMng.getClient(session.getId());
					if (client == null) {
						client = new Client();
					}
					client.setIp(IpUtil.getIpAddr(request));
					client.setLogindatetime(new Date());
					client.setMember(sessionMember);
					clientMng.addClinet("front" + session.getId(), client);

					message = "用户: " + member.getUsername() + "登录成功";
					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_LOGIN);
				}
			}
		} catch (Exception e) {
			message = "LoginAct/getQQUser()-239";
			e.printStackTrace();
			return new ModelAndView(new RedirectView("loginAct.do?login") , m);
		}
		return new ModelAndView(new RedirectView("MemberAct.do?personal") , m);
	}

	/**
	 * 获取新浪用户的信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/sinaUserInfo")
	public ModelAndView sinaUserInfo(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		try {
			// 获取授权以后的CODE值
			String code = request.getParameter("code");
			// 根据CODE获取ACCESSTOKEN
			Oauth oauth = new Oauth();
			AccessToken accesstoken = oauth.getAccessTokenByCode(code);
			// 根据ACCESSTOKEN获取UID
			String uid = accesstoken.getUid();
			// 根据UID获取用户信息
			String token = accesstoken.getAccessToken();
			Users users = new Users();
			users.setToken(token);
			User user = users.showUserById(uid);
			// 本站会员对象，获取SINA用户信息后存入本地用户表
			MemberEntity member = new MemberEntity();
			member.setUsername(user.getName());
			member.setRealname(user.getScreenName());
			member.setName(user.getScreenName());
			//member.setSex(Integer.parseInt(user.getGender()));
			member.setAddress(user.getLocation());
			member.setRemark(user.getDescription());
			member.setLastlogin(new Date());
			member.setThirdLogin_uid(user.getId()); // SINA用户的UID
			member.setThirdType("sina"); // 用户类型
			member.setLogincount(0);

			MemberEntity t = thirdloginService.singleResult("from MemberEntity where thirdLogin_uid='" + uid + "'");
			if(null != t){
				MyBeanUtils.copyBeanNotNull2Bean(member, t);
				memberService.saveOrUpdate(t);
				member.setId(t.getId());
			}else{
				member.setCreatetime(new Date());//创建时间
				memberService.save(member);
			}
			HttpSession session = ContextHolderUtils.getSession();

			MemberEntity sessionMember = new MemberEntity();
			sessionMember.setId(member.getId());
			sessionMember.setUsername(member.getUsername());
			sessionMember.setThirdToken(token);

			ClientManager clientMng = ClientManager.getInstance();
			Client client = clientMng.getClient(session.getId());
			if (client == null) {
				client = new Client();
			}
			client.setIp(IpUtil.getIpAddr(request));
			client.setLogindatetime(new Date());
			client.setMember(sessionMember);
			clientMng.addClinet("front" + session.getId(), client);
			

			message = "用户: " + member.getUsername() + "登录成功";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_LOGIN);
		} catch (WeiboException e) {
			e.printStackTrace();
			return new ModelAndView(new RedirectView("loginAct.do?login") , m);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(new RedirectView("loginAct.do?login") , m);
		}
		return new ModelAndView(new RedirectView("MemberAct.do?personal") , m);
	}
}
