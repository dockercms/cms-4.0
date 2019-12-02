package com.leimingtech.cms.controller.Statistics;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.json.AjaxJson;
import com.leimingtech.cms.service.statistics.StatisticsServiceI;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.datatable.DataGrid;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.DBTypeUtil;
import com.leimingtech.core.util.FileUtil;
import com.leimingtech.core.util.ResourceUtil;
import com.leimingtech.core.util.TemplateExcelUtil;

/**
 * @author  :zhangxiaoqiang
 * @version :2014年6月9日11:24:59
 * @description :栏目内容统计
 **/
@Controller
@RequestMapping("contentStatisticsController")
public class ContentStatisticsController extends BaseController{
	
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private StatisticsServiceI statisticsService;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 内容统计左边树
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "contentCatTree")
	public ModelAndView contentCatTree(HttpServletRequest request){
		CriteriaQuery cq = new CriteriaQuery(ContentCatEntity.class);
		cq.eq("levels", 0);
		cq.add();
		List<ContentCatEntity> list = statisticsService.getListByCriteriaQuery(cq, false);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		return new ModelAndView("cms/statistics/contentCatTree", m);
	}
	/**
	 * 内容统计页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="contentStatistics")
	public ModelAndView contentStatistics(HttpServletRequest request){
		String contentCatId = request.getParameter("contentCatId");
		if(contentCatId.equals("")){
			contentCatId = "0";
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentCatId", contentCatId);
		return new ModelAndView("cms/statistics/contentStatistics",m); 
	}
	/**
	 * 加载统计图
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="loadStat")
	public ModelAndView loadStat(HttpServletRequest request){
		//年
		String year = request.getParameter("year");
		//月
		String month = request.getParameter("month");
		//模型分类
		String classify = request.getParameter("classify");
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		if(contentCatId.equals("")&&contentCatId==null){
			contentCatId = "0";
		}
		ContentCatEntity contentCat = statisticsService.get(ContentCatEntity.class, String.valueOf(contentCatId));
		Map<String , Object> m = new HashMap<String, Object>();
		m.put("searchYear", year);
		m.put("searchMonth", month);
		m.put("classify", classify);
		m.put("contentCatId", contentCatId);
		m.put("contentCat",contentCat);
		return new ModelAndView("cms/statistics/statistics",m); 
	}
	/**
	 * 加载统计数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params="loadDataStat")
	@ResponseBody
	public JSONArray loadDataStat(HttpServletRequest request){
		//年
		String searchYear = request.getParameter("searchYear");
		//月
		String searchMonth = request.getParameter("searchMonth");
		//模型分类
		String classify = request.getParameter("classify");
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = searchYear;
		String month = searchMonth;
		if(StringUtils.isEmpty(searchYear)){
			year = sdf.format(new Date());
		}
		JSONArray jsonArray = new JSONArray();
		//年统计
		jsonArray = yearStat(year,month,classify,contentCatId);
		return jsonArray;
	}
	/**
	 * 按年、月、模型分类统计
	 * @param year
	 * @return
	 */
	public JSONArray yearStat(String year,String month,String classify,String contentCatId){
		JSONArray jsonArray = new JSONArray();
		//获取选中月的天数
		int days = 0;
		if(!month.equals("0")){
			String date = year+"/"+month;
			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
			try {
				rightNow.setTime(simpleDate.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if(Integer.valueOf(month)<10&&Integer.valueOf(month)!=0){
			month = "0"+month;
		}
		//数据库
		String dbType = DBTypeUtil.getDBType();
		//选中年
		if(!year.equals("0")){
			//按月进行查询，显示天数
			jsonArray = this.days(days, year, month,classify,contentCatId,dbType);
		//全部年
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			int yearV = Integer.valueOf(sdf.format(new Date()));
			for(int i=0;i<10;i++){
				JSONObject jsonObject = new JSONObject();
				int value = yearV-i;
				String sql = "",sql1 = "";
				if(month.equals("0")){  
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y')='"+value+"' ";
						sql1= "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y')='"+value+"' ";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+value+"' ";
						sql1= "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+value+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy')='"+value+"' ";
						sql1= "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy')='"+value+"' ";
					}
				}else{
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+value+"-"+month+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+value+"-"+month+"' ";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+value+"' and month(published)='"+month+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+value+"' and month(commentaryTime)='"+month+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+value+"-"+month+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+value+"-"+month+"' ";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				if(!classify.equals("0")){
					sql += " and classify ='"+classify+"' ";
					sql1 += " and classify ='"+classify+"' ";
				}
				if(!contentCatId.equals("0")){
					sql += " and catid ='"+contentCatId+"' ";
					sql1 += " and catid ='"+contentCatId+"' ";
				}
				sql1 += ")";
				List<Map<String,Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("counts"));
					jsonObject.put("pv", mapList1.get(0).get("pv"));
					jsonObject.put("count1", mapList2.get(0).get("counts1"));
					jsonObject.put("date", value);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("pv", 0);
					jsonObject.put("count1", 0);
					jsonObject.put("date", value);
					jsonArray.add(jsonObject);
				}
			}
		}
		return jsonArray;
	}
	/**
	 * 按月进行查询，显示天数
	 * @param days
	 * @param year
	 * @param month
	 * @return
	 */
	public JSONArray days(int days,String year,String month,String classify,String contentCatId,String dbType) {
		JSONArray jsonArray = new JSONArray();
		if(!month.equals("0")){
			for(int i=1;i<days+1;i++){
				JSONObject jsonObject = new JSONObject();
				String sql = "",sql1 = "";
				if("mysql".equals(dbType)){
					sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m-%d')='"+year+"-"+month+"-"+i+"' ";
					sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m-%d')='"+year+"-"+month+"-"+i+"' ";
				}
				if("sqlserver".equals(dbType)){
					sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+year+"' and month(published)='"+month+"' and day(published)='"+i+"' ";
					sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+year+"' and month(commentaryTime)='"+month+"' and day(commentaryTime)='"+i+"' ";
				}
				if("oracle".equals(dbType)){
					sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"' ";
					sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM-dd')='"+year+"-"+month+"-"+i+"' ";
				}
				if(i<10){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m-%d')='"+year+"-"+month+"-0"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m-%d')='"+year+"-"+month+"-0"+i+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM-dd')='"+year+"-"+month+"-0"+i+"' ";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				if(!classify.equals("0")){
					sql += " and classify ='"+classify+"' ";
					sql1 += " and classify ='"+classify+"' ";
				}
				if(!contentCatId.equals("0")){
					sql += " and catid ='"+contentCatId+"' ";
					sql1 += " and catid ='"+contentCatId+"' ";
				}
				sql1 += ")";
				List<Map<String,Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("counts"));
					jsonObject.put("pv", mapList1.get(0).get("pv"));
					jsonObject.put("count1", mapList2.get(0).get("counts1"));
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("pv", 0);
					jsonObject.put("count1", 0);
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}
			}
		}else{//按年查询，显示所有月
			for(int i=1; i<13; i++){
				JSONObject jsonObject = new JSONObject();
				String sql = "",sql1 = "";
				if("mysql".equals(dbType)){
					sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+year+"-"+i+"' ";
					sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+year+"-"+i+"' ";
				}
				if("sqlserver".equals(dbType)){
					sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+year+"' and month(published)='"+i+"' ";
					sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+year+"' and month(commentaryTime)='"+i+"' ";
				}
				if("oracle".equals(dbType)){
					sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+year+"-"+i+"' ";
					sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+year+"-"+i+"' ";
				}
				if(i<10){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+year+"-0"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+year+"-0"+i+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+year+"-0"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+year+"-0"+i+"' ";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				if(!classify.equals("0")){
					sql += " and classify ='"+classify+"' ";
					sql1 += " and classify ='"+classify+"' ";
				}
				if(!contentCatId.equals("0")){
					sql += " and catid ='"+contentCatId+"' ";
					sql1 += " and catid ='"+contentCatId+"' ";
				}
				sql1 += ")";
				List<Map<String,Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					jsonObject.put("count", mapList1.get(0).get("counts"));
					jsonObject.put("pv", mapList1.get(0).get("pv"));
					jsonObject.put("count1", mapList2.get(0).get("counts1"));
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}else{
					jsonObject.put("count", 0);
					jsonObject.put("pv", 0);
					jsonObject.put("count1", 0);
					jsonObject.put("date", i);
					jsonArray.add(jsonObject);
				}
				
			}
//			List<Map<String, Object>> list = memberService.findForJdbc(sql, null);
//			jsonArray = jsonArray.fromObject(list);
		}
		return jsonArray;
	}
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	@ResponseBody
	public AjaxJson exportXls(ContentsEntity contents,HttpServletRequest request, DataGrid dataGrid) {
		AjaxJson j = new AjaxJson();
		//年
		String searchYear = request.getParameter("searchYear");
		//月
		String searchMonth = request.getParameter("searchMonth");
		//模型分类
		String classify = request.getParameter("classify");
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//获取选中月的天数
		int days = 0;
		//数据库
		String dbType = DBTypeUtil.getDBType();
		if(!searchMonth.equals("0")){
			String date = searchYear+"/"+searchMonth;
			Calendar rightNow = Calendar.getInstance();
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); 
			try {
				rightNow.setTime(simpleDate.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		if(Integer.valueOf(searchMonth)<10&&Integer.valueOf(searchMonth)!=0){
			searchMonth = "0"+searchMonth;
		}
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		
		String sql="",sql1 = "";
		if(!searchYear.equals("0")){
			if(days!=0){
				for(int i=1;i<days+1;i++){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m-%d')='"+searchYear+"-"+searchMonth+"-"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m-%d')='"+searchYear+"-"+searchMonth+"-"+i+"' ";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where CONVERT(varchar(100), published, 23)='"+searchYear+"-"+searchMonth+"-"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where CONVERT(varchar(100), commentaryTime, 23)='"+searchYear+"-"+searchMonth+"-"+i+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM-dd')='"+searchYear+"-"+searchMonth+"-"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM-dd')='"+searchYear+"-"+searchMonth+"-"+i+"' ";
					}
					if(i<10){
						if("mysql".equals(dbType)){
							sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m-%d')='"+searchYear+"-"+searchMonth+"-0"+i+"' ";
							sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m-%d')='"+searchYear+"-"+searchMonth+"-0"+i+"' ";
						}
						if("oracle".equals(dbType)){
							sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM-dd')='"+searchYear+"-"+searchMonth+"-0"+i+"' ";
							sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM-dd')='"+searchYear+"-"+searchMonth+"-0"+i+"' ";
						}
					}
					sql1 += " and contentId in (select id from cms_content where 1=1";
					if(!classify.equals("0")){
						sql += " and classify ='"+classify+"' ";
						sql1 += " and classify ='"+classify+"' ";
					}
					if(!contentCatId.equals("0")){
						sql += " and catid ='"+contentCatId+"' ";
						sql1 += " and catid ='"+contentCatId+"' ";
					}
					sql1 += ")";
					List<Map<String, Object>> mapList1 = statisticsService.findForJdbc(sql);
					List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
					if(mapList1.size()!=0&&mapList2.size()!=0){
						mapList1.get(0).put("date", searchYear+"年"+searchMonth+"月"+i+"日");
						mapList1.get(0).put("count1", mapList2.get(0).get("counts1"));
						mapList.add(mapList1.get(0));
					}else{
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("date", searchYear+"年"+searchMonth+"月"+i+"日");
						map.put("counts", 0);
						map.put("count1", 0);
						map.put("pv", 0);
						mapList.add(map);
					}
				}
			}else{
				for(int i=1; i<13; i++){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+searchYear+"-"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+searchYear+"-"+i+"' ";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+searchYear+"' and month(published)='"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+searchYear+"' and month(commentaryTime)='"+i+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+searchYear+"-"+i+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+searchYear+"-"+i+"' ";
					}
					if(i<10){
						if("mysql".equals(dbType)){
							sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+searchYear+"-0"+i+"' ";
							sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+searchYear+"-0"+i+"' ";
						}
						if("oracle".equals(dbType)){
							sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+searchYear+"-0"+i+"' ";
							sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+searchYear+"-0"+i+"' ";
						}
					}
					sql1 += " and contentId in (select id from cms_content where 1=1";
					if(!classify.equals("0")){
						sql += " and classify ='"+classify+"' ";
						sql1 += " and classify ='"+classify+"' ";
					}
					if(!contentCatId.equals("0")){
						sql += " and catid ='"+contentCatId+"' ";
						sql1 += " and catid ='"+contentCatId+"' ";
					}
					sql1 += ")";
					List<Map<String, Object>> mapList1 = statisticsService.findForJdbc(sql);
					List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
					if(mapList1.size()!=0&&mapList2.size()!=0){
						mapList1.get(0).put("date", searchYear+"年"+i+"月");
						mapList1.get(0).put("count1", mapList2.get(0).get("counts1"));
						mapList.add(mapList1.get(0));
					}else{
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("date", searchYear+"年"+i+"月");
						map.put("counts", 0);
						map.put("count1", 0);
						map.put("pv", 0);
						mapList.add(map);
					}
				}
			}
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			int yearV = Integer.valueOf(sdf.format(new Date()));
			for(int i=0;i<10;i++){
				int value = yearV-i;
				if(searchMonth.equals("0")){
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y')='"+value+"' ";
						sql1= "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y')='"+value+"' ";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+value+"' ";
						sql1= "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+value+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy')='"+value+"' ";
						sql1= "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy')='"+value+"' ";
					}
				}else{
					if("mysql".equals(dbType)){
						sql = "select count(*) counts,ifnull(sum(pv),0) pv from cms_content where date_format(published,'%Y-%m')='"+value+"-"+searchMonth+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where date_format(commentaryTime,'%Y-%m')='"+value+"-"+searchMonth+"' ";
					}
					if("sqlserver".equals(dbType)){
						sql = "select count(*) counts,isnull(sum(pv),0) pv from cms_content where year(published)='"+value+"' and month(published)='"+searchMonth+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where year(commentaryTime)='"+value+"' and month(commentaryTime)='"+searchMonth+"' ";
					}
					if("oracle".equals(dbType)){
						sql = "select count(*) counts,nvl(sum(pv),0) pv from cms_content where to_char(published,'yyyy-MM')='"+value+"-"+searchMonth+"' ";
						sql1 = "select count(*) counts1 from cms_commentary where to_char(commentaryTime,'yyyy-MM')='"+value+"-"+searchMonth+"' ";
					}
				}
				sql1 += " and contentId in (select id from cms_content where 1=1";
				if(!classify.equals("0")){
					sql += " and classify ='"+classify+"' ";
					sql1 += " and classify ='"+classify+"' ";
				}
				if(!contentCatId.equals("0")){
					sql += " and catid ='"+contentCatId+"' ";
					sql1 += " and catid ='"+contentCatId+"' ";
				}
				sql1 += ")";
				List<Map<String, Object>> mapList1 = statisticsService.findForJdbc(sql);
				List<Map<String,Object>> mapList2 = statisticsService.findForJdbc(sql1);
				if(mapList1.size()!=0&&mapList2.size()!=0){
					if(searchMonth.equals("0")){
						mapList1.get(0).put("date", value+"年");
					}else{
						mapList1.get(0).put("date", value+"年"+searchMonth+"月");
					}
					mapList1.get(0).put("count1", mapList2.get(0).get("counts1"));
					mapList.add(mapList1.get(0));
				}else{
					Map<String,Object> map = new HashMap<String,Object>();
					if(searchMonth.equals("0")){
						map.put("date", value+"年");
					}else{
						map.put("date", value+"年"+searchMonth+"月");
					}
					map.put("counts", 0);
					map.put("count1", 0);
					map.put("pv", 0);
					mapList.add(map);
				}
				
			}
		}
		 //使用excel导出流量报表
		TemplateExcelUtil excelUtil = new TemplateExcelUtil();
		// 流量报表excel模板在类包中，转为流给excelutil
		InputStream in = FileUtil.getResourceAsStream("excel/statExcel.xls");
		excelUtil.openModal(in);
		int i = 2;
		for(Map<String,Object> m : mapList){
			excelUtil.writeStringToCell(i, 0, m.get("date").toString()); // 日期
			excelUtil.writeStringToCell(i, 1, m.get("counts").toString()); // 发稿量
			excelUtil.writeStringToCell(i, 2, m.get("count1").toString()); // 评论量
			excelUtil.writeStringToCell(i, 3, m.get("pv").toString()); // 访问量
			i++;
		}
		// saas 版导出目录用户上下文目录access文件夹
		String filename = "";
		String codedFileName = "lanmutongji";
		File file = new File(ResourceUtil.getSysPath()+"\\temp\\"+filename);
		if (!file.exists()){
			file.mkdirs();
		}
		//ResourceUtil.getSysPath()==E:\workspace\lm-cms\WebRoot\
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
