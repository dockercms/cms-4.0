package com.leimingtech.cms.controller.videoadv;
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

import com.leimingtech.cms.service.VideoadvServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.VideoadvEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 视频广告管理
 * @author 
 * @date 2014-07-11 11:05:15
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/videoadvController")
public class VideoadvController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VideoadvController.class);

	@Autowired
	private VideoadvServiceI videoadvService;
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
	 * 视频广告管理列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "videoadv")
	public ModelAndView videoadv(VideoadvEntity videoadv, HttpServletRequest request) {
		//获取视频广告管理列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VideoadvEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, videoadv, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.videoadvService.getPageList(cq, true);
		List<VideoadvEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "videoadvController.do?videoadv");
		return new ModelAndView("cms/videoadv/videoadvList", m);
	}

	/**
	 * 视频广告管理添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		VideoadvEntity videoadv = videoadvService.getEntity(VideoadvEntity.class, "1");
		Map<String, Object> m = new HashMap<String, Object>();
		if (videoadv == null) {
			videoadv = new VideoadvEntity();
		}
		m.put("videoadv", videoadv);
		return new ModelAndView("cms/videoadv/videoadv", m);
	}
	
	/**
	 * 视频广告管理更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VideoadvEntity videoadv = videoadvService.getEntity(VideoadvEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("videoadv", videoadv);
		return new ModelAndView("cms/videoadv/videoadv", m);
	}

	/**
	 * 视频广告管理保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VideoadvEntity videoadv, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String siteid = PlatFormUtil.getSessionSiteId();
		videoadv.setSiteId(siteid);
		if (StringUtil.isNotEmpty(videoadv.getId())) {
			message = "视频广告管理【"+videoadv.getId()+"】更新成功";
			VideoadvEntity t = videoadvService.get(VideoadvEntity.class, videoadv.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(videoadv, t);
				videoadvService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "视频广告管理【"+videoadv.getId()+"】更新失败";
			}
		} else {
			message = "视频广告管理【"+videoadv.getId()+"】添加成功";
			videoadv.setCreatedtime(new Date());//创建时间
			videoadvService.save(videoadv);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoadvController.do?add");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 视频广告管理删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		VideoadvEntity videoadv = videoadvService.getEntity(VideoadvEntity.class, String.valueOf(id));
		message = "视频广告管理【"+videoadv.getId()+"】删除成功";
		videoadvService.delete(videoadv);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "videoadvController.do?videoadv");
		String str = j.toString();
		return str;
	}
}
