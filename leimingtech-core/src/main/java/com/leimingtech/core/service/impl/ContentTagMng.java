package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.common.TagConstants;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.ContentCatServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IContentTagMng;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.ResourceUtil;
import com.leimingtech.core.util.oConvertUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 文档标签管理
 * 
 * @author liuzhen 2014年4月25日 17:15:34
 * 
 */
@Service("contentTagMng")
@Transactional
public class ContentTagMng extends CommonServiceImpl implements IContentTagMng {

	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private ArticleServiceI articleService;
	@Autowired
	private ContentCatServiceI contentCatService;
	@Autowired
	private ModelExtServiceI modelExtService;

	/**
	 * 获取文章（此方法由标签调用）
	 *
	 * @param params
	 *            文章参数
	 * @return 根据参数不同既可以获取单个文章也可以获取文章列表 article or List<article>
	 */
	@Override
	public Object getContentByTag(Map params) {
		if (MapUtils.isEmpty(params))
			return null;

		Boolean getSelf = false;
		CriteriaQuery cq = null;
		int count= oConvertUtils.getInt(params.get("count"),0);
		Boolean page = (Boolean) params.get("page") == null ? false
				: (Boolean) params.get("page");
		String pageindex = (String) params.get("pageindex");
		String isGetImage = (String) params.get("isGetImage");
		if (page && StringUtils.isNotEmpty(pageindex)
				&& StringUtils.isNumeric(pageindex)) {
			cq = new CriteriaQuery(ContentsEntity.class, 0,
					Integer.valueOf(pageindex), "", "");
		} else {
			cq = new CriteriaQuery(ContentsEntity.class);
			page = false;
		}
		cq.eq("constants", "50");
		String name = (String) params.get("name");
		String weight = (String) params.get("weight");//获取标签权重
        String siteid = (String) params.get("siteid");

        String level = (String) params.get("level");// 文章获取级别


		if(StringUtils.isEmpty(name) && StringUtils.isNotEmpty(weight)){

		}else{
			if (StringUtils.isNotEmpty(level) && !page) {
				if (level.equalsIgnoreCase(TagConstants.LEVEL_ROOT)
						&& StringUtils.isEmpty(name)) {
					// 获取所有栏目下文章 当name被指定则无效
				} else {
					if (StringUtils.isEmpty(name)){
						return null;
					}

					if (level.equalsIgnoreCase(TagConstants.LEVEL_SELF)) {// 获取文章本身
						if (StringUtils.isEmpty(name))
							return null;

						getSelf = true;

						ContentCatEntity contentcat = contentCatService
								.getEntity(name);
						if (contentcat == null) {
							cq.eq("catName", name);
						} else {
							cq.eq("id", name);
						}

					} else if (level.equalsIgnoreCase(TagConstants.LEVEL_CURRENT)) {
						queryCurrentContent(cq, name);// 获取当前栏目下的新闻 同级目录
					} else if (level.equalsIgnoreCase(TagConstants.LEVEL_All)) {
						queryAllArticle(cq, name,false);// 获取当前栏目下,包括子集的新闻
					} else {
						queryContentDefault(cq, name, page);// 当前栏目下子栏目的所有新闻，不包含当前栏目
					}
				}
			} else {// 获取子集
				if (StringUtils.isEmpty(name))
					return null;

				if (StringUtils.isNotEmpty(level) && level.equalsIgnoreCase(TagConstants.LEVEL_All)) {
					queryAllArticle(cq, name,page);// 获取当前栏目下,包括子集的新闻
				} else {
					queryContentDefault(cq, name, page);// 当前栏目下子栏目的所有新闻，不包含当前栏目
				}
			}
		}


		String type = (String) params.get("type");

		String order = (String) params.get("order");
		if (StringUtils.isNotEmpty(isGetImage)) {
			if (isGetImage.equals("true")) {
				cq.notEq("thumb", " ");
			}
		}
		if(StringUtils.isNotEmpty(weight)){
			String[]  strs = weight.split("-");
			int low_weight = Integer.parseInt(strs[0]);
			int high_weight = Integer.parseInt(strs[1]);
			cq.between("weight",low_weight,high_weight);
            cq.addOrder("weight", SortDirection.desc);
		}
        if(siteid != null && siteid.length() > 0){
            cq.eq("siteid",siteid);
        }
        setOrder(cq, order);// 设置排序

		cq.add();
		PageList pagelist = getPageList(cq, page);
		List<ContentsEntity> contentList = pagelist.getResultList();

		// 如果不是分页获取指定条数
		if (!page && contentList != null && !getSelf) {
			if (count>0) {
				if (contentList.size() > count) {
					contentList = contentList
							.subList(0, count);
				}
			} else {
				if (contentList.size() > 10) {
					contentList = contentList.subList(0, 10);
				}
			}

		}
		// 获取当前一条数据
		if (getSelf && contentList != null && contentList.size() > 0) {
			return contentList.get(0);
		}

		for (ContentsEntity contentsEntity : contentList) {

			ArticleEntity article = articleService
					.getArticleById(contentsEntity.getId());
			contentsEntity.setAtriles(article.getContent());

		}

		return contentList;
	}

