package com.leimingtech.cms.controller.vote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.leimingtech.cms.service.vote.VoteLogServiceI;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.entity.VoteEntity;
import com.leimingtech.core.entity.VoteLogDataEntity;
import com.leimingtech.core.entity.VoteLogEntity;
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MemberServiceI;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.IpUtil;
import com.leimingtech.core.util.MapJsonUtil;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.RemoteRequest;
import com.leimingtech.core.util.Request;
import com.leimingtech.core.util.Response;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.ip.IPSeeker;

/**   
 * @Title: Controller
 * @Description: 投票日志
 * @author 
 * @date 2014-06-10 19:13:19
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/front/voteLogController")
public class VoteLogController extends ContentsbaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoteLogController.class);

	@Autowired
	private VoteLogServiceI voteLogService;
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
	 * 投票日志列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "voteLog")
	public ModelAndView voteLog(VoteLogEntity voteLog, HttpServletRequest request) {
		//栏目id，用于返回
		String contentCatId = request.getParameter("contentCatId");
		//内容Id
		String contentsId = request.getParameter("contentsId");
		List<VoteEntity> voteList = voteLogService.findByProperty(VoteEntity.class, "contentid", String.valueOf(contentsId));
		//投票id
		String voteId="0";
		if(voteList.size()!=0){
			 voteId = voteList.get(0).getId();
		}
	
		List<Map<String,Object>> mapList = this.getMapList(String.valueOf(voteId));
		List<VoteOptionEntity> voteOptionList = voteLogService.findByProperty(VoteOptionEntity.class, "voteid", String.valueOf(voteId));
		//获取投票日志列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(VoteLogEntity.class, pageSize, pageNo, "", "");
		//查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, voteLog, param);
		//排序条件
		cq.eq("voteid", voteId);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		PageList pageList = this.voteLogService.getPageList(cq, true);
		List<VoteLogEntity> resultList = pageList.getResultList();
		//获取路径前缀
		String conpath = ContextHolderUtils.getRequest().getContextPath();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
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
		m.put("voteOptionList", voteOptionList);
		m.put("actionUrl", "front/voteLogController.do?voteLog&contentCatId="+contentCatId+"&contentsId="+contentsId);
		return new ModelAndView("cms/vote/voteLogList", m);
	}

	/**
	 * 投票日志添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteLog", new VoteLogEntity());
		return new ModelAndView("cms/vote/voteLog", m);
	}
	
	/**
	 * 投票日志更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust){
		String id = reqeust.getParameter("id");
		VoteLogEntity voteLog = voteLogService.getEntity(VoteLogEntity.class, String.valueOf(id));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteLog", voteLog);
		return new ModelAndView("cms/vote/voteLog", m);
	}

	/**
	 * 投票日志保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(VoteLogEntity voteLog, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if (StringUtil.isNotEmpty(voteLog.getId())) {
			message = "投票日志【"+voteLog.getId()+"】更新成功";
			VoteLogEntity t = voteLogService.get(VoteLogEntity.class, voteLog.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(voteLog, t);
				voteLogService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "投票日志【"+voteLog.getId()+"】更新失败";
			}
		} else {
			message = "投票日志【"+voteLog.getId()+"】添加成功";
			voteLog.setCreated(new Date());//创建时间
			voteLogService.save(voteLog);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteLogController.do?voteLog");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 投票日志删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		
		VoteLogEntity voteLog = voteLogService.getEntity(VoteLogEntity.class, String.valueOf(id));
		message = "投票日志【"+voteLog.getId()+"】删除成功";
		voteLogService.delete(voteLog);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "voteLogController.do?voteLog");
		String str = j.toString();
		return str;
	}
	/**
	 * 投票保存
	 * @param request
	 */
	@RequestMapping(params = "voteLogTag")
	@ResponseBody
	public String voteLogTag(HttpServletRequest request){
		
		Boolean isSuccess=false;
		JSONObject j = new JSONObject();
		String voteId = request.getParameter("voteId");
		String cheradio = request.getParameter("cheradio");
		
        //获取登陆用户
        MemberEntity member=memberMng.getSessionMember();
        String username="";
		
		if(member==null){
//			message = "您还未登录系统，无法投票";
			username = "匿名";
		}else{
			username = member.getUsername();
		}
		
		VoteEntity vote = voteLogService.get(VoteEntity.class, String.valueOf(voteId));
		int ipnum = 0;
		String sql = "";
		
		//获取客户端IP
		String ip = IpUtil.getIpAddr(request);
		
		if(StringUtil.isNotEmpty(vote.getVoteiplimit())){
			ipnum = Integer.valueOf(vote.getVoteiplimit());
			int ipLimit = ipnum*3600;//小时限制
			sql = "select * from cms_vote_log where ip='"+ip+"' and UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(created)<'"+ipLimit+"'";
		}else{
			sql = "select * from cms_vote_log where ip='"+ip+"' and UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(created)<'"+ipnum+"'";
		}
		List<VoteLogEntity> voteLogList = voteLogService.findListbySql(sql);
		int size = voteLogList.size();
		if(size==0){
			if(StringUtils.isEmpty(cheradio)){
				message="请先选择选项～！";
			}else{
				String[] voteids=cheradio.split(",");
				Integer maxvotes = vote.getMaxvotes();
				if (maxvotes != null && voteids.length > maxvotes) {
					message = "最多可以选择 " + maxvotes + " 项";
				} else{
					try {
						Map map = clientIP(ip);
						
						VoteLogEntity voteLog = new VoteLogEntity();
						voteLog.setCreated(new Date());
						voteLog.setVoteid(String.valueOf(voteId));
						voteLog.setCreatedby(username);
						voteLog.setIp(ip);
						voteLog.setCountry((String)map.get("country"));
						voteLog.setProvince((String)map.get("region"));
						voteLog.setCity((String)map.get("city"));
						voteLog.setCounty((String)map.get("county"));
						voteLogService.save(voteLog);
						
						for(int i=0;i<voteids.length;i++){
							VoteLogDataEntity voteLogData = new VoteLogDataEntity();
							voteLogData.setLogid(voteLog.getId());
							voteLogData.setOptionid(String.valueOf(voteids[i]));
							voteLogData.setVoteid(voteId);
							voteLogData.setIp(ip);
							voteLogData.setCountry((String)map.get("country"));
							voteLogData.setProvince((String)map.get("region"));
							voteLogData.setCity((String)map.get("city"));
							voteLogData.setCounty((String)map.get("county"));
							voteLogData.setCreatedtime(new Date());//创建时间
							voteLogService.save(voteLogData);
							message = "投票成功，感谢您的参与。";
						}
						isSuccess=true;
					} catch (NumberFormatException e) {
						e.printStackTrace();
						message = "提交异常";
					}
				}
			}
		}else{
			message = "请勿在 "+ipnum+" 小时之内重复投票，详情请咨询管理员";
		}
		j.accumulate("isSuccess", isSuccess);
		j.accumulate("msg", message);
		return j.toString();
	}
	/**
	 * 根据IP地址获取国家、省市、区县
	 * @param ip
	 * @return
	 */
	private Map clientIP(String ip){
		Map map = new HashMap();
        Request remoteRequest  = new RemoteRequest();
		Response remoteResponse = remoteRequest.execute("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
		String content  = remoteResponse.getContent();
		String country = "";
		String region = "";
		String city = "";
		String county = "";
		if(remoteResponse.getStatusCode()==200 && !StringUtil.isEmpty(content)){
			Map result = MapJsonUtil.parseJSON2Map(content);
			String data = result.get("data") + "";
			result = MapJsonUtil.parseJSON2Map(data);
			country = result.get("country") + "";//国家
			region = result.get("region") + "";//省
			if(!region.equals("")){
				region = region.substring(0, 2);
			}
			city = result.get("city") + "";//市
			county = result.get("county") + "";//区县
		}else{
			IPSeeker ipseeker = IPSeeker.getInstance();
			country = ipseeker.getCountry(ip);
			System.out.println(country);
			region = country.substring(0, 2); //获取访问者所在的省
			city = country.substring(country.length() - 3, country.length() - 1); //获取访问者所在的市
		}
		map.put("country", country);
		map.put("region", region);
		map.put("city", city);
		map.put("county", county);
		return map;
	}
	/**
	 * 投票结果跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "queryVoteLog")
	public ModelAndView queryVoteLog(HttpServletRequest request){
		String contentId = request.getParameter("contentId");
		String voteId = request.getParameter("voteId");
		List<VoteOptionEntity> voteOptionList = voteLogService.findByProperty(VoteOptionEntity.class, "voteid", voteId);
		List<Map<String,Object>> mapList = this.getMapList(voteId);
		//内容
		ContentsEntity content = voteLogService.get(ContentsEntity.class, String.valueOf(contentId));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("content", content);
		m.put("sitePath", memberMng.getSitePath());
		m.put("mapList", mapList);
		m.put("voteOptionList", voteOptionList);
		return new ModelAndView("wwwroot/www/template/voteResult", m);
	}
	/**
	 * pc端投票结果跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "queryPCvoteLog")
	public ModelAndView queryPCvoteLog(HttpServletRequest request){
		String contentId = request.getParameter("contentId");
		String voteId = request.getParameter("voteId");
		List<VoteOptionEntity> voteOptionList = voteLogService.findByProperty(VoteOptionEntity.class, "voteid", voteId);
		List<Map<String,Object>> mapList = this.getMapList(voteId);
		//内容
		ContentsEntity content = voteLogService.get(ContentsEntity.class, String.valueOf(contentId));
		Map<String, Object> m = new HashMap<String, Object>();
		SiteEntity site=siteServiceI.getSite(content.getSiteid());
		m.put("site", site);
		m.put("contentF", content);
		m.put("sitePath", memberMng.getSitePath());
		m.put("mapList", mapList);
		m.put("voteOptionList", voteOptionList);
		return new ModelAndView("wwwroot/www/template/vote_pcresult", m);
	}
	/**
	 * 投票结果计算
	 * @param voteId
	 * @return
	 */
	public List<Map<String,Object>> getMapList(String voteId){
		List<VoteOptionEntity> voteOptionList = voteLogService.findByProperty(VoteOptionEntity.class, "voteid", voteId);
		//共多少选项
		List<VoteLogEntity> voteLogList = voteLogService.findByProperty(VoteLogEntity.class, "voteid", String.valueOf(voteId));
		int num = voteLogList.size();
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(VoteOptionEntity voteOption:voteOptionList){
			List<VoteLogDataEntity> voteLogDataList1 = voteLogService.findByProperty(VoteLogDataEntity.class, "optionid", voteOption.getId());
			double count = voteLogDataList1.size()+.0;
			double f1 = 0;
			if(num!=0){
				double f = (count/num);
				BigDecimal bg = new BigDecimal(f);
				f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ids", voteOption.getId());
			map.put("count", f1);
			map.put("poll", count);
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 后台查看详细投票日志
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "queryLog")
	public ModelAndView queryLog(HttpServletRequest request){
		String voteId = request.getParameter("voteId");
		String voteLogId = request.getParameter("voteLogId");
		//投票
		VoteEntity vote = voteLogService.get(VoteEntity.class, String.valueOf(voteId));
		//投票日志数据
		List<VoteLogDataEntity> voteLogDataList = voteLogService.findByProperty(VoteLogDataEntity.class, "logid", String.valueOf(voteLogId));
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("vote", vote);
		m.put("voteLogDataList", voteLogDataList);
		return new ModelAndView("cms/vote/queryVoteLogList", m);
	}
	/**
	 * 针对选项查看单个记录
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "queryAloneLog")
	public ModelAndView queryAloneLog(HttpServletRequest request){
		String voteOptionId = request.getParameter("voteOptionId");
		List<VoteLogDataEntity> voteLogDataList = voteLogService.findByProperty(VoteLogDataEntity.class, "optionid", String.valueOf(voteOptionId));
		//获取投票日志列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		CriteriaQuery cq = new CriteriaQuery(VoteLogEntity.class, pageSize, pageNo, "", "");
		if(voteLogDataList.size()!=0){
			Object[] array = new Object[voteLogDataList.size()];
			for(int i=0;i<voteLogDataList.size();i++){
				array[i] = voteLogDataList.get(i).getId();
			}
			cq.in("id", array);
			cq.add();
		}else{
			cq.eq("id", "0");
			cq.add();
		}
		PageList pageList = this.voteLogService.getPageList(cq, true);
		List<VoteLogEntity> resultList = pageList.getResultList();
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("voteLogList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "voteLogController.do?queryAloneLog&voteOptionId="+voteOptionId);
		return new ModelAndView("cms/vote/queryVoteLog", m);
	}
}
