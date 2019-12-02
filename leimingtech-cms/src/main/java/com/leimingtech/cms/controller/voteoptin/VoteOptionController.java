package com.leimingtech.cms.controller.voteoptin;
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
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VoteOptionServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;


/**   
 * @Title: Controller
 * @Description: 投票选项
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-29 11:02:24
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/voteOptionController")
public class VoteOptionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoteOptionController.class);

	@Autowired
	private VoteOptionServiceI voteOptionService;
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
	 * 投票选项列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(VoteOptionEntity voteOption, HttpServletRequest request) {
		//获取投票选项列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VoteOptionEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		voteOption.setSiteid(site.getId());
		HqlGenerateUtil.installHql(cq, voteOption, param);
		//排序条件
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.voteOptionService.getPageList(cq, true);
		List<VoteOptionEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "voteOptionController.do?table");
		return new ModelAndView("cms/voteoption/voteOptionList", m);
	}

	/**
	 * 投票选项添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		String contentCatId = reqeust.getParameter("contentCatId");
		String contentsId = reqeust.getParameter("contentid");
		String modelsId = reqeust.getParameter("modelsId");
		String voteId = reqeust.getParameter("voteId");
		//临时给投票id赋值为当前毫秒数
		String temporary = reqeust.getParameter("temporary");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new VoteOptionEntity());
		m.put("contentid", contentsId);
		m.put("contentCatId", contentCatId);
		m.put("modelsId", modelsId);
		m.put("temporary", temporary);
		m.put("voteId", voteId);
		return new ModelAndView("cms/voteoption/voteOption", m);
	}
	
	/**
	 * 投票选项更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VoteOptionEntity voteOption = voteOptionService.getEntity(VoteOptionEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", voteOption);
		return new ModelAndView("cms/voteoption/voteOption", m);
	}

	/**
	 * 投票选项保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VoteOptionEntity voteOption, HttpServletRequest request) {
		//传递参数跳转到投票添加页面
		String contentCatId = request.getParameter("contentCatId");
		String modelsId = request.getParameter("modelsId");
		String voteId = request.getParameter("voteId");
		//临时毫秒数
		String temporary = request.getParameter("temporary");
		
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(voteOption.getId())) {
			message = "投票选项["+voteOption.getOptionname()+"]更新成功";
			VoteOptionEntity t = voteOptionService.get(VoteOptionEntity.class, voteOption.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(voteOption, t);
				voteOptionService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "投票选项["+voteOption.getOptionname()+"]更新失败";
			}
		} else {
			message = "投票选项["+voteOption.getOptionname()+"]添加成功";
			//获取当前站点id
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			voteOption.setSiteid(site.getId());
			voteOption.setCreatedtime(new Date());//创建时间
			voteOptionService.save(voteOption);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteController.do?jumpOption&optionVId="+temporary+"&voteId="+voteId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 投票选项删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String optionid = request.getParameter("id");
		String voteId = request.getParameter("voteId");
		//临时毫秒数
		String temporary = request.getParameter("temporary");
		VoteOptionEntity voteOption = voteOptionService.getEntity(VoteOptionEntity.class, String.valueOf(optionid));
		message = "投票选项["+voteOption.getOptionname()+"]删除成功";
		voteOptionService.delete(voteOption);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteController.do?jumpOption&optionVId="+temporary+"&voteId="+voteId);
		String str = j.toString();
		return str;
	}
}
