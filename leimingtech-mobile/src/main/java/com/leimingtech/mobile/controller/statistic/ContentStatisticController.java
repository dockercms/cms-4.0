package com.leimingtech.mobile.controller.statistic;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.mobile.service.statistic.contentStatisticServiceI;
import jodd.typeconverter.Convert;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Controller 移动平台内容统计
 * @author zhangyulong
 * 
 */
@Controller
@RequestMapping("/contentStatisticController")
public class ContentStatisticController extends BaseController {
	@Autowired
	private contentStatisticServiceI contentStatisticService;

	/**
	 * 内容统计页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "toQuery")
	public ModelAndView toQuery(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = null;
		Long time = System.currentTimeMillis();

		String startDate =null;
		if (startDate == null || startDate.length() < 0) {
			dateTime = new Date(time);// 2592000000
			startDate = format.format(dateTime.getTime()-Convert.toLongValue("2592000000"));
		}
		// 结束时间
		String stopDate = null;
		if (stopDate == null || stopDate.length() < 0) {
			stopDate = format.format(new Date(time));

		}
		map.put("startDate", startDate);
		map.put("stopDate", stopDate);
		map.put("type", "dateTime");
		return new ModelAndView("mobile/statistic/statisticList",map);
	}

	/**
	 * 获取频道集合
	 * 
	 * @return
	 */
	@RequestMapping(params = "getListChannel")
	@ResponseBody
	public String getListChannel() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray jsonArray = new JSONArray();
		List<MobileChannelEntity> list = contentStatisticService.getListChannel();
		for (int i = 0; i < list.size(); i++) {
			jsonArray.add(list.get(i), jsonConfig);
		}

		return jsonArray.toString();
	}

	/**
	 * 获取频道
	 * 
	 * @return 频道实体
	 */
	@RequestMapping(params = "getChannelById")
	@ResponseBody
	public MobileChannelEntity getChannelById(HttpServletRequest request) {
		// 频道ID
		String id = request.getParameter("id");
		if (id.length() < 0 || id == null) {
			return null;
		}
		MobileChannelEntity entity = null;
		entity = contentStatisticService.getChannelById(id);
		return entity;
	}

	/**
	 * 获取模型集合
	 * 
	 * @return
	 */
	@RequestMapping(params = "getListMobileChannel")
	@ResponseBody
	public String getListMobileChannel() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray jsonArray = new JSONArray();
		List<ModelsEntity> list = contentStatisticService.getListMobileChannel();
		for (int i = 0; i < list.size(); i++) {
			jsonArray.add(list.get(i), jsonConfig);
		}

		return jsonArray.toString();
	}

	/**
	 * 获取模型
	 * 
	 * @return 模型实体
	 */
	@RequestMapping(params = "getMobileChannelById")
	@ResponseBody
	public ModelsEntity getMobileChannelById(HttpServletRequest request) {
		// 模型ID
		String id = request.getParameter("id");
		if (id.length() < 0 || id == null) {
			return null;
		}
		ModelsEntity entity = null;
		entity = contentStatisticService.getMobileChannelById(id);
		return entity;
	}

	/**
	 * 加载统计图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadStat")
	public ModelAndView loadStat(HttpServletRequest request) {
		// 开始时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = null;
		Long time = System.currentTimeMillis();

		String startDate = request.getParameter("startDate");
		if (startDate == null || startDate.length() < 0) {
			dateTime = new Date(time);// 2592000000
			startDate = format.format(dateTime.getTime()-Convert.toLongValue("2592000000"));
		}
		// 结束时间
		String stopDate = request.getParameter("stopDate");
		if (stopDate == null || stopDate.length() < 0) {
			dateTime = new Date(time);
			stopDate = format.format(dateTime);

		}
		// 统计类型
		String type = request.getParameter("type");
		// 频道ID
		String catId = request.getParameter("catId");
		// 模型ID
		String classify = request.getParameter("classify");
		
		// 频道Name
		String catName = request.getParameter("catName");
//		if(catName!=null){
//			try {
//				//解决GET提交乱码
//				catName = new String(catName.getBytes("iso-8859-1"), "UTF-8");
//				//catName=URLDecoder.decode(catName, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}else{
//			catName="全部";
//		}
		
		if(catName == null){
			catName="全部";
		}
		
		// 模型Name
		String classifyName = request.getParameter("classifyName");
//		if(classifyName!=null){
//			try {
//				//解决GET提交乱码
//				classifyName=new String(classifyName.getBytes("iso-8859-1"), "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}else{
//			classifyName="全部";
//		}
		
		if(classifyName == null){
			classifyName="全部";
		}
		
		// catId':catId,'classify':classify,'type':staticsType,'startDate':null,'stopDate':null
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("startDate", startDate);
		m.put("stopDate", stopDate);
		m.put("type", type);
		m.put("catId", catId);
		m.put("classify", classify);
		m.put("catName", catName);
		m.put("classifyName", classifyName);
		return new ModelAndView("mobile/statistic/statistics", m);
	}

	/**
	 * 获取移动平台内容统计信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadDataStatistic")
	@ResponseBody
	public JSONArray getCatStatistic(HttpServletRequest request) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray array = new JSONArray();
		// 开始日期
		String startDate = request.getParameter("startDate");
		// 结束日期
		String stopDate = request.getParameter("stopDate");
		// 频道(栏目)ID
		String catId = null;
		String catIdStr = request.getParameter("catId");
		if (catIdStr == null || catIdStr.equals("")) {
			catId = "0";
		} else {
			catId = String.valueOf(catIdStr);
		}
		// 模型ID
		String classify = request.getParameter("classify");
		// 类型
		String type = request.getParameter("type");

		array = contentStatisticService.getStatistic(catId, classify, startDate, stopDate, type);

		return array;
	}
}
