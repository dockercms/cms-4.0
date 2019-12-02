package com.leimingtech.cms.controller.simplespecial;
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

import com.leimingtech.cms.service.simplespecial.SimplespecialServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.SimplespecialEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 简单专题
 * @author 
 * @date 2015-01-19 11:09:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/simplespecialController")
public class SimplespecialController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SimplespecialController.class);

	@Autowired
	private SimplespecialServiceI simplespecialService;
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
	 * 简单专题列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "simplespecial")
	public ModelAndView simplespecial(SimplespecialEntity simplespecial, HttpServletRequest request) {
		//获取简单专题列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(SimplespecialEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, simplespecial, param);
		//排序条件
		cq.addOrder("isTop", SortDirection.desc);
		cq.addOrder("sort",SortDirection.desc);
		cq.addOrder("specialCreate", SortDirection.desc);
		cq.add();
		PageList pageList = this.simplespecialService.getPageList(cq, true);
		List<SimplespecialEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "simplespecialController.do?simplespecial");
		return new ModelAndView("cms/simplespecial/simplespecialList", m);
	}

	/**
	 * 简单专题添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("simplespecial", new SimplespecialEntity());
		m.put("markpeople", PlatFormUtil.getSessionUser().getUserName());
		return new ModelAndView("cms/simplespecial/simplespecial", m);
	}
	
	/**
	 * 简单专题更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		SimplespecialEntity simplespecial = simplespecialService.getEntity(SimplespecialEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("simplespecial", simplespecial);
		return new ModelAndView("cms/simplespecial/simplespecial", m);
	}

	/**
	 * 简单专题保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SimplespecialEntity simplespecial, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(simplespecial.getId())) {
			message = "简单专题["+simplespecial.getSpecialContent()+"]更新成功";
			SimplespecialEntity t = simplespecialService.get(SimplespecialEntity.class, simplespecial.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(simplespecial, t);
				simplespecialService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "简单专题["+simplespecial.getSpecialContent()+"]更新失败";
			}
		} else {
			message = "简单专题["+simplespecial.getSpecialContent()+"]添加成功";
			simplespecial.setSpecialCreate(new Date());
			simplespecialService.save(simplespecial);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "simplespecialController.do?simplespecial");
		String str = j.toString();
		return str;
	}
	/**
	 * 简单专题字段排序
	 * 
	 * @return
	 */
	@RequestMapping(params = "saveacquireSort")
	@ResponseBody
	public String saveacquireSort( HttpServletRequest request) {
		JSONObject j = new JSONObject();
		//获取自定义排序字段
		String sort = request.getParameter("sort");
			String[] sortArray = sort.split(",");
			
				for (int i = 0; i < sortArray.length; i++) {
					String[] strArr = sortArray[i].split("=");
						SimplespecialEntity simplespecial = simplespecialService.getEntity(SimplespecialEntity.class, String.valueOf(strArr[0]));
						if(simplespecial.getSort() != Integer.valueOf(strArr[1])){
							simplespecial.setSort(Integer.valueOf(strArr[1]));
							simplespecialService.save(simplespecial);
						}
				}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	/**
	 * 简单专题删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		List list =simplespecialService.findByQueryString("from SimplespecialContentEntity sc where sc.simplespecialid = '"+id+"'");
		if(list.size()>0){
			message="删除失败,请先删除转发中的关联！";
			j.accumulate("isSuccess", false);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "simplespecialController.do?simplespecial"); 
		}else{
			SimplespecialEntity simplespecial = simplespecialService.getEntity(SimplespecialEntity.class, String.valueOf(id));
			message = "简单专题["+simplespecial.getSpecialContent()+"]删除成功";
			simplespecialService.delete(simplespecial);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			
			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
			j.accumulate("toUrl", "simplespecialController.do?simplespecial");
		}
		String str = j.toString();
		return str;
	}
	
	@RequestMapping(params = "unlockContent")
	@ResponseBody
	public String unlockContent(HttpServletRequest request){
		JSONObject j = new JSONObject();
		String simplespecial = request.getParameter("simplespecialId");
		String contentId = request.getParameter("contentId");
		j = simplespecialService.unlockContent(contentId, simplespecial);
		String str = j.toString();
		return str;
	}
	/**
	 * 专题置顶
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "updateTop")
	@ResponseBody
	public String updateTop(HttpServletRequest request) {
		String simplespecialId = request.getParameter("simplespecialId");

		JSONObject j = new JSONObject();

		if (StringUtils.isEmpty(simplespecialId)) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		SimplespecialEntity simplespecial = simplespecialService.getEntity(SimplespecialEntity.class, String.valueOf(simplespecialId) );
		if (simplespecial == null) {
			j.accumulate("isSuccess", false);
			j.accumulate("msg", "参数不合法");
			return j.toString();
		}

		String logInfo = "内容【" + simplespecial.getSpecialName() + "】";
		String message = "";
		String result = "";
		int i = simplespecialService.getCurrentMaxTop() + 1;
		
		if (simplespecial.getIsTop()==null || 0 == simplespecial.getIsTop()) {
			// 执行置顶操作
			Boolean flag = simplespecialService.setTop( simplespecialId, simplespecialService.getCurrentMaxTop() + 1);
			if (flag) {
				result = "置顶";
				message = "置顶成功";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "simplespecialController.do?simplespecial" );
			} else {
				message = "置顶失败";
				j.accumulate("isSuccess", false);
			}
		} else {
			// 执行取消置顶操作
			Boolean flag = simplespecialService.cancelTop(simplespecialId);
			if (flag) {
				result = "";
				message = "成功取消置顶";
				systemService.addLog(logInfo + message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				j.accumulate("isSuccess", true);
				j.accumulate("toUrl", "simplespecialController.do?simplespecial");
			} else {
				message = "取消置顶失败";
				j.accumulate("isSuccess", false);
			}
		}
		
		j.accumulate("result", result);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
}
