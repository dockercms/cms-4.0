package com.leimingtech.weibo.controller.sinaweibo;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.LogUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.weibo.entity.sinacontent.SinaContentEntity;
import com.leimingtech.weibo.entity.sinaweibo.SinaWeiboEntity;
import com.leimingtech.weibo.service.sinacontent.SinaContentServiceI;
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
@RequestMapping("/sinaWeiboController")
public class SinaWeiboController extends BaseController {

	private String message;
	/** 新浪微博接口 */
	@Autowired
	private SinaWeiboServiceI sinaWeiboService;
	/** 新浪微博内容接口 */
	@Autowired
	private SinaContentServiceI sinaContentService;
	/** 系统接口 */
	@Autowired
	private SystemService systemService;
	/** 系统接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 新浪微博列表
	 * 
	 * @param request
	 */
	@RequestMapping(params = "sinaWeibo")
	public ModelAndView sinaWeibo(HttpServletRequest request) {
		SinaWeiboEntity sinaWeibos  =null;
		String siteid=PlatFormUtil.getSessionSiteId();
		sinaWeibos = sinaWeiboService.getSitEntity(siteid);
		
		if(sinaWeibos==null){
			sinaWeibos=new SinaWeiboEntity();
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaWeibo", sinaWeibos);
		return new ModelAndView("weibo/sinaweibo/sinaWeiboList", m);
	}

	/**
	 * 新浪微博添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaWeibo", new SinaWeiboEntity());
		return new ModelAndView("weibo/sinaweibo/sinaWeibo", m);
	}
	
	/**
	 * 新浪微博更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SinaWeiboEntity sinaWeibo = sinaWeiboService.getEntity(java.lang.String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaWeibo", sinaWeibo);
		return new ModelAndView("weibo/sinaweibo/sinaWeibo", m);
	}

	/**
	 * 新浪微博保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(SinaWeiboEntity sinaWeibo, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		String codeUrl=null;
		String sinaUrl=PlatFormUtil.getCurrentSitedomain();
		if (StringUtils.isNotEmpty(sinaWeibo.getId())) {
			message = "新浪微博配置更新成功!";
			SinaWeiboEntity t = sinaWeiboService.getEntity(sinaWeibo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sinaWeibo, t);
				sinaWeiboService.saveOrUpdate(t);
				Oauth oauth = new Oauth();
				try {
					codeUrl=oauth.authorize("code");
				} catch (WeiboException e) {
					e.printStackTrace();
				}
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
				LogUtil.info(message);
			} catch (Exception e) {
				e.printStackTrace();
				message = "新浪微博配置更新失败";
				LogUtil.error(message, e);
				isSuccess = false;
			}
		} else {
			message = "新浪微博配置信息添加成功";
			String site=PlatFormUtil.getSessionSite().getId();
			sinaWeibo.setBaseurl("https://api.weibo.com/2/");
			sinaWeibo.setAccesstokenurl("https://api.weibo.com/oauth2/access_token");
			sinaWeibo.setAuthorizeurl("https://api.weibo.com/oauth2/authorize");
			sinaWeibo.setRmurl("https\\://rm.api.weibo.com/2/");
			sinaWeibo.setSiteId(site);
			sinaWeiboService.save(sinaWeibo);
			Oauth oauth = new Oauth();
			try {
				codeUrl=oauth.authorize("code");
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			LogUtil.info(message);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("codeurl", codeUrl);
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sinaWeiboController.do?sinaWeibo");
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 发布新浪微博
	 * 
	 * @return
	 */
	@RequestMapping(params = "send")
	@ResponseBody
	public String send(SinaContentEntity sinaContent,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean isSuccess = true;
		sinaContent.setCreatetime(new Date());
		sinaContentService.save(sinaContent);
		String imgurl=sinaContent.getSinaThumb();
		if(!imgurl.isEmpty()){
			try {
				String uploadFilesPath=CmsConstants.getUploadFilesPath(PlatFormUtil.getSessionSite());
				String imgpath=sinaContent.getSinaThumb();
				ImageItem pic = null;
				if(imgpath.contains("http")){
					byte[] content = readHttpImage(imgpath);
					pic = new ImageItem("pic", content);
				}else{
					byte[] content = readFileImage(uploadFilesPath+imgpath);
					pic = new ImageItem("pic", content);
				}
				String contentsina=sinaContent.getSinaDigest();
				String sinaStaus=StringEscapeUtils.unescapeHtml4(contentsina);
				String sinaUrl=PlatFormUtil.getCurrentSitedomain()+sinaContent.getSinaUrl();
				String s = java.net.URLEncoder.encode(sinaStaus+sinaUrl, "utf-8");
				String site=PlatFormUtil.getSessionSite().getId();
				SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
				String access_token =sinaWeibo.getAccesstoken();
				Timeline tm = new Timeline(access_token);
				Status status = tm.uploadStatus(s, pic);
				String retxt=status.getText();
				if(!retxt.isEmpty()){
					message = "新浪微博发布成功！";
				}else{
					isSuccess = false;
					message = "新浪微博发布失败！请检查公账号配置";
				}
			} catch (Exception e) {
				isSuccess = false;
				message = "新浪微博发布失败！请检查公账号配置";
				e.printStackTrace();
			}
		}else{
			String site=PlatFormUtil.getSessionSite().getId();
			SinaWeiboEntity sinaWeibo=sinaWeiboService.getSitEntity(site);
			String access_token =sinaWeibo.getAccesstoken();
			String content=sinaContent.getSinaDigest();
			String sinaStaus=StringEscapeUtils.unescapeHtml4(content);
			String sinaUrl=PlatFormUtil.getCurrentSitedomain()+sinaContent.getSinaUrl();
			Timeline tm = new Timeline(access_token);
			try {
				Status status = tm.updateStatus(sinaStaus+sinaUrl);
				String retxt=status.getId();
				if(!retxt.isEmpty()){
					message = "新浪微博发布成功！";
				}else{
					isSuccess = false;
					message = "新浪微博发布失败！请检查公账号配置";
				}
			} catch (WeiboException e) {
				isSuccess = false;
				message = "新浪微博发布失败！请检查公账号配置";
				e.printStackTrace();
			}	
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}

	public static byte[] readFileImage(String filename) throws IOException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(filename));
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}
	
