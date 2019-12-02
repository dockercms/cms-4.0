package com.leimingtech.member.controller.thirdlogin;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.entity.thirdlogin.ThirdloginEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.thirdlogin.ThirdloginServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.weibo4j.Oauth;
import com.leimingtech.core.weibo4j.Users;
import com.leimingtech.core.weibo4j.http.AccessToken;
import com.leimingtech.core.weibo4j.model.User;
import com.leimingtech.core.weibo4j.model.WeiboException;
import com.leimingtech.core.weibo4j.util.WeiboConfig;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**   
 * @Title: Controller
 * @Description: 第三方登录配置
 * @author 
 * @date 2014-05-22 15:51:37
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/thirdloginController")
public class ThirdloginController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ThirdloginController.class);

	@Autowired
	private ThirdloginServiceI thirdloginService;
	@Autowired
	private MemberServiceI memberService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 第三方登录配置列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ThirdloginEntity thirdlogin, HttpServletRequest request) {
		//获取第三方登录配置列表
		int pageSize = 10;
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoStr != null && !"".equals(pageNoStr) && pageNoStr.matches("[0-9]+")){
			pageNo = Integer.valueOf(pageNoStr);
		}else{
			pageNo = 1;
		}
		
		CriteriaQuery cq = new CriteriaQuery(ThirdloginEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, thirdlogin, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.thirdloginService.getPageList(cq, true);
		List<ThirdloginEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("actionUrl", "thirdloginController.do?table");
		return new ModelAndView("platform/thirdlogin/thirdloginList", m);
	}

	/**
	 * 第三方登录配置添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new ThirdloginEntity());
		return new ModelAndView("platform/thirdlogin/thirdlogin", m);
	}
	
	/**
	 * 第三方登录配置更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		ThirdloginEntity thirdlogin = thirdloginService.getEntity(ThirdloginEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", thirdlogin);
		return new ModelAndView("platform/thirdlogin/thirdlogin", m);
	}

	/**
	 * 第三方登录配置保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(ThirdloginEntity thirdlogin, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		//授权请求地址
		String authorizeurl = "";
		String name = thirdlogin.getName();
		if("sina".equals(name)){
			authorizeurl = WeiboConfig.getValue("baseURL") + "/authorize?client_id=" 
				+ thirdlogin.getAppid() + "&redirect_uri=" + thirdlogin.getReturnurl();
		}else if("qq".equals(name)){
			try {
				//由于qq发送请求的时候，需要带一个随机的32位的状态，所以这里就不和上面的一样，直接拼好
				authorizeurl= new com.qq.connect.oauth.Oauth().getAuthorizeURL(request);
			} catch (QQConnectException e) {
				e.printStackTrace();
			}
		}
		thirdlogin.setAuthorizeurl(authorizeurl);
		
		if (StringUtil.isNotEmpty(thirdlogin.getId())) {
			message = "第三方登录配置["+thirdlogin.getName()+"]更新成功";
			ThirdloginEntity t = thirdloginService.get(ThirdloginEntity.class, thirdlogin.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(thirdlogin, t);
				thirdloginService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "第三方登录配置["+thirdlogin.getName()+"]更新失败";
			}
		} else {
			message = "第三方登录配置["+thirdlogin.getName()+"]添加成功";
			thirdlogin.setCreatedtime(new Date());//创建时间
			thirdloginService.save(thirdlogin);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		if("sina".equals(name)){
			updateSinaConfig(thirdlogin);
		}else if("qq".equals(name)){
			updateQQConfig(thirdlogin);
		}
		
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "thirdloginController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 第三方登录配置删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		ThirdloginEntity thirdlogin = thirdloginService.getEntity(ThirdloginEntity.class, String.valueOf(id));
		message = "第三方登录配置["+thirdlogin.getName()+"]删除成功";
		thirdloginService.delete(thirdlogin);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "thirdloginController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 修改新浪微博的配置文件
	 * @param thirdlogin
	 */
	private void updateSinaConfig(ThirdloginEntity thirdlogin){
		String outfile = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "weiboconfig.properties";
		String[] outfiles = outfile.split("class");
		outfile = outfiles[0] + "classes/weiboconfig.properties";
		try {
			Properties props = new Properties();
			InputStream input = new FileInputStream(outfile);
			props.load(input);
			input.close(); // 一定要在修改之前关闭input
			OutputStream out = new FileOutputStream(outfile);
			props.setProperty("client_ID", thirdlogin.getAppid());
			props.setProperty("client_SERCRET", thirdlogin.getAppkey());
			props.setProperty("redirect_URI", thirdlogin.getReturnurl());
			props.store(out, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取新浪用户的信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "sinauser")
	@ResponseBody
	public String getSinaUser(HttpServletRequest request){
		JSONObject j = new JSONObject();
		try {
			//获取授权以后的CODE值
			String code = request.getParameter("code");
			//根据CODE获取ACCESSTOKEN
			Oauth oauth = new Oauth();
			AccessToken accesstoken = oauth.getAccessTokenByCode(code);
			//根据ACCESSTOKEN获取UID
			String uid = accesstoken.getUid();
			//根据UID获取用户信息
			User user = new Users().showUserById(uid);
			//本站会员对象，获取SINA用户信息后存入本地用户表
			MemberEntity member = new MemberEntity();
			member.setUsername(user.getName());
			member.setRealname(user.getScreenName());
			member.setName(user.getScreenName());
			member.setSex(Integer.parseInt(user.getGender()));
			member.setAddress(user.getLocation());
			member.setRemark(user.getDescription());
			member.setLastlogin(new Date());
			member.setThirdLogin_uid(user.getId()); //SINA用户的UID
			member.setThirdType("sina");       //用户类型
			
			MemberEntity t = thirdloginService.singleResult("from MemberEntity where prop1='" + uid + "'");
			if(null != t){
				MyBeanUtils.copyBeanNotNull2Bean(member, t);
				memberService.saveOrUpdate(t);
			}else{
				member.setCreatetime(new Date());//创建时间
				thirdloginService.save(member);
			}
			HttpSession session = ContextHolderUtils.getSession();
			message = "用户: " + member.getUsername() + "登录成功";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_LOGIN);
			session.setAttribute("user", member);
			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "registerController.do?loginin");
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = j.toString();
		return str;
	}
	/**
	 * 获取qq用户的信息
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "qquser")
	@ResponseBody
	public String getQQUser(HttpServletRequest request){
		JSONObject j = new JSONObject();
		try {
			//授权成功后获取token 在getAccessTokenByRequest()方法中有client.post()先拿到了返回来的code然后去获取token的
			com.qq.connect.javabeans.AccessToken accessTokenObj = (new com.qq.connect.oauth.Oauth()).getAccessTokenByRequest(request);
			
			//对AccessToken进行判断，如果用户取消了 AccessToken就为空
			if (!accessTokenObj.getAccessToken().equals("")){
	        	String accessToken   = null;
		        String openID        = null;
		        
	        	accessToken = accessTokenObj.getAccessToken();
	            
	            //利用获取到的accessToken去获取当前用的openid
	            OpenID openIDObj =  new OpenID(accessToken);
	            openID = openIDObj.getUserOpenID();	//同一个qq号的openid是相同的
	            //利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息
	            UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
	            UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
	            
	            //本站会员对象，获取SINA用户信息后存入本地用户表
				MemberEntity member = new MemberEntity();
	            if (userInfoBean.getRet() == 0) {
	            	member.setUsername(userInfoBean.getNickname());						//昵称
	            	Integer sex = userInfoBean.getGender().equals("男") ? 1 : 0 ;
	            	member.setSex(sex);					//性别
	            	member.setLastlogin(new Date()); 	//最后登录时间
	            	member.setThirdLogin_uid(openID); 			//qq用户的的唯一标示
	            	member.setThirdType("qq");        		//用户类型
	    			MemberEntity t = thirdloginService.singleResult("from MemberEntity where prop1='" + openID + "'");
	    			if(null != t){
	    				MyBeanUtils.copyBeanNotNull2Bean(member, t);
	    				memberService.saveOrUpdate(t);
	    			}else{
	    				member.setCreatetime(new Date());//创建时间
	    				thirdloginService.save(member);
	    			}
	    			HttpSession session = ContextHolderUtils.getSession();
	    			message = "用户: " + member.getUsername() + "登录成功";
	    			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_LOGIN);
	    			session.setAttribute("user", member);
	    			j.accumulate("isSuccess", true);
	    			j.accumulate("msg", message);
	    			j.accumulate("toUrl", "registerController.do?loginin");
	            }
	        }
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = j.toString();
		return str;
	}
	
	/**
	 * 修改qq的配置文件
	 * @param thirdlogin
	 */
	private void updateQQConfig(ThirdloginEntity thirdlogin){
		String outfile = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "qqconnectconfig.properties";
		String[] outfiles = outfile.split("class");
		outfile = outfiles[0] + "classes/qqconnectconfig.properties";
		try {
			Properties props = new Properties();
			InputStream input = new FileInputStream(outfile);
			props.load(input);
			input.close(); // 一定要在修改之前关闭input
			OutputStream out = new FileOutputStream(outfile);
			props.setProperty("app_ID", thirdlogin.getAppid());
			props.setProperty("app_KEY", thirdlogin.getAppkey());
			props.setProperty("redirect_URI", thirdlogin.getReturnurl());
			props.store(out, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
