package com.leimingtech.mobile.controller.videoclassify;
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
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.videoclassify.MobileVideoClassifyEntity;
import com.leimingtech.mobile.service.videoclassify.MobileVideoClassifyServiceI;

/**   
 * @Title: Controller
 * @Description: 移动视频分类表
 * @author 
 * @date 2014-07-14 10:29:07
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mobileVideoClassifyController")
public class MobileVideoClassifyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobileVideoClassifyController.class);

	@Autowired
	private MobileVideoClassifyServiceI mobileVideoClassifyService;
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
	 * 移动视频分类表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "mobileVideoClassify")
	public ModelAndView mobileVideoClassify(MobileVideoClassifyEntity mobileVideoClassify, HttpServletRequest request) {
		//获取移动视频分类表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(MobileVideoClassifyEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, mobileVideoClassify, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.mobileVideoClassifyService.getPageList(cq, true);
		List<MobileVideoClassifyEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "mobileVideoClassifyController.do?mobileVideoClassify");
		return new ModelAndView("mobile/videoclassify/mobileVideoClassifyList", m);
	}

	/**
	 * 移动视频分类表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileVideoClassify", new MobileVideoClassifyEntity());
		return new ModelAndView("mobile/videoclassify/mobileVideoClassify", m);
	}
	
	/**
	 * 移动视频分类表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MobileVideoClassifyEntity mobileVideoClassify = mobileVideoClassifyService.getEntity(MobileVideoClassifyEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileVideoClassify", mobileVideoClassify);
		return new ModelAndView("mobile/videoclassify/mobileVideoClassify", m);
	}

	/**
	 * 移动视频分类表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MobileVideoClassifyEntity mobileVideoClassify, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(mobileVideoClassify.getId())) {
			message = "移动视频分类表["+mobileVideoClassify.getName()+"]更新成功";
			MobileVideoClassifyEntity t = mobileVideoClassifyService.get(MobileVideoClassifyEntity.class, mobileVideoClassify.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mobileVideoClassify, t);
				mobileVideoClassifyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "移动视频分类表["+mobileVideoClassify.getName()+"]更新失败";
			}
		} else {
			message = "移动视频分类表["+mobileVideoClassify.getName()+"]添加成功";
			mobileVideoClassify.setCreatedtime(new Date());//创建时间
			mobileVideoClassifyService.save(mobileVideoClassify);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobileVideoClassifyController.do?mobileVideoClassify");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 移动视频分类表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MobileVideoClassifyEntity mobileVideoClassify = mobileVideoClassifyService.getEntity(MobileVideoClassifyEntity.class, id);
		message = "移动视频分类表["+mobileVideoClassify.getName()+"]删除成功";
		mobileVideoClassifyService.delete(mobileVideoClassify);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobileVideoClassifyController.do?mobileVideoClassify");
		String str = j.toString();
		return str;
	}
}
