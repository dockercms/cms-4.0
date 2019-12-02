package com.leimingtech.platform.controller.wxlotterrecord;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.leimingtech.platform.entity.wxlotterrecord.LotterRecordEntity;
import com.leimingtech.platform.service.wxlotterrecord.WxLotterRecordServiceI;

/**
 * @Title: Controller
 * @Description: 中奖记录
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-10 17:28:50
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/wxLotterRecordController")
public class WxLotterRecordController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WxLotterRecordController.class);

	@Autowired
	private WxLotterRecordServiceI wxLotterRecordService;
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
	 * 中奖记录列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(LotterRecordEntity wxLotterRecord, HttpServletRequest request) {

		String lid = request.getParameter("lid");// 活动id

		// 获取中奖记录列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(LotterRecordEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, wxLotterRecord, param);

		if (StringUtils.isNotEmpty(lid) && StringUtils.isNumeric(lid)) {
			cq.eq("lid", String.valueOf(lid));
			cq.eq("islottery", 1);
		}

		// 排序条件
		cq.addOrder("time", SortDirection.desc);
		//cq.addOrder("id", SortDirection.desc);
		cq.add();
		PageList pageList = this.wxLotterRecordService.getPageList(cq, true);
		List<LotterRecordEntity> testList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageSize", pageSize);
		m.put("pageCount", pageCount);
		if (StringUtils.isNotEmpty(lid) && StringUtils.isNumeric(lid)) {
			m.put("actionUrl", "wxLotterRecordController.do?table&lid="+lid);
		}else{
			m.put("actionUrl", "wxLotterRecordController.do?table");
		}
		return new ModelAndView("weixin/wxlottery/lotterRecordList", m);
	}

	/**
	 * 中奖记录添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new LotterRecordEntity());
		return new ModelAndView("weixin/wxlotterrecord/wxLotterRecord", m);
	}

	/**
	 * 中奖记录更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		LotterRecordEntity wxLotterRecord = wxLotterRecordService.getEntity(LotterRecordEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", wxLotterRecord);
		return new ModelAndView("weixin/wxlotterrecord/wxLotterRecord", m);
	}

	/**
	 * 中奖记录保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(LotterRecordEntity wxLotterRecord, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(wxLotterRecord.getId())) {
			message = "中奖记录["+wxLotterRecord.getId()+"]更新成功";
			LotterRecordEntity t = wxLotterRecordService.get(LotterRecordEntity.class, wxLotterRecord.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wxLotterRecord, t);
				wxLotterRecordService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "中奖记录["+wxLotterRecord.getId()+"]更新失败";
			}
		} else {
			message = "中奖记录["+wxLotterRecord.getId()+"]添加成功";
			RandomStringUtils.random(5);
			wxLotterRecord.setSn(RandomStringUtils.randomAlphanumeric(5));
			wxLotterRecord.setCreatedtime(new Date());//创建时间
			wxLotterRecordService.save(wxLotterRecord);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotterRecordController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 中奖记录删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		LotterRecordEntity wxLotterRecord = wxLotterRecordService.getEntity(LotterRecordEntity.class, String.valueOf(id));
		message = "中奖记录["+wxLotterRecord.getId()+"]删除成功";
		wxLotterRecordService.delete(wxLotterRecord);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotterRecordController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 发放奖品
	 * 
	 * @return
	 */
	@RequestMapping(params = "fajiang")
	@ResponseBody
	public String fajiang(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		boolean success = false;
		String msg = "操作失败";
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id) && StringUtils.isNumeric(id)) {
			LotterRecordEntity lotterRecord = wxLotterRecordService.getEntity(LotterRecordEntity.class, String.valueOf(id));
			if (lotterRecord.getSendstutas() != 1) {
				lotterRecord.setSendstutas(1);
				lotterRecord.setSendtime(new Date());
				message = "手机号：" + lotterRecord.getPhone() + ",SN码：" + lotterRecord.getSn() + "；奖品已经发放";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
				msg = "奖品发放成功";
				success = true;
			}
		}
		j.accumulate("success", success);
		j.accumulate("msg", msg);
		return j.toString();
	}
}
