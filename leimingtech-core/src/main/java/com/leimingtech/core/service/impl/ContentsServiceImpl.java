package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.*;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.*;
import com.leimingtech.core.service.modelext.ModelExtServiceI;
import com.leimingtech.core.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 内容管理接口实现
 * 
 * @author liuzhen 2014年8月1日 15:18:50 
 */
@Service("contentsService")
@Transactional
public class ContentsServiceImpl extends CommonServiceImpl implements
		ContentsServiceI {

	@Autowired
	private IStatic staticImpl;
	@Autowired
	private ContentCatServiceI contentCatService;// 栏目管理接口
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ContentVideoServiceI contentVideoService;
	@Autowired
	private PictureGroupServiceI pictureGroupService;
	/**组图内图片管理接口*/
	@Autowired
	private PictureAloneServiceI pictureAloneService;
	@Autowired
	private ContentIllegalServiceI contentIllegalService;
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	/** 稿件复制或引用关联接口 */
	@Autowired
	private CopyContentRefServiceI copyContentRefService;
	
	@Autowired
	private ModelExtServiceI modelExtService;
	@Autowired
	private CommonService commonService;
	/**投票内容接口*/
	@Autowired
	private VoteServiceI voteService;
	/**投票选项接口*/
	@Autowired
	private VoteOptionServiceI voteOptionService;
	/**调查接口*/
	@Autowired
	private SurveyServiceI surveyService;
	/**调查选项接口*/
	@Autowired
	private SurveyOptionServiceI surveyOptionService;
	/**调查选项子项操作接口*/
	@Autowired
	private SurveyOptionExtServiceI surveyOptionExtService;

	/**
	 * 保存任意内容时均保存对应的链接内容
	 * 
	 * @param contents
	 */
	@Override
	public void saveLinkContent(ContentsEntity contents, String funVal0) {
		String[] funVals = funVal0.split(",");
		try {
			for (int i = 0; i < funVals.length; i++) {
				String funVal = funVals[i];
				if (StringUtils.isNotEmpty(funVal)) {
					contents = constantsService.getEntity(ContentsEntity.class,
							contents.getId());
					ContentCatEntity catalog = getEntity(
							ContentCatEntity.class, funVal);
					ContentsEntity c = new ContentsEntity();
					MyBeanUtils.copyBean2Bean(c, contents);
					c.setId(null);
					c.setPathids(catalog.getPathids());
					c.setClassify(ContentClassify.CONTENT_LINK);
					c.setCatid(String.valueOf(funVal));
					c.setConstants("50");
					this.saveOrUpdate(c);

					ContentLinkEntity contentLink = new ContentLinkEntity();
					contentLink.setContentid(c.getId());
					contentLink.setSiteid(PlatFormUtil.getSessionSiteId());
					contentLink.setLinkurl(c.getUrl());
					contentLink.setCreatedtime(new Date());// 创建时间
					this.save(contentLink);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据参数不同获取内容（wap标签调用）
	 * 
	 * @param params
	 *            栏目id
	 * @return
	 */
	@Override
	public Object getContentByTag(Map params) {
		if (MapUtils.isEmpty(params))
			return new ArrayList<ContentsEntity>();

		String catalogid = params.get("catalogid").toString();
		if (StringUtils.isEmpty(catalogid) || !StringUtils.isNumeric(catalogid))
			return new ArrayList<ContentsEntity>();

		ContentCatEntity catalog = getEntity(ContentCatEntity.class,
				String.valueOf(catalogid));

		SiteEntity site = getEntity(SiteEntity.class, catalog.getSiteid());

		WapManager manager = new WapManager(site);
		WapconfigEntity config = manager.getWapconfig();

		Set<String> modelsSet = manager.getModels();

		Object[] modelArray = manager.getModels().toArray();
		String[] modelids = new String[modelsSet.size()];
		modelids = modelsSet.toArray(modelids);

		CriteriaQuery cqContent = new CriteriaQuery(ContentsEntity.class,
				Integer.valueOf(config.getColumncount()), 1, null, null);
		cqContent.eq("catid", String.valueOf(catalogid));
		cqContent.in("modelid", modelids);
		Date date = new Date();
		cqContent.or(Restrictions.isNull("noted"),
				Restrictions.ge("noted", date));
		cqContent.le("published", date);
		cqContent.in("constants", new String[] {
				ContentStatus.CONTENT_COMMITTED,
				ContentStatus.CONTENT_PUBLISHED });
		cqContent.eq("siteid", site.getId());
		cqContent.addOrder("published", SortDirection.desc);
		cqContent.addOrder("id", SortDirection.desc);
		cqContent.add();
		List<ContentsEntity> childcontentList = getListByCriteriaQuery(
				cqContent, true);
		return childcontentList;
	}

	/**
	 * 获取当前站点中允许访问的文章
	 * 
	 * @return Map key==>contentList 内容列表 key==>contentDataMap 内容包含数据列表
	 */
	@Override
	public Map<String, Object> getAllOpenContent(SiteEntity site) {
		Map<String, Object> m = new HashMap<String, Object>();

		String[] catalogArray = contentCatService.getAllOpenContentCatIdArray(
				site, true);// 获取当前站点中所有开启的栏目

		if (catalogArray.length == 0) {
			LogUtil.error("当前站点【" + site.getSiteName() + "】中所有开启的栏目 is null");
			return null;
		}

		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);

		DetachedCriteria dc = cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("id").as("id"));
		pList.add(Projections.property("title").as("title"));
		pList.add(Projections.property("url").as("url"));
		pList.add(Projections.property("wapurl").as("wapurl"));
		pList.add(Projections.property("digest").as("digest"));
		pList.add(Projections.property("published").as("published"));
		pList.add(Projections.property("modelid").as("modelid"));
		pList.add(Projections.property("catid").as("catid"));
		pList.add(Projections.property("classify").as("classify"));
		pList.add(Projections.property("pathids").as("pathids"));
		pList.add(Projections.property("tags").as("tags"));
		dc.setProjection(pList);
		dc.setResultTransformer(Transformers.aliasToBean(ContentsEntity.class));

		cq.setDetachedCriteria(dc);
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.eq("constants", ContentStatus.CONTENT_PUBLISHED );
		cq.eq("siteid", site.getId());
		cq.in("catid", catalogArray);
		cq.addOrder("weight", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		List<ContentsEntity> catalogList = getListByCriteriaQuery(cq, false);
		m.put("contentList", catalogList);

		Map<java.lang.String, Object> contentdataMap = new HashMap<String, Object>();
		if (catalogList != null && catalogList.size() > 0) {
			for (int i = 0; i < catalogList.size(); i++) {
				ContentsEntity content = catalogList.get(i);
				String contentid = content.getId();
				contentdataMap = getIndexContent(contentid);
			}
		}
		m.put("contentDataMap", contentdataMap);

		return m;
	}

	/**
	 * 移动到、引用到、复制到内容
	 * 
	 * @param ids
	 * @param contentCatId
	 * @param toType
	 * @return
	 */
	@Override
	public String mobiles(String ids, String contentCatId, String toType,String siteId) {
		JSONObject j = new JSONObject();
		String[] contentsids = ids.split(",");
		String message = "";
		ContentCatEntity catalog = getEntity(ContentCatEntity.class,
				contentCatId);
		try {
			if (toType.equals("mobile")) {
				for (String contentsid : contentsids) {
					message = "移动内容到 [ " + catalog.getName() + " ] 成功";
					ContentsEntity contents = this.get(ContentsEntity.class,
							contentsid);
					contents.setSiteid(siteId);
					contents.setCatid(String.valueOf(contentCatId));
					contents.setPathids(catalog.getPathids());
					this.saveOrUpdate(contents);
				}
			}
			if (toType.equals("cite")) {
				for (String contentsid : contentsids) {
					message = "引用内容到 [ " + catalog.getName() + " ] 成功";
					ContentsEntity contents = this.get(ContentsEntity.class,
							contentsid);
					ContentsEntity c = new ContentsEntity();
					MyBeanUtils.copyBean2Bean(c, contents);
					c.setId(null);
					c.setPathids(catalog.getPathids());
					c.setClassify(ContentClassify.CONTENT_LINK);
					c.setCatid(contentCatId);
					c.setSiteid(siteId);
					this.saveOrUpdate(c);

					ContentLinkEntity contentLink = new ContentLinkEntity();
					contentLink.setContentid(c.getId());
					contentLink.setSiteid(siteId);
					contentLink.setLinkurl(c.getUrl());
					contentLink.setCreatedtime(new Date());// 创建时间
					this.save(contentLink);

					copyContentRefService.save(contents, c.getId());// 添加关联引用
				}
			}
			if (toType.equals("copy")) {
				for (int i = 0; i < contentsids.length; i++) {
					String contentsid = contentsids[i];
					ContentsEntity contents = this.get(ContentsEntity.class,
							String.valueOf(contentsid));

					ContentsEntity mainContent = copyContentRefService
							.findLockMainContent(contentsid);

					if (contentsids.length > 1) {
						message += "<br/>";
					}
					if (mainContent != null) {
						message += "【" + contents.getTitle() + "】复制内容到【"
								+ catalog.getName()
								+ " 】栏目 失败！原因：稿件已被锁定，请复制源稿件！源稿件路径（栏目“"
								+ catalog.getName() + "”==>"
								+ mainContent.getTitle() + "）";
						continue;
					}

					message += "【" + contents.getTitle() + "】复制内容到【"
							+ catalog.getName() + " 】栏目 成功";

					ContentsEntity c = new ContentsEntity();
					MyBeanUtils.copyBean2Bean(c, contents);
					c.setId(null);
					c.setPathids(catalog.getPathids());
					c.setCatid(String.valueOf(contentCatId));
					c.setSiteid(siteId);
					c.setLockContent("false");// 复制后的稿件本身不再是锁定稿件
					this.saveOrUpdate(c);

					// 复制文章
					List<ArticleEntity> articleList = this.findByProperty(
							ArticleEntity.class, "contentid", contents.getId());
					if (articleList.size() != 0) {
						ArticleEntity articles= articleList.get(0);
						ArticleEntity article =new ArticleEntity();
						MyBeanUtils.copyBean2Bean(article, articles);
						article.setId(null);
						article.setContentid(c.getId());
						article.setSiteid(siteId);
						this.saveOrUpdate(article);
					}
					if(contents.getClassify().equals(
							ContentClassify.CONTENT_PICTUREGROUP)){

						// 复制组图
						PictureGroupEntity pictureGroup=this.pictureGroupService.getPictureGroupByContentId(contents.getId());

						PictureGroupEntity picture =new PictureGroupEntity();
						MyBeanUtils.copyBean2Bean(picture, pictureGroup);
						picture.setId(null);
						picture.setContentid(c.getId());
						picture.setSiteid(siteId);
						this.saveOrUpdate(picture);

						List<PictureAloneEntity> picList=this.pictureAloneService.findAllByPictureGroupId(pictureGroup.getId());
						for (PictureAloneEntity pictureAlone:  picList ) {
							PictureAloneEntity newPictureAlone=new PictureAloneEntity();
							MyBeanUtils.copyBean2Bean(newPictureAlone, pictureAlone);
							newPictureAlone.setId(null);
							newPictureAlone.setPicturegroupid(picture.getId());
							this.pictureAloneService.saveOrUpdate(newPictureAlone);
						}

					}
					// 复制链接
					List<ContentLinkEntity> contentLinkList = this
							.findByProperty(ContentLinkEntity.class,
									"contentid", contents.getId());
					if (contentLinkList.size() != 0) {
						ContentLinkEntity contentLinks = contentLinkList.get(0);
						ContentLinkEntity contentLink =new ContentLinkEntity();
						MyBeanUtils.copyBean2Bean(contentLink, contentLinks);
						contentLink.setId(null);
						contentLink.setContentid(c.getId());
						contentLink.setSiteid(siteId);
						this.saveOrUpdate(contentLink);
					}
					// 复制视频
					List<ContentVideoEntity> contentVideoList = this
							.findByProperty(ContentVideoEntity.class,
									"contentid", contents.getId());
					if (contentVideoList.size() != 0) {
						ContentVideoEntity contentVideos = contentVideoList
								.get(0);
						ContentVideoEntity contentVideo =new ContentVideoEntity();
						MyBeanUtils.copyBean2Bean(contentVideo, contentVideos);
						contentVideo.setId(null);
						contentVideo.setSiteid(siteId);
						contentVideo.setContentid(c.getId());
						this.saveOrUpdate(contentVideo);
					}

					if(contents.getClassify().equals(
							ContentClassify.CONTENT_VOTE)){

						// 复制投票
						VoteEntity vote = this.voteService.getVoteByContentId(contents.getId());//根据内容id获取投票内容
						VoteEntity newVote =new VoteEntity();
						MyBeanUtils.copyBean2Bean(newVote, vote);
						newVote.setId(null);
						newVote.setContentid(c.getId());
						newVote.setSiteid(siteId);
						newVote.setVoteOptionList(null);
						this.voteService.save(newVote);

						List<VoteOptionEntity> voteOptionList=this.voteOptionService.getAllByVoteId(vote.getId());
						for (VoteOptionEntity option: voteOptionList) {
							VoteOptionEntity newOpiton =new VoteOptionEntity();
							MyBeanUtils.copyBean2Bean(newOpiton, option);
							newOpiton.setId(null);
							newOpiton.setVoteid(newVote.getId());
							newOpiton.setSiteid(siteId);
						}
					}

					if (contents.getClassify().equals(
							ContentClassify.CONTENT_SURVEY)) {
						// 复制调查
						//获取原来的调查内容
						SurveyEntity survey = this.surveyService.getSurveyByContentId(contents.getId());
						SurveyEntity newSurvey = new SurveyEntity();
						MyBeanUtils.copyBean2Bean(newSurvey, survey);
						newSurvey.setId(null);
						newSurvey.setContentid(c.getId());
						newSurvey.setSiteid(siteId);
						newSurvey.setOptionList(null);
						this.saveOrUpdate(newSurvey);

						//获取调查选项
						List<SurveyOptionEntity> optionList = this.surveyOptionService.getAllOptionBySurveyId(survey.getId());

						if (optionList != null && optionList.size() > 0) {
							for (SurveyOptionEntity option : optionList) {
								SurveyOptionEntity newOption = new SurveyOptionEntity();
								MyBeanUtils.copyBean2Bean(newOption, option);
								newOption.setId(null);
								newOption.setSurveyid(newSurvey.getId());
								newOption.setOptionextList(null);
								this.surveyOptionService.save(newOption);

								List<SurveyOptionExtEntity> extList = this.surveyOptionExtService.getAllExtByOptionId(option.getId());

								if (extList != null && extList.size() > 0) {
									for (SurveyOptionExtEntity ext : extList) {
										SurveyOptionExtEntity newExt = new SurveyOptionExtEntity();
										MyBeanUtils.copyBean2Bean(newExt, ext);
										newExt.setId(null);
										newExt.setOptionsid(newOption.getId());
										this.surveyOptionExtService.save(newExt);
									}

								}
							}

						}
					}

					copyContentRefService.save(contents, c.getId());// 添加关联引用
					staticImpl.staticContentActOnPreview(c);
				}
			}
			//复制到其他的站点
			if (toType.equals("toOtherSite")) {
				for (int i = 0; i < contentsids.length; i++) {
					String contentsid = contentsids[i];
					ContentsEntity contents = this.get(ContentsEntity.class,
							String.valueOf(contentsid));

					ContentsEntity mainContent = copyContentRefService
							.findLockMainContent(contentsid);

					if (contentsids.length > 1) {
						message += "<br/>";
					}
					if (mainContent != null) {
						message += "【" + contents.getTitle() + "】复制内容到【"
								+ catalog.getName()
								+ " 】栏目 失败！原因：稿件已被锁定，请复制源稿件！源稿件路径（栏目“"
								+ catalog.getName() + "”==>"
								+ mainContent.getTitle() + "）";
						continue;
					}

					message += "【" + contents.getTitle() + "】复制内容到【"
							+ catalog.getName() + " 】栏目 成功";

					ContentsEntity c = new ContentsEntity();
					MyBeanUtils.copyBean2Bean(c, contents);
					c.setId(null);
					c.setPathids(catalog.getPathids());
					c.setCatid(String.valueOf(contentCatId));
					c.setSiteid(siteId);
					c.setLockContent("false");// 复制后的稿件本身不再是锁定稿件
					this.saveOrUpdate(c);
					staticImpl.staticContentActOnPreview(c);
					// 复制文章
					List<ArticleEntity> articleList = this.findByProperty(
							ArticleEntity.class, "contentid", contents.getId());
					if (articleList.size() != 0) {
						ArticleEntity articles= articleList.get(0);
						ArticleEntity article =new ArticleEntity();
						MyBeanUtils.copyBean2Bean(article, articles);
						article.setId(null);
						article.setContentid(c.getId());
						article.setSiteid(siteId);
						this.saveOrUpdate(article);
					}

					if(contents.getClassify().equals(
							ContentClassify.CONTENT_PICTUREGROUP)){

						// 复制组图
						PictureGroupEntity pictureGroup=this.pictureGroupService.getPictureGroupByContentId(contents.getId());

						PictureGroupEntity picture =new PictureGroupEntity();
						MyBeanUtils.copyBean2Bean(picture, pictureGroup);
						picture.setId(null);
						picture.setContentid(c.getId());
						picture.setSiteid(siteId);
						this.saveOrUpdate(picture);

						List<PictureAloneEntity> picList=this.pictureAloneService.findAllByPictureGroupId(pictureGroup.getId());
						for (PictureAloneEntity pictureAlone:  picList ) {
							PictureAloneEntity newPictureAlone=new PictureAloneEntity();
							MyBeanUtils.copyBean2Bean(newPictureAlone, pictureAlone);
							newPictureAlone.setId(null);
							newPictureAlone.setPicturegroupid(picture.getId());
							this.pictureAloneService.saveOrUpdate(newPictureAlone);
						}

					}

					// 复制链接
					List<ContentLinkEntity> contentLinkList = this
							.findByProperty(ContentLinkEntity.class,
									"contentid", contents.getId());
					if (contentLinkList.size() != 0) {
						ContentLinkEntity contentLinks = contentLinkList.get(0);
						ContentLinkEntity contentLink =new ContentLinkEntity();
						MyBeanUtils.copyBean2Bean(contentLink, contentLinks);
						contentLink.setId(null);
						contentLink.setContentid(c.getId());
						contentLink.setSiteid(siteId);
						this.saveOrUpdate(contentLink);
					}
					// 复制视频
					List<ContentVideoEntity> contentVideoList = this
							.findByProperty(ContentVideoEntity.class,
									"contentid", contents.getId());
					if (contentVideoList.size() != 0) {
						ContentVideoEntity contentVideos = contentVideoList
								.get(0);
						ContentVideoEntity contentVideo =new ContentVideoEntity();
						MyBeanUtils.copyBean2Bean(contentVideo, contentVideos);
						contentVideo.setId(null);
						contentVideo.setSiteid(siteId);
						contentVideo.setContentid(c.getId());
						this.saveOrUpdate(contentVideo);
					}

					if(contents.getClassify().equals(
							ContentClassify.CONTENT_VOTE)){

						// 复制投票
						VoteEntity vote = this.voteService.getVoteByContentId(contents.getId());//根据内容id获取投票内容
						VoteEntity newVote =new VoteEntity();
						MyBeanUtils.copyBean2Bean(newVote, vote);
						newVote.setId(null);
						newVote.setContentid(c.getId());
						newVote.setSiteid(siteId);
						newVote.setVoteOptionList(null);
						this.voteService.save(newVote);

						List<VoteOptionEntity> voteOptionList=this.voteOptionService.getAllByVoteId(vote.getId());
						for (VoteOptionEntity option: voteOptionList) {
							VoteOptionEntity newOpiton =new VoteOptionEntity();
							MyBeanUtils.copyBean2Bean(newOpiton, option);
							newOpiton.setId(null);
							newOpiton.setVoteid(newVote.getId());
							newOpiton.setSiteid(siteId);
						}
					}



					if (contents.getClassify().equals(
							ContentClassify.CONTENT_SURVEY)) {
						// 复制调查
						//获取原来的调查内容
						SurveyEntity survey = this.surveyService.getSurveyByContentId(contents.getId());
						SurveyEntity newSurvey = new SurveyEntity();
						MyBeanUtils.copyBean2Bean(newSurvey, survey);
						newSurvey.setId(null);
						newSurvey.setContentid(c.getId());
						newSurvey.setSiteid(siteId);
						newSurvey.setOptionList(null);
						this.saveOrUpdate(newSurvey);

						//获取调查选项
						List<SurveyOptionEntity> optionList = this.surveyOptionService.getAllOptionBySurveyId(survey.getId());

						if (optionList != null && optionList.size() > 0) {
							for (SurveyOptionEntity option : optionList) {
								SurveyOptionEntity newOption = new SurveyOptionEntity();
								MyBeanUtils.copyBean2Bean(newOption, option);
								newOption.setId(null);
								newOption.setSurveyid(newSurvey.getId());
								newOption.setOptionextList(null);
								this.surveyOptionService.save(newOption);

								List<SurveyOptionExtEntity> extList = this.surveyOptionExtService.getAllExtByOptionId(option.getId());

								if (extList != null && extList.size() > 0) {
									for (SurveyOptionExtEntity ext : extList) {
										SurveyOptionExtEntity newExt = new SurveyOptionExtEntity();
										MyBeanUtils.copyBean2Bean(newExt, ext);
										newExt.setId(null);
										newExt.setOptionsid(newOption.getId());
										this.surveyOptionExtService.save(newExt);
									}

								}
							}

						}
					}

					copyContentRefService.save(contents, c.getId());// 添加关联引用
				}
			}
		} catch (Exception e) {
			LogUtil.error("内容操作失败",e);
			j.accumulate("isSuccess", true);
			j.accumulate("msg", "操作失败！原因：" + e.getMessage());
			return j.toString();
		}
		j.accumulate("isSuccess", true);
		j.accumulate("msg", message);
		j.accumulate("toUrl",
				"contentsController.do?table&tab=all&contentCatId="
						+ contentCatId);
		String str = j.toString();
		return str;
	}

	/**
	 * 获取文分页列表
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param map
	 * @param memberid
	 *            为－1时查询全部
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageList contentsList(Integer pageSize, Integer pageNo, Map map,
			String memberid, ContentsEntity contens) {
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, pageSize,
				pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, contens, map);
		if (!memberid.equals("-1")) {
			cq.eq("memberid", memberid);
		}
		// 排序条件
		cq.addOrder("id", SortDirection.desc);
		cq.add();
		PageList pageList = getPageList(cq, true);
		return pageList;
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 */
	public void delContentsById(String id) {
		deleteEntityById(ContentsEntity.class, id);
	}

	/**
	 * 增加浏览量
	 * 
	 * @param contentId
	 */
	@Override
	public void addPv(String contentId) {
		String sql = "update cms_content set pv = pv+1 where id = '"
				+ contentId + "'";
		executeSql(sql);
	}

	/**
	 * 通过内容Id获取内容全部信息
	 * 
	 * @param contentId
	 * @return
	 */
	@Override
	public ContentBean loadContentDetail(String contentId) {
		ContentBean contentBean = new ContentBean();
		try {
			ContentsEntity content = get(ContentsEntity.class, contentId);
			MyBeanUtils.copyBeanNotNull2Bean(content, contentBean);
			if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_ARTICLE)) {
				List<ArticleEntity> articleList = findByProperty(
						ArticleEntity.class, "contentid", contentBean.getId());
				contentBean.setArticle(articleList.get(0));
				// 组图分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_PICTUREGROUP)) {
				List<PictureGroupEntity> pictureGroupList = findByProperty(
						PictureGroupEntity.class, "contentid",
						contentBean.getId());
				contentBean.setPictureGroup(pictureGroupList.get(0));
				// 链接分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_LINK)) {
				List<ContentLinkEntity> contentLinkList = findByProperty(
						ContentLinkEntity.class, "contentid",
						contentBean.getId());
				contentBean.setLink(contentLinkList.get(0));
				// 视频分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_VIDEO)) {
				List<ContentVideoEntity> contentVideoList = findByProperty(
						ContentVideoEntity.class, "contentid",
						contentBean.getId());
				contentBean.setVideo(contentVideoList.get(0));
				// 活动分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_ACTIVITY)) {
				List<ActivityEntity> activityList = findByProperty(
						ActivityEntity.class, "contentid", contentBean.getId());
				// 投票分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_VOTE)) {
				List<VoteEntity> voteList = findByProperty(VoteEntity.class,
						"contentid", contentBean.getId());
				contentBean.setVote(voteList.get(0));
				// 访谈分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_INTERVIEW)) {
				List<InterviewEntity> interviewList = findByProperty(
						InterviewEntity.class, "contentid", contentBean.getId());
				// 调查分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_SURVEY)) {
				List<SurveyEntity> surveyList = findByProperty(
						SurveyEntity.class, "contentid", contentBean.getId());
				contentBean.setSurvey(surveyList.get(0));
				// 专题分类
			} else if (contentBean.getClassify().equals(
					ContentClassify.CONTENT_SPECIAL)) {
				List<SpecialEntity> specialList = findByProperty(
						SpecialEntity.class, "contentid", contentBean.getId());
				// 其他类别
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contentBean;
	}

	/**
	 * 获取内容列表
	 * 
	 * @return
	 */
	public List<ContentsEntity> getContentsList(Map params) {
		String status = params.get("constants") + "";
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		if (StringUtils.isNotEmpty(status)) {
			cq.eq("constants", status);
		}
		cq.addOrder("id", SortDirection.desc);
		cq.add();
		List<ContentsEntity> list = new ArrayList<ContentsEntity>();
		list = getListByCriteriaQuery(cq, false);
		return list;
	}

	/**
	 * 保存内容及分类入口
	 * 
	 * @param
	 */
	@Override
	public void saveContent(Map<String, Object> map) {
		ContentsEntity contents = (ContentsEntity) map.get("contents");
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String id = request.getParameter("id");
		String contentCatId = request.getParameter("contentCatId");
		// 模型模块id
		String modelsId = request.getParameter("modelsId");
		// 同时发布的栏目id
		String funVal0 = request.getParameter("funVal0");
		// 内容id
		String contentsId = request.getParameter("contentsId");
		// 栏目
		ContentCatEntity contentcat = get(ContentCatEntity.class, contentCatId);
		String sourceName = contents.getSourceid(); // 来源名称
		if (StringUtils.isNotEmpty(sourceName)) {
			contents.setSourceid(sourceName.substring(0, sourceName.length()));
		}
		SiteEntity site = PlatFormUtil.getSessionSite();
		// 修改
		if (StringUtils.isNotEmpty(contentsId)) {
			ContentsEntity c = get(ContentsEntity.class, contentsId);
			try {
				MyBeanUtils.copyBeanNotNull2Bean(contents, c);
				c.setModified(new Date());// 修改时间
				c.setModifiedby(PlatFormUtil.getSessionUser().getUserName());// 修改人
				c.setId(contentsId);
				saveOrUpdate(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 新增
			contents.setSiteid(site.getId());
			contents.setPathids(contentcat.getPathids());
			contents.setCatid(contentcat.getId());
			contents.setModelid(modelsId);
			// 当前人id
			String userId = "", userName = "";
			if (PlatFormUtil.getSessionUser() != null) {
				userId = PlatFormUtil.getSessionUser().getId();
				userName = PlatFormUtil.getSessionUser().getUserName();
			}
			contents.setCreated(new Date());// 创建时间
			contents.setCreatedby(userName);// 创建人
			contents.setPublishedbyid(userId);// 发布人id
			contents.setSynCatid(funVal0);// 同时发布id
			contents.setCatName(contentcat.getName());// 栏目名
			save(contents);
			contentsId=contents.getId();
		}
		
		//保存拓展字段
		saveContentExtField(contents.getCatid(), request, contentsId);
		
	}

	/**
	 * 保存拓展字段
	 * @param catId
	 * @param request
	 * @param contentsId
	 */
	private void saveContentExtField(String catId,
			HttpServletRequest request, String contentsId) {
		String[] extid=request.getParameterValues("extid");
		String[] field=request.getParameterValues("field");
		String[] dataType=request.getParameterValues("dataType");
		String[] attrName=request.getParameterValues("attrName");
		String[] modelid=request.getParameterValues("extFieldModelid");
		String[] modelItemId=request.getParameterValues("modelItemId");
		String[] attrvalue=request.getParameterValues("attrvalue");
		
		if(extid==null || extid.length==0){
			return;
		}
		
		for (int i = 0; i < attrvalue.length; i++) {
			ModelExtEntity modelExts=new ModelExtEntity();
			modelExts.setChannelId(catId);
			modelExts.setAttrValue(attrvalue[i]);
			modelExts.setDataType(oConvertUtils.getInt(dataType[i], 1));
			modelExts.setAttrName(attrName[i]);
			modelExts.setModelItemId(modelItemId[i]);
			modelExts.setCreatedtime(new Date());//创建时间
			modelExts.setContentId(contentsId);
			modelExts.setAttrToken(field[i]);
			modelExts.setModelId(modelid[i]);
			if(StringUtils.isBlank(extid[i])){
				modelExts.setId(null);
			}else{
				modelExts.setId(extid[i]);
			}
			modelExtService.saveOrUpdate(modelExts);
		}
	}

	
	@Override
	public void saveModelExt(ContentsEntity contents) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		// 扩展字段id
		String modelId = request.getParameter("modelId");
		// 获取自定义字段/值
		List<ModelExtEntity> modelExtList = new ArrayList<ModelExtEntity>();
		if (StringUtils.isNotEmpty(modelId)) {
			String sql = "select modelext.id from cms_model_ext modelext where modelext.model_id='"
					+ modelId + "' and content_id='" + contents.getId() + "'";
			List<Map<String, Object>> mapList = findForJdbc(sql);
			if (mapList.size() > 0) {
				Map<String, Object> map;
				for (int k = 0; k < mapList.size(); k++) {
					String modelextid = "";
					map = mapList.get(k);
					modelextid = map.get("id") + "";
					ModelExtEntity modelExt1 = get(ModelExtEntity.class,
							String.valueOf(modelextid));
					modelExtList.add(modelExt1);
				}
			}
		}
		// 保存参数名称和value
		for (int i = 0; i < modelExtList.size(); i++) {
			ModelExtEntity modelExt = modelExtList.get(i);
			String name = request.getParameter("name" + i);// label
			String input = request.getParameter(i + "");// input
			if (modelExt.getAttrName().equals(name)) {
				modelExt.setAttrValue(input);
				saveOrUpdate(modelExt);
			}
		}
	}

	/**
	 * 在modelExt中保存modelId/attrName
	 * 
	 * @param contents
	 */
	@Override
	public void saveModelItem(ContentsEntity contents) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		// 扩展字段id
		String modelId = request.getParameter("modelId");
		// 获取参数名称
		List<ModelItemEntity> modelItemList = new ArrayList<ModelItemEntity>();
		ModelItemEntity modelItem = new ModelItemEntity();

		if (StringUtils.isNotEmpty(modelId)) {
			modelItemList = findByProperty(ModelItemEntity.class, "modelId",
					String.valueOf(modelId));
		}
		// 在modelExt中保存modelId/attrName
		if (StringUtils.isNotEmpty(modelId)) {
			for (int i = 0; i < modelItemList.size(); i++) {
				modelItem = modelItemList.get(i);
				String name = request.getParameter("name" + i);// label
				String input = request.getParameter(i + "");// input
				if (modelItem.getItemLabel().equals(name)) {
					ModelExtEntity modelExt = new ModelExtEntity();
					modelExt.setAttrName(modelItem.getItemLabel());
					modelExt.setModelId(String.valueOf(modelId));
					modelExt.setContentId(contents.getId());
					modelExt.setModelItemId(modelItem.getId());
					modelExt.setAttrToken(modelItem.getField());
					modelExt.setDataType(modelItem.getDataType());
					modelExt.setAttrValue(input);
					modelExt.setCreatedtime(new Date());// 创建时间
					save(modelExt);
				}
			}
		}
	}

	/**
	 * 保存相关内容
	 * 
	 * @param contents
	 */
	@Override
	public void saveRelateContent(ContentsEntity contents, String temporary,
			String divValue) {
		String[] ulValue = null;
		if (StringUtils.isNotEmpty(divValue)) {
			ulValue = divValue.split(",");
		}
		List<RelateContentEntity> relateContentList = new ArrayList<RelateContentEntity>();
		if (StringUtils.isNotEmpty(temporary)) {
			// 更新保存相关内容表，新增的相关项
			relateContentList = findByProperty(RelateContentEntity.class,
					"contentId", temporary);
		}
		// 保存相关内容
		for (RelateContentEntity relateContent : relateContentList) {
			if (StringUtil.isNotEmpty(ulValue)) {
				for (int i = 0; i < ulValue.length; i++) {
					String[] ul = ulValue[i].split("_");
					if (ul[1].equals(String.valueOf(relateContent.getId()))) {
						relateContent.setRelateOrder(Integer.valueOf(ul[0]));
					}
				}
			}
			relateContent.setContentId(String.valueOf(contents.getId()));
			relateContent.setRelateType(contents.getClassify());
			saveOrUpdate(relateContent);
		}
		if (StringUtil.isNotEmpty(ulValue)) {
			// 已添加的相关项，用于修改排序
			if (StringUtil.isNotEmpty(ulValue)) {
				List<RelateContentEntity> relateContentListT = findByProperty(
						RelateContentEntity.class, "contentId",
						String.valueOf(contents.getId()));
				for (RelateContentEntity relateContent : relateContentListT) {
					for (int i = 0; i < ulValue.length; i++) {
						String[] ul = ulValue[i].split("_");
						if (ul[1].equals(String.valueOf(relateContent.getId()))) {
							relateContent
									.setRelateOrder(Integer.valueOf(ul[0]));
						}
					}
					saveOrUpdate(relateContent);
				}
			}
		}
	}

	/**
	 * 根据id获取文章
	 * 
	 * @param id
	 * @return
	 */
	public ContentsEntity getContensById(String id) {
		ContentsEntity contents = getEntity(ContentsEntity.class, id);
		return contents;
	}

	/**
	 * 根据内容id获取要索引的内容
	 * 
	 * @param contentid
	 * @return
	 */
	public Map<String, Object> getIndexContent(String contentid) {
		ContentsEntity contents = getContensById(contentid);
		String classify = contents.getClassify();
		Map<java.lang.String, Object> contentdataMap = new HashMap<String, Object>();
		if (ContentClassify.CONTENT_ARTICLE.equals(classify)) {
			List<ArticleEntity> contentdataList = findByProperty(
					ArticleEntity.class, "contentid", contentid);
			if (contentdataList != null && contentdataList.size() > 0) {
				contentdataMap.put(contentid, contentdataList.get(0));
			} else {
				contentdataMap.put(contentid, new ArticleEntity());
			}
		} else if (ContentClassify.CONTENT_VIDEO.equals(classify)) {
			List<ContentVideoEntity> videoList = contentVideoService
					.videoList(contentid);
			if (null != videoList && videoList.size() > 0) {
				contentdataMap.put(contentid, videoList.get(0));
			} else {
				contentdataMap.put(contentid, new ContentVideoEntity());
			}
		} else if (ContentClassify.CONTENT_PICTUREGROUP.equals(classify)) {
			List<PictureAloneEntity> picture = pictureGroupService
					.pictureList(contentid);
			if (null != picture && picture.size() > 0) {
				contentdataMap.put(contentid, picture.get(0));
			} else {
				contentdataMap.put(contentid, new PictureAloneEntity());
			}
		}
		return contentdataMap;
	}

	/**
	 * 删除内容
	 * 
	 * @param ids
	 */
	@Override
	public void delContent(String[] ids) {
		for (String id : ids) {
			ContentsEntity contents = getEntity(ContentsEntity.class,
					String.valueOf(id));
			// 删除本地html页面文件
			HttpServletRequest request = ContextHolderUtils.getRequest();
			ContentCatEntity contentCat = get(ContentCatEntity.class,
					contents.getCatid());
			SiteEntity site = getEntity(SiteEntity.class, contents.getSiteid());
			if (site == null) {
				return;
			}
			String sitePath = CmsConstants.getSitePath(site);
			String filepaths = sitePath + contentCat.getPath();
			try {
				contentIllegalService.delContentHtmlFile(contents);
				FileUtils.deleteDirectory(filepaths);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//拓展字段删除
			List<ModelExtEntity> extFieldList=modelExtService.getContentAllExt(id);
			for (int i = 0; i < extFieldList.size(); i++) {
				modelExtService.delete(extFieldList.get(i));
			}
			
			// 删除级联文章
			List<ArticleEntity> articleList = findByProperty(
					ArticleEntity.class, "contentid", contents.getId());
			deleteAllEntitie(articleList);

			// 删除级联组图
			List<PictureGroupEntity> pictureGroupList = findByProperty(
					PictureGroupEntity.class, "contentid", contents.getId());
			for (PictureGroupEntity pictureGroup : pictureGroupList) {
				delete(pictureGroup);
			}
			// 删除级联链接
			List<ContentLinkEntity> contentLinkList = findByProperty(
					ContentLinkEntity.class, "contentid", contents.getId());
			for (ContentLinkEntity contentLink : contentLinkList) {
				delete(contentLink);
			}
			// 删除级联活动
			List<ActivityEntity> activityList = findByProperty(
					ActivityEntity.class, "contentid", contents.getId());
			for (ActivityEntity activity : activityList) {

				// 删除活动表单
				List<ActivityOptionContentEntity> activityOptionContentList = findByProperty(
						ActivityOptionContentEntity.class, "activityids",
						activity.getId());
				if (activityOptionContentList != null) {

					deleteAllEntitie(activityOptionContentList);
				}
				// 删除活动表单
				List<ActivityPeopleEntity> activityPeopleList = findByProperty(
						ActivityPeopleEntity.class, "activityids",
						activity.getId());
				if (activityPeopleList != null) {
					for (ActivityPeopleEntity people : activityPeopleList) {
						List<ActivityOptionExtEntity> activityExeList = findByProperty(
								ActivityOptionExtEntity.class, "logids",
								people.getId());
						if (activityExeList != null) {
							deleteAllEntitie(activityExeList);
						}
						delete(people);
					}
				}

				delete(activity);
			}
			// 删除级联视频
			List<ContentVideoEntity> contentVideoList = findByProperty(
					ContentVideoEntity.class, "contentid", contents.getId());
			for (ContentVideoEntity contentVideo : contentVideoList) {
				delete(contentVideo);
			}
			// 删除级联投票以及选项
			List<VoteEntity> voteList = findByProperty(VoteEntity.class,
					"contentid", contents.getId());
			for (VoteEntity vote : voteList) {
				List<VoteOptionEntity> voteOptionList = findByProperty(
						VoteOptionEntity.class, "voteid",
						String.valueOf(vote.getId()));
				for (VoteOptionEntity voteOption : voteOptionList) {
					delete(voteOption);
				}
				delete(vote);
			}
			// 删除级联访谈
			List<InterviewEntity> interviewList = findByProperty(
					InterviewEntity.class, "contentid", contents.getId());
			for (InterviewEntity interview : interviewList) {
				delete(interview);
			}
			// 删除级联调查
			List<SurveyEntity> surveyList = findByProperty(SurveyEntity.class,
					"contentid", contents.getId());
			for (SurveyEntity survey : surveyList) {
				delete(survey);
			}
			// 删除级联专题
			List<SpecialEntity> specialList = findByProperty(
					SpecialEntity.class, "contentid", contents.getId());
			for (SpecialEntity special : specialList) {
				delete(special);
			}

			List<ContentsMobileEntity> list = contentsMobileService
					.getListByRelevanceContent(contents.getId());// 通过PC稿件获取关联的移动稿件
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					contentsMobileService.delContent(new String[] { list.get(i)
							.getId().toString() });
				}
			}
			delete(contents);
			systemService.addLog("PC内容【" + contents.getTitle() + "】删除成功",
					Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}

	}

	/**
	 * 获取符合下线的内容
	 */
	@Override
	public List<ContentsEntity> getOfflineContent() {
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		cq.lt("noted", new Date());
		cq.in("constants", new String[] { "10", "20", "30", "40", "50" });
		cq.add();
		List<ContentsEntity> list = getListByCriteriaQuery(cq, false);
		return list;
	}
	/**
	 * 发布到其他栏目
	 */
	@Override
	public void saveOtherContent(String contentId, String otherCatIds) {
		ContentsEntity contents=constantsService.getEntity(ContentsEntity.class, contentId);
	
		try {
			String otherCatId[]=otherCatIds.split(",");
			for (int i = 0; i < otherCatId.length; i++) {
				ContentsEntity c = new ContentsEntity();
				MyBeanUtils.copyBean2Bean(c, contents);
				c.setId(null);
				c.setCatid(otherCatId[i]);
				ContentCatEntity cat = contentCatService.getEntity(otherCatId[i]);
				c.setCatName(cat.getName());
				c.setPathids(cat.getPathids());
				constantsService.saveOrUpdate(c);
				staticImpl.staticContentActOnPreview(c);
				// 复制文章
				List<ArticleEntity> articleList = this.findByProperty(
						ArticleEntity.class, "contentid", contents.getId());
				if (articleList.size() != 0) {
					ArticleEntity articles= articleList.get(0);
					ArticleEntity article =new ArticleEntity();
					MyBeanUtils.copyBean2Bean(article, articles);
					article.setId(null);
					article.setContentid(c.getId());
					
					this.saveOrUpdate(article);
				}
				// 复制组图
				List<PictureGroupEntity> pictureList = this.findByProperty(
						PictureGroupEntity.class, "contentid",
						contents.getId());
				if (pictureList.size() != 0) {
					PictureGroupEntity pictures = pictureList.get(0);
					PictureGroupEntity picture =new PictureGroupEntity();
					MyBeanUtils.copyBean2Bean(picture, pictures);
					picture.setId(null);
					picture.setContentid(c.getId());
					
					this.saveOrUpdate(picture);
				}
				// 复制链接
				List<ContentLinkEntity> contentLinkList = this
						.findByProperty(ContentLinkEntity.class,
								"contentid", contents.getId());
				if (contentLinkList.size() != 0) {
					ContentLinkEntity contentLinks = contentLinkList.get(0);
					ContentLinkEntity contentLink =new ContentLinkEntity();
					MyBeanUtils.copyBean2Bean(contentLink, contentLinks);
					contentLink.setId(null);
					contentLink.setContentid(c.getId());
					
					this.saveOrUpdate(contentLink);
				}
				// 复制视频
				List<ContentVideoEntity> contentVideoList = this
						.findByProperty(ContentVideoEntity.class,
								"contentid", contents.getId());
				if (contentVideoList.size() != 0) {
					ContentVideoEntity contentVideos = contentVideoList
							.get(0);
					ContentVideoEntity contentVideo =new ContentVideoEntity();
					MyBeanUtils.copyBean2Bean(contentVideo, contentVideos);
					contentVideo.setId(null);
					
					contentVideo.setContentid(c.getId());
					this.saveOrUpdate(contentVideo);
				}
				// 复制投票
				List<VoteEntity> voteList = this.findByProperty(
						VoteEntity.class, "contentid", contents.getId());
				if (voteList.size() != 0) {
					VoteEntity votes = voteList.get(0);
					VoteEntity vote =new VoteEntity();
					MyBeanUtils.copyBean2Bean(vote, votes);
					vote.setId(null);
					vote.setContentid(c.getId());
					
					this.saveOrUpdate(vote);
				}
				// 复制调查
				List<SurveyEntity> surveyList = this.findByProperty(
						SurveyEntity.class, "contentid", contents.getId());
				if (surveyList.size() != 0) {
					SurveyEntity surveys = surveyList.get(0);
					SurveyEntity survey =new SurveyEntity();
					MyBeanUtils.copyBean2Bean(survey, surveys);
					survey.setId(null);
					survey.setContentid(c.getId());
					
					this.saveOrUpdate(survey);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 权限栏目ID
	 */
	@SuppressWarnings("null")
	@Override
	public String[] catRoleList() {
		List<String> contentCatIdList=contentCatService.getAllIdList(PlatFormUtil.getSessionSiteId());
		
		List<String> catIdList = new ArrayList<String>();
		if (contentCatIdList != null && contentCatIdList.size() > 0) {
			Set<String> pcCatalog = CMSUtil.initPCCatalogPermission();
			if (pcCatalog == null || pcCatalog.size() == 0) {
				contentCatIdList.clear();
				return null;
			}
			
			for (int i = contentCatIdList.size() - 1; i >= 0; i--) {
				if (pcCatalog.contains(contentCatIdList.get(i))) {
					
					catIdList.add(contentCatIdList.get(i));
					
				} else {
					contentCatIdList.remove(i);
				}
			}
			
		}	
		if(catIdList!=null && catIdList.size()>0){
			String[] catId = (String[])catIdList.toArray(new String[catIdList.size()]);//使用了第二种接口，返回值和参数均为结果
			return catId;
		}
		return null;
		

		
	}

	/**
	 * 获取栏目下允许查看的内容总数
	 *
	 * @param catalogIds
	 * @return
	 */
	@Override
	public long getCatalogOpenContentCount(List<String> catalogIds) {

		if (catalogIds == null || catalogIds.size() == 0) {
			return 0;
		}

		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class);
		DetachedCriteria dc = cq.getDetachedCriteria();
		dc.setProjection(Projections.rowCount());
		cq.setDetachedCriteria(dc);

		if (catalogIds != null && catalogIds.size() > 1) {
			cq.in("catid", catalogIds.toArray(new String[]{}));
		} else {
			cq.eq("catid", catalogIds.get(0));
		}
		cq.in("constants", new String[]{ContentStatus.CONTENT_COMMITTED, ContentStatus.CONTENT_PUBLISHED});
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.add();
		List<Long> result = this.commonService.getListByCriteriaQuery(cq, false);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return 0;
	}

	/**
	 * 获取栏目下最新的一条内容
	 *
	 * @param
	 * @return
	 */
	@Override
	public ContentsEntity getCatalogOneChild(String catalogId) {
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, 1, 1, "", "");
		cq.eq("catid", catalogId);
		cq.in("constants", new String[]{ContentStatus.CONTENT_COMMITTED, ContentStatus.CONTENT_PUBLISHED});
		Date date = new Date();
		cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
		cq.le("published", date);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		List<ContentsEntity> result = this.commonService.getListByCriteriaQuery(cq, true);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	/**
	 * 增加赞同数
	 *
	 * @param contentId
	 */
	@Override
	public Integer addSupport(String contentId) {
		String sql = "update cms_content set supportcount = supportcount+1 where id = '"
				+ contentId + "'";
		executeSql(sql);
		ContentsEntity content=commonService.get(ContentsEntity.class,contentId);
		return content.getSupport();
	}
	/**
	 * 增加反对数
	 *
	 * @param contentId
	 */
	@Override
	public Integer addOppose(String contentId) {
		String sql = "update cms_content set opposecount = opposecount+1 where id = '"
				+ contentId + "'";
		executeSql(sql);
		ContentsEntity content=commonService.get(ContentsEntity.class,contentId);
		return content.getOppose();
	}

	/**
	 * 获取赞同数
	 *
	 * @param contentId
	 */
	@Override
	public Integer getSupport(String contentId) {
		String sql = "select supportcount from cms_content where id = '"
				+ contentId + "'";
		executeSql(sql);
		ContentsEntity content=commonService.get(ContentsEntity.class,contentId);
		return content.getSupport();
	}

	/**
	 * 获取反对数
	 *
	 * @param contentId
	 */
	@Override
	public Integer getOppose(String contentId) {
		String sql = "select opposecount from cms_content where id = '"
				+ contentId + "'";
		executeSql(sql);
		ContentsEntity content=commonService.get(ContentsEntity.class,contentId);
		return content.getOppose();
	}

    @Override
    public long calculationCommentCount(String contentId) {
        Criteria criteria = commonService.getSession().createCriteria(CommentaryFrontEntity.class);
        criteria.add(Restrictions.eq("contentid", contentId));
		criteria.add(Restrictions.ne("ischeck", "2"));
        long commentCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
        return commentCount;
    }
}