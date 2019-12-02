package com.leimingtech.cms.controller.survey;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.controller.contents.ContentsbaseController;
import com.leimingtech.cms.entity.survey.SurveyLogExtEntity;
import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.Client;
import com.leimingtech.core.entity.ContentCatDefault;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.SurveyEntity;
import com.leimingtech.core.entity.SurveyLogDataEntity;
import com.leimingtech.core.entity.SurveyLogEntity;
import com.leimingtech.core.entity.SurveyOptionEntity;
import com.leimingtech.core.entity.SurveyOptionExtEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.SurveyLogServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.MapJsonUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.RemoteRequest;
import com.leimingtech.core.util.Request;
import com.leimingtech.core.util.Response;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.ip.IPSeeker;
import com.leimingtech.core.weibo4j.Users;
import com.leimingtech.core.weibo4j.model.WeiboException;
import com.leimingtech.core.weibo4j.org.json.JSONException;

/**
 * @Title: Controller
 * @Description: 调查日志
 * @author
 * @date 2014-06-11 09:12:26
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/front/surveyLogController")
public class SurveyLogController extends ContentsbaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(SurveyLogController.class);

	@Autowired
	private SurveyLogServiceI surveyLogService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private MemberServiceI memberMng;
	@Autowired
	private SiteServiceI siteServiceI;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 调查日志列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "surveyLog")
	public ModelAndView surveyLog(SurveyLogEntity surveyLog,
			HttpServletRequest request) {
		// 栏目id，用于返回
		String contentCatId = request.getParameter("contentCatId");
		// 内容id
		String contentsId = request.getParameter("contentsId");
		List<SurveyEntity> surveyList = surveyLogService.findByProperty(
				SurveyEntity.class, "contentid", String.valueOf(contentsId));
		// 调查id
		String surveyId = "0";
		if (surveyList.size() != 0) {
			surveyId = surveyList.get(0).getId();
		}
		List<Map<String, Object>> mapList = this.getMapList(String
				.valueOf(surveyId));
		// 选项集合
		List<SurveyOptionEntity> surveyOptionList = surveyLogService
				.findByProperty(SurveyOptionEntity.class, "surveyid", surveyId);
		// 获取调查日志列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10
				: Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1
				: Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(SurveyLogEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, surveyLog, param);
		cq.eq("surveyid", surveyId);
		// 排序条件
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		PageList pageList = this.surveyLogService.getPageList(cq, true);
		List<SurveyLogEntity> resultList = pageList.getResultList();
		// 获取路径前缀
		String conpath = ContextHolderUtils.getRequest().getContextPath();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("conpath", conpath);
		m.put("contentCatId", contentCatId);
		m.put("mapList", mapList);
		m.put("surveyOptionList", surveyOptionList);
		m.put("actionUrl",
				"front/surveyLogController.do?surveyLog&contentCatId="
						+ contentCatId + "&contentsId=" + contentsId);
		return new ModelAndView("cms/survey/surveyLogList", m);
	}

	/**
	 * 调查日志添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("surveyLog", new SurveyLogEntity());
		return new ModelAndView("cms/survey/surveyLog", m);
	}

	/**
	 * 调查日志更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		SurveyLogEntity surveyLog = surveyLogService.getEntity(
				SurveyLogEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("surveyLog", surveyLog);
		return new ModelAndView("cms/survey/surveyLog", m);
	}

	/**
	 * 调查日志保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(SurveyLogEntity surveyLog,
			HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(surveyLog.getId())) {
			message = "调查日志[" + surveyLog.getSurveyLogDataList() + "]更新成功";
			SurveyLogEntity t = surveyLogService.get(SurveyLogEntity.class,
					surveyLog.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(surveyLog, t);
				surveyLogService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO,
						Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "调查日志[" + surveyLog.getSurveyLogDataList() + "]更新失败";
			}
		} else {
			message = "调查日志[" + surveyLog.getSurveyLogDataList() + "]添加成功";
			surveyLog.setCreated(new Date());// 创建时间
			surveyLogService.save(surveyLog);
			systemService.addLog(message, Globals.Log_Leavel_INFO,
					Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "surveyLogController.do?surveyLog");
		String str = j.toString();
		return str;
	}

	/**
	 * 调查日志删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		SurveyLogEntity surveyLog = surveyLogService.getEntity(
				SurveyLogEntity.class, String.valueOf(id));
		message = "调查日志[" + surveyLog.getSurveyLogDataList() + "]删除成功";
		surveyLogService.delete(surveyLog);
		systemService.addLog(message, Globals.Log_Leavel_INFO,
				Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "surveyLogController.do?surveyLog");
		String str = j.toString();
		return str;
	}

	/**
	 * 调查提交保存
	 * 
	 * @param request
	 */
	@RequestMapping(params = "subSurvey")
	@ResponseBody
	public String subSurvey(HttpServletRequest request) {
		JSONObject j = new JSONObject();

		String surveyId = request.getParameter("surveyId");

		//获取客户端IP
		String ip = IpUtil.getIpAddr(request);

		SurveyEntity survey = surveyLogService.get(SurveyEntity.class,
				String.valueOf(surveyId));
		int ipnum = 0;
		if (StringUtil.isNotEmpty(survey.getSurveyiplimit())) {
			ipnum = Integer.valueOf(survey.getSurveyiplimit());
		}
		int ipLimit = ipnum * 3600;// 小时限制

		String sql = "select * from cms_survey_log where ip='" + ip
				+ "' and UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(created)<'"
				+ ipLimit + "'";
		List<SurveyLogEntity> surveyLogList = surveyLogService
				.findListbySql(sql);
		int size = surveyLogList.size();
		// 同IP,ipLimit小时之内没有数据表示可以投票
		if (size == 0) {
			// 是否会员登录
			if (survey.getIsvip().equals("true")) {
				HttpSession session = ContextHolderUtils.getSession();
				ClientManager clientMng = ClientManager.getInstance();
				Client client = clientMng.getClient(session.getId());
				if (client == null || client.getMember() == null) {
					message = "您还未登录系统，无法提交";
					j.accumulate("isSuccess", false);
				} else {
					try {
						message = savaLogList(surveyId, ip, client.getMember()
								.getUsername(), request);
						j.accumulate("isSuccess", true);
					} catch (Exception e) {
						e.printStackTrace();
						j.accumulate("isSuccess", false);
						message = "服务器处理失败，请稍后重试";
					}
				}
			} else {
				try {
					message = savaLogList(surveyId, ip, "匿名", request);
					j.accumulate("isSuccess", true);
				} catch (Exception e) {
					e.printStackTrace();
					j.accumulate("isSuccess", false);
					message = "服务器处理失败，请稍后重试";
				}
			}
		} else {
			j.accumulate("isSuccess", false);
			message = "请勿在 " + ipnum + " 小时之内重复提交，详情请咨询管理员";
		}

		j.accumulate("msg", message);
		return j.toString();
	}

	public String savaLogList(String surveyId, String ip, String username,
			HttpServletRequest request) {
		Map map = clientIP(ip);

		SurveyLogEntity surveyLog = new SurveyLogEntity();
		surveyLog.setSurveyid(String.valueOf(surveyId));
		surveyLog.setCreated(new Date());
		surveyLog.setCreatedby(username);
		surveyLog.setIp(ip);
		surveyLog.setCountry((String) map.get("country"));
		surveyLog.setProvince((String) map.get("region"));
		surveyLog.setCity((String) map.get("city"));
		surveyLog.setCounty((String) map.get("county"));

		surveyLogService.save(surveyLog);
		List<SurveyOptionEntity> optionList = surveyLogService.findByProperty(
				SurveyOptionEntity.class, "surveyid", surveyId);
		if (optionList != null) {
			for (SurveyOptionEntity option : optionList) {
				String name = request.getParameter(option.getId());
				if (!option.getDataType().equals("text")) {
					if(StringUtils.isNotEmpty(name)){
						if (option.getDataType().equals("radio")) {
							SurveyLogDataEntity surveyLogData = new SurveyLogDataEntity();
							surveyLogData.setOptionid(option.getId());// 选项
							surveyLogData.setOptionextid(name);// 选项内容
							surveyLogData.setLogid(surveyLog.getId());
							surveyLogData.setIp(ip);
							surveyLogData.setCountry((String) map.get("country"));
							surveyLogData.setProvince((String) map.get("region"));
							surveyLogData.setCity((String) map.get("city"));
							surveyLogData.setCounty((String) map.get("county"));
							surveyLogData.setCreatedtime(new Date());// 创建时间
							surveyLogService.save(surveyLogData);
						} else if (option.getDataType().equals("checkbox")) {
							String checkname[]=request.getParameterValues(option.getId());
							for(int i=0;i<checkname.length;i++){
								SurveyLogDataEntity surveyLogData = new SurveyLogDataEntity();
								surveyLogData.setOptionid(option.getId());// 选项
								surveyLogData.setOptionextid(checkname[i]);// 选项内容
								surveyLogData.setLogid(surveyLog.getId());
								surveyLogData.setIp(ip);
								surveyLogData.setCountry((String) map.get("country"));
								surveyLogData.setProvince((String) map.get("region"));
								surveyLogData.setCity((String) map.get("city"));
								surveyLogData.setCounty((String) map.get("county"));
								surveyLogData.setCreatedtime(new Date());// 创建时间
								surveyLogService.save(surveyLogData);
							}
						}
						
					}
					

				} else {
					SurveyLogExtEntity logexe = new SurveyLogExtEntity();
					logexe.setLogid(surveyLog.getId());
					logexe.setIp(ip);
					logexe.setCreatedtime(new Date());// 创建时间
					logexe.setExt(name);
					logexe.setOptionid(option.getId());
				
				}
			}

		}

		message = "提交成功，感谢您的参与。";
		return message;
	}

	/**
	 * 根据IP地址获取国家、省市、区县
	 * 
	 * @param ip
	 * @return
	 */
	private Map clientIP(String ip) {
		Map map = new HashMap();
		Request remoteRequest = new RemoteRequest();
		Response remoteResponse = remoteRequest
				.execute("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
		String content = remoteResponse.getContent();
		String country = "";
		String region = "";
		String city = "";
		String county = "";
		if (remoteResponse.getStatusCode() == 200
				&& !StringUtil.isEmpty(content)) {
			Map result = MapJsonUtil.parseJSON2Map(content);
			String data = result.get("data") + "";
			result = MapJsonUtil.parseJSON2Map(data);
			country = result.get("country") + "";// 国家
			region = result.get("region") + "";// 省
			if (!region.equals("")) {
				region = region.substring(0, 2);
			}
			city = result.get("city") + "";// 市
			county = result.get("county") + "";// 区县
		} else {
			IPSeeker ipseeker = IPSeeker.getInstance();
			country = ipseeker.getCountry(ip);
			System.out.println(country);
			region = country.substring(0, 2); // 获取访问者所在的省
			city = country
					.substring(country.length() - 3, country.length() - 1); // 获取访问者所在的市
		}
		map.put("country", country);
		map.put("region", region);
		map.put("city", city);
		map.put("county", county);
		return map;
	}

	/**
	 * 跳转到显示结果页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "surveyTag")
	public ModelAndView surveyTag(HttpServletRequest request) {
		String contentId = request.getParameter("contentId");
		String surveyId = request.getParameter("surveyId");
		List<Map<String, Object>> mapList = this.getMapList(surveyId);
		// 选项集合
		List<SurveyOptionEntity> surveyOptionList = surveyLogService
				.findByProperty(SurveyOptionEntity.class, "surveyid",
						String.valueOf(surveyId));
		// 内容
		ContentsEntity content = surveyLogService.getEntity(
				ContentsEntity.class, String.valueOf(contentId));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sitePath", memberMng.getSitePath());
		m.put("content", content);
		m.put("surveyOptionList", surveyOptionList);
		m.put("mapList", mapList);
		return new ModelAndView("wwwroot/www/template/surveyResult", m);
	}

	/**
	 * 跳转到显示结果页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "surveyPcTag")
	public ModelAndView surveyPcTag(HttpServletRequest request) {
		String contentId = request.getParameter("contentId");
		String surveyId = request.getParameter("surveyId");
		String siteId=request.getParameter("siteId");
		String catId=request.getParameter("catId");
		ContentCatEntity catalogF=new ContentCatEntity();
		if(StringUtils.isNotEmpty(catId)){
			catalogF=surveyLogService.get(ContentCatEntity.class, catId);
		}
		List<Map<String, Object>> mapList = this.getMapList(surveyId);
		// 选项集合
		List<SurveyOptionEntity> surveyOptionList = surveyLogService
				.findByProperty(SurveyOptionEntity.class, "surveyid",
						String.valueOf(surveyId));
		// 内容
		ContentsEntity content = surveyLogService.getEntity(
				ContentsEntity.class, String.valueOf(contentId));
		Map<String, Object> m = new HashMap<String, Object>();
		SiteEntity site=siteServiceI.getSite(content.getSiteid());
		
		m.put("site", site);
		m.put("contentF", content);
		m.put("catalogF", catalogF);
		m.put("sitePath", memberMng.getSitePath());
		m.put("content", content);
		m.put("surveyOptionList", surveyOptionList);
		m.put("mapList", mapList);
		if(StringUtils.isNotEmpty(catId)){
			return new ModelAndView("wwwroot/" + memberMng.getSitePath() + "/template/survey/survey_result", m);
		}else{
			
			return new ModelAndView("wwwroot/www/template/survey_pcresult", m);
		}
	}

	/**
	 * 获取结果总数
	 * 
	 * @param surveyId
	 * @return
	 */
	public List<Map<String, Object>> getMapList(String surveyId) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<SurveyOptionEntity> surveyOptionList = surveyLogService
				.findByProperty(SurveyOptionEntity.class, "surveyid",
						String.valueOf(surveyId));
		for (SurveyOptionEntity surveyOption : surveyOptionList) {
			// 加载全部选项、计算总和
			List<SurveyLogDataEntity> surveyLogDataList = surveyLogService
					.findByProperty(SurveyLogDataEntity.class, "optionid",
							surveyOption.getId());
			int num = surveyLogDataList.size();
			// 日志选项内容
			List<SurveyLogExtEntity> surveyLogExtList = surveyLogService
					.findByProperty(SurveyLogExtEntity.class, "optionid",
							surveyOption.getId());
			Map<String, Object> extmap = new HashMap<String, Object>();
			extmap.put("surveyLogExtList", surveyLogExtList);
			extmap.put("extCount", surveyLogExtList.size());
			extmap.put("ids", null);
			extmap.put("count", null);
			extmap.put("poll", null);
			mapList.add(extmap);
			// 选项内容
			List<SurveyOptionExtEntity> surveyOptionExtList = surveyLogService
					.findByProperty(SurveyOptionExtEntity.class, "optionsid",
							String.valueOf(surveyOption.getId()));
			for (SurveyOptionExtEntity surveyOptionExt : surveyOptionExtList) {
				List<SurveyLogDataEntity> s1 = surveyLogService.findByProperty(
						SurveyLogDataEntity.class, "optionextid",
						surveyOptionExt.getId());
				double count = s1.size() + .0;
				double f1 = 0;
				if (num != 0) {
					double f = (count / num);
					BigDecimal bg = new BigDecimal(f);
					f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ids", surveyOptionExt.getId());
				map.put("count", f1);
				map.put("poll", count);
				map.put("surveyLogExtList", null);
				map.put("extCount", null);
				mapList.add(map);
			}
		}

		return mapList;
	}

	/**
	 * 后台查看详细调查日志
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "queryLog")
	public ModelAndView queryLog(HttpServletRequest request) {
		String surveyLogId = request.getParameter("surveyLogId");
		String surveyId = request.getParameter("surveyId");
		// 调查
		SurveyEntity survey = surveyLogService.get(SurveyEntity.class,
				String.valueOf(surveyId));
		// 调查日志数据
		List<SurveyLogDataEntity> surveyLogDataList = surveyLogService
				.findByProperty(SurveyLogDataEntity.class, "logid",
						String.valueOf(surveyLogId));
		// 调查日志文本内容
		List<SurveyLogExtEntity> surveyLogExtList = surveyLogService
				.findByProperty(SurveyLogExtEntity.class, "logid",
						String.valueOf(surveyLogId));

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("survey", survey);
		m.put("surveyLogDataList", surveyLogDataList);
		m.put("surveyLogExtList", surveyLogExtList);
		return new ModelAndView("cms/survey/querySurveyLogList", m);
	}

	/**
	 * 判断是否登录
	 * 
	 * @param reqeust
	 * @return
	 */
	@RequestMapping(params = "isLogin")
	public ModelAndView isLogin(HttpServletRequest reqeust) {
		// 获取路径前缀
		String conpath = ContextHolderUtils.getRequest().getContextPath();
		HttpSession session = ContextHolderUtils.getSession();
		ClientManager clientMng = ClientManager.getInstance();
		Client client = clientMng.getClient("front" + session.getId());
		String flag = "";
		if (client == null) {
			client = new Client();
			flag = "0";
		} else {
			if (client.getMember() == null) {
				flag = "0";
			} else {
				flag = "1";
			}
		}

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("member", client.getMember());
		m.put("conpath", conpath);
		m.put("flag", flag);
		return new ModelAndView("member/isLogin", m);
	}

	/**
	 * 会员退出
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "exitVip")
	@ResponseBody
	public String exitVip(HttpServletRequest req) {

		// 获取路径前缀
		// String conpath = ContextHolderUtils.getRequest().getContextPath();
		JSONObject j = new JSONObject();

			try {
			HttpSession session = ContextHolderUtils.getSession();
			MemberEntity member = memberMng.getSessionMember();

				List<String> defaultList = new ArrayList<String>();
				List<ContentCatDefault> listcat = memberMng.getList(ContentCatDefault.class);
				if(listcat.size()!=0){
					for(ContentCatDefault cat :listcat){
						defaultList.add(cat.getChannelId());
					}
				}
				req.getSession().setAttribute("channel",defaultList);

			String sinatoken = member.getThirdToken(); // 新浪授权的token值
			if (StringUtils.isNotEmpty(sinatoken)) {
				Users users = new Users();
				users.setToken(sinatoken);
				com.leimingtech.core.weibo4j.org.json.JSONObject json = users
						.removeOauth(sinatoken);
				String result = json.get("result") + "";
				if ("result".equals(result)) {
					ClientManager.getInstance().removeClinet(
							"front" + session.getId());
					message = "退出成功！";
					j.accumulate("isSuccess", true);
					j.accumulate("msg", message);
				} else {
					message = "退出失败！";
					j.accumulate("isSuccess", false);
					j.accumulate("msg", message);
				}
			} else {
				ClientManager.getInstance().removeClinet(
						"front" + session.getId());
			/*	Subject subject = SecurityUtils.getSubject();
				if (subject.isAuthenticated()){
					subject.logout();
				}*/
				message = "退出成功！";
				j.accumulate("isSuccess", true);
				j.accumulate("msg", message);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String str = j.toString();
		return str;
	}
}
