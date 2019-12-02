package com.leimingtech.platform.controller.note;
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
import com.leimingtech.platform.entity.note.NoteEntity;
import com.leimingtech.platform.service.note.NoteServiceI;

/**   
 * @Title: Controller
 * @Description: 短信平台
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-03 16:59:27
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/noteController")
public class NoteController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NoteController.class);

	@Autowired
	private NoteServiceI noteService;
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
	 * 短信平台列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(NoteEntity note, HttpServletRequest request) {
		//获取短信平台列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(NoteEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, note, param);
		//排序条件
		cq.addOrder("createdtime",SortDirection.desc);
		cq.add();
		PageList pageList = this.noteService.getPageList(cq, true);
		List<NoteEntity> testList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", testList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("actionUrl", "noteController.do?table");
		return new ModelAndView("lmPage/note/noteList", m);
	}

	/**
	 * 短信平台添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		NoteEntity note = new NoteEntity();
		m.put("page", note);
		return new ModelAndView("lmPage/note/note", m);
	}
	
	/**
	 * 短信平台更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		NoteEntity note = noteService.getEntity(NoteEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", note);
		return new ModelAndView("lmPage/note/note", m);
	}

	/**
	 * 短信平台保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(NoteEntity note, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(note.getId())) {
			message = "短信平台["+note.getId()+"]更新成功";
			NoteEntity t = noteService.get(NoteEntity.class, note.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(note, t);
				noteService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "短信平台["+note.getId()+"]更新失败";
			}
		} else {
			message = "短信平台["+note.getId()+"]添加成功";
			note.setCreatedtime(new Date()); //创建时间
			noteService.save(note);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "noteController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 短信平台删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		NoteEntity note = noteService.getEntity(NoteEntity.class, String.valueOf(id));
		message = "短信平台["+note.getId()+"]删除成功";
		noteService.delete(note);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "noteController.do?table");
		String str = j.toString();
		return str;
	}
}
