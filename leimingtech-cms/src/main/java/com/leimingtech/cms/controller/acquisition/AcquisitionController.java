package com.leimingtech.cms.controller.acquisition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.entity.acquisition.AcquisitionEntity;
import com.leimingtech.cms.entity.acquisition.AcquisitionreplaceEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionServiceI;
import com.leimingtech.cms.service.acquisition.AcquisitionSvcI;
import com.leimingtech.cms.service.acquisition.AcquisitionreplaceServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**
 * @Title: Controller
 * @Description: 数据采集
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-15 17:06:01
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/acquisitionController")
public class AcquisitionController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(AcquisitionController.class);

	@Autowired
	private AcquisitionServiceI acqMng;
	@Autowired
	private AcquisitionSvcI acqSvcMng;
	@Autowired
	private SystemService systemService;
	/** 数据采集中 内容替换接口 */
	@Autowired
	private AcquisitionreplaceServiceI acquisitionreplaceService;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 数据采集列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(AcquisitionEntity acquisition, HttpServletRequest request) {
		// 获取数据采集列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(AcquisitionEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		acquisition.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, acquisition, param);
		cq.addOrder("createdtime", SortDirection.desc);
		cq.add();
		// 排序条件
		PageList pageList = this.acqMng.getPageList(cq, true);
		List<AcquisitionEntity> testList = pageList.getResultList();

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
		m.put("actionUrl", "acquisitionController.do?table");
		return new ModelAndView("cms/acquisition/acquisitionList", m);
	}

	/**
	 * 数据采集添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new AcquisitionEntity());
		m.put("acqReplace", new ArrayList<AcquisitionreplaceEntity>());// 数据采集关联表
																		// 内容替换
		return new ModelAndView("cms/acquisition/acquisition", m);
	}

	/**
	 * 数据采集更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		AcquisitionEntity acquisition = acqMng.getEntity(AcquisitionEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", acquisition);
		m.put("acqReplace", acquisitionreplaceService.getListByAcq(id));
		return new ModelAndView("cms/acquisition/acquisition", m);
	}

	/**
	 * 数据采集保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(AcquisitionEntity acquisition, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(acquisition.getId())) {
			AcquisitionEntity t = acqMng.get(AcquisitionEntity.class, acquisition.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(acquisition, t);
				acqMng.saveOrUpdate(t);

				String[] repid = request.getParameterValues("repid");
				String[] replaceOld = request.getParameterValues("replaceOld");
				String[] replaceNew = request.getParameterValues("replaceNew");

				if (repid != null && repid.length > 0 && replaceOld != null && replaceOld.length > 0 && replaceNew != null
						&& replaceNew.length > 0 && repid.length == replaceOld.length &&  replaceOld.length==replaceNew.length) {
					for (int i = 0; i < repid.length; i++) {
						AcquisitionreplaceEntity rep=null;
						if(StringUtils.isNotEmpty(repid[i])){
							rep=acquisitionreplaceService.getEntity(AcquisitionreplaceEntity.class, repid[i]);
						}
						
						if(rep==null){
							rep=new AcquisitionreplaceEntity();
							rep.setCreatetime(new Date());
							rep.setAcquisitionId(t.getId()+"");
						}
						rep.setReplaceOld(replaceOld[i]);
						rep.setReplaceNew(replaceOld[i]);
						if(StringUtils.isEmpty(rep.getId())){
							rep.setCreatetime(new Date());//创建时间
							acquisitionreplaceService.save(rep);
						}else{
							acquisitionreplaceService.saveOrUpdate(rep);
						}
					}
				}

				message = "数据采集【" + acquisition.getAcqName() + "】更新成功";
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "数据采集【" + acquisition.getAcqName() + "】更新失败";
			}
		} else {
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			acquisition.setSiteId(site.getId());
			acquisition.setCreatedtime(new Date());//创建时间
			acqMng.save(acquisition);
			message = "数据采集【" + acquisition.getAcqName() + "】添加成功";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 数据采集删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		AcquisitionEntity acquisition = acquisitionreplaceService.getEntity(AcquisitionEntity.class, String.valueOf(id));
		message = "数据采集【" + acquisition.getAcqName() + "】删除成功";
		acqMng.delete(acquisition);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionController.do?table");
		String str = j.toString();
		return str;
	}

	/**
	 * 开启采集
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "start")
	@ResponseBody
	public String start(String[] ids, HttpServletRequest request) {
		Integer queueNum = acqMng.hasStarted();// 判断有多少已经开始采集数据
		if (queueNum == 0) {
			acqSvcMng.start(ids[0]);
		}
		acqMng.addToQueue(ids, queueNum);
		logger.info("start CmsAcquisition ids={}", Arrays.toString(ids));
		JSONObject j = new JSONObject();
		message = "开始采集";
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		String str = j.toString();
		return str;
	}
	
	
	/**
	 * 停止数据采集
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "end")
	@ResponseBody
	public String end(String[] ids, HttpServletRequest request) {
		
		JSONObject j = new JSONObject();
		try {
			String id = request.getParameter("ids");
			acqMng.end(id==null?"0":id);
			message = "成功停止采集";
			j.accumulate("isSuccess", true);
			j.accumulate("msg", message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "停止采集失败";
			j.accumulate("isSuccess", false);
			j.accumulate("msg", message);
		}
		logger.info("stop CmsAcquisition ids={}", Arrays.toString(ids));
		String str = j.toString();
		return str;
	}
	
	
	
	
	
	
	
}
