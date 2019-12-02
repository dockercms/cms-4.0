package com.leimingtech.cms.controller.Statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.statistics.StatisticsServiceI;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SystemService;

/**
 * @author  :linjm linjianmao@gmail.com
 * @version :2014-5-26下午07:28:31
 * @description :留言、评论、会员统计
 **/
@Controller
@RequestMapping("statisticsController")
public class StatisticsController extends BaseController{
	
	@Autowired
	private MemberServiceI memberService;
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
	
	private String searchYear = "";
	
	@RequestMapping(params ="memberRegStatis")
	public ModelAndView memberRegister(HttpServletRequest request){
		searchYear = request.getParameter("searchYear");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		String year = searchYear;
//		if(StringUtils.isEmpty(searchYear)){
//			year = sdf.format(new Date())+"-0";
//		}
//		JSONObject jsonObject = new JSONObject();
//		for(int i=1; i<=12; i++){
//			String sql = "select count(*) from t_s_member where date_format(createtime,'%Y-%m')='"+year+i+"' ";
//			long count = memberService.getCountForJdbc(sql);
//			jsonObject.put(i+"", count);
//		}
		Map<String , Object> m = new HashMap<String, Object>();
		m.put("searchYear", searchYear);
		return new ModelAndView("cms/statistics/memberStatistics"); 
	}
	
	/**
	 * 获取报表绘图数据
	 * @param request
	 * @return
	 */
	@RequestMapping(params="loadData")
	@ResponseBody
	public JSONArray LoadData(HttpServletRequest request){
		//String searchYear = request.getParameter("searchYear");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = searchYear;
		if(StringUtils.isEmpty(searchYear)){
			year = sdf.format(new Date());
		}
		net.sf.json.JSONArray jsonArray = new JSONArray();
		String sql = "select date_format(createtime,'%m') month,count(*) count from t_s_member where date_format(createtime,'%Y') = '"+year+"' group by date_format(createtime,'%Y-%m')";
//		String sql = "select date_format(createtime,'%d') month,count(*) count from t_s_member where date_format(createtime,'%Y') = '2014' group by date_format(createtime,'%Y-%m-%d')";
		List<Map<String, Object>> list = memberService.findForJdbc(sql, null);
//		for(int i=0; i<=12; i++){
//			JSONObject jsonObject = new JSONObject();
//			if(i>0){
//				String sql = "select count(*) from t_s_member where date_format(createtime,'%Y-%m')='"+year+"-"+i+"' ";
//				if(i<10){
//					sql = "select count(*) from t_s_member where date_format(createtime,'%Y-%m')='"+year+"-0"+i+"' ";
//				}
////				System.out.println("sql=="+sql);
//				long count = memberService.getCountForJdbc(sql);
////				System.out.println("count=="+count);
//				jsonObject.put("count", count);
//				jsonArray.add(jsonObject);
//			}else{
//				jsonObject.put("count", 0);
//				jsonArray.add(jsonObject);
//			}
//		}
		jsonArray = jsonArray.fromObject(list);
		return jsonArray;
	}
	/**
	 * 会员统计页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="memberStatistics")
	public ModelAndView memberStatistics(HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("staticsType", "member");
		return new ModelAndView("cms/statistics/memberComStatistics",m); 
	}
	/**
	 * 评论统计页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="commentStatistics")
	public ModelAndView commentStatistics(HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("staticsType", "comment");
		return new ModelAndView("cms/statistics/memberComStatistics",m); 
	}
	/**
	 * 留言统计页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(params ="messagesStatistics")
	public ModelAndView messagesStatistics(HttpServletRequest request){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("staticsType", "messages");
		return new ModelAndView("cms/statistics/memberComStatistics",m); 
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
		//统计类型
		String staticsType = request.getParameter("staticsType");
		Map<String , Object> m = new HashMap<String, Object>();
		m.put("searchYear", year);
		m.put("searchMonth", month);
		m.put("staticsType", staticsType);
		return new ModelAndView("cms/statistics/mcStatistics",m); 
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
		//统计类型
		String staticsType = request.getParameter("staticsType");
		JSONArray jsonArray = statisticsService.statisticsData(searchYear, searchMonth, staticsType);
		return jsonArray;
	}
	
}
