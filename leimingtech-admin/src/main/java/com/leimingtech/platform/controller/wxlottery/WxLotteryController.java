package com.leimingtech.platform.controller.wxlottery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.LotteryEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.WxLotteryServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.date.DataUtils;

/**   
 * @Title: Controller
 * @Description: 大转盘
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-08 18:53:35
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wxLotteryController")
public class WxLotteryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WxLotteryController.class);

	@Autowired
	private WxLotteryServiceI wxLotteryService;
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
	 * 大转盘列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(LotteryEntity wxLottery, HttpServletRequest request) {
		wxLottery.setType(1);
		//获取大转盘列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		String status=request.getParameter("status");
		CriteriaQuery cq = new CriteriaQuery(LotteryEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, wxLottery, param);
		//排序条件
		cq.addOrder("createdate",SortDirection.desc);
		cq.add();
		
		PageList pageList = this.wxLotteryService.getPageList(cq, true);
		List<LotteryEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "wxLotteryController.do?table");
		return new ModelAndView("weixin/wxlottery/wxLotteryList", m);
	}
    
	@RequestMapping(params = "scratchcardtable")
	public ModelAndView tablehappy(LotteryEntity wxLottery, HttpServletRequest request) {
		//获取刮刮卡列表
		wxLottery.setType(2);
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(LotteryEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, wxLottery, param);
		//排序条件
		cq.addOrder("createdate",SortDirection.desc);
		cq.add();
		PageList pageList = this.wxLotteryService.getPageList(cq, true);
		List<LotteryEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "wxLotteryController.do?scratchcardtable");
		return new ModelAndView("weixin/wxlottery/wxscratchcardList", m);
	}
	
	
	@RequestMapping(params = "jindan")
	public ModelAndView jindan(LotteryEntity wxLottery, HttpServletRequest request) {
		//获取砸金蛋列表
		wxLottery.setType(3);
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(LotteryEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, wxLottery, param);
		//排序条件
		cq.addOrder("createdate",SortDirection.desc);
		cq.add();
		PageList pageList = this.wxLotteryService.getPageList(cq, true);
		List<LotteryEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "wxLotteryController.do?scratchcardtable");
		return new ModelAndView("weixin/wxlottery/jindanList", m);
	}
	
	/**
	 * 砸金蛋 添加/更新 界面
	 * 
	 * @return
	 */
	@RequestMapping(params = "jindanUpdatePageModel")
	public ModelAndView jindanUpdatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		LotteryEntity wxLottery=new LotteryEntity();
		if(StringUtils.isNotEmpty(id)){
			wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id));
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", wxLottery);
		m.put("statdate",DataUtils.date2Str(DataUtils.datetimeFormat));
		m.put("enddate",DataUtils.date2Str(DateUtils.addDays(new Date(), 1),DataUtils.datetimeFormat));
		return new ModelAndView("weixin/wxlottery/jindan", m);
	}
	
	/**
	 * 大转盘添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new LotteryEntity());
		return new ModelAndView("weixin/wxlottery/wxLottery", m);
	}
	
	/**
	 * 刮刮卡添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addscratchcardtable")
	public ModelAndView addscratchcardtablel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new LotteryEntity());
		return new ModelAndView("weixin/wxlottery/wxScratchcard", m);
	}
	
	
	
	/**
	 * 大转盘更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		LotteryEntity wxLottery =new LotteryEntity();
		if(StringUtils.isNotEmpty(id)){
			wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id));
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", wxLottery);
		m.put("statdate",DataUtils.date2Str(DataUtils.datetimeFormat));
		m.put("enddate",DataUtils.date2Str(DateUtils.addDays(new Date(), 1),DataUtils.datetimeFormat));
		return new ModelAndView("weixin/wxlottery/wxLottery", m);
	}
    
	/**
	 * 刮刮卡更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatescratchcard")
	public ModelAndView updatescratchcard(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		LotteryEntity wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", wxLottery);
		return new ModelAndView("weixin/wxlottery/wxScratchcard", m);
	}
	
	/**
	 * 开始或者停止大转盘活动
	 */
	@RequestMapping(params = "startOrStopTask")
	@ResponseBody
	public String startOrStopTask(LotteryEntity wxLotter, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String status = request.getParameter("status");
		
		boolean ds = wxLotter.getStatus().equals("1");
		wxLotter = wxLotteryService.get(LotteryEntity.class, wxLotter.getId());
		wxLotter.setStatus(Integer.valueOf(status));
		if(wxLotter!=null){
			//timeTask.setIsStart(isStart?"1":"0");
			wxLotteryService.updateEntitie(wxLotter);
			systemService.addLog((ds?"["+wxLotter.getTitle()+"]开始活动":"["+wxLotter.getTitle()+"]停止活动"), Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			message="状态修改成功!";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotteryController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 开始或者停止活动
	 */
	@RequestMapping(params = "startOrStop")
	@ResponseBody
	public String startOrStop(LotteryEntity wxLotter, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String status = request.getParameter("status");
		boolean ds = wxLotter.getStatus().equals("1");
		wxLotter = wxLotteryService.get(LotteryEntity.class, wxLotter.getId());
		wxLotter.setStatus(Integer.valueOf(status));
		if(wxLotter!=null){
			//timeTask.setIsStart(isStart?"1":"0");
			wxLotteryService.updateEntitie(wxLotter);
			systemService.addLog((ds?"活动["+wxLotter.getTitle()+"]开始":"活动["+wxLotter.getTitle()+"]停止"), Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			message="状态修改成功!";
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		/**类型（1代表大转盘、2代表刮刮卡、3代表砸金蛋）*/
		if (1 == wxLotter.getType()) {
			j.accumulate("toUrl", "wxLotteryController.do?table");
		} else if (2 == wxLotter.getType()) {
			j.accumulate("toUrl", "wxLotteryController.do?scratchcardtable");
		} else if (3 == wxLotter.getType()) {
			j.accumulate("toUrl", "wxLotteryController.do?jindan");
		}
		String str = j.toString();
		return str;
	}
	
	/**
	 * 砸金蛋保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "savejindan")
	@ResponseBody
	public String savejindan(LotteryEntity lottery, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		j.accumulate("isSuccess", true);
		if (StringUtil.isNotEmpty(lottery.getId())) {
			message = "砸金蛋【"+lottery.getTitle()+"】更新成功";
			LotteryEntity t = wxLotteryService.get(LotteryEntity.class, lottery.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(lottery, t);
				wxLotteryService.saveOrUpdate(t);
//				wxLotteryService.staticZaJinDan(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "砸金蛋【"+lottery.getTitle()+"】更新失败";
				j.accumulate("isSuccess", false);
			}
		} else {
			message = "砸金蛋【"+lottery.getTitle()+"】添加成功";
			lottery.setType(3);
			lottery.setFistlucknums(0);
			lottery.setFivelucknums(0);
			lottery.setFourlucknums(0);
			lottery.setSecondlucknums(0);
			lottery.setSixlucknums(0);
			lottery.setThirdlucknums(0);
			lottery.setStatus(1);
			lottery.setSiteId(PlatFormUtil.getSessionSiteId());
			lottery.setCreatedate(new Date());
			wxLotteryService.save(lottery);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotteryController.do?jindan");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 大转盘保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(LotteryEntity lottery, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(lottery.getId())) {
			message = "大转盘["+lottery.getTitle()+"]更新成功";
			LotteryEntity t = wxLotteryService.get(LotteryEntity.class, lottery.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(lottery, t);
				wxLotteryService.saveOrUpdate(t);
//				wxLotteryService.staticDaZhuanPan(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "大转盘["+lottery.getTitle()+"]更新失败";
			}
		} else {
			message = "大转盘["+lottery.getTitle()+"]添加成功";
			lottery.setType(1);
			lottery.setSiteId(PlatFormUtil.getSessionSiteId());
			lottery.setFistlucknums(0);
			lottery.setFivelucknums(0);
			lottery.setFourlucknums(0);
			lottery.setSecondlucknums(0);
			lottery.setSixlucknums(0);
			lottery.setThirdlucknums(0);
			lottery.setStatus(1);
			lottery.setCreatedate(new Date());
			wxLotteryService.save(lottery);
//			wxLotteryService.staticDaZhuanPan(wxLottery);//静态化大转盘页面
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotteryController.do?table");
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 刮刮卡保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "savescratchcard")
	@ResponseBody
	public String savescratchcard(LotteryEntity wxLottery, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(wxLottery.getId())) {
			message = "刮刮卡["+wxLottery.getTitle()+"]更新成功";
			LotteryEntity t = wxLotteryService.get(LotteryEntity.class, wxLottery.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(wxLottery, t);
				wxLotteryService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "刮刮卡["+wxLottery.getTitle()+"]更新失败";
			}
		} else {
			message = "刮刮卡["+wxLottery.getTitle()+"]添加成功";
			wxLottery.setType(2);
			wxLottery.setCreatedate(new Date());//创建时间
			wxLotteryService.save(wxLottery);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotteryController.do?scratchcardtable");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 大转盘删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		LotteryEntity wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id));
		message = "大转盘["+wxLottery.getTitle()+"]删除成功";
		wxLotteryService.delete(wxLottery);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotteryController.do?table");
		String str = j.toString();
		return str;
	}
	

	/**
	 * 砸金蛋删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delJindan")
	@ResponseBody
	public String delJindan(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		LotteryEntity wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id));
		message = "砸金蛋["+wxLottery.getTitle()+"]删除成功";
		wxLotteryService.delete(wxLottery);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "wxLotteryController.do?jindan");
		String str = j.toString();
		return str;
	}
/**
 * 刮刮卡删除
 * 
 * @return
 */
	
@RequestMapping(params = "delscratchcard")
@ResponseBody
public String delscratchcard(HttpServletRequest request) {
	JSONObject j = new JSONObject();
	String id = request.getParameter("id");
	
	LotteryEntity wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id));
	message = "刮刮卡["+wxLottery.getTitle()+"]删除成功";
	wxLotteryService.delete(wxLottery);
	systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
	
	j.accumulate("isSuccess", true);
	j.accumulate("msg", message);
	j.accumulate("toUrl", "wxLotteryController.do?scratchcardtable");
	String str = j.toString();
	return str;
}


/**
 * 
 * @param reqeust
 * @return
 */
@RequestMapping(params="lotteryindex")
public ModelAndView lotteryIndex(HttpServletRequest reqeust){
	String id_lottery=reqeust.getParameter("idlottery");
//	Integer type=1;
	LotteryEntity wxLottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(id_lottery));
	Map<String, Object> m = new HashMap<String, Object>();
	m.put("page", wxLottery);
	return new ModelAndView("weixin/Lottery_index",m);
}
}
