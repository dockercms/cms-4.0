package com.leimingtech.cms.controller.videoalburm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.VideoalburmarticleEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VideoalburmarticleServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 视频专辑关联文章
 * @author 
 * @date 2014-07-10 17:25:40
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/videoalburmarticleController")
public class VideoalburmarticleController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VideoalburmarticleController.class);

	@Autowired
	private VideoalburmarticleServiceI videoalburmarticleService;
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
	 * 视频专辑关联文章列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "videoalburmarticle")
	public ModelAndView videoalburmarticle(VideoalburmarticleEntity videoalburmarticle, HttpServletRequest request) {
		//获取视频专辑关联文章列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VideoalburmarticleEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, videoalburmarticle, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.videoalburmarticleService.getPageList(cq, true);
		List<VideoalburmarticleEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "videoalburmarticleController.do?videoalburmarticle");
		return new ModelAndView("cms/videoalburm/videoalburmarticleList", m);
	}

	/**
	 * 视频专辑关联文章添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("videoalburmarticle", new VideoalburmarticleEntity());
		return new ModelAndView("cms/videoalburm/videoalburmarticle", m);
	}
	
	/**
	 * 视频专辑关联文章更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VideoalburmarticleEntity videoalburmarticle = videoalburmarticleService.getEntity(VideoalburmarticleEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("videoalburmarticle", videoalburmarticle);
		return new ModelAndView("cms/videoalburm/videoalburmarticle", m);
	}

	/**
	 * 视频专辑关联文章保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VideoalburmarticleEntity videoalburmarticle, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(videoalburmarticle.getId())) {
			message = "视频专辑关联文章["+videoalburmarticle.getId()+"]更新成功";
			VideoalburmarticleEntity t = videoalburmarticleService.get(VideoalburmarticleEntity.class, videoalburmarticle.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(videoalburmarticle, t);
				videoalburmarticleService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频专辑关联文章["+videoalburmarticle.getId()+"]更新失败";
			}
		} else {
			message = "视频专辑关联文章["+videoalburmarticle.getId()+"]添加成功";
			videoalburmarticle.setCreatedtime(new Date());//创建时间
			videoalburmarticleService.save(videoalburmarticle);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoalburmarticleController.do?videoalburmarticle");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 视频专辑关联文章删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		VideoalburmarticleEntity videoalburmarticle = videoalburmarticleService.getEntity(VideoalburmarticleEntity.class, String.valueOf(id));
		String videoalburmid = videoalburmarticle.getAlburmid() + "";
		message = "视频专辑关联文章["+videoalburmarticle.getId()+"]删除成功";
		videoalburmarticleService.delete(videoalburmarticle);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoalburmController.do?addVideo&id="+videoalburmid);
		String str = j.toString();
		return str;
	}
}