	public static byte[] readHttpImage(String http) throws IOException {
		URL	url = new URL(http);//filePath是网络的地址
    	URLConnection conn = url.openConnection();
        InputStream inStream = conn.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inStream);
		int len = bufferedInputStream.available();
		byte[] bytes = new byte[len];
		int r = bufferedInputStream.read(bytes);
		if (len != r) {
			bytes = null;
			throw new IOException("读取文件不正确");
		}
		bufferedInputStream.close();
		return bytes;
	}
	
	/**
	 * 新浪微博删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		SinaWeiboEntity sinaWeibo = sinaWeiboService.getEntity(java.lang.String.valueOf(id));
		message = "新浪微博配置信息删除成功";
		sinaWeiboService.delete(sinaWeibo);
		LogUtil.info(message);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "sinaWeiboController.do?sinaWeibo");
		String str = j.toString();
		return str;
	}
	
	
	/**
	 *展示新浪推送内容
	 * 
	 * @param request
	 */
	@RequestMapping(params = "toShowSian")
	public ModelAndView toShowSian(SinaWeiboEntity sinaWeibo, HttpServletRequest request) {
		String site=PlatFormUtil.getSessionSite().getId();
		String code=sinaWeiboService.getWeiboinfo("accessToken", site);
		if(code==null){
		Map<String, Object> m = new HashMap<String, Object>();
		String message="请先配置微博公众号信息！";
		m.put("content", message);
		return new ModelAndView("weibo/sinaweibo/message", m);
		}else{
		String countId= request.getParameter("countId");//内容id
		ContentsEntity content = commonService.getEntity(ContentsEntity.class, countId);
		String imglist[] = content.getThumb().split(",");
		String img =imglist[0].replace(",", "");
		String url=content.getUrl();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("content", content);
		m.put("img", img);
		m.put("url", url);
		return new ModelAndView("weibo/sinaweibo/toShowSina", m);
		}
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
