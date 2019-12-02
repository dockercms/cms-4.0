package com.leimingtech.platform.controller.memo;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.platform.entity.memo.MemoEntity;
import com.leimingtech.platform.service.memo.MemoServiceI;

/**   
 * @Title: Controller
 * @Description: 我的便签
 * @author 
 * @date 2014-08-13 12:01:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/memoController")
public class MemoController extends BaseController {

	@Autowired
	private MemoServiceI memoService;
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
	 * 我的便签列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "memo")
	public ModelAndView memo(HttpServletRequest request) {
		TSUser user = PlatFormUtil.getSessionUser();
		MemoEntity memo = null;
		if (null != user) {
			user = memoService.getEntity(TSUser.class, user.getId());
			memo = memoService.findUniqueByProperty(MemoEntity.class, "userid",
					user.getId());
		}

		if (memo == null) {
			memo = new MemoEntity();
		}
		Map<String, Object> m = new HashMap<String, Object>();

		m.put("user", user);
		m.put("memo", memo);
		return new ModelAndView("platform/memo/memoList", m);
	}

	/**
	 * 我的便签添加
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("memo", new MemoEntity());
		return new ModelAndView("platform/memo/memoList", m);
	}
	
	*//**
	 * 我的便签更新
	 * 
	 * @return
	 *//*
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		MemoEntity memo = memoService.getEntity(MemoEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("memo", memo);
		return new ModelAndView("platform/memo/memoList", m);
	}*/

	/**
	 * 我的便签保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MemoEntity memo, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		TSUser user = PlatFormUtil.getSessionUser();
		if (StringUtil.isNotEmpty(memo.getId())) {
			message = "我的便签["+memo.getContent()+"]更新成功";
			MemoEntity t = memoService.get(MemoEntity.class, memo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(memo, t);
				t.setCreatetime(new Date());
				memoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "我的便签["+memo.getContent()+"]更新失败";
			}
		} else {
			message = "我的便签["+memo.getContent()+"]添加成功";
			memo.setUserid(user.getId());
			memo.setCreatetime(new Date());
			memoService.save(memo);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memoController.do?memo");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 我的便签删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		MemoEntity memo = memoService.getEntity(MemoEntity.class, String.valueOf(id));
		message = "我的便签["+memo.getContent()+"]删除成功";
		memoService.delete(memo);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "memoController.do?memo");
		String str = j.toString();
		return str;
	}
}
