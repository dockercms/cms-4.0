package com.leimingtech.weibo.controller.sinaweibo;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import weibo4j.Oauth;
import weibo4j.model.WeiboException;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.weibo.entity.sinaweibo.SinaWeiboEntity;
import com.leimingtech.weibo.service.sinaweibo.SinaWeiboServiceI;

/**   
 * @Title: Controller
 * @Description: 新浪微博
 * @author 
 * @date 2015-12-03 14:22:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/sinaWeiboController")
public class SinaCodeController extends BaseController {

	private String message;
	/** 新浪微博接口 */
	@Autowired
	private SinaWeiboServiceI sinaWeiboService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	/** 系统接口 */
	@Autowired
	private CommonService commonService;
	
	
	/**
	 * 获取新浪微博Code
	 * 
	 * @return
	 * @throws WeiboException 
	 * @throws IOException 
	 */
	@RequestMapping(params = "sinapi")
	public ModelAndView sinapi(HttpServletRequest request) throws WeiboException, IOException {
		String code=request.getParameter("code");
		Oauth oauth = new Oauth();
		String accesstoken=oauth.getAccessTokenByCode(code).getAccessToken();	
		String site=PlatFormUtil.getSessionSite().getId();
		SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
		sinaWeibo.setAccesstoken(accesstoken);
		sinaWeiboService.saveOrUpdate(sinaWeibo);
		if(accesstoken!=null&&!accesstoken.equals("")){
		return new ModelAndView("weibo/sinaweibo/success");
		}else{
		return new ModelAndView("weibo/sinaweibo/error");
		}
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