	/**
	 * 获取当前栏目下的新闻
	 *
	 * @param cq
	 * @param name
	 */
	private void queryCurrentContent(CriteriaQuery cq, String name) {
		ContentCatEntity catalog = null;
		catalog = getEntity(ContentCatEntity.class, name);
		if (catalog == null) {
			catalog = findUniqueByProperty(ContentCatEntity.class, "name", name);
		}
		if (catalog == null) {
			cq.eq("catid", -1 + "");
		} else {
			cq.eq("catid", catalog.getContentCat().getId());
		}

		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED,
				ContentStatus.CONTENT_PUBLISHED });
	}

	/**
	 * 获取当前栏目下,包括子集的新闻
	 * 
	 * @param cq
	 * @param name
	 * @param page 
	 */
	private void queryAllArticle(CriteriaQuery cq, String name, Boolean page) {
		List<String> catalogid = new ArrayList<String>();

		ContentCatEntity catalog = getEntity(ContentCatEntity.class, name);

		if (catalog == null) {
			catalog = findUniqueByProperty(ContentCatEntity.class, "name", name);
		}
		
		if (catalog == null) {
			//当栏目不存在查不出数据
			cq.eq("id", "-1");
		}else if(RequestContextHolder.getRequestAttributes()!=null && ContextHolderUtils.getSession()!=null  && ContextHolderUtils.getRequest().getAttribute("static2Html")==null){
			//当是动态访问模板时，可以有session，则根据权限划分获取数据
			HttpSession session = ContextHolderUtils.getSession();
			
			List<String> list = (List<String>) session.getAttribute("channel");
			
			List<String> catalogids = new ArrayList<String>();
			
			catalogids = getChildidList(catalogids, catalog,list);
			if (catalogids != null && catalogids.size() > 0) {
				cq.in("catid", toStringArray(catalogids));
			} else {
				cq.eq("catid", -1 + "");
				
			}
		}else{
			//静态化默认处理方式
			catalogid = getChildidList(catalogid, catalog);

			if (catalogid != null && catalogid.size() > 0) {
				
				cq.in("catid", toStringArray(catalogid));
				if (page) {
					cq.setPageSize(catalog.getPagesize());
				}
			} else {
				cq.eq("catid", -1 + "");
			}
		}
		
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED,
				ContentStatus.CONTENT_PUBLISHED });
	}
	
	private List<String> getChildidList(List<String> idList,
			ContentCatEntity contentCat, List<String> list) {
		
		if(list==null || list.size()==0){
			return idList;
		}
		
		if (contentCat != null && list.contains(contentCat.getId())) {
			idList.add(contentCat.getId());
			List<ContentCatEntity> contentCatList = contentCatService.getListByPid(contentCat.getId());
			if (contentCatList != null && contentCatList.size() > 0) {
				for (ContentCatEntity contentCatEntity : contentCatList) {
					idList = getChildidList(idList, contentCatEntity,list);
				}
			}
		}
		return idList;
	}

	/**
	 * 默认文章查询
	 *
	 * @param cq
	 * @param name
	 */
	private void queryContentDefault(CriteriaQuery cq, String name, Boolean page) {
		ContentCatEntity catalog = getEntity(ContentCatEntity.class,
				String.valueOf(name));
		if (catalog == null) {
			catalog = findUniqueByProperty(ContentCatEntity.class, "name", name);
		}
		if (catalog == null) {
			cq.eq("catid", -1 + "");
		} else {
			cq.eq("catid", catalog.getId());
			if (page) {
				cq.setPageSize(catalog.getPagesize());
			}
		}
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED,
				ContentStatus.CONTENT_PUBLISHED });
	}

	/**
	 * 设置排序
	 *
	 * @param cq
	 * @param order
	 */
	private void setOrder(CriteriaQuery cq, String order) {
		if (StringUtils.isNotEmpty(order)) {
			if (order.equalsIgnoreCase(TagConstants.ORDER_TOP)) {
	                cq.addOrder("isHeadline", SortDirection.desc);// 头条
            } else if (order.equalsIgnoreCase(TagConstants.ORDER_PV)) {
                cq.addOrder("pv", SortDirection.desc);// 浏览量
			} else if (order.equalsIgnoreCase(TagConstants.ORDER_ISTOP)) {
				cq.addOrder("isTop", SortDirection.desc);// 置顶
			}
		}
		cq.addOrder("published", SortDirection.desc);
	}

	private List<String> getChildidList(List<String> idList,
										ContentCatEntity contentCat) {
		if (contentCat != null) {
			idList.add(contentCat.getId());
			List<ContentCatEntity> contentCatList = findByProperty(
					ContentCatEntity.class, "contentCat.id", contentCat.getId());
			if (contentCatList != null && contentCatList.size() > 0) {
				for (ContentCatEntity contentCatEntity : contentCatList) {
					idList = getChildidList(idList, contentCatEntity);
				}
			}
		}
		return idList;
	}

	private String[] toStringArray(List<String> args) {
		if (args != null && args.size() == 0)
			return null;

		String[] intArray = new String[args.size()];
		for (int i = 0; i < args.size(); i++) {
			intArray[i] = args.get(i);
		}
		return intArray;
	}

	/**
	 * 通过文章获取文章模板路径
	 *
	 * @return 模板路径
	 */
	@Override
	public String getContentTemplatePath(ContentsEntity article,ContentCatEntity catalog) {
		if (article == null)
			return null;

		String modelType = parseIntModelToStr(article.getClassify());
		if (StringUtils.isEmpty(modelType))
			return null;

		JSONObject json = JSONObject.fromObject(catalog.getTemplateList());
		if (json.get(modelType) != null) {
			return json.get(modelType).toString();
		}
		return null;
	}

	/**
	 * 模型转换 int转str
	 *
	 * @param modelType
	 * @return
	 */
	public String parseIntModelToStr(String modelType) {
		if (ContentClassify.CONTENT_ACTIVITY.equals(modelType)) {
			// 活动
			return ContentClassify.CONTENT_ACTIVITY_STR;
		}

		if (ContentClassify.CONTENT_ARTICLE.equals(modelType)) {
			// 文章
			return ContentClassify.CONTENT_ARTICLE_STR;
		}

		if (ContentClassify.CONTENT_INTERVIEW.equals(modelType)) {
			// 访谈
			return ContentClassify.CONTENT_INTERVIEW_STR;
		}

		if (ContentClassify.CONTENT_LINK.equals(modelType)) {
			// 链接
			return ContentClassify.CONTENT_LINK_STR;
		}

		if (ContentClassify.CONTENT_PICTUREGROUP.equals(modelType)) {
			// 组图
			return ContentClassify.CONTENT_PICTUREGROUP_STR;
		}

		if (ContentClassify.CONTENT_SPECIAL.equals(modelType)) {
			// 专题
			return ContentClassify.CONTENT_SPECIAL_STR;
		}

		if (ContentClassify.CONTENT_SURVEY.equals(modelType)) {
			// 调查
			return ContentClassify.CONTENT_SURVEY_STR;
		}

		if (ContentClassify.CONTENT_VIDEO.equals(modelType)) {
			// 视频
			return ContentClassify.CONTENT_VIDEO_STR;
		}

		if (ContentClassify.CONTENT_VOTE.equals(modelType)) {
			// 投票
			return ContentClassify.CONTENT_VOTE_STR;
		}

		return "";
	}

	/**
	 * 根据文章id获取相关模型数据
	 *
	 * @param articleid
	 * @return map
	 */
	@Override
	public Map<String, Object> getContent(Integer articleid) {
		ContentsEntity article = getEntity(ContentsEntity.class, articleid);
		if (article != null) {
			return getContent(article);
		}
		return null;
	}

	/**
	 * 根据文章获取相关模型数据
	 *
	 * @param content
	 * @return map
	 */
	@Override
	public Map<String, Object> getContent(ContentsEntity content) {
		if (content == null)
			return null;

		Map<String, Object> m = new HashMap<String, Object>();
		
		//跟内容相关的拓展字段
		List<ModelExtEntity> extList=modelExtService.getContentAllExt(content.getId());
		for (int i = 0; i < extList.size(); i++) {
			ModelExtEntity ext=extList.get(i);
			m.put(ext.getAttrToken(), ext.getAttrValue());
		}
		
		m.put("contentF", content);

		String articleid = content.getId();
		String modelType = content.getClassify();

		if (ContentClassify.CONTENT_ACTIVITY.equals(modelType)) {
			// 活动
			m.putAll(getActivity(articleid));
		}

		if (ContentClassify.CONTENT_ARTICLE.equals(modelType)) {
			// 文章
			m.putAll(getArticleData(articleid));
		}

		if (ContentClassify.CONTENT_INTERVIEW.equals(modelType)) {
			// 访谈
			m.putAll(getInterview(articleid));
		}

		if (ContentClassify.CONTENT_LINK.equals(modelType)) {
			// 链接
			m.putAll(getLink(articleid));
		}

		if (ContentClassify.CONTENT_PICTUREGROUP.equals(modelType)) {
			// 组图
			m.putAll(getPicturegroup(articleid));
		}

		if (ContentClassify.CONTENT_SPECIAL.equals(modelType)) {
			// 专题
			m.putAll(getSpecial(articleid));
		}

		if (ContentClassify.CONTENT_SURVEY.equals(modelType)) {
			// 调查
			m.putAll(getSurvey(articleid));
		}

		if (ContentClassify.CONTENT_VIDEO.equals(modelType)) {
			// 视频
			m.putAll(getVideo(articleid));
		}

		if (ContentClassify.CONTENT_VOTE.equals(modelType)) {
			// 投票
			m.putAll(getVote(articleid));
		}

		return m;
	}

	/**
	 * 链接模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getLink(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<ContentLinkEntity> linkList = findByProperty(
				ContentLinkEntity.class, "contentid", articleid);
		if (linkList != null && linkList.size() > 0) {
			m.put("linkF", linkList.get(0));
		} else {
			m.put("linkF", new ContentLinkEntity());
		}
		return m;
	}

	/**
	 * 组图模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getPicturegroup(String articleid) {

		Map<String, Object> m = new HashMap<String, Object>();

		List<PictureGroupEntity> pictureGroupList = findByProperty(
				PictureGroupEntity.class, "contentid", articleid);
		if (pictureGroupList != null && pictureGroupList.size() > 0) {
			m.put("pictureGroupF", pictureGroupList.get(0));

			List<PictureAloneEntity> pictureAloneList = findByProperty(
					PictureAloneEntity.class, "picturegroupid",
					pictureGroupList.get(0).getId().toString());
			if (pictureAloneList != null && pictureAloneList.size() > 0) {
				m.put("pictureAloneListF", pictureAloneList);
			} else {
				m.put("pictureAloneListF", new ArrayList<PictureAloneEntity>());
			}
		} else {
			m.put("pictureGroupF", new PictureGroupEntity());
			m.put("pictureAloneListF", new ArrayList<PictureAloneEntity>());
		}
		return m;
	}

	/**
	 * 专题模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getSpecial(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<SpecialEntity> specialList = findByProperty(SpecialEntity.class,
				"contentid", articleid);
		if (specialList != null && specialList.size() > 0) {
			m.put("specialF", specialList.get(0));
		} else {
			m.put("specialF", new SpecialEntity());
		}
		return m;
	}

	/**
	 * 调查模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getSurvey(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<SurveyEntity> surveyList = findByProperty(SurveyEntity.class,
				"contentid", articleid);
		if (surveyList != null && surveyList.size() > 0) {
			m.put("surveyF", surveyList.get(0));
		} else {
			m.put("surveyF", new SurveyEntity());
		}
		return m;
	}

	/**
	 * 视频模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getVideo(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		ContentVideoEntity video = findUniqueByProperty(
				ContentVideoEntity.class, "contentid", articleid);
		if (video == null) {
			video = new ContentVideoEntity();
		}
		m.put("videoF", video);
		return m;
	}

	/**
	 * 投票模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getVote(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<VoteEntity> voteList = findByProperty(VoteEntity.class,
				"contentid", articleid);
		if (voteList != null && voteList.size() > 0) {
			m.put("voteF", voteList.get(0));

			List<VoteOptionEntity> voteOptionList = findByProperty(
					VoteOptionEntity.class, "voteid", voteList.get(0).getId()
							.toString());
			if (voteOptionList != null && voteOptionList.size() > 0) {
				m.put("voteOptionListF", voteOptionList);
			} else {
				m.put("voteOptionListF", new ArrayList<VoteOptionEntity>());
			}
		} else {
			m.put("voteF", new VoteEntity());
			m.put("voteOptionListF", new ArrayList<VoteOptionEntity>());
		}
		return m;
	}

	/**
	 * 访谈模型
	 *
	 * @param articleid
	 * @return
	 */
	private Map<String, Object> getInterview(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<InterviewEntity> interviewList = findByProperty(
				InterviewEntity.class, "contentid", articleid);
		if (interviewList != null && interviewList.size() > 0) {
			m.put("interviewF", interviewList.get(0));

			List<InterviewGuestEntity> interviewGuestList = findByProperty(
					InterviewGuestEntity.class, "interviewid", interviewList
							.get(0).getId().toString());
			if (interviewGuestList != null && interviewGuestList.size() > 0) {
				m.put("interviewGuestListF", interviewGuestList);
			} else {
				m.put("interviewGuestListF",
						new ArrayList<InterviewGuestEntity>());
			}
		} else {
			m.put("interviewF", new InterviewEntity());
			m.put("interviewGuestListF", new ArrayList<InterviewGuestEntity>());
		}
		return m;
	}

	/**
	 * 文章模型
	 *
	 * @param articleid
	 *
	 * @return
	 */
	private Map<String, Object> getArticleData(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		ArticleEntity article = articleService.getArticleById(articleid);
		if (article != null) {
			article.setContent(replaceWapStr(article.getContent()));
		}

		List<ContentAccessoryEntity> accessoryList = articleService.findByProperty(ContentAccessoryEntity.class, "contentId", articleid);
		String uploadPath = ResourceUtil.getCMSUploadFilesPath();

		m.put("accessoryList", accessoryList);
		m.put("uploadPath", uploadPath);

		m.put("articleDataF", article);
		return m;
	}

	public String replaceWapStr(String str) {
		if (StringUtils.isNotEmpty(str)) {
			str = str.replaceAll("<html>", "");
			str = str.replaceAll("<head>", "");
			str = str.replaceAll("</head>", "");
			str = str.replaceAll("<body>", "");
			str = str.replaceAll("</body>", "");
			str = str.replaceAll("</html>", "");

		}
		return str;
	}

	/**
	 * 活动模型
	 *
	 * @param articleid
	 *
	 * @return
	 */
	private Map<String, Object> getActivity(String articleid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<ActivityEntity> activityList = findByProperty(
				ActivityEntity.class, "contentid", articleid);

		if (activityList != null && activityList.size() > 0) {
			m.put("activityF", activityList.get(0));
			m.put("sysdate",new Date());
			String sql = "select apc.id as id, apc.optionids as optionids,ap.validation as validation,ap.textsize_limit as textsizeLimit ,"
					+ "ap.selectsize_limit as selectsizeLimit, ap.filesize_limit as filesizeLimit, "
					+ "ap.optional_value as optionalValue,ap.date_type as dateType,apc.optionids as optionids,"
					+ "ap.is_show as isShow, ap.option_name as optionName,apc.is_enableds as isEnableds,apc.is_receptionShow "
					+ "as isReceptionshow,apc.is_required as isRequired"
					+ " from cms_activity_optioncontent  apc,cms_activity_option ap "
					+ "WHERE ap.id=apc.optionids and ap.is_enabled=1 and apc.is_receptionShow=1 "
					+ "and  apc.activityids='"
					+ activityList.get(0).getId()
					+ "' ORDER BY ap.sort ,ap.createtime DESC" ;

			List<Map<String, Object>> ActivityOptionList = findForJdbc(sql);

			if (ActivityOptionList != null && ActivityOptionList.size() > 0) {
				// 获取单个的Map数据
		/*for (int i = 0; i < ActivityOptionList.size(); i++) {
		Map<String, Object> map = ActivityOptionList.get(i) ;
		Set<String> set = map.keySet();
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(set) ;
		for (int j = 0; j < keyList.size(); j++) {

		System.out.println("key:" + keyList.get(4) + "---Value:" + map.get(keyList.get(4)));
		}
		}*/

				m.put("activityOptionListF", ActivityOptionList);
			}
		} else {
			m.put("activityF", new ActivityEntity());
		}
		return m;
	}

	@Override
	/**
	 *
	 * 置顶文章
	 *
	 */
	public Object getContentByTagTop(Map params) {
		if (MapUtils.isEmpty(params))
			return null;

		Boolean getSelf = false;
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		String count = (String) params.get("count");// 获取数据量
		Boolean page = (Boolean) params.get("page") == null ? false
				: (Boolean) params.get("page");
		String pageindex = (String) params.get("pageindex");

		if (page && StringUtils.isNotEmpty(pageindex)
				&& StringUtils.isNumeric(pageindex)) {
			cq.setCurPage(Integer.valueOf(pageindex));
			cq.setMyAction("");
			cq.setMyForm("");
			cq.add();
		} else {
			page = false;
		}
		// String type = (String) params.get("type");

		String order = (String) params.get("order");
		cq.notEq("isTop", 0);
		setOrder(cq, order);// 设置排序
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		PageList pagelist = getPageList(cq, page);
		List<ContentsEntity> contentList = pagelist.getResultList();

		// 如果不是分页获取指定条数
		if (!page && contentList != null && !getSelf) {

			if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)) {
				if (contentList.size() > Integer.valueOf(count)) {
					contentList = contentList
							.subList(0, Integer.valueOf(count));
				}
			} else {
				if (contentList.size() > 10) {
					contentList = contentList.subList(0, 10);
				}
			}

		}

		// 获取当前一条数据
		if (getSelf && contentList != null && contentList.size() > 0) {
			return contentList.get(0);
		}

		return contentList;
	}
}
