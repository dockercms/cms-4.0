package com.leimingtech.cms.controller.videosources;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.VideoSourcesEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VideoSourcesServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 视频库
 * @author linjm 20140402
 * @date 2014-04-30 10:35:08
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/videoSourcesController")
public class VideoSourcesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VideoSourcesController.class);

	@Autowired
	private VideoSourcesServiceI videoSourcesService;
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
	 * 视频库列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(VideoSourcesEntity videoSources, HttpServletRequest request) {
		//获取视频库列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 12 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VideoSourcesEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		videoSources.setSiteid(site.getId());
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		HqlGenerateUtil.installHql(cq, videoSources, param);
		//排序条件
		PageList pageList = this.videoSourcesService.getPageList(cq, true);
		List<VideoSourcesEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "videoSourcesController.do?table");
		return new ModelAndView("cms/videosources/videoSourcesList", m);
	}
	
	/**
	 * 视频库添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new VideoSourcesEntity());
		return new ModelAndView("cms/videosources/videoSources", m);
	}
	
	/**
	 * 视频库更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VideoSourcesEntity videoSources = videoSourcesService.getEntity(VideoSourcesEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", videoSources);
		return new ModelAndView("cms/videosources/videoSources", m);
	}

	/**
	 * 视频库保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VideoSourcesEntity videoSources, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		videoSourcesService.changeVideoDefaultImage(videoSources);//改变视频默认图
		if (StringUtil.isNotEmpty(videoSources.getId())) {
			message = "视频库["+videoSources.getRealname()+"]更新成功";
			VideoSourcesEntity t = videoSourcesService.get(VideoSourcesEntity.class, videoSources.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(videoSources, t);
				videoSourcesService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频库["+videoSources.getRealname()+"]更新失败";
			}
		} else {
			message = "视频库["+videoSources.getRealname()+"]添加成功";
			videoSources.setSiteid(PlatFormUtil.getSessionSiteId());
			videoSources.setCreatedate(new Date());//创建时间
			videoSourcesService.save(videoSources);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoSourcesController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 视频库删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		VideoSourcesEntity videoSources = videoSourcesService.getEntity(VideoSourcesEntity.class, String.valueOf(id));
		message = "视频库["+videoSources.getRealname()+"]删除成功";
		videoSourcesService.delete(videoSources);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoSourcesController.do?table");
		String str = j.toString();
		return str;
	}
	
	
	
	/**
	 * 视频库列弹出框表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "tableDialog")
	public ModelAndView tableDialog(VideoSourcesEntity videoSources,  HttpServletRequest request) {
		//获取视频库列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 12 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VideoSourcesEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		videoSources.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, videoSources, param);
		//排序条件
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		PageList pageList = this.videoSourcesService.getPageList(cq, true);
		List<VideoSourcesEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		m.put("actionUrl", "videoSourcesController.do?tableDialog&ischecks="+request.getParameter("ischecks")+"&requestCode="+request.getParameter("requestCode"));
		m.put("ischecks", request.getParameter("ischecks"));
		m.put("requestCode", request.getParameter("requestCode"));
		return new ModelAndView("cms/videosources/dialog_videoSourcesList", m);
	}
	
	/**
	 * 后台视频播放器
	 * @param request
	 * @return
	 */
	@RequestMapping(params="videoPlayer")
	public ModelAndView player(HttpServletRequest request){
		String id = request.getParameter("id");
		VideoSourcesEntity v = videoSourcesService.singleResult("from VideoSourcesEntity where id='"+id+"'");
		Map<String ,String> m = new HashMap<String ,String>();
		m.put("uri", v.getLocalpath());
		m.put("defaultimage", v.getDefaultimage());
		m.put("videoName", v.getVideoname());
		return new ModelAndView("cms/videosources/player" ,m);
	}
	
	/**
	 * 生成视频的6张随机图
	 * @param request
	 * @param id 视频库id
	 * @return
	 */
	@RequestMapping(params="getImage")
	public ModelAndView getImage(HttpServletRequest request,String id,Boolean flag){
		VideoSourcesEntity v=videoSourcesService.getVideoAndnewRandomImage(id,flag);
		Map<String ,Object> m=new HashMap<String,Object>();
		if (null == v) {
			v = new VideoSourcesEntity();
			m.put("flag", false);
		}
		m.put("page", v);
		return new ModelAndView("cms/videosources/videoRandomImageList",m);
	}
}
