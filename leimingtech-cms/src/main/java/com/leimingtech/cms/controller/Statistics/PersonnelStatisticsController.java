package com.leimingtech.cms.controller.Statistics;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leimingtech.core.util.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.datatable.DataGrid;
import com.leimingtech.core.entity.TSDepart;
import com.leimingtech.core.entity.TSUser;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;

/**
 * @author  :zhangxiaoqiang
 * @version :2014年6月9日11:42:30
 * @description :部门人员统计
 **/
@Controller
@RequestMapping("personnelStatisticsController")
public class PersonnelStatisticsController extends BaseController{
	
	@Autowired
	private MemberServiceI memberService;
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
	 * 人员统计左边部门树
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "deptTree")
	public ModelAndView deptTree(HttpServletRequest request){
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		cq.isNull("TSPDepart");
		cq.add();
		List<TSDepart> departsList = memberService.getListByCriteriaQuery(cq, false);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("departList", departsList);
		return new ModelAndView("cms/statistics/departList",m);
	}
	@RequestMapping(params = "personnelList")
	public ModelAndView personnelList(HttpServletRequest request){
		String id = request.getParameter("id");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		return new ModelAndView("cms/statistics/personnelList",m);
	}
	/**
	 * 人员统计页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="personnelStatistics")
	public ModelAndView personnelStatistics(HttpServletRequest request){
		String id = request.getParameter("id");
		//查询开始时间
		String startTime = request.getParameter("startTime");
		//查询结束时间
		String endTime = request.getParameter("endTime");
		//查询内容分类
		String model = request.getParameter("model");
		//用户集合
		List<TSUser> userList=new ArrayList<TSUser>();
		if(StringUtil.isNotEmpty(id)){
			CriteriaQuery cq = new CriteriaQuery(TSUser.class);
			DetachedCriteria dc = cq.getDetachedCriteria();
			DetachedCriteria dcDepart = dc.createCriteria("TSDepart");
			dcDepart.add(Restrictions.eq("id", id));
			
			Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
			cq.in("status", userstate);
			cq.add();
			userList = memberService.getListByCriteriaQuery(cq, false);
		}
		//当前人发稿量集合
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		if(userList.size()!=0){
			mapList = this.map(userList,startTime,endTime,model);
		}else{
			List<TSUser> userList1 = memberService.loadAll(TSUser.class);
			mapList = this.map(userList1,startTime,endTime,model);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mapList", mapList);
		return new ModelAndView("cms/statistics/personnelStatistics",m); 
	}
	/**
	 * 比较用户表和内容表用户所关联的内容
	 * @param userList
	 * @return
	 */
	public List<Map<String,Object>> map(List<TSUser> userList,String startTime,String endTime,String model){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		String sql = "";
		//数据库
		String dbType = DBTypeUtil.getDBType();
		for(TSUser user:userList){
			String userId = user.getId();
			if("mysql".equals(dbType)){
				sql = "select ifnull(sum(pv),0) pv,ifnull(sum(grade),0) grades, count(*) counts from cms_content where publishedbyid='"+userId+"'";
			}
			if("sqlserver".equals(dbType)){
				sql = "select isnull(sum(pv),0) pv,isnull(sum(grade),0) grades, count(*) counts from cms_content where publishedbyid='"+userId+"'";
			}
			if("oracle".equals(dbType)){
				sql = "select nvl(sum(pv),0) pv,nvl(sum(grade),0) grades, count(*) counts from cms_content where publishedbyid='"+userId+"'";
			}
			if(startTime!=null&&!startTime.equals("0")){
				if("mysql".equals(dbType)){
					sql += " and date_format(published,'%Y-%m-%d')>='"+startTime+"'";
				}
				if("sqlserver".equals(dbType)){
					sql += " and  CONVERT(varchar(100), published, 23)>='"+startTime+"'";
				}
				if("oracle".equals(dbType)){
					sql += " and to_char(published,'yyyy-MM-dd')>='"+startTime+"'";
				}
			}
			if(endTime!=null&&!endTime.equals("0")){
				if("mysql".equals(dbType)){
					sql += " and date_format(published,'%Y-%m-%d')<='"+endTime+"'";
				}
				if("sqlserver".equals(dbType)){
					sql += " and  CONVERT(varchar(100), published, 23)<='"+endTime+"'";
				}
				if("oracle".equals(dbType)){
					sql += " and to_char(published,'yyyy-MM-dd')<='"+endTime+"'";
				}
			}
			if(model!=null&&!model.equals("0")){
				sql += " and classify='"+model+"'";
			}
			List<Map<String,Object>> mapList1 = memberService.findForJdbc(sql);
			if(mapList1.size()!=0){
				for(int i=0;i<mapList1.size();i++){
					Map<String,Object> map = mapList1.get(i);
					map.put("publishedby", user.getUserName());
					mapList.add(map);
				}
			}
			
		}
		return mapList;
	}
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	@ResponseBody
	public AjaxJson exportXls(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		
		String id = request.getParameter("id");
		//查询开始时间
		String startTime = request.getParameter("startTime");
		//查询结束时间
		String endTime = request.getParameter("endTime");
		//查询内容分类
		String model = request.getParameter("model");
		//查询标记
		String flag = request.getParameter("flag");
		//报表显示信息
		String data = "全部";
		String data1 = "全部";
		if(startTime!=null&&!startTime.equals("0")){
			data = startTime;
			flag = "query";
		}
		if(endTime!=null&&!endTime.equals("0")){
			data += "至"+endTime;
			flag = "query";
		}
		if(model!=null&&!model.equals("0")){
			flag = "query";
			if(model.equals(ContentClassify.CONTENT_ARTICLE)){
				data1 = ContentClassify.CONTENT_ARTICLE_STR;
			}
			if(model.equals(ContentClassify.CONTENT_PICTUREGROUP)){
				data1 = ContentClassify.CONTENT_PICTUREGROUP_STR;
			}
			if(model.equals(ContentClassify.CONTENT_LINK)){
				data1 = ContentClassify.CONTENT_LINK_STR;
			}
			if(model.equals(ContentClassify.CONTENT_VIDEO)){
				data1 = ContentClassify.CONTENT_VIDEO_STR;
			}
			if(model.equals(ContentClassify.CONTENT_VOTE)){
				data1 = ContentClassify.CONTENT_VOTE_STR;
			}
			if(model.equals(ContentClassify.CONTENT_SURVEY)){
				data1 = ContentClassify.CONTENT_SURVEY_STR;
			}
		}
		//用户集合
		List<TSUser> userList=new ArrayList<TSUser>();
		if(id != null && !"".equals(id)){
			CriteriaQuery cq = new CriteriaQuery(TSUser.class);
			DetachedCriteria dc = cq.getDetachedCriteria();
			DetachedCriteria dcDepart = dc.createCriteria("TSDepart");
			dcDepart.add(Restrictions.eq("id", id));
			
			Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN };
			cq.in("status", userstate);
			cq.add();
			userList = memberService.getListByCriteriaQuery(cq, false);
		}
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		if(userList.size()!=0){
			mapList = this.map(userList,startTime,endTime,model);
		}else{
			List<TSUser> userList1 = memberService.loadAll(TSUser.class);
			mapList = this.map(userList1,startTime,endTime,model);
		}
		
		 //使用excel导出流量报表
		TemplateExcelUtil excelUtil = new TemplateExcelUtil();
		// 流量报表excel模板在类包中，转为流给excelutil


		InputStream in=FileUtil.getResourceAsStream("excel/statPannelExcel.xls");
		excelUtil.openModal(in);
		excelUtil.writeStringToCell(1, 0, "日期："+data+"  分类："+data1); // 在第二行加入时间等信息
		int i = 3;
		for(Map<String,Object> m : mapList){
			excelUtil.writeStringToCell(i, 0, m.get("publishedby").toString()); // 人员
			excelUtil.writeStringToCell(i, 1, m.get("counts").toString()); // 发稿量
			excelUtil.writeStringToCell(i, 2, m.get("pv").toString()); // 访问量
			excelUtil.writeStringToCell(i, 3, m.get("grades").toString()); // 评分
			i++;
		}
		// saas 版导出目录用户上下文目录access文件夹
		String filename = "";
		String codedFileName = "renyuantongji";
		File file = new File(ResourceUtil.getSysPath()+"\\temp\\"+filename);
		if (!file.exists()){
			file.mkdirs();
		}
		SimpleDateFormat t = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			filename = codedFileName+"-"+t.format(new Date())+ ".xls";
			excelUtil.writeToFile(ResourceUtil.getSysPath()+"\\temp\\" + filename);//写
			message = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/temp/" + filename;
		} catch (Exception e) {
			e.printStackTrace();
			message="";
		}
		j.setMsg(message);
		return j;
	}
	
}
