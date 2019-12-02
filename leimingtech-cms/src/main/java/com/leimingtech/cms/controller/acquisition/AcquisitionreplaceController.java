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

import com.leimingtech.cms.entity.acquisition.AcquisitionreplaceEntity;
import com.leimingtech.cms.service.acquisition.AcquisitionreplaceServiceI;
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
 * @Description: 数据采集关联表 内容替换
 * @author
 * @date 2015-04-07 15:52:11
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/acquisitionreplaceController")
public class AcquisitionreplaceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AcquisitionreplaceController.class);

	@Autowired
	private AcquisitionreplaceServiceI acquisitionreplaceService;
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
	 * 数据采集关联表 内容替换列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "acquisitionreplace")
	public ModelAndView acquisitionreplace(AcquisitionreplaceEntity acquisitionreplace, HttpServletRequest request) {
		// 获取数据采集关联表 内容替换列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(AcquisitionreplaceEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, acquisitionreplace, param);
		// 排序条件
		cq.addOrder("createtime", SortDirection.desc);
		cq.add();
		PageList pageList = this.acquisitionreplaceService.getPageList(cq, true);
		List<AcquisitionreplaceEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "acquisitionreplaceController.do?acquisitionreplace");
		return new ModelAndView("cms/acquisition/acquisitionreplaceList", m);
	}

	/**
	 * 数据采集关联表 内容替换添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("acquisitionreplace", new AcquisitionreplaceEntity());
		return new ModelAndView("cms/acquisition/acquisitionreplace", m);
	}

	/**
	 * 数据采集关联表 内容替换更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		AcquisitionreplaceEntity acquisitionreplace = acquisitionreplaceService.getEntity(AcquisitionreplaceEntity.class,
				String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("acquisitionreplace", acquisitionreplace);
		return new ModelAndView("cms/acquisition/acquisitionreplace", m);
	}

	/**
	 * 数据采集关联表 内容替换保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(AcquisitionreplaceEntity acquisitionreplace, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(acquisitionreplace.getId())) {
			message = "数据采集关联表【"+acquisitionreplace.getReplaceOld()+"】 内容替换更新成功";
			AcquisitionreplaceEntity t = acquisitionreplaceService.get(AcquisitionreplaceEntity.class, acquisitionreplace.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(acquisitionreplace, t);
				acquisitionreplaceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "数据采集关联表 【"+acquisitionreplace.getReplaceOld()+"】内容替换更新失败";
			}
		} else {
			message = "数据采集关联表【"+acquisitionreplace.getReplaceOld()+"】 内容替换添加成功";
			acquisitionreplace.setCreatetime(new Date());//创建时间
			acquisitionreplaceService.save(acquisitionreplace);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionreplaceController.do?acquisitionreplace");
		String str = j.toString();
		return str;
	}

	/**
	 * 数据采集关联表 内容替换删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		boolean isSuccess = false;

		try {
			AcquisitionreplaceEntity acquisitionreplace = acquisitionreplaceService.getEntity(AcquisitionreplaceEntity.class, id);
			acquisitionreplaceService.delete(acquisitionreplace);
			isSuccess = true;
			message = "删除成功";
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败";
		}

		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "acquisitionreplaceController.do?acquisitionreplace");
		String str = j.toString();
		return str;
	}
}
