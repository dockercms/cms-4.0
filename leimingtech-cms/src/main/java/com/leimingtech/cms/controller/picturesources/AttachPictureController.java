package com.leimingtech.cms.controller.picturesources;
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
import com.leimingtech.core.entity.AttachPictureEntity;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.AttachPictureServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 图片库
 * @author linjm 20140402
 * @date 2014-05-11 14:46:57
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/attachPictureController")
public class AttachPictureController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AttachPictureController.class);

	@Autowired
	private AttachPictureServiceI attachPictureService;
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
	 * 图片库列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(AttachPictureEntity attachPicture, HttpServletRequest request) {
		//获取图片库列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 12 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(AttachPictureEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		attachPicture.setSiteid(site.getId());
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		HqlGenerateUtil.installHql(cq, attachPicture, param);
		//排序条件
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		PageList pageList = this.attachPictureService.getPageList(cq, true);
		List<AttachPictureEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "attachPictureController.do?table");
		return new ModelAndView("cms/picturesources/attachPictureList", m);
	}
	
	/**
	 * 图片库列弹出框表页ftl
	 * 
	 * @param request
	 * @param ischecks 是否是多选可选值true/false
	 * @param requestCode 请求码 
	 * @param pageList
	 */
	@RequestMapping(params = "tableDialog")
	public ModelAndView tableDialog(AttachPictureEntity attachPicture, HttpServletRequest request) {
		
		String fun = request.getParameter("fun");
		if (StringUtils.isEmpty(fun)) {
			fun = "selectPictureBack";
		}
		//获取图片库列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 12 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(AttachPictureEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		HqlGenerateUtil.installHql(cq, attachPicture, param);
		//排序条件
		PageList pageList = this.attachPictureService.getPageList(cq, true);
		List<AttachPictureEntity> testList = pageList.getResultList();
		
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
		m.put("fun", fun);
		m.put("actionUrl", "attachPictureController.do?tableDialog&ischecks="+request.getParameter("ischecks")+"&requestCode="+request.getParameter("requestCode"));
		m.put("ischecks", request.getParameter("ischecks"));
		m.put("requestCode", request.getParameter("requestCode"));
		return new ModelAndView("cms/picturesources/dialog_attachPictureList", m);
	}

	/**
	 * 图片库添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", new AttachPictureEntity());
		return new ModelAndView("cms/picturesources/attachPicture", m);
	}
	
	/**
	 * 图片库更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		AttachPictureEntity attachPicture = attachPictureService.getEntity(AttachPictureEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", attachPicture);
		m.put("isUpdate", true);
		return new ModelAndView("cms/picturesources/attachPicture", m);
	}

	/**
	 * 图片库保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(AttachPictureEntity attachPicture, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(attachPicture.getId())) {
			message = "图片库["+attachPicture.getRealname()+"]更新成功";
			AttachPictureEntity t = attachPictureService.get(AttachPictureEntity.class, attachPicture.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(attachPicture, t);
				attachPictureService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "图片库["+attachPicture.getRealname()+"]更新失败";
			}
		} else {
			String path = attachPicture.getLocalpath();
			String[] localPath = path.split(",");
			for(int i=0;i<localPath.length;i++){
				AttachPictureEntity ap =new AttachPictureEntity();
				ap.setCreatedate(new Date());
				ap.setLocalpath(localPath[i]);
				ap.setRealname(attachPicture.getRealname());
				ap.setLocalname(attachPicture.getLocalname());
				ap.setThumbnailpath(attachPicture.getThumbnailpath());
				HttpSession session = ContextHolderUtils.getSession();
				Client client = ClientManager.getInstance().getClient(session.getId());
				SiteEntity site = client.getSite();
				ap.setSiteid(site.getId());
				attachPictureService.save(ap);
			}
			message = "图片库["+attachPicture.getRealname()+"]添加成功";
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "attachPictureController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * tab页图片库删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		AttachPictureEntity attachPicture = attachPictureService.getEntity(AttachPictureEntity.class, String.valueOf(id));
		message = "图片库["+attachPicture.getLocalname()+"]删除成功";
		attachPictureService.delete(attachPicture);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "attachPictureController.do?table");//tab页删除返回页面
		String str = j.toString();
		return str;
	}
	
	/**
	 * 弹出框图片库删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "delDialog")
	@ResponseBody
	public String delDialog(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		AttachPictureEntity attachPicture = attachPictureService.getEntity(AttachPictureEntity.class, String.valueOf(id));
		message = "图片库["+attachPicture.getLocalname()+"]删除成功";
		attachPictureService.delete(attachPicture);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "attachPictureController.do?tableDialog");//弹出框删除返回页面
		String str = j.toString();
		return str;
	}
}
