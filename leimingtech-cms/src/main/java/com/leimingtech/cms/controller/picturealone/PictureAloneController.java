package com.leimingtech.cms.controller.picturealone;
import java.util.ArrayList;
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
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.PictureAloneServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 图片
 * @author 
 * @date 2014-05-13 13:31:32
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/pictureAloneController")
public class PictureAloneController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PictureAloneController.class);

	@Autowired
	private PictureAloneServiceI pictureAloneService;
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
	 * 图片列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(PictureAloneEntity pictureAlone, HttpServletRequest request) {
		//获取图片列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(PictureAloneEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, pictureAlone, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.pictureAloneService.getPageList(cq, true);
		List<PictureAloneEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "pictureAloneController.do?table");
		return new ModelAndView("cms/picturealone/pictureAloneList", m);
	}

	/**
	 * 图片添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new PictureAloneEntity());
		return new ModelAndView("cms/picturealone/pictureAlone", m);
	}
	
	/**
	 * 图片更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		//毫秒数
		String pictureVId = reqeust.getParameter("pictureVId");
		//组图id
		String pictureGroupId = reqeust.getParameter("pictureGroupId");
		PictureAloneEntity pictureAlone = pictureAloneService.getEntity(PictureAloneEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", pictureAlone);
		m.put("pictureVId", pictureVId);
		m.put("pictureGroupId", pictureGroupId);
		return new ModelAndView("cms/picturealone/pictureAlone", m);
	}

	/**
	 * 图片保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(PictureAloneEntity pictureAlone, HttpServletRequest request) {
		//毫秒数
		String pictureVId = request.getParameter("pictureVId");
		//组图id
		String pictureGroupId = request.getParameter("pictureGroupId");
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(pictureAlone.getId())) {
			message = "图片["+pictureAlone.getPictureMessage()+"]更新成功";
			PictureAloneEntity t = pictureAloneService.get(PictureAloneEntity.class, pictureAlone.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(pictureAlone, t);
				pictureAloneService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "图片更新失败";
			}
		} else {
			message = "图片["+pictureAlone.getPictureMessage()+"]添加成功";
			pictureAlone.setCreatedtime(new Date());//创建时间
			pictureAloneService.save(pictureAlone);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "pictureAloneController.do?pictureJump&pictureVId="+pictureVId+"&pictureGroupId="+pictureGroupId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 图片删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		//毫秒数
		String pictureVId = request.getParameter("pictureVId");
		//组图id
		String pictureGroupId = request.getParameter("pictureGroupId");
		
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		PictureAloneEntity pictureAlone = pictureAloneService.getEntity(PictureAloneEntity.class, String.valueOf(id));
		message = "图片删除成功";
		pictureAloneService.delete(pictureAlone);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "pictureAloneController.do?pictureJump&pictureVId="+pictureVId+"&pictureGroupId="+pictureGroupId);
		String str = j.toString();
		return str;
	}
	/**
	 * 删除跳转到图片列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "pictureJump")
	public ModelAndView pictureJump(HttpServletRequest request){
		//毫秒数
		String pictureVId = request.getParameter("pictureVId");
		//组图id
		String pictureGroupId = request.getParameter("pictureGroupId");
		
		List<PictureAloneEntity> pictureAloneList = new ArrayList<PictureAloneEntity>();
		List<PictureAloneEntity> pictureAloneListT = new ArrayList<PictureAloneEntity>();
		if(!pictureVId.equals("")){
			CriteriaQuery cq = new CriteriaQuery(PictureAloneEntity.class);
			cq.eq("picturegroupid",pictureVId);
			cq.addOrder("pictureSort", SortDirection.desc);
			cq.add();
			//排序条件
			PageList pageList = this.pictureAloneService.getPageList(cq, false);
			pictureAloneList = pageList.getResultList();
//			pictureAloneList = systemService.findByProperty(PictureAloneEntity.class, "picturegroupid", pictureVId);
		}
		if(!pictureGroupId.equals("")){
			CriteriaQuery cq = new CriteriaQuery(PictureAloneEntity.class);
//			pictureAloneListT = systemService.findByProperty(PictureAloneEntity.class, "picturegroupid", pictureGroupId);
			cq.eq("picturegroupid",pictureGroupId);
		
			cq.addOrder("pictureSort", SortDirection.desc);
			cq.add();
			//排序条件
			PageList pageList = this.pictureAloneService.getPageList(cq, false);
			pictureAloneListT = pageList.getResultList();
			for(int i=0;i<pictureAloneListT.size();i++){
				pictureAloneList.add(pictureAloneListT.get(i));
			}
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pictureAloneList", pictureAloneList);
		m.put("pictureVId", pictureVId);
		m.put("pictureGroupId", pictureGroupId);
		return new ModelAndView("cms/picturealone/pictureAloneListRefresh", m);
	}
	/**
	 * 添加跳转到图片列表(保存)
	 * 
	 * @return
	 */
	@RequestMapping(params = "pictureList")
	public ModelAndView pictureList(HttpServletRequest request){
		//毫秒数
		String pictureVId = request.getParameter("pictureVId");
		//组图url
		String pictureGroup = request.getParameter("pictureGroup");
		//组图id
		String pictureGroupId = request.getParameter("pictureGroupId");
		List<PictureAloneEntity> pictureAloneList = new ArrayList<PictureAloneEntity>();
		if(!pictureGroup.equals("")){
			String[] str = pictureGroup.split(",");
 			for(int i=0;i<str.length;i++){
				if(!str[i].equals("")){
					PictureAloneEntity pictureAlone = new PictureAloneEntity();
					pictureAlone.setPicturegroupid(pictureVId);
					pictureAlone.setPictureUrl(str[i]);
					pictureAlone.setCreatedtime(new Date());//创建时间
					pictureAloneService.save(pictureAlone);
				}
			}
		}
		if(!pictureVId.equals("")){
			pictureAloneList = pictureAloneService.findByProperty(PictureAloneEntity.class, "picturegroupid", pictureVId);
		}
		if(!pictureGroupId.equals("")){
			List<PictureAloneEntity> pictureAloneListT = pictureAloneService.findByProperty(PictureAloneEntity.class, "picturegroupid", pictureGroupId);
			for(PictureAloneEntity pictureAlone:pictureAloneListT){
				pictureAloneList.add(pictureAlone);
			}
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pictureAloneList", pictureAloneList);
		m.put("pictureVId", pictureVId);
		m.put("pictureGroupId", pictureGroupId);
		return new ModelAndView("cms/picturealone/pictureAloneListRefresh", m);
	}
}
