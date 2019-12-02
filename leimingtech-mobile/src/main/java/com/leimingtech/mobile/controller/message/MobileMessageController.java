package com.leimingtech.mobile.controller.message;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.mobile.entity.message.MobileMessageEntity;
import com.leimingtech.mobile.entity.sysiostoken.SysIosTokenEntity;
import com.leimingtech.mobile.service.impl.message.PushMessage;
import com.leimingtech.mobile.service.message.MobileMessageServiceI;
import com.leimingtech.mobile.service.sysiostoken.SysIosTokenServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 移动通知表
 * @author
 * @date 2014-08-04 12:18:52
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/mobileMessageController")
public class MobileMessageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobileMessageController.class);

	@Autowired
	private MobileMessageServiceI mobileMessageService;
	@Autowired
	private SystemService systemService;
	//@Autowired
	@Autowired
	private SysIosTokenServiceI sysIosTokenService = null;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 移动通知表列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "mobileMessage")
	public ModelAndView mobileMessage(MobileMessageEntity mobileMessage, HttpServletRequest request) {
		// 获取移动通知表列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));

		CriteriaQuery cq = new CriteriaQuery(MobileMessageEntity.class, pageSize, pageNo, "", "");
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq, mobileMessage, param);
		// 排序条件
		cq.addOrder("createdtime",SortDirection.desc );
		cq.add();
		PageList pageList = this.mobileMessageService.getPageList(cq, true);
		List<MobileMessageEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("searchMap", param);
		m.put("pageList", resultList);
		m.put("pageNo", pageList.getCurPageNO());
		m.put("pageCount", pageCount);
		m.put("pageSize", pageSize);
		m.put("actionUrl", "mobileMessageController.do?mobileMessage");
		return new ModelAndView("mobile/message/mobileMessageList", m);
	}

	/**
	 * 移动通知表添加
	 * 
	 * @return
	 */
	@RequestMapping(params = "add")
	public ModelAndView add(HttpServletRequest reqeust) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileMessage", new MobileMessageEntity());
		return new ModelAndView("mobile/message/mobileMessage", m);
	}

	/**
	 * 移动通知表更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		MobileMessageEntity mobileMessage = mobileMessageService.getEntity(MobileMessageEntity.class, id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("mobileMessage", mobileMessage);
		return new ModelAndView("mobile/message/mobileMessage", m);
	}

	/**
	 * 移动通知表保存
	 * 
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public String saveOrUpdate(MobileMessageEntity mobileMessage, HttpServletRequest request) {
		JSONObject j = new JSONObject();
		// 接受checkbox多选文本框 :推送目标
		String[] targetExs = request.getParameterValues("targetEx");
		Integer targetEx = 0;
		// 处理checkbox多选文本框 :推送目标(1,苹果2,安卓3,苹果+安卓)
		for (int i = 0; i < targetExs.length; i++) {
			targetEx += Integer.parseInt(targetExs[i]);
		}
		mobileMessage.setTarget(targetEx);
		if (StringUtil.isNotEmpty(mobileMessage.getId())) {
			message = "移动通知【"+mobileMessage.getTitle()+"】更新成功";
			MobileMessageEntity t = mobileMessageService.get(MobileMessageEntity.class, mobileMessage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(mobileMessage, t);
				mobileMessageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "移动通知【"+mobileMessage.getTitle()+"】更新失败";
			}
		} else {
			if (mobileMessage.getTarget() == 1 || mobileMessage.getTarget() == 3) {
				List<SysIosTokenEntity> tokenList = null;
				tokenList = sysIosTokenService.findHql("from SysIosTokenEntity sit where sit.sysType = ?", "IOS");
				// 添加推送
				if (tokenList != null && tokenList.size() > 0) {
					String path = null;//"E:\\iphone\\WPNPushService.p12";
					path = request.getSession().getServletContext().getRealPath("");
					path+="\\upload\\files\\iphone\\WPNPushService.p12";
					List<String> tokenListStr = new ArrayList<String>();
					for (int i = 0; i < tokenList.size(); i++) {
						tokenListStr.add(tokenList.get(i).getToken());
					}
					//JSONObject jsonObject = new JSONObject();
					//JsonConfig jsonConfig = new JsonConfig();
					//jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
					// jsonConfig.setExcludes(new String[] { "checked",
					// "locked", "noted", "sortDateTime", "modified" });
					//jsonObject = JSONObject.fromObject(mobileMessage, jsonConfig);
					//服务器运行环境类型 0,测试环境 1,正式环境
					//String json = jsonObject.toString();
					
					boolean serviceType = "0".equals(mobileMessage.getEntironmentType()) ? false : true;

					PushMessage push = new PushMessage();
					String contentId = request.getParameter("contentId");
					String contentTitle= mobileMessage.getTitle();
					if(contentTitle!=null && contentTitle.length()>0){
						Integer end = contentTitle.length()>10?10:contentTitle.length();
						if(end>contentTitle.length()){
							contentTitle=contentTitle.substring(0, end);
							contentTitle+="...";
						}
					}else{
						contentTitle="";
					}
					String content = mobileMessage.getInformContent();
					if(content!=null && content.length()>0){
						Integer end = content.length()>20?20:content.length();
						if(content.length()>20){
							content=content.substring(0, end);
							content+="...";
						}
					}else{
						content="";
					}
					push.sendpush(tokenListStr, path, "1","{'aps':{'id':'"+contentId+"','title':'"+contentTitle+"','alert':'"+content+"','url':'"+mobileMessage.getUrl()+"','imgUrl':'"+mobileMessage.getImgUrl()+"'}}", 1, false,serviceType);
				}
			}

			message = "移动通知【"+mobileMessage.getTitle()+"】添加成功";
			mobileMessage.setCreatedtime(new Date());//创建时间
			mobileMessageService.save(mobileMessage);
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobileMessageController.do?mobileMessage");
		String str = j.toString();
		return str;
	}

	/**
	 * 移动通知表删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");

		MobileMessageEntity mobileMessage = mobileMessageService.getEntity(MobileMessageEntity.class, id);
		message = "移动通知【"+mobileMessage.getTitle()+"】删除成功";
		mobileMessageService.delete(mobileMessage);
		systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);

		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl", "mobileMessageController.do?mobileMessage");
		String str = j.toString();
		return str;
	}

	/**
	 * 获取频道信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "toSelectInfoChannels", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray toSelectInfoChannels(HttpServletRequest reqeust) {
		JSONArray jsonArray = new JSONArray(); // 定义返回的JSON
		JsonConfig jsonConfig = new JsonConfig();//
		List<MobileChannelEntity> listMobileChannels = null;
		// List<ModelsEntity> listModels = null;
		// 频道集合
		listMobileChannels = mobileMessageService.getList(MobileChannelEntity.class);
		// 模型集合
		// listModels = systemService.getList(MobileChannelEntity.class);

		// Map<String, Object> m = new HashMap<String, Object>();
		// 添加到MAP
		// if (listMobileChannels != null && listMobileChannels.size() > 0) {
		// m.put("listCat", listMobileChannels);
		// }
		// if (listModels != null && listModels.size() > 0) {
		// m.put("listClassify", listModels);
		// }
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setExcludes(new String[] { "created", "mobileChannel", "mobileChannels", "modified" });
		// 转换成JSON
		if (listMobileChannels != null && listMobileChannels.size() > 0) {
			jsonArray = JSONArray.fromObject(listMobileChannels, jsonConfig);
		}
		// return new ModelAndView("mobile/message/soso", m);
		return jsonArray;
	}

	/**
	 * 获取模型信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "toSelectInfoModel", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray toSelectInfoModel(HttpServletRequest reqeust) {
		JSONArray jsonArray = new JSONArray(); // 定义返回的JSON
		JsonConfig jsonConfig = new JsonConfig();//
		// List<MobileChannelEntity> listMobileChannels = null;
		List<ModelsEntity> listModels = null;
		// 频道集合
		// listMobileChannels =
		// systemService.getList(MobileChannelEntity.class);
		// 模型集合
		listModels = mobileMessageService.getList(MobileChannelEntity.class);

		// Map<String, Object> m = new HashMap<String, Object>();
		// 添加到MAP
		// if (listMobileChannels != null && listMobileChannels.size() > 0) {
		// m.put("listCat", listMobileChannels);
		// }
		// if (listModels != null && listModels.size() > 0) {
		// m.put("listClassify", listModels);
		// }
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setExcludes(new String[] { "created" });
		// 转换成JSON
		if (listModels != null && listModels.size() > 0) {
			jsonArray = JSONArray.fromObject(listModels, jsonConfig);
		}
		// return new ModelAndView("mobile/message/soso", m);
		return jsonArray;
	}

	/**
	 * 请求获得推送信息列表的JSON
	 * 
	 * @return
	 */
	@RequestMapping(params = "getListContentInfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getListContentInfo(HttpServletRequest reqeust) {
		List<ContentsMobileEntity> list = getListContents(reqeust);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		// jsonConfig.setIgnoreDefaultExcludes(false); //设置默认忽略
		// jsonConfig.setExcludes(new String[]{"articleList"});
		jsonConfig.setExcludes(new String[] { "checked", "locked", "noted", "sortDateTime", "modified" });
		JSONArray jsonArray = new JSONArray(); // 定义返回的JSON

		if (list != null && list.size() > 0) {
			jsonArray = JSONArray.fromObject(list, jsonConfig);
		}
		return jsonArray;
	}

	/**
	 * 初始化获得推送信息列表的Map
	 * 
	 * @return
	 */
	@RequestMapping(params = "getListSelectInfo")
	public ModelAndView getListSelectInfo(HttpServletRequest reqeust) {
		Map<String, Object> map = null;
		List<ContentsMobileEntity> list = getListContents(reqeust);

		if (list != null && list.size() > 0) {
			map = new HashMap<String, Object>();
			map.put("listInfo", list);
		}
		return new ModelAndView("mobile/message/soso", map);
	}

	private List<ContentsMobileEntity> getListContents(HttpServletRequest reqeust) {
		// JSONArray jsonArray = new JSONArray(); // 定义返回的JSON
		// JsonConfig jsonConfig = new JsonConfig();//
		List<ContentsMobileEntity> list = null; // 定义查询结果的集合
		String[] catIdsParameter = null; // 定义频道参数的集合
		String[] classifysParameter = null; // 定义模型参数的集合

		Integer[] catIdsInteger = null; // 定义Integer频道参数的集合
		Integer[] classifysInteger = null; // 定义Integer模型参数的集合

		List<Object> listParameter = new ArrayList<Object>(); // 综合集合
		Object[] objectsParameter = null; // 定义 Object数组参数的集合 由 List 综合集合 转换
		// 接收频道参数,并分割参数为数组
		String catIdsStr = reqeust.getParameter("catIds");
		if (catIdsStr != null && catIdsStr.length() > 0) {
			catIdsParameter = catIdsStr.split(",");
			if (catIdsParameter != null && catIdsParameter.length > 0) {
				catIdsInteger = new Integer[catIdsParameter.length];
				for (int i = 0; i < catIdsParameter.length; i++) {
					catIdsInteger[i] = Integer.parseInt(catIdsParameter[i]);
				}
			}
		}
		// 接收模型参数,并分割参数为数组
		String classifysStr = reqeust.getParameter("classify");
		if (classifysStr != null && classifysStr.length() > 0) {
			classifysParameter = classifysStr.split(",");
			if (catIdsParameter != null && classifysParameter.length > 0) {
				classifysInteger = new Integer[classifysParameter.length];
				for (int i = 0; i < classifysParameter.length; i++) {
					classifysInteger[i] = Integer.parseInt(classifysParameter[i]);
				}
			}
		}
		// 接收标题参数
		String title = reqeust.getParameter("title");
		if (title != null && title.length() > 0) {
			try {
				title = new String(title.getBytes("iso8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				title = "";
				e.printStackTrace();
			}
			listParameter.add("%" + title + "%");
		} else {
			listParameter.add("%%");
		}
		// 频道参数整合到List
		if (catIdsStr != null && catIdsStr.length() > 0) {
			for (int i = 0; i < catIdsInteger.length; i++) {
				listParameter.add(catIdsInteger[i]);
			}
		}
		// 模型参数整合到List
		if (classifysStr != null && classifysStr.length() > 0) {
			for (int i = 0; i < classifysInteger.length; i++) {
				listParameter.add(classifysInteger[i]);
			}
		}
		// 参数整合到Object数组
		if (listParameter.size() > 0) {
			objectsParameter = new Object[listParameter.size()];
			for (int i = 0; i < listParameter.size(); i++) {
				objectsParameter[i] = listParameter.get(i);
			}
		}
		StringBuffer buffer = new StringBuffer(); // 定义 StringBuffer 拼接参数
		// 拼接主体
		// buffer.append("select con.id,con.title,con.url,con.listThumbnail,con.contentThumbnail,con.slideThumbnail from ContentsMobileEntity con where 1 = 1 ");
		buffer.append("from ContentsMobileEntity con ");

		// 拼接标题参数
		// if (title != null && title.length() > 0) {
		buffer.append("where con.title like ?");
		// }
		// 拼接所有频道参数
		if (catIdsStr != null && catIdsStr.length() > 0) {
			if (catIdsParameter.length == 1) {
				buffer.append(" and con.catid = ?");
			} else {
				for (int i = 0; i < catIdsParameter.length; i++) {
					if (i == 0) {
						buffer.append(" and ( con.catid = ?");
					} else if (i == (catIdsParameter.length - 1)) {
						buffer.append(" or con.catid = ?)");
					} else {
						buffer.append(" or con.catid = ?");
					}
				}
			}
		}
		// 拼接所有模型参数
		if (classifysStr != null && classifysStr.length() > 0) {
			if (classifysParameter.length == 1) {
				buffer.append(" and con.classify = ?");
			} else {
				for (int i = 0; i < classifysParameter.length; i++) {
					if (i == 0) {
						buffer.append(" and ( con.classify = ?");
					} else if (i == (classifysParameter.length - 1)) {
						buffer.append(" or con.classify)");
					} else {
						buffer.append(" or con.classify = ?");
					}
				}
			}
		}

		// 查询结果List
		list = mobileMessageService.findHql(buffer.toString(), objectsParameter);
		// jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// 转换成JSON
		// if (list != null && list.size() > 0) {
		// jsonArray = JSONArray.fromObject(list, jsonConfig);
		// }
		// return jsonArray;
		return list;
	}
}
