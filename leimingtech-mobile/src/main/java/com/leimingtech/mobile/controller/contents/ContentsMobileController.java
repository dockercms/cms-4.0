package com.leimingtech.mobile.controller.contents;

import com.leimingtech.core.base.ClientManager;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.*;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.mobile.entity.pictureclassify.MobilePictureClassifyEntity;
import com.leimingtech.mobile.entity.relatecontent.RelateContentMobileEntity;
import com.leimingtech.mobile.entity.videoclassify.MobileVideoClassifyEntity;
import com.leimingtech.mobile.push.util.PushUtil;
import com.leimingtech.mobile.push.utl.enums.MobilePhoneSystemEnum;
import com.leimingtech.mobile.service.pictureclassify.MobilePictureClassifyServiceI;
import com.leimingtech.mobile.service.relatecontent.RelateContentMobileServiceI;
import com.leimingtech.mobile.service.videoclassify.MobileVideoClassifyServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 内容
 * @author zhangdaihao modify by linjm 20140402
 * @date 2014-04-21 15:05:15
 * @version V1.0
 * 
 */
@Controller
@RequestMapping("/contentsMobileController")
public class ContentsMobileController extends ContentsMobilebaseController {
	
	/**栏目管理接口*/
	@Autowired
	private ContentCatServiceI contentCatService;
	@Autowired
	private WorkFlowServiceI workFlowService;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ArticleMobileServiceI articleService;
	@Autowired
	private PictureGroupMobileServiceI pictureGroupService;
	@Autowired
	private ContentLinkMobileServiceI contentLinkService;
	@Autowired
	private ContentVideoServiceI contentVideoService;
	@Autowired
	private VoteOptionServiceI voteOptionService;
	@Autowired
	private ActivityServiceI activityService;
	@Autowired
	private VoteServiceI voteService;
	@Autowired
	private SurveyServiceI surveyService;
	@Autowired
	private SpecialServiceI specialService;
	@Autowired
	private InterviewServiceI interviewService;
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	/**模型管理接口*/
	@Autowired
	private ModelsServiceI modelsService;
	/**PC稿件管理接口*/
	@Autowired
	private ContentsServiceI contentsService;
	/**来源管理接口*/
	@Autowired
	private SourceServiceI sourceService;
	/**移动内容跟帖管理接口*/
	@Autowired
	private InvitationMobileServiceI invitationMobileService;
	/**调查日志管理接口*/
	@Autowired
	private SurveyLogServiceI surveyLogService;
	/**专题管理接口*/
	@Autowired
	private SimplespecialContentServiceI simplespecialContentService;
	/**移动稿件中相关稿件管理接口*/
	@Autowired
	private RelateContentMobileServiceI relateContentMobileService;
	/**移动视频分类管理接口*/
	@Autowired
	private MobileVideoClassifyServiceI mobileVideoClassifyService;
	/**移动图片分类管理接口*/
	@Autowired
	private MobilePictureClassifyServiceI mobilePictureClassifyService;
	/**数据字典分类管理接口*/
	@Autowired
	private TypegroupServiceI typegroupService;
	private String message;
	
