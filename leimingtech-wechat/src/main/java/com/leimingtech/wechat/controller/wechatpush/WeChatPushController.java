package com.leimingtech.wechat.controller.wechatpush;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.PlatformConstants;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.wechat.entity.wechatcontent.WeChatContentEntity;
import com.leimingtech.wechat.entity.wechatpush.WeChatPushEntity;
import com.leimingtech.wechat.service.wechatpush.WeChatPushServiceI;

/**   
 * @Title: Controller
 * @Description: 微信推送
 * @author 
 * @date 2015-09-11 14:47:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/weChatPushController")
public class WeChatPushController extends BaseController {

	private String message;
	@Autowired
	private WeChatPushServiceI weChatPushService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private ArticleServiceI articleService;
	
	/**
	 * 微信推送列表页跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "weChatPush")
	public ModelAndView weChatPush(WeChatPushEntity weChatPush,HttpServletRequest request) {
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? PlatformConstants.PAGESIZE
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		Map param = request.getParameterMap();
		Map<String, Object> m = weChatPushService.getPageList(weChatPush, param,
				pageSize, pageNo);
		m.put("searchMap", param);
		m.put("pageNo", pageNo);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "weChatPushController.do?weChatPush");
		return new ModelAndView("wechat/wechatpush/weChatPushList",m);
	}

	/**
	 * 微信推送添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		WeChatPushEntity weChatPush = null;
		if (StringUtils.isNotEmpty(id)) {
			weChatPush = weChatPushService.getEntity(String.valueOf(id));
		} else {
			weChatPush = new WeChatPushEntity();
		}
		m.put("weChatPush", weChatPush);
		return new ModelAndView("wechat/wechatpush/weChatPush", m);
	}

	/**
	 * 微信推送修改
	 *
	 * @return
	 */
	@RequestMapping(params = "edit")
	public ModelAndView edit(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		WeChatPushEntity weChatPush = null;
		if (StringUtils.isNotEmpty(id)) {
			weChatPush = weChatPushService.getEntity(String.valueOf(id));
		} else {
			weChatPush = new WeChatPushEntity();
		}
		m.put("weChatPush", weChatPush);
		return new ModelAndView("wechat/wechatpush/weChatPush", m);
	}

	/**
	 * 微信推送保存
	 *
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String save(WeChatPushEntity weChatPush, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtils.isNotEmpty(weChatPush.getId())) {
			message = "微信推送更新成功";
			WeChatPushEntity t = weChatPushService.getEntity(weChatPush.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(weChatPush, t);
				weChatPushService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "微信推送更新失败";
			}
		} else {
			message = "微信推送添加成功";
			weChatPushService.save(weChatPush);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "weChatPushController.do?weChatPush");
		String str = j.toString();
		return str;
	}

	/**
	 * 微信推送删除
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(WeChatPushEntity weChatPush,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		weChatPush = weChatPushService.getEntity(weChatPush.getId());
		message = "微信推送删除成功";
		weChatPushService.delete(weChatPush);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "weChatPushController.do?weChatPush");
		String str = j.toString();
		return str;
	}


	/**
	 * 微信内容添加
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addWeChatContent")
	public ModelAndView addWeChatContent(HttpServletRequest request) {

		Map<String, Object> m = new HashMap<String, Object>();
		// 当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());

		m.put("weChatContent", new ArrayList<WeChatContentEntity>());
		m.put("temporary", temporary);
		return new ModelAndView("wechat/wechatcontent/weChatContent", m);
	}

	/**
	 * 微信内容修改
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "editWeChatContent")
	public ModelAndView editWeChatContent(HttpServletRequest request) {

		String weixinId = request.getParameter("weixinId");
		// 当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		Map<String, Object> m = new HashMap<String, Object>();
		List<WeChatContentEntity> weChatContent = null;
		if (StringUtils.isNotEmpty(weixinId)) {
			weChatContent = weChatPushService.getListByPid(String.valueOf(weixinId));
			
		} else {
			weChatContent =  new ArrayList<WeChatContentEntity>();
		}
		request.setAttribute("weixinid", weixinId);
		m.put("weChatContent", weChatContent);
		m.put("temporary", temporary);
		m.put("num", weChatContent.size());
		return new ModelAndView("wechat/wechatcontent/weChatContent", m);
	}
	
	/**
	 * 微信内容保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveWeChatContent")
	@ResponseBody
	public String saveWeChatContent(WeChatContentEntity weChatContent, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String contentId[]=request.getParameterValues("id");
		String contentIds[]= request.getParameterValues("contentId");  //导入的模板id
		String weixinTitle[] =request.getParameterValues("weixinTitle");//标题
		String weixinId =request.getParameter("weixinId");
		String weixinAuthor[]=request.getParameterValues("weixinAuthor");//作者
		String weixinContent[]=request.getParameterValues("weixinContent");//内容
		String weixinDigest[]=request.getParameterValues("weixinDigest");//摘要
		String weixinContentaddress[]=request.getParameterValues("weixinContentaddress");//内容地址
		String weixinPictureurl[]=request.getParameterValues("weixinPictureurl");//图片
		String weixinSort[]=request.getParameterValues("weixinSort");//排序
		
			
		if(StringUtils.isNotEmpty(weixinId)){
		try{
			for(int i=0;i<contentId.length;i++){
				WeChatContentEntity conent=null;
				if(StringUtils.isNotEmpty(contentId[i])){
					conent=commonService.getEntity(WeChatContentEntity.class, contentId[i]);
				}
				
				if(conent==null){
					conent=new WeChatContentEntity();
					conent.setWeixinId(weixinId);
					conent.setWeixinCreateTime(new Date());
				}
				conent.setWeixinContent(weixinContent[i]);
				
				conent.setWeixinTitle(weixinTitle[i]);
				conent.setWeixinAuthor(weixinAuthor[i]);
				conent.setWeixinContentaddress(weixinContentaddress[i]);
				conent.setWeixinDigest(weixinDigest[i]);
				conent.setWeixinSort(weixinSort[i]);
				conent.setContentId(contentIds[i]);//导入的模板id
				String[] showCoverPic=request.getParameterValues("showCoverPic_"+(i+1)+"");//是否显示封面
				conent.setShowCoverPic(Integer.parseInt(showCoverPic[0]));
				String[] weixinTop=request.getParameterValues("weixinTop_"+(i+1)+"");//是否是头条
				conent.setWeixinTop(Integer.parseInt(weixinTop[0]));
		
				conent.setWeixinPictureurl(weixinPictureurl[i]);
				if(StringUtils.isEmpty(conent.getId())){
					commonService.save(conent);
				}else{
					commonService.saveOrUpdate(conent);
					}
			}
			message="微信内容修改成功";
			j.accumulate("isSuccess", true);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch (Exception e) {
			e.printStackTrace();
			message="微信内容修改失败";
			j.accumulate("isSuccess", false);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		}else{
			WeChatPushEntity push=new WeChatPushEntity();
			push.setCreatePerson(PlatFormUtil.getSessionUser().getUserName());
			push.setCreateTime(new Date());
			String siteId = PlatFormUtil.getSessionSite().getId();
			push.setSiteId(siteId);
			weChatPushService.save(push);
		try{
		for(int i=0;i<contentId.length;i++){
			WeChatContentEntity conent=null;
			if(StringUtils.isNotEmpty(contentId[i])){
				conent=commonService.getEntity(WeChatContentEntity.class, contentId[i]);
			}
			if(conent==null){
				conent=new WeChatContentEntity();
				conent.setWeixinId(push.getId());
				conent.setWeixinCreateTime(new Date());
			}
			conent.setWeixinContent(weixinContent[i]);
			conent.setWeixinTitle(weixinTitle[i]);
			conent.setContentId(contentIds[i]);//导入的模板id
			conent.setWeixinAuthor(weixinAuthor[i]);
			conent.setWeixinContentaddress(weixinContentaddress[i]);
			conent.setWeixinDigest(weixinDigest[i]);
			conent.setWeixinSort(weixinSort[i]);
			String showCoverPic[]=request.getParameterValues("showCoverPic_"+(i+1));//是否显示封面
			conent.setShowCoverPic(Integer.parseInt(showCoverPic[0]));
			String weixinTop[]=request.getParameterValues("weixinTop_"+(i+1)+"");//是否是头条
			conent.setWeixinTop(Integer.parseInt(weixinTop[0]));
		
			conent.setWeixinPictureurl(weixinPictureurl[i]);
				commonService.save(conent);
			}
			message="微信内容提交成功";
			j.accumulate("isSuccess", true);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			}catch (Exception e) {
				e.printStackTrace();
				weChatPushService.delete(push);
				message="微信内容提交失败";
				j.accumulate("isSuccess", false);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			}
		}	
		j.accumulate("msg", message);
		j.accumulate("toUrl", "weChatPushController.do?weChatPush");
		String str = j.toString();
		return str;
	}

	/**
	 * 微信内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delWeChatContent")
	@ResponseBody
	public String delSub(WeChatContentEntity weChatContent,HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String contentId=request.getParameter("contentId");
		weChatContent = weChatPushService.getWeChatContentEntity(contentId);
		message = "微信内容【"+weChatContent.getId()+"】删除成功";
		weChatPushService.delete(weChatContent);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "weChatPushController.do?weChatContent&weixinId="+weChatContent.getWeixinId());
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 相关搜索弹出框
	 * 
	 * @return
	 * @author zhangxiaoqiang
	 */
	@RequestMapping(params = "correlationDialog")
	public ModelAndView correlationDialog(ContentsEntity contents,
			String title, String classify1, HttpServletRequest request) {
		
		// 获取当前毫秒数
		String temporary = request.getParameter("temporary");
		
		// 获取内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, pageSize,
				pageNo, "", "");
		if (!"0".equals(classify1)) {
			cq.eq("classify", classify1);
		}
		cq.eq("constants", "50");
		cq.addOrder("created", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, contents, param);
		// 排序条件
		PageList pageList = this.contentsService.getPageList(cq, true);
		List<ContentsEntity> testList = pageList.getResultList();
		for (ContentsEntity content : testList) {
			ContentCatEntity contentCat = contentsService.get(
					ContentCatEntity.class, content.getCatid());
			if (contentCat != null) {
				content.setCatName(contentCat.getName());
			}
		}
		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("temporary", temporary);
		
		m.put("classify1", classify1);
		m.put("title", title);
		m.put("actionUrl", "weChatPushController.do?correlationDialog&temporary="
				+ temporary);
		return new ModelAndView("wechat/wechatcontent/attachArticleList", m);
	}
	/**
	 * 从相关内容导入
	 * 
	 * @return
	 */
	@RequestMapping(params = "correlation")
	@ResponseBody
	public String correlation(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String checked=request.getParameter("checked");
		// 有选中项时
				List<ContentsEntity> contentsList = new ArrayList<ContentsEntity>();
					String[] checkArray = checked.split(",");
					for (int i = 0; i < checkArray.length; i++) {
						if (checkArray[i] != "") {
							ContentsEntity content = contentsService.get(ContentsEntity.class,String.valueOf(checkArray[i]));
							ArticleEntity article = articleService.getArticleById(content.getId());
							String domain = PlatFormUtil.getCurrentSitedomain(); //获取站点域名
							
							ContentsEntity contents = new ContentsEntity();
							contents.setTitle(content.getTitle());//标题
							contents.setAuthor(content.getAuthor());//作者
							contents.setDigest(content.getDigest()); //摘要
							contents.setThumb(content.getThumb());  //缩略图
							contents.setId(content.getId());  //缩略图
							contents.setUrl(content.getUrl());
							if(StringUtils.isNotEmpty(article.getId())){							
								String atrile = StringEscapeUtils.unescapeHtml4("<!doctype html><html><head><meta charset=\"utf-8\"><base href=\""+domain+"\" /></head><body>"+article.getContent().trim()+"</body></html>");
								contents.setAtriles(atrile);
							}
							contentsList.add(contents);
						}
					}
		j.accumulate("contentsList", contentsList);	
		j.accumulate("isSuccess", true);
		String str = j.toString();
		return str;
	}
	
	
    
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
