package com.leimingtech.cms.controller.vote;
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

import com.leimingtech.cms.service.vote.VoteLogDataServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.VoteLogDataEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 投票日志选项
 * @author 
 * @date 2014-06-10 18:50:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/voteLogDataController")
public class VoteLogDataController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoteLogDataController.class);

	@Autowired
	private VoteLogDataServiceI voteLogDataService;
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
	 * 投票日志选项列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "voteLogData")
	public ModelAndView voteLogData(VoteLogDataEntity voteLogData, HttpServletRequest request) {
		//获取投票日志选项列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VoteLogDataEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, voteLogData, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.voteLogDataService.getPageList(cq, true);
		List<VoteLogDataEntity> resultList = pageList.getResultList();
		
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
		m.put("actionUrl", "voteLogDataController.do?voteLogData");
		return new ModelAndView("cms/vote/voteLogDataList", m);
	}

	/**
	 * 投票日志选项添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteLogData", new VoteLogDataEntity());
		return new ModelAndView("cms/vote/voteLogData", m);
	}
	
	/**
	 * 投票日志选项更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VoteLogDataEntity voteLogData = voteLogDataService.getEntity(VoteLogDataEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteLogData", voteLogData);
		return new ModelAndView("cms/vote/voteLogData", m);
	}

	/**
	 * 投票日志选项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VoteLogDataEntity voteLogData, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(voteLogData.getId())) {
			message = "投票日志选项["+voteLogData.getId()+"]更新成功";
			VoteLogDataEntity t = voteLogDataService.get(VoteLogDataEntity.class, voteLogData.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(voteLogData, t);
				voteLogDataService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "投票日志选项["+voteLogData.getId()+"]更新失败";
			}
		} else {
			message = "投票日志选项["+voteLogData.getId()+"]添加成功";
			voteLogData.setCreatedtime(new Date());//创建时间
			voteLogDataService.save(voteLogData);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteLogDataController.do?voteLogData");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 投票日志选项删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		VoteLogDataEntity voteLogData = voteLogDataService.getEntity(VoteLogDataEntity.class, String.valueOf(id));
		message = "投票日志选项["+voteLogData.getId()+"]删除成功";
		voteLogDataService.delete(voteLogData);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteLogDataController.do?voteLogData");
		String str = j.toString();
		return str;
	}
}
