package com.leimingtech.cms.controller.acquisition;
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

import com.leimingtech.cms.entity.acquisition.AcquisitionHistoryEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionHistoryServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 采集历史记录
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-15 17:13:09
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/acquisitionHistoryController")
public class AcquisitionHistoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AcquisitionHistoryController.class);

	@Autowired
	private AcquisitionHistoryServiceI acquisitionHistoryService;
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
	 * 采集历史记录列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(AcquisitionHistoryEntity acquisitionHistory, HttpServletRequest request) {
		//获取采集历史记录列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(AcquisitionHistoryEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, acquisitionHistory, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.acquisitionHistoryService.getPageList(cq, true);
		List<AcquisitionHistoryEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "acquisitionHistoryController.do?table");
		return new ModelAndView("cms/acquisition/acquisitionHistoryList", m);
	}

	/**
	 * 采集历史记录添加
	 * 
	 * @return
	 */
//	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new AcquisitionHistoryEntity());
		return new ModelAndView("cms/acquisition/acquisitionHistory", m);
	}
	
	/**
	 * 采集历史记录更新
	 * 
	 * @return
	 */
//	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		AcquisitionHistoryEntity acquisitionHistory = acquisitionHistoryService.getEntity(AcquisitionHistoryEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", acquisitionHistory);
		return new ModelAndView("cms/acquisition/acquisitionHistory", m);
	}

	/**
	 * 采集历史记录保存
	 * 
	 * @return
	 */
//	@RequestMapping(params = "save")
//	@ResponseBody
	public String saveOrUpdate(AcquisitionHistoryEntity acquisitionHistory, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(acquisitionHistory.getId())) {
			message = "采集历史记录【"+acquisitionHistory.getTitle()+"】更新成功";
			AcquisitionHistoryEntity t = acquisitionHistoryService.get(AcquisitionHistoryEntity.class, acquisitionHistory.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(acquisitionHistory, t);
				acquisitionHistoryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "采集历史记录【"+acquisitionHistory.getTitle()+"】更新失败";
			}
		} else {
			message = "采集历史记录【"+acquisitionHistory.getTitle()+"】添加成功";
			acquisitionHistory.setCreatedtime(new Date());//创建时间
			acquisitionHistoryService.save(acquisitionHistory);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionHistoryController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 采集历史记录删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		AcquisitionHistoryEntity acquisitionHistory = acquisitionHistoryService.getEntity(AcquisitionHistoryEntity.class, String.valueOf(id));
		message = "采集历史记录【"+acquisitionHistory.getTitle()+"】删除成功";
		acquisitionHistoryService.delete(acquisitionHistory);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionHistoryController.do?table");
		String str = j.toString();
		return str;
	}
}
