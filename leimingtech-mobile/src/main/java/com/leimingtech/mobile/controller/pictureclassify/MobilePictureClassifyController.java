package com.leimingtech.mobile.controller.pictureclassify;
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
import com.leimingtech.mobile.entity.pictureclassify.MobilePictureClassifyEntity;
import com.leimingtech.mobile.service.pictureclassify.MobilePictureClassifyServiceI;

/**   
 * @Title: Controller
 * @Description: 移动图片分类表
 * @author 
 * @date 2014-07-14 10:28:05
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/mobilePictureClassifyController")
public class MobilePictureClassifyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobilePictureClassifyController.class);

	@Autowired
	private MobilePictureClassifyServiceI mobilePictureClassifyService;
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
	 * 移动图片分类表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "mobilePictureClassify")
	public ModelAndView mobilePictureClassify(MobilePictureClassifyEntity mobilePictureClassify, HttpServletRequest request) {
		//获取移动图片分类表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(MobilePictureClassifyEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, mobilePictureClassify, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.mobilePictureClassifyService.getPageList(cq, true);
		List<MobilePictureClassifyEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "mobilePictureClassifyController.do?mobilePictureClassify");
		return new ModelAndView("mobile/pictureclassify/mobilePictureClassifyList", m);
	}

	/**
	 * 移动图片分类表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobilePictureClassify", new MobilePictureClassifyEntity());
		return new ModelAndView("mobile/pictureclassify/mobilePictureClassify", m);
	}
	
	/**
	 * 移动图片分类表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MobilePictureClassifyEntity mobilePictureClassify = mobilePictureClassifyService.getEntity(MobilePictureClassifyEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobilePictureClassify", mobilePictureClassify);
		return new ModelAndView("mobile/pictureclassify/mobilePictureClassify", m);
	}

	/**
	 * 移动图片分类表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MobilePictureClassifyEntity mobilePictureClassify, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(mobilePictureClassify.getId())) {
			message = "移动图片分类["+mobilePictureClassify.getName()+"]更新成功";
			MobilePictureClassifyEntity t = mobilePictureClassifyService.get(MobilePictureClassifyEntity.class, mobilePictureClassify.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mobilePictureClassify, t);
				mobilePictureClassifyService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "移动图片分类["+mobilePictureClassify.getName()+"]更新失败";
			}
		} else {
			message = "移动图片分类["+mobilePictureClassify.getName()+"]添加成功";
			mobilePictureClassify.setCreatedtime(new Date());//创建时间
			mobilePictureClassifyService.save(mobilePictureClassify);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobilePictureClassifyController.do?mobilePictureClassify");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 移动图片分类表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MobilePictureClassifyEntity mobilePictureClassify = mobilePictureClassifyService.getEntity(MobilePictureClassifyEntity.class, id);
		message = "移动图片分类["+mobilePictureClassify.getName()+"]删除成功";
		mobilePictureClassifyService.delete(mobilePictureClassify);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobilePictureClassifyController.do?mobilePictureClassify");
		String str = j.toString();
		return str;
	}
}
