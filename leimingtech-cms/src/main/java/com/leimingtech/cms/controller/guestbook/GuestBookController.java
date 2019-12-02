package com.leimingtech.cms.controller.guestbook;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.leimingtech.cms.entity.guestbook.GuestBookEntity;
import com.leimingtech.cms.service.guestbook.GuestBookServiceI;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.TSTypegroup;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**   
 * @Title: Controller
 * @Description: 留言板
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-21 13:38:22
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/guestBookController")
public class GuestBookController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(GuestBookController.class);

	@Autowired
	private GuestBookServiceI guestBookService;
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
	 * 留言板列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(GuestBookEntity guestBook, HttpServletRequest request) {
		//获取留言板列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(GuestBookEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		SiteEntity site = client.getSite();
		guestBook.setSiteId(site.getId());
		HqlGenerateUtil.installHql(cq, guestBook, param);
		//排序条件
		cq.addOrder("createTime", SortDirection.desc);
		cq.add();
		PageList pageList = this.guestBookService.getPageList(cq, true);
		List<GuestBookEntity> testList = pageList.getResultList();
		
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
		m.put("actionUrl", "guestBookController.do?table");
		return new ModelAndView("cms/guestbook/guestBookList", m);
	}

	/**
	 * 留言板添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "addPageModel")
	public ModelAndView addPageModel(HttpServletRequest reqeust){
		//TSUser userSession = ResourceUtil.getSessionUserName();
		//String realId = userSession.getId();
		Map<String, Object> m = new HashMap<String, Object>();
		//获取留言板类别
		TSTypegroup typeGroup = systemService.getTypeGroupByCode("guestbook_ctg");
		List<TSType> typeList = new ArrayList<TSType>();
		CriteriaQuery cq = new CriteriaQuery(TSType.class);
		cq.eq("TSTypegroup.id", typeGroup.getId());
		cq.add();
		typeList=guestBookService.getListByCriteriaQuery(cq, false);
		m.put("page", new GuestBookEntity());
		m.put("typeList", typeList);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date =new Date();
	    String createTime =format.format(date);
		m.put("createTime", createTime);
		m.put("memberId", 1);
		return new ModelAndView("cms/guestbook/guestBook", m);
	}
	
	
	/**
	 * 留言板更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		GuestBookEntity guestBook = guestBookService.getEntity(GuestBookEntity.class, String.valueOf(id));
		//获取留言板类别
		TSTypegroup typeGroup = systemService.getTypeGroupByCode("guestbook_ctg");
		List<TSType> typeList = new ArrayList<TSType>();
		CriteriaQuery cq = new CriteriaQuery(TSType.class);
		cq.eq("TSTypegroup.id", typeGroup.getId());
		cq.add();
		typeList=guestBookService.getListByCriteriaQuery(cq, false);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("page", guestBook);
		m.put("typeList", typeList);
		return new ModelAndView("cms/guestbook/guestBook", m);
	}
	
	/**
	 * 留言板保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(GuestBookEntity guestBook, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(guestBook.getId())) {
			message = "留言板【"+guestBook.getContent()+"】更新成功";
			GuestBookEntity t = guestBookService.get(GuestBookEntity.class, guestBook.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(guestBook, t);
				guestBookService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "留言板更新失败";
			}
		} else {
			message = "留言板【"+guestBook.getContent()+"】添加成功";
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			SiteEntity site = client.getSite();
			guestBook.setSiteId(site.getId());
			guestBook.setCreateTime(new Date());//创建时间
			guestBookService.save(guestBook);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "guestBookController.do?table");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 留言板删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		GuestBookEntity guestBook = guestBookService.getEntity(GuestBookEntity.class, String.valueOf(id));
		message = "留言板【"+guestBook.getContent()+"】删除成功";
		guestBookService.delete(guestBook);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "guestBookController.do?table");
		String str = j.toString();
		return str;
	}
	 /**
     * 从 request 获取可能为多个值的数组表示, ""不是有效单个值
     *
     * @param request
     * @param inputName
     * @return
     */
    public static String[] getArrayFromRequest(HttpServletRequest reqeust, String inputName) {
        String[] returnStrArray = null;
        String tempStr = reqeust.getParameter(inputName);
        if(tempStr != null && tempStr.length() > 0) {
            returnStrArray = tempStr.split(",");
        }
        if(returnStrArray == null) {
            returnStrArray = new String[0];
        }
        return returnStrArray;
    }
    
    /**
	 * 留言板审核
	 * 
	 * @return
	 */
	@RequestMapping(params = "AuditPageModel")
	@ResponseBody
	public String AuditPageModel(HttpServletRequest reqeust){
		JSONObject j = new JSONObject();
		String isCheck=reqeust.getParameter("isCheck");
		String[] id = getArrayFromRequest(reqeust, "ids"); // 从request获取多条记录id
		GuestBookEntity guestBook = new GuestBookEntity();
		if (id != null && id.length != 0) {
    		for (int i = 0; i < id.length; i++) {
    			guestBook = guestBookService.getEntity(GuestBookEntity.class, id[i]);
    			if (guestBook != null) {
    				message = "留言板【"+guestBook.getContent()+"】审核成功";
    				GuestBookEntity t = guestBookService.get(GuestBookEntity.class, id[i]);
	    				if("1".equals(isCheck)){
	    					t.setIsChecked(1);
	    				}else{
	    					t.setIsChecked(0);
	    				}
    				try {
    					MyBeanUtils.copyBeanNotNull2Bean(guestBook, t);
    					guestBookService.saveOrUpdate(t);
    					systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
    				} catch (Exception e) {
    					e.printStackTrace();
    					message = "留言板审核失败";
    				}
    			}
    		}
    	}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "guestBookController.do?table");
		String str = j.toString();
		return str;
	}

}