	@Override
	public String getMessage() {
		return message;
	}
	
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "contentCat")
	public ModelAndView function(HttpServletRequest request) {
		List<ContentCatEntity> list = contentCatService.getRootContentCat();
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("list" , list);
		return new ModelAndView("mobile/contents/contentEditList" , m);
	}
	
	/**
	 * 加载下级
	 * 
	 * @param test
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "load")
	@ResponseBody
	public JSONArray loadMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		List<ContentCatEntity> list = contentCatService.getListByPid(id);
		JSONArray jsonArray = new JSONArray();
		for(ContentCatEntity contentCat : list) {
			JSONObject json = new JSONObject();
			if(contentCat.getContentCats() != null && contentCat.getContentCats().size() > 0) {
				json.put("text" , contentCat.getName());
				json.put("value" , contentCat.getId());
				json.put("leaf" , false);
				json.put("expanded" , false);
				json.put("cls" , "folder");
				json.put("id" , contentCat.getId());
				json.put("href" , "contentsController.do?load&id=" + contentCat.getId());
				json.put("data-role" , "branch");
				json.put("children" , new JSONArray());
			} else {
				json.put("text" , contentCat.getName());
				json.put("value" , contentCat.getId());
				json.put("leaf" , true);
				json.put("href" , "javascript:void(0);");
				json.put("data-role" , "leaf");
				json.put("id" , contentCat.getId());
				json.put("checked" , false);
			}
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 内容列表页ftl
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param pageList
	 */
	@RequestMapping(params = "table")
	public ModelAndView table(ContentsMobileEntity contentsMobile , String publishedStart, String publishedEnd, HttpServletRequest request) {
		String mobileChannelId = request.getParameter("mobileChannelId");
		// 是否显示全部分类内容(all)
		String tab = request.getParameter("tab");
		if(StringUtils.isEmpty(mobileChannelId)) {
			mobileChannelId = "-1";
		}
		
		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(mobileChannelId );
		
		// 获取内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 15 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		Map param = request.getParameterMap();
		Map<String,Object> m=contentsMobileService.getPageList(publishedEnd,publishedStart,tab,mobileChannel,contentsMobile, param, pageSize, pageNo);
		
		// 获取模型modelsList
		List<ModelsEntity> modelsList = modelsService.getAllOpenModels();
		
		m.put("searchMap" , param);
		m.put("pageNo" , pageNo);
		m.put("pageSize" , pageSize);
		m.put("modelsList" , modelsList);
		m.put("mobileChannelId" , mobileChannelId);
		m.put("tab" , tab);
		m.put("actionUrl" , "contentsMobileController.do?table&tab=all&mobileChannelId=" + mobileChannelId);
		HttpSession session = ContextHolderUtils.getSession();
		SiteEntity site = ClientManager.getInstance().getClient(session.getId()).getSite();
		m.put("domain" ,site!=null?site.getDomain():null);
		m.put("conpath" , Globals.CONTEXTPATH);
		m.put("mobileChannel" , mobileChannel);
		m.put("publishedStart", publishedStart);
		m.put("publishedEnd", publishedEnd);
		return new ModelAndView("mobile/contents/contentsList" , m);
	}
	
	/**
	 * 移动内容更新
	 * 
	 * @return
	 */
	@RequestMapping(params = "updatePageModel")
	public ModelAndView updatePageModel(HttpServletRequest reqeust) {
		String id = reqeust.getParameter("id");
		ContentsMobileEntity contentsMobile = contentsMobileService.getEntity(id );
		// 获取频道
		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(contentsMobile.getCatid());
		//获取关联的PC内容
		ContentsEntity contents = new ContentsEntity();
		if(null!=contentsMobile.getRelevanceid()){
			contents = contentsService.getEntity(ContentsEntity.class,contentsMobile.getRelevanceid());
		}
		// 获取当前毫秒数
		String temporary = String.valueOf(System.currentTimeMillis());
		
		List<SourceEntity> sourceEntityList = sourceService.loadAll(SourceEntity.class); // 获取来源内容
		// 来源
		String sourceStr = "";
		for(int i = 0 ; i < sourceEntityList.size() ; i++) {
			sourceStr += sourceEntityList.get(i).getName() + ",";
		}
		// 从数据字典中获取内容标记
		TSTypegroup typeGroup = typegroupService.findByTypegroupCode("content_mark");
		List<TSType> typeList = typeGroup.getTSTypes();
		// 区分添加/编辑页面
		String flag = "edit";
		Map<String , Object> m = new HashMap<String , Object>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(sourceStr)) {
			m.put("sourceStr" , sourceStr.substring(0 , sourceStr.length() - 1));
		} else {
			m.put("sourceStr" , CmsConstants.DEFAULT_SOURCE);
		}
		m.put("contentsMobile" , contentsMobile);
		m.put("mobileChannel" , mobileChannel);
		m.put("flag" , flag);
		m.put("typeList" , typeList);
		m.put("markpeople" , markpeople());// 当前人
		m.put("memberinfo", PlatFormUtil.getSessionUser());
		// 发布文章分类(1文章2组图3链接4视频5活动6投票7访谈8调查9专题)
		m.put("sourceEntityLists" , sourceEntityList); // 来源内容
		m.put("temporary" , temporary); // 随机数
		m.put("sessionId" , reqeust.getSession().getId());
		HttpSession session = ContextHolderUtils.getSession();
		SiteEntity site = ClientManager.getInstance().getClient(session.getId()).getSite();
		m.put("domain" , site.getDomain());
		m.put("contents", contents);
		if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_ARTICLE)) {
			List<ArticleMobileEntity> articleList = articleService.findByProperty(ArticleMobileEntity.class , "contentid" , contentsMobile.getId());
			if(articleList.size()>0){
				
				m.put("page" , articleList.get(0));
			}
			m.put("classify" , ContentMobileClassify.CONTENT_ARTICLE);
			return new ModelAndView("mobile/article/article" , m);
			// 组图分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_PICTUREGROUP)) {
			PictureGroupMobileEntity picture=pictureGroupService.findUniqueByProperty(PictureGroupMobileEntity.class, "contentid", contentsMobile.getId());
			// 全部图片分类
			List<MobilePictureClassifyEntity> pictureClassifyList = mobilePictureClassifyService.loadAll(MobilePictureClassifyEntity.class);
			m.put("pictureClassifyList" , pictureClassifyList);
			m.put("page" , picture==null?new PictureGroupMobileEntity():picture);
			m.put("classify" , ContentMobileClassify.CONTENT_PICTUREGROUP);
			return new ModelAndView("mobile/picturegroup/pictureGroup" , m);
			// 链接分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_LINK)) {
			List<ContentLinkMobileEntity> contentLinkList = contentLinkService.findByProperty(ContentLinkMobileEntity.class , "contentid" , contentsMobile.getId());
			m.put("page" , contentLinkList.get(0));
			m.put("classify" , ContentMobileClassify.CONTENT_LINK);
			return new ModelAndView("mobile/link/contentLink" , m);
			// 视频分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_VIDEO)) {
			List<ContentVideoMobileEntity> contentVideoList = contentVideoService.findByProperty(ContentVideoMobileEntity.class , "contentid" , contentsMobile.getId());
			// 全部视频分类
			List<MobileVideoClassifyEntity> videoClassifyList = mobileVideoClassifyService.loadAll(MobileVideoClassifyEntity.class);
			m.put("videoClassifyList" , videoClassifyList);
			m.put("page" , contentVideoList.get(0));
			m.put("classify" , ContentMobileClassify.CONTENT_VIDEO);
			return new ModelAndView("mobile/video/contentVideo" , m);
			// 活动分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_ACTIVITY)) {
			List<ActivityEntity> activityList = activityService.findByProperty(ActivityEntity.class , "contentid" , contentsMobile.getId());
			for(ActivityEntity activity : activityList) {
				m.put("page" , activity);
			}
			return new ModelAndView("mobile/activity/activity" , m);
			// 投票分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_VOTE)) {
			List<VoteMobileEntity> voteList = voteService.findByProperty(VoteMobileEntity.class , "contentid" , contentsMobile.getId());
			m.put("page" , voteList.get(0));
			m.put("classify" , ContentMobileClassify.CONTENT_VOTE);
			return new ModelAndView("mobile/vote/vote" , m);
			// 访谈分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_INTERVIEW)) {
			List<InterviewEntity> interviewList = interviewService.findByProperty(InterviewEntity.class , "contentid" , contentsMobile.getId());
			for(InterviewEntity interview : interviewList) {
				m.put("page" , interview);
			}
			return new ModelAndView("mobile/interview/interview" , m);
			// 调查分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_SURVEY)) {
			List<SurveyMobileEntity> surveyList = surveyService.findByProperty(SurveyMobileEntity.class , "contentid" , contentsMobile.getId());
			List<SurveyLogEntity> surveyLogList = new ArrayList<SurveyLogEntity>();
			if(surveyList.size() != 0) {
				surveyLogList = surveyLogService.findByProperty(SurveyLogEntity.class , "surveyid" , surveyList.get(0).getSurveyid());
			}
			m.put("size" , surveyLogList.size());
			m.put("page" , surveyList.get(0));
			m.put("classify" , ContentMobileClassify.CONTENT_SURVEY);
			return new ModelAndView("mobile/survey/survey" , m);
			// 专题分类
		} else if(contentsMobile.getClassify().equals(ContentMobileClassify.CONTENT_SPECIAL)) {
			SpecialEntity special = specialService.findByContentId(contentsMobile.getId());
			m.put("page" , special);
			return new ModelAndView("mobile/special/special" , m);
			// 其他类别
		} else {
			return new ModelAndView("mobile/contents/contents" , m);
		}
	}
	
	/**
	 * 移动内容删除
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public String del(HttpServletRequest request) {
		JSONObject j = new JSONObject();
		String id = request.getParameter("id");
		String mobileChannelId = request.getParameter("mobileChannelId");
		// 判断是否为全部显示
		String tab = request.getParameter("tab");
		String [] ids = id.split(",");
		
		try {
			contentsMobileService.delContent(ids);
			message = "内容删除成功";
		
		} catch (Exception e) {
			message = "内容删除失败";
		}
		j.accumulate("isSuccess" , true);
		j.accumulate("msg" , message);
		j.accumulate("toUrl" , "contentsMobileController.do?table&tab="+tab+"&mobileChannelId=" + mobileChannelId);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 左边树展示
	 * 
	 * @param requset
	 * @return
	 */
	@RequestMapping(params = "contentsTree")
	public ModelAndView contentsTree(HttpServletRequest requset) {
		String istree = requset.getParameter("istree");
		List<MobileChannelEntity> list = mobileChannelService.getRootMobileChannel();
		mobileChannelService.sortChildCatalog(list);
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("list" , list);
		return new ModelAndView("mobile/contents/leftcontentEditTree" , m);
	}
	
	/**
	 * 获取选中的相关内容
	 * 
	 * @return
	 */
	@RequestMapping(params = "correlation")
	@ResponseBody
	public String correlation(VoteOptionEntity voteOption , HttpServletRequest request) {
		String checked = request.getParameter("checked");
		// 获取当前毫秒数
		String temporary = request.getParameter("temporary");
		String contentId = request.getParameter("contentId");
		
		JSONObject j = new JSONObject();
		j.accumulate("isSuccess" , true);
		j.accumulate("checked" , checked);
		j.accumulate("temporary" , temporary);
		j.accumulate("contentId" , contentId);
		j.accumulate("toUrl" , "contentsMobileController.do?showDiv");
		String str = j.toString();
		return str;
	}
	
	/**
	 * 相关div显示
	 * 
	 * @return
	 */
	@RequestMapping(params = "showDiv")
	public ModelAndView showDiv(HttpServletRequest request) {
		// 获取当前毫秒数
		String temporary = request.getParameter("temporary");
		String contentId = request.getParameter("contentId");
		
		List<RelateContentMobileEntity> relateContentList = new ArrayList<RelateContentMobileEntity>();
		List<RelateContentMobileEntity> relateContentListT = new ArrayList<RelateContentMobileEntity>();
		String checked = request.getParameter("checked");
		// 有选中项时
		if(!checked.equals("")) {
			String [] checkArray = checked.split(",");
			for(int i = 0 ; i < checkArray.length ; i++) {
				if(StringUtils.isNotEmpty(checkArray[i])) {
					ContentsMobileEntity content = contentsMobileService.getEntity(checkArray[i] );
					// 所有选中的保存于相关内容表中
					RelateContentMobileEntity relateContent = new RelateContentMobileEntity();
					relateContent.setContentId(temporary);
					relateContent.setRelateTitle(content.getTitle());
					relateContent.setRelateUrl(content.getUrl());
					relateContent.setImages(content.getThumb());
					relateContent.setPart(checkArray[i]);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
					try {
						relateContent.setCreated(df.parse(df.format(new Date())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					relateContentMobileService.save(relateContent);
				}
			}
		}
		if(!temporary.equals("")) {
			relateContentListT = relateContentMobileService.getListByContentID(temporary);
			for(RelateContentMobileEntity relateContent : relateContentListT) {
				relateContentList.add(relateContent);
			}
		}
		if(!contentId.equals("-1") && !contentId.equals("")) {
			relateContentListT = relateContentMobileService.getListByContentID(contentId);
			for(RelateContentMobileEntity relateContent : relateContentListT) {
				relateContentList.add(relateContent);
			}
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("relateContentList" , relateContentList);
		m.put("temporary" , temporary);
		m.put("contentId" , contentId);
		return new ModelAndView("mobile/article/dialog_attacharticleDiv" , m);
	}
	
	/**
	 * 相关div显示(修改)
	 * 
	 * @return
	 */
	@RequestMapping(params = "showEditDiv")
	public ModelAndView showEditDiv(HttpServletRequest request) {
		// 获取当前毫秒数
		String temporary = request.getParameter("temporary");
		String contentId = request.getParameter("contentId");
		List<RelateContentMobileEntity> relateContentList = new ArrayList<RelateContentMobileEntity>();
		if(!contentId.equals("") && !contentId.equals("-1")) {
			relateContentList = relateContentMobileService.getListByContentID(contentId);
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("relateContentList" , relateContentList);
		m.put("temporary" , temporary);
		m.put("contentId" , contentId);
		return new ModelAndView("mobile/article/dialog_attacharticleDiv" , m);
	}
	
	/**
	 * 相关搜索弹出框
	 * 
	 * @return
	 * @author zhangxiaoqiang
	 */
	@RequestMapping(params = "correlationDialog")
	public ModelAndView correlationDialog(ContentsMobileEntity contentsMobile , String title , String classify1 , HttpServletRequest request) {
		String content_id = request.getParameter("content_id");
		// 获取当前毫秒数
		String temporary = request.getParameter("temporary");
		// 相关搜索关键字
		String seek = request.getParameter("seek");
		String str = "";
		if(StringUtils.isNotEmpty(seek)) {
			try {
				str = new String(seek.getBytes("iso-8859-1") , "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		// 获取内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		// 查询条件组装器
		Map param = request.getParameterMap();
		
		
		Map<String,Object> m = contentsMobileService.getPageListByClassify(classify1,contentsMobile, param, pageSize, pageNo);
		
		// 获取自定义字段/值
		String sql = "select con.id,con.title,con.published,mobile.name,con.url,con.created,con.thumb  from cm_content  con,cm_mobile_channel mobile where con.catid=mobile.id ";
		List<RelateContentMobileEntity> relateList = new ArrayList<RelateContentMobileEntity>();
		List<RelateContentMobileEntity> relateListT = new ArrayList<RelateContentMobileEntity>();
		if(!content_id.equals("-1") && !content_id.equals("")) {
			relateListT = relateContentMobileService.getListByContentID(content_id);// 获取内容相关表数据
			for(RelateContentMobileEntity relateContent : relateListT) {
				relateList.add(relateContent);
			}
		}
		if(!temporary.equals("")) {
			relateListT = relateContentMobileService.getListByContentID(temporary);// 获取内容相关表数据
			for(RelateContentMobileEntity relateContent : relateListT) {
				relateList.add(relateContent);
			}
		}
		for(RelateContentMobileEntity relateContent : relateList) {
			String part = relateContent.getPart();// 区分内容id
			if(StringUtils.isNotEmpty(part)) {
				sql += " and con.id <>" + part;
			}
		}
		List<Map<String , Object>> mapList = new ArrayList<Map<String , Object>>();
		mapList = contentsService.findForJdbc(sql);
		m.put("pageNo" , pageNo);
		m.put("pageSize" , pageSize);
		m.put("modelsList" , mapList);
		m.put("temporary" , temporary);
		m.put("contentId" , content_id);
		m.put("sql" , sql);
		m.put("seek" , str);
		m.put("classify1" , classify1);
		m.put("title" , title);
		m.put("actionUrl" , "contentsMobileController.do?correlationDialog&temporary=" + temporary + "&content_id=" + content_id + "&seek=" + seek);
		return new ModelAndView("mobile/article/attachArticleList" , m);
	}
	
	/**
	 * 相关条件查询
	 * 
	 * @param request
	 * @return
	 * @author zhangxiaoqiang
	 */
	@ResponseBody
	@RequestMapping(params = "showContents")
	public String showContents(HttpServletRequest request) {
		String cm_title_s = request.getParameter("cm_title_s"); // 标题
		String cm_classify_s = request.getParameter("cm_classify_s"); // 内容发布分类(内容发布下面的)
		String sql1 = request.getParameter("sql");
		// String sql =
		// "select con.id,con.title,con.published,cat.name from cms_content  con,cms_content_cat cat where con.catid=cat.id ";
		List<Map<String , Object>> mapList = new ArrayList<Map<String , Object>>();
		Map<String , Object> m = new HashMap<String , Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(sql1);
		if(cm_title_s.equals("") && cm_classify_s.equals("0")) { // 如果都为空，则查找全部数据
			mapList = contentsService.findForJdbc(sql.toString());
		} else if(cm_title_s.equals("") && !cm_classify_s.equals("0")) { // 判断标题是否为空
			sql.append(" and con.classify='" + cm_classify_s + "'");
			mapList = contentsService.findForJdbc(sql.toString());
			
		} else if(!cm_title_s.equals("") && cm_classify_s.equals("0")) { // 判断（内容发布分类是否没空）
			sql.append(" and title like'%" + cm_title_s + "%'");
			mapList = contentsService.findForJdbc(sql.toString());
			
		} else if(!cm_title_s.equals("") && !cm_classify_s.equals("0")) { // 都不为空
			sql.append(" and con.title like '%" + cm_title_s + "%' and con.classify='" + cm_classify_s + "'");
			mapList = contentsService.findForJdbc(sql.toString());
		}
		m.put("models" , mapList);
		JSONObject j = new JSONObject();
		j.accumulateAll(m);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 选取内容页面加载频道树
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "loadmobile")
	@ResponseBody
	public JSONArray loadmobile(HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		// 频道tree
		List<ContentCatEntity> contentCatList = contentCatService.getAllIndexContentCat();
		for(ContentCatEntity contentCat : contentCatList) {
			JSONObject json = new JSONObject();
			// 父节点
			if(contentCat.getLevels() == 0) {
				json.put("id" , contentCat.getId());
				json.put("name" , contentCat.getName());
				json.put("pathids" , contentCat.getPathids());
				json.put("open" , false);
				if(contentCat.getContentCats().size() != 0) {
					json.put("nocheck" , true);
				}
				json.put("children" , getChildren(contentCat));
				jsonArray.add(json);
			}
		}
		return jsonArray;
	}
	
	/**
	 * 子节点
	 * 
	 * @param mobileChannel
	 * @return
	 */
	public JSONArray getChildren(ContentCatEntity contentCat) {
		JSONArray jsonArray = new JSONArray();
		if(contentCat.getContentCats() == null || contentCat.getContentCats().size() == 0) {
			return jsonArray;
		}
		
		contentCatService.getListByPid(contentCat.getId());
		
		for(ContentCatEntity contentCat1 : contentCat.getContentCats()) {
			JSONObject json = new JSONObject();
			json.put("id" , contentCat1.getId());
			json.put("name" , contentCat1.getName());
			json.put("open" , false);
			if(contentCat1.getContentCats().size() != 0) {
				json.put("nocheck" , true);
			}
			json.put("children" , getChildren(contentCat1));
			jsonArray.add(json);
		}
		return jsonArray;
	}
	
	/**
	 * 选项内容搜索结果,视频专辑有用到，修改请注意
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "showContent")
	public String showContent(HttpServletRequest request) {
		String title = request.getParameter("title");
		String mobileId = request.getParameter("mobileId");
		String classify = request.getParameter("classify");
		String sql1 = "select id,title,url,published from cms_content where classify='" + classify + "'";
		List<Map<String , Object>> mapList = new ArrayList<Map<String , Object>>();
		Map<String , Object> m = new HashMap<String , Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(sql1);
		if(title.equals("") && mobileId.equals("")) {
			mapList = contentsService.findForJdbc(sql.toString());
		}
		if(!title.equals("") && mobileId.equals("")) {
			sql.append(" and title like'%" + title + "%'");
			mapList = contentsService.findForJdbc(sql.toString());
		}
		if(title.equals("") && !mobileId.equals("")) {
			sql.append(" and catid in (" + mobileId + ")");
			mapList = contentsService.findForJdbc(sql.toString());
		}
		if(!title.equals("") && !mobileId.equals("")) {
			sql.append(" and title like'%" + title + "%' and catid in (" + mobileId + ")");
			mapList = contentsService.findForJdbc(sql.toString());
		}
		m.put("contentsList" , mapList);
		JSONObject j = new JSONObject();
		j.accumulateAll(m);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 选取内容
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseContent")
	public ModelAndView chooseContent(ContentsEntity contents , HttpServletRequest request) {
		String classify1 = request.getParameter("classify");
		String mobileId = request.getParameter("mobileId");
		String cm_title = request.getParameter("cm_title");
		
		String simplespecial = request.getParameter("simplespecial");
		String contentId = "";
		if(StringUtils.isNotEmpty(simplespecial)){
			List<SimplespecialContentEntity> simplespecialcontentList = simplespecialContentService.findByProperty(SimplespecialContentEntity.class, "simplespecialid",  simplespecial );
			for(int i=0;i<simplespecialcontentList.size();i++){
				contentId += simplespecialcontentList.get(i).getContentid()+",";
			}
		}
		String[] contentsId = contentId.split(",");
		String[] intconid = new String[contentsId.length];
		for(int i=0;i<contentsId.length;i++){
			if(StringUtils.isNotEmpty(contentsId[i])){
				intconid[i] = String.valueOf(contentsId[i]);
			}
		}
		// 获取内容列表
		int pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		int pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class , pageSize , pageNo , "" , "");
		String str = "";
		if(StringUtils.isNotEmpty(cm_title)){
			try {
				str = new String(cm_title.getBytes("iso-8859-1"),"utf-8");
				cq.like("title", "%"+str+"%");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotEmpty(classify1)) {
			cq.eq("classify" , classify1);
		}
		if(StringUtils.isNotEmpty(mobileId)) {
			String [] catids = mobileId.split(",");
			String[] intcatid = new String[catids.length];
			for(int i=0;i<catids.length;i++){
				if(StringUtils.isNotEmpty(catids[i])){
					intcatid[i] = String.valueOf(catids[i]);
				}
			}
			cq.in("catid",intcatid);
		}
		if(StringUtils.isNotEmpty(contentId)){
			cq.in("id", intconid);
		}
		if(StringUtils.isNotEmpty(simplespecial)&& StringUtils.isEmpty(contentId)){
			cq.eq("id", "0");
		}
		//判断选取内容为已发的内容
		cq.eq("constants", ContentStatus.CONTENT_PUBLISHED);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		// 查询条件组装器
		Map param = request.getParameterMap();
		HqlGenerateUtil.installHql(cq , contents , param);
		// 排序条件
		PageList pageList = contentsService.getPageList(cq , true);
		List<ContentsEntity> testList = pageList.getResultList();
		
		for(ContentsEntity content:testList){
			ContentCatEntity contentCat = contentCatService.getEntity(content.getCatid());
			if(contentCat!=null){
				content.setCatName(contentCat.getName());
			}
		}
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0) {
			pageCount = 1;
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("pageList" , testList);
		m.put("pageNo" , pageList.getCurPageNO());
		m.put("pageSize" , pageSize);
		m.put("pageCount" , pageCount);
		m.put("classify" , classify1);
		m.put("simplespecial", simplespecial);
		m.put("cm_title", str);
		m.put("actionUrl" , "contentsMobileController.do?chooseContent&classify=" + classify1);
		m.put("domain", PlatFormUtil.getCurrentSitedomain());
		return new ModelAndView("mobile/article/chooseContentMobile" , m);
	}
	
	/**
	 * 已选择的内容
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "checkContent")
	public String checkContent(HttpServletRequest request) {
		String contetsId = request.getParameter("chestr");
		String classify = request.getParameter("classify");
		ContentsEntity content = new ContentsEntity();
		if(StringUtils.isNotEmpty(contetsId)) {
			content = contentsService.getEntity(ContentsEntity.class ,  contetsId );
		}
		JSONObject j = new JSONObject();
		if(null != content) {
			
			if(classify.equals(ContentMobileClassify.CONTENT_ARTICLE)) {
				// 文章
				List<ArticleEntity> articleList = articleService.findByProperty(ArticleEntity.class , "contentid" , content.getId());
				ArticleEntity article = new ArticleEntity();
				if(articleList.size() != 0) {
					article = articleList.get(0);
				}
				j.accumulate("article" , article);
			}
			if(classify.equals(ContentMobileClassify.CONTENT_PICTUREGROUP)) {
				// 组图
				List<PictureGroupEntity> pictureGroupList = pictureGroupService.findByProperty(PictureGroupEntity.class , "contentid" , content.getId());
				PictureGroupEntity pictureGroup = new PictureGroupEntity();
				if(pictureGroupList.size() != 0) {
					pictureGroup = pictureGroupList.get(0);
				}
				j.accumulate("pictureGroup" , pictureGroup);
			}
			if(classify.equals(ContentMobileClassify.CONTENT_LINK)){
				//链接
				List<ContentLinkEntity> linkList = contentLinkService.findByProperty(ContentLinkEntity.class, "contentid" , content.getId());
				ContentLinkEntity link = new ContentLinkEntity();
				if(linkList.size()!=0){
					link = linkList.get(0);
				}
				j.accumulate("link", link);
			}
			if(classify.equals(ContentMobileClassify.CONTENT_VIDEO)) {
				// 视频
				List<ContentVideoEntity> videoList = contentVideoService.findByProperty(ContentVideoEntity.class , "contentid" , content.getId());
				ContentVideoEntity video = new ContentVideoEntity();
				if(videoList.size() != 0) {
					video = videoList.get(0);
				}
				j.accumulate("video" , video);
			}
			if(classify.equals(ContentMobileClassify.CONTENT_VOTE)) {
				// 投票
				List<VoteEntity> voteList = voteService.findByProperty(VoteEntity.class , "contentid" , content.getId());
				VoteEntity vote = new VoteEntity();
				if(voteList.size() != 0) {
					vote = voteList.get(0);
				}
				j.accumulate("vote" , vote);
			}
			if(classify.equals(ContentMobileClassify.CONTENT_SURVEY)) {
				// 调查
				SurveyEntity survey = new SurveyEntity();
				List<SurveyLogEntity> surveyLogList = new ArrayList<SurveyLogEntity>();
				List<SurveyEntity> surveyList = surveyService.findByProperty(SurveyEntity.class , "contentid" , content.getId());
				if(surveyList.size() != 0) {
					survey = surveyList.get(0);
					surveyLogList = surveyLogService.findByProperty(SurveyLogEntity.class , "surveyid" , survey.getId());
				}
				j.accumulate("survey" , survey);
				j.accumulate("surveyOptionSize" , surveyLogList.size());
			}
		}
		HttpSession session = ContextHolderUtils.getSession();
		SiteEntity site = ClientManager.getInstance().getClient(session.getId()).getSite();
		j.accumulate("domain" , site.getDomain());
		j.accumulate("isSuccess" , true);
		j.accumulate("content" , content);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 保存转发的移动内容
	 * 
	 * @param contentId
	 * @param mobileChannelId
	 * @param classify
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "savePushMobile")
	public String savePushMobile(String contentId , String mobileChannelId , String classify,String specialids , HttpServletRequest request) {
		JSONObject j = new JSONObject();
		if(StringUtils.isNotEmpty(mobileChannelId)){
			
			JSONObject state=contentsMobileService.savePushMobile(contentId , mobileChannelId , classify);
			if(!state.getBoolean("isSuccess")){
				return state.toString();
			}
		}
		if(StringUtils.isNotEmpty(specialids)){
			contentsMobileService.saveOrDel(contentId,specialids);
		}
		
		j.accumulate("msg" , "操作成功");
		j.accumulate("isSuccess" , true);
		String str = j.toString();
		return str;
	}
	
	/**
	 * 设置消息推送参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "setPushParam")
	public ModelAndView setPushParam(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/cms/contents/setPushParam");
		String id = request.getParameter("id");
		String int_id = StringUtils.isEmpty(StringUtils.trim(id)) ? 0+"" : id ;
		ContentsMobileEntity ce = contentsMobileService.getEntity(int_id);
		String path = "";
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		String url = basePath + ce.getUrl();
		mav.addObject("appKey" , PushUtil.getAndroidAppkey());
		mav.addObject("url" , url);
		mav.addObject("appMasterSecret" , PushUtil.getAndroidAppMasterSecret());
		mav.addObject("cme" , ce);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(params = "generateFilter")
	public JSONObject generateFilter(HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		String filterStr = "";
		@SuppressWarnings("unchecked")
		Map<String , Object> map = request.getParameterMap();
		Iterator<String> iter = map.keySet().iterator();
		String allParamsStr = "";
		while (iter.hasNext()) {
			String key = iter.next();
			Object value = request.getParameter(key);
			if(!StringUtils.isEmpty(value.toString())) {
				if(key.equals("contains") || key.equals("notContains")) {
					if(key.equals("contains")) {
						allParamsStr += "#contains=" + value;
					} else {
						allParamsStr += "#notContains=" + value;
					}
				} else {
					allParamsStr += key + ":" + value + ";";
				}
			}
		}
		 filterStr=generateJsonFilterStr(allParamsStr);
		 JSONObject json=new JSONObject();
		 json.accumulate("result" ,filterStr);
		 System.out.println(filterStr);
		 request.getSession().setAttribute("lastGenerateJsonfilterStr" , json.getJSONObject("result"));
		return json;
	}
	
	/**
	 * 向客户端推送消息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(params = "sendPush")
	public String sendPush(HttpServletRequest request) {
		
		String contentid = request.getParameter("cmeId");
		String contentJson="";
		if (StringUtils.isNotEmpty(contentid) && StringUtils.isNumeric(contentid)) {
			ContentsMobileEntity content = contentsMobileService.getEntity(contentid );
			List<InvitationMobileEntity> invitationMobileList = invitationMobileService.findByProperty(InvitationMobileEntity.class,
					"contentsId", content.getId());
			if (content != null) {
				JSONObject j = new JSONObject();//友盟限制自定义字段最多400字符
				j.put("id", content.getId());
				j.put("invitationCount", invitationMobileList.size());
//				j.put("title", content.getTitle());
				j.put("listUrl", content.getUrl());
//				j.put("listUrl", "/mobile/mobile_447.shtml");
//				j.put("content", content.getDigest());
				contentJson = j.toString();
//				contentJson = "{'id':'"+content.getId()+"','invitationCount':'"+invitationMobileList.size()+"','listUrl':'/mobile/mobile_447.shtml'}";
			}
		}
		
		String result;
		try {
			result = "0";
			String pushToAndroid = request.getParameter("pushToAndroid");
			String pushToIOS = request.getParameter("pushToIOS");
			Map<String , Object> params = new HashMap<String , Object>();
			Map<String , Object> iosExtraMap = new HashMap<String , Object>();
			List<String> notParamKey = new ArrayList<String>();
			notParamKey.add("sendPush");
			notParamKey.add("push_type");
			notParamKey.add("pushToAndroid");
			notParamKey.add("pushToIOS");

			notParamKey.add("cmeId");//文章Id
			notParamKey.add("cmeUrl");
			
			notParamKey.add("launch_in");
			notParamKey.add("not_launch_in");
			notParamKey.add("contains");
			notParamKey.add("notcontains");
			notParamKey.add("operation");
			
			Map<String , Object> extraMap = new HashMap<String , Object>();
			
			@SuppressWarnings("rawtypes")
			Enumeration paramNames = request.getParameterNames();
			while (paramNames.hasMoreElements()) {
				Object key = paramNames.nextElement();
				if(!notParamKey.contains(key)) {
					if(!key.equals("extra")) {
						String value = request.getParameter(key.toString());
						if(!StringUtils.isEmpty(value)) {
							if(key.equals("filter")){
								Object obj=request.getSession().getAttribute("lastGenerateJsonfilterStr");
								params.put(key.toString() , obj);
							}else{
								params.put(key.toString() , value);
							}
						}
					} else {
						// extra={"key1":"12121","key2":"234234234","key3":"sfasdf234234"}
						String extra = request.getParameter(key.toString());
						extra = extra.replace("{" , "").replace("}" , "");
						String keyValue[] = extra.split(",");
						String extraKey = "";
						String extraValue = "";
						for(String kv : keyValue) {
							extraKey = kv.split(":")[0];
							extraValue = kv.split(":")[1];
							extraMap.put(extraKey , extraValue);
							iosExtraMap.put(extraKey , extraValue);
						}
					}
				}
			}
			extraMap.put("newsBean", contentJson);
			params.put("extra", extraMap);
			
			params.put("ticker" , params.get("title"));
			boolean testModel = true;// 是否是测试模式
			if(!StringUtils.isEmpty(pushToAndroid) && !StringUtils.isEmpty(pushToIOS)) {
				boolean sendToAndroid = true , sendToIOS = true;
				String realUrl = request.getParameter("url");
				if(StringUtils.isEmpty(realUrl)) {
					realUrl = request.getParameter("cmeUrl");
				}
				iosExtraMap.put("id" , request.getParameter("cmeId"));
				iosExtraMap.put("url" , realUrl);
				iosExtraMap.put("title" , params.get("title"));
				iosExtraMap.put("newsBean" , contentJson);
				if(Boolean.parseBoolean(pushToAndroid) && Boolean.parseBoolean(pushToIOS)) {// Android和IOS客户端均需要推送
					sendToAndroid = PushUtil.instance(MobilePhoneSystemEnum.ANDROID).generalAndroidSendPush(params , MobilePhoneSystemEnum.ANDROID , testModel);
					sendToIOS = sendIOSPush(params , iosExtraMap , testModel);
					result = sendToAndroid && sendToIOS ? "1" : "0";
				} else if(!Boolean.parseBoolean(pushToIOS) && Boolean.parseBoolean(pushToAndroid)) {// 只推送至Android客户端
					sendToAndroid = PushUtil.instance(MobilePhoneSystemEnum.ANDROID).generalAndroidSendPush(params , MobilePhoneSystemEnum.ANDROID , testModel);
					result = sendToAndroid ? "1" : "0";
				} else if(!Boolean.parseBoolean(pushToAndroid) && Boolean.parseBoolean(pushToIOS)) {// 只推送至IOS客户端
					sendToIOS = sendIOSPush(params , iosExtraMap , testModel);
					result = sendToIOS ? "1" : "0";
				}
			}
		} catch (Exception e) {
			result="0";
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 向IOSAPP端发送推送
	 * 
	 * @param params
	 * @param other
	 * @param production_mode
	 * @return
	 * @throws Exception
	 */
	public boolean sendIOSPush(Map<String , Object> params , Map<String , Object> other , boolean production_mode) throws Exception {
		boolean result = false;
		Object type_obj = params.get("type");
		if(type_obj == null) {
			return false;
		}
		String type = type_obj.toString();
		String alert = params.get("ticker").toString();
		if(type.equals("broadcast")) {
			result = PushUtil.instance(MobilePhoneSystemEnum.IOS).sendIOSBroadcast(alert , production_mode , other);
		} else if(type.equals("groupcast")) {
			Object filterJsn = params.get("filter");
			result = PushUtil.instance(MobilePhoneSystemEnum.IOS).sendIOSGroupcast(alert , production_mode , filterJsn , other);
		} else if(type.equals("unicast")) {
			String device_token = params.get("device_tokens").toString();
			result = PushUtil.instance(MobilePhoneSystemEnum.IOS).sendIOSUnicast(alert , production_mode , device_token , other);
		} else if(type.equals("customizedcast")) {
			String alias_type = "";
			String alias = "";
			result = PushUtil.instance(MobilePhoneSystemEnum.IOS).sendIOSCustomizedcast(alert , production_mode , other , alias_type , alias);
		}
		return result;
	}
	
	/**
	 * 生成过滤字Json字符串
	 * @param values
	 * @return
	 */
	private String generateJsonFilterStr(String values) {
		StringBuffer filterStr = new StringBuffer("{" + "'where':{" + "'and':[");
		boolean isNotContains = false;
		boolean isFirst = true;
		for(String str : values.split("#")) {
			String multiFilter[] = null;
			if(str.indexOf("=") < 0) {
				multiFilter = str.split(";");
			} else {
				multiFilter = str.split("=")[1].split(";");
				isNotContains = str.split("=")[0].equals("notContains");
			}
			for(String filter : multiFilter) {
				if(filter.indexOf(":") < 0) {
					continue;
				}
				String key1 = filter.split(":")[0];
				String value1 = filter.split(":")[1];
				if(key1.equals("not_launch_in")) {
					if(isFirst) {
						filterStr.append("{'not_launch_from':'" + getDiffTime(Integer.parseInt(value1)) + "'},");
					} else {
						filterStr.append("{'or':['not_launch_from':'" + getDiffTime(Integer.parseInt(value1)) + "']},");
					}
				} else if(key1.equals("launch_in")) {
					if(isFirst) {
						filterStr.append("{'launch_from':'" + getDiffTime(Integer.parseInt(value1)) + "'},");
					} else {
						filterStr.append("{'or':['launch_from':'" + getDiffTime(Integer.parseInt(value1)) + "']},");
					}
				} else if(key1.equals("app_version")) {
					generateSubFilterStr(filterStr , value1 , "app_version" , isNotContains);
				} else if(key1.equals("province")) {
					generateSubFilterStr(filterStr , value1 , "province" , isNotContains);
				} else if(key1.equals("language")) {
					generateSubFilterStr(filterStr , value1 , "language" , isNotContains);
				}
			}
		}
		filterStr.append("" + "]" + "}" + "}");
		return filterStr.toString().replaceFirst(",]" , "]");
	}
	
	/**
	 * @param filterStr
	 *            生成结果字符串
	 * @param isFirst
	 *            时候是第一个被解析的键值对
	 * @param values
	 *            需要接卸的字符串
	 * @return
	 */
	private void generateSubFilterStr(StringBuffer filterStr , String values , String key , boolean isNotContains) {
		if(!isNotContains) {
			filterStr.append("{'or':[");
			String sss[] = values.split(",");
			for(int i = 0 ; i < sss.length ; i++) {
				String pro = sss[i];
				filterStr.append("{'" + key + "':'" + pro + "'}");
				if(sss.length - 1 != i) {
					filterStr.append(",");
				}
			}
			filterStr.append("]},");
		} else {
			filterStr.append("{'and':[");
			String sss[] = values.split(",");
			for(int i = 0 ; i < sss.length ; i++) {
				String pro = sss[i];
				filterStr.append("{'not':{'" + key + "':'" + pro + "'}}");
				if(sss.length - 1 != i) {
					filterStr.append(",");
				}
			}
			filterStr.append("]},");
		}
	}
	
	/**
	 * 得到指定天数之前的时间（如：当前时间为：2015-01-12,此时想知道30天之前的年月日则diffDay=30即可，返回结果为：2014-12-
	 * 13）
	 * 
	 * @param diffDay
	 *            多少天之前
	 * @return
	 */
	private String getDiffTime(int diffDay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Timestamp t1 = Timestamp.valueOf(format.format(new Date()));
		long s = Long.parseLong(new BigDecimal(t1.getTime() + "").subtract(new BigDecimal(diffDay * 24 * 60 * 60 * 1000L + "")).toString());
		Timestamp t2 = new Timestamp(s);
		String timeStr = new SimpleDateFormat("yyyy-MM-dd").format(t2);
		return timeStr;
	}
	
	/**
	 * 增加浏览量
	 * @param contentId
	 */
	@RequestMapping(params = "addPv")
	@ResponseBody
	public void addPv(HttpServletRequest request){
		String contentId = request.getParameter("contentId");
		contentsMobileService.addPv(contentId);
	}
	
	 
}
