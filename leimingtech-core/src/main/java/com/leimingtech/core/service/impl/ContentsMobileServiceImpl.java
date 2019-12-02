package com.leimingtech.core.service.impl;

import java.io.File;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.common.ContentMobileClassify;
import com.leimingtech.core.common.Globals;
import com.leimingtech.core.common.MobileChannelConstants;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ArticleEntity;
import com.leimingtech.core.entity.ArticleMobileEntity;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.CommentaryFrontEntity;
import com.leimingtech.core.entity.ContentLinkEntity;
import com.leimingtech.core.entity.ContentLinkMobileEntity;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentVideoMobileEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.InvitationMobileEntity;
import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.entity.PictureAloneMobileEntity;
import com.leimingtech.core.entity.PictureGroupEntity;
import com.leimingtech.core.entity.PictureGroupMobileEntity;
import com.leimingtech.core.entity.SimplespecialContentEntity;
import com.leimingtech.core.entity.SimplespecialEntity;
import com.leimingtech.core.entity.SpecialEntity;
import com.leimingtech.core.entity.SurveyEntity;
import com.leimingtech.core.entity.SurveyLogEntity;
import com.leimingtech.core.entity.SurveyMobileEntity;
import com.leimingtech.core.entity.TSType;
import com.leimingtech.core.entity.VoteEntity;
import com.leimingtech.core.entity.VoteMobileEntity;
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ActivityServiceI;
import com.leimingtech.core.service.ArticleMobileServiceI;
import com.leimingtech.core.service.ArticleServiceI;
import com.leimingtech.core.service.CommentaryFrontServiceI;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentLinkMobileServiceI;
import com.leimingtech.core.service.ContentLinkServiceI;
import com.leimingtech.core.service.ContentVideoMobileServiceI;
import com.leimingtech.core.service.ContentVideoServiceI;
import com.leimingtech.core.service.ContentsMobileServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.InterviewGuestServiceI;
import com.leimingtech.core.service.InvitationMobileServiceI;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.PictureAloneMobileServiceI;
import com.leimingtech.core.service.PictureAloneServiceI;
import com.leimingtech.core.service.PictureGroupMobileServiceI;
import com.leimingtech.core.service.PictureGroupServiceI;
import com.leimingtech.core.service.SimplespecialContentServiceI;
import com.leimingtech.core.service.SpecialServiceI;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.service.SurveyMobileServiceI;
import com.leimingtech.core.service.SurveyServiceI;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.TypeServiceI;
import com.leimingtech.core.service.VoteMobileServiceI;
import com.leimingtech.core.service.VoteOptionServiceI;
import com.leimingtech.core.service.VoteServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;
import com.leimingtech.core.util.TwoDimensionCode;
import com.leimingtech.core.util.oConvertUtils;

/**
 * 移动稿件管理接口实现
 * 
 * @author liuzhen 2015年8月31日 15:53:10
 * 
 */
@Service("contentsMobileService")
@Transactional
public class ContentsMobileServiceImpl implements ContentsMobileServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	/**评论管理接口*/
	@Autowired
	private CommentaryFrontServiceI commentaryFrontService;
	/**跟帖管理接口*/
	@Autowired
	private InvitationMobileServiceI invitationMobileService;
	/**移动组图管理接口*/
	@Autowired
	private PictureGroupMobileServiceI pictureGroupMobileService;
	/**PC组图管理接口*/
	@Autowired
	private PictureGroupServiceI pictureGroupService;
	/**移动组图中图片管理接口*/
	@Autowired
	private PictureAloneMobileServiceI pictureAloneMobileService;
	/**PC组图中图片管理接口*/
	@Autowired
	private PictureAloneServiceI pictureAloneService;
	/**通用类型字典管理接口*/
	@Autowired
	private TypeServiceI typeService;
	/**PC内容管理接口*/
	@Autowired
	private ContentsServiceI contentsService;
	/**PC文章管理接口*/
	@Autowired
	private ArticleServiceI articleService;
	/**移动文章管理接口*/
	@Autowired
	private ArticleMobileServiceI articleMobileService;
	/**移动链接稿件管理接口*/
	@Autowired
	private ContentLinkMobileServiceI contentLinkMobileService;
	/**PC链接稿件管理接口*/
	@Autowired
	private ContentLinkServiceI contentLinsService;
	/**PC视频稿件管理接口*/
	@Autowired
	private ContentVideoServiceI contentVideoService;
	/**移动视频稿件管理接口*/
	@Autowired
	private ContentVideoMobileServiceI contentVideoMobileService;
	/**PC投票稿件管理接口*/
	@Autowired
	private VoteServiceI voteService;
	/**移动投票稿件管理接口*/
	@Autowired
	private VoteMobileServiceI voteMobileService;
	/**PC调查稿件管理接口*/
	@Autowired
	private SurveyServiceI surveyService;
	/**移动调查稿件管理接口*/
	@Autowired
	private SurveyMobileServiceI surveyMobileService;
	/**简单专题管理接口*/
	@Autowired
	private SimplespecialContentServiceI simplespecialContentService;
	/**PC活动稿件管理接口*/
	@Autowired
	private ActivityServiceI activityService;
	/**PC投票选项管理接口*/
	@Autowired
	private VoteOptionServiceI voteOptionService;
	/**PC访谈稿件管理接口*/
	@Autowired
	private InterviewGuestServiceI interviewGuestService;
	/**PC专题管理接口*/
	@Autowired
	private SpecialServiceI specialService;
	
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;
	@Autowired
	private SystemService systemService;
	private String message;
	/**
	 * 内容列表
	 * 
	 * @param all
	 * @param mobileChannelId
	 * @param pageSize
	 * @param pageNo
	 * @return String
	 */
	@Override
	public JSONObject getcontentsMobileList(String all, String mobileChannelId, int pageSize,int pageNo,
			int isTop,String contentIds,SimplespecialEntity simpleSpecial){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.getItems().add(mobileChannelService.setValue());
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				MobileChannelEntity mobileChannel = new MobileChannelEntity();
				if(StringUtil.isNotEmpty(mobileChannelId)){
					mobileChannel = mobileChannelService.getEntity(mobileChannelId);
					if(mobileChannel.getLevels()==0){
						//默认显示第一个频道下的频道分类
						mobileChannelId =  mobileChannel.getMobileChannels().get(0).getId() ;
					}
				}
				//排序条件
				CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class, pageSize, pageNo, "", "");
				if(StringUtil.isNotEmpty(mobileChannelId)){
					cq.eq("catid", String.valueOf(mobileChannelId));
				}
				cq.addOrder("isTop", SortDirection.desc);
				cq.addOrder("published", SortDirection.desc);
				//专题内容列表过滤
				if(StringUtil.isNotEmpty(contentIds)){
					String[] contentIdArr = contentIds.split(",");
					String[] intconId = new String[contentIdArr.length];
					for(int i=0;i<contentIdArr.length;i++){
						intconId[i] = String.valueOf(contentIdArr[i]);
					}
					cq.in("id", intconId);
				}
				//专题下没有内容数据
				if(null!=simpleSpecial&&StringUtil.isEmpty(contentIds)){
					cq.eq("id", "0");
				}
				//头图
				if(isTop!=0){
					cq.eq("isTopPic", 1);
					cq.notEq("listThumbnail", " ");
				}
				cq.add();
				PageList pageList = commonService.getPageList(cq, true);
				List<ContentsMobileEntity> testList = pageList.getResultList();
				int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
				if(pageCount <= 0){
					pageCount = 1;
				}
				
				//所有内容集合
				beanApi.setResultCode("1");
				beanApi.setResultMsg("成功");
				if(null!=mobileChannel.getId()){//频道栏目
					beanApi.setId(String.valueOf(mobileChannel.getId()));
					beanApi.setTitle(mobileChannel.getName());
					beanApi.setType(mobileChannel.getChannelType());
				}else if(null!=simpleSpecial){//专题
					beanApi.setId(String.valueOf(simpleSpecial.getId()));
					beanApi.setTitle(simpleSpecial.getSpecialName());
					beanApi.setContent(simpleSpecial.getSpecialContent());//专题内容摘要
					beanApi.setType(MobileChannelConstants.CHANNEL_NEWS);
					beanApi.setFaceImg(simpleSpecial.getSpecialSlide());//专题头图
					mobileChannel.setChannelType(MobileChannelConstants.CHANNEL_NEWS);
				}
				beanApi.setPageCount(String.valueOf(pageCount));
				beanApi.setPageSize(String.valueOf(pageSize));
//				beanApi.setPageItemsCount(String.valueOf(mobileChannel.getMobileChannels().size()));
				if(MobileChannelConstants.CHANNEL_NEWS.equals(mobileChannel.getChannelType())){
					beanApi.setItems(getContentsMobileList(testList,isTop,pageNo));
				}else{
					beanApi.setItems(getAllContentsMobileList(testList));
				}
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}
		}catch(Exception e1){
			e1.printStackTrace();
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	public List<BeanListApi> getContentsMobileList(List<ContentsMobileEntity> contentsMobileList,int isTop,int pageNo){
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		int size = contentsMobileList.size();
		//头图显示个数/列表
		int num = 3;
		//头图
		if(isTop!=0){
			if(isTop<size){
				for(int i=0;i<isTop;i++){
					ContentsMobileEntity contentsMobile = contentsMobileList.get(i);
					beanList.add(topAndList(contentsMobile));
				}
			}else{
				for(int i=0;i<size;i++){
					ContentsMobileEntity contentsMobile = contentsMobileList.get(i);
					beanList.add(topAndList(contentsMobile));
				}
			}
		//内容列表
		}else{
			//第一页列表显示(pageNo-num)条，除去头图的num条
//			if(pageNo==1){
//				for(int i=num;i<size;i++){
//					ContentsMobileEntity contentsMobile = contentsMobileList.get(i);
//					beanList.add(topAndList(contentsMobile));
//				}
//			}else{
				for(int i=0;i<size;i++){
					ContentsMobileEntity contentsMobile = contentsMobileList.get(i);
					beanList.add(topAndList(contentsMobile));
				}
//			}
		}
		return beanList;
	}
	public BeanListApi topAndList(ContentsMobileEntity contentsMobile){
		HttpServletRequest request = ContextHolderUtils.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CommentaryFrontEntity> commentMobileList = commentaryFrontService.findByProperty(CommentaryFrontEntity.class, "contentid", contentsMobile.getId());
		List<InvitationMobileEntity> invitationMobileList = invitationMobileService.findByProperty(InvitationMobileEntity.class, "contentsId", contentsMobile.getId());
		int inviCount = 0;
		for(InvitationMobileEntity invitationMobile:invitationMobileList){
			if(!"1".equals(invitationMobile.getDisabled())){
				inviCount++;
			}
		}
		BeanListApi item = new BeanListApi();
		item.setId(String.valueOf(contentsMobile.getId()));
		item.setTitle(contentsMobile.getTitle());
		item.setContent(contentsMobile.getDigest());
		if(StringUtil.isNotEmpty(contentsMobile.getPublished())){
			item.setPubDate(sdf.format(contentsMobile.getPublished()));
		}
		
	//新闻详细页/视频详细页
		item.setListUrl(contentsMobile.getUrl());
		
		item.setTopUrl(contentsMobile.getSlideThumbnail());
		
		item.setListImage(contentsMobile.getListThumbnail());
		
		item.setType(mobileChannelService.getContentType(contentsMobile.getClassify()));//内容类型
//		item.setCommentCount(String.valueOf(commentMobileList.size()));//评论条数
		item.setInvitationCount(String.valueOf(inviCount));//跟帖条数
		item.setIsposter(String.valueOf(oConvertUtils.getInt(contentsMobile.getIsposter(),0)));
		//内容标记
		if(StringUtil.isNotEmpty(contentsMobile.getContentMark())){
			TSType type = typeService.getEntity(contentsMobile.getContentMark());
			if(null!=type){
				item.setContentMark(type.getTypecode());
			}
		}
		//内容类型
		item.setContentType(contentsMobile.getClassify());
		if(ContentMobileClassify.CONTENT_PICTUREGROUP.equals(contentsMobile.getClassify())){
			List<PictureGroupMobileEntity> pictureGroupList = pictureGroupMobileService.findByProperty(PictureGroupMobileEntity.class, "contentid", contentsMobile.getId());
			if(pictureGroupList.size()!=0){
				PictureGroupMobileEntity pictureGroup = pictureGroupList.get(0);
				List<PictureAloneMobileEntity> pictureAloneList = pictureAloneMobileService.findByProperty(PictureAloneMobileEntity.class, "picturegroupid", String.valueOf(pictureGroup.getId()));
				int n = pictureAloneList.size();
				String pictureUrl = "";
				List<Map<String,Object>> allDetailPicture=new ArrayList<Map<String,Object>>();
				for(int i=0;i<n;i++){
					PictureAloneMobileEntity pictureAlone = pictureAloneList.get(i);
					if(i==n-1){
						pictureUrl += pictureAlone.getPictureUrl();
					}else{
						pictureUrl += pictureAlone.getPictureUrl()+",";
					}
					Map<String,Object> m=new HashMap<String, Object>();
					m.put("pictureUrl", pictureAlone.getPictureUrl());
					if(org.apache.commons.lang3.StringUtils.isEmpty(pictureAlone.getPictureMessage())){
						m.put("pictureMessage", contentsMobile.getDigest());
					}else{
						m.put("pictureMessage", pictureAlone.getPictureMessage());
					}
					allDetailPicture.add(m);
				}
				item.setAllDetailPicture(allDetailPicture);
				item.setPictureAll(pictureUrl);
			}
		}
		return item;
	}
	/**
	 * 搜索内容列表
	 * @param all
	 * @param key
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@Override
	public JSONObject getSearchContentList(String all, String key, int pageSize,int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.getItems().add(mobileChannelService.setValue());
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				List<MobileChannelEntity> mobileChannelList = mobileChannelService.getRootMobileChannel();
				MobileChannelEntity mobileChannel = new MobileChannelEntity();
				for(MobileChannelEntity mobileChannel1:mobileChannelList){
					if(mobileChannel1.getName().contains("新闻")&&mobileChannel1.getChannelType().equals("news")){
						mobileChannel = mobileChannel1;
					}
				}
				//排序条件
				CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class, pageSize, pageNo, "", "");
				if(StringUtil.isNotEmpty(key)){
					key = URLDecoder.decode(key,"utf-8");
					cq.or(Restrictions.like("title", "%"+key+"%"), Restrictions.like("digest", "%"+key+"%"));
				}
				cq.eq("isposter", 0);
				cq.addOrder("published", SortDirection.desc);
				cq.add();
				PageList pageList = commonService.getPageList(cq, true);
				List<ContentsMobileEntity> testList = pageList.getResultList();
				int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
				if(pageCount <= 0){
					pageCount = 1;
				}
				
				//所有内容集合
				beanApi.setId(String.valueOf(mobileChannel.getId()));
				beanApi.setTitle(mobileChannel.getName());
				beanApi.setResultCode("1");
				beanApi.setResultMsg("成功");
				beanApi.setPageCount(String.valueOf(pageCount));
				beanApi.setPageSize(String.valueOf(pageSize));
				beanApi.setType(mobileChannel.getChannelType());
//				beanApi.setPageItemsCount(String.valueOf(mobileChannel.getMobileChannels().size()));
				beanApi.setItems(getAllContentsMobileList(testList));
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				
			}
		}catch(Exception e1){
			e1.printStackTrace();
			beanApi.setResultCode("0");
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	@Override
	public List<BeanListApi> getAllContentsMobileList(
			List<ContentsMobileEntity> contentsMobileList) {
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		for(ContentsMobileEntity contentsMobile:contentsMobileList){
			beanList.add(topAndList(contentsMobile));
		}
		return beanList;
	}
	@Override
	public JSONObject isCollect(String contentId,String isCollect) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try {
			if(StringUtil.isNotEmpty(contentId)){
				ContentsMobileEntity contentMobile = getEntity(contentId );
				if("1".equals(isCollect)){
					contentMobile.setIscollect(isCollect);
					this.saveOrUpdate(contentMobile);
					beanApi.setResultMsg("收藏成功");
					beanApi.setResultCode("1");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
				if("0".equals(isCollect)){
					contentMobile.setIscollect(isCollect);
					this.saveOrUpdate(contentMobile);
					beanApi.setResultMsg("取消收藏");
					beanApi.setResultCode("1");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	
	@Override
	public void delContent(String[] ids) {
		if(ids==null || ids.length ==0 )
			return;
		
		for(String id:ids){
			ContentsMobileEntity contentsMobile = getEntity(id);
			this.delete(contentsMobile);
		}
		
	}
	
	@Override
	public JSONObject savePushMobile(String contentId, String mobileChannelId,String classify) {
		JSONObject j=new JSONObject();
		ContentsEntity content = new ContentsEntity();
		ContentsMobileEntity contentsMobile = new ContentsMobileEntity();
		MobileChannelEntity mobileChannel = mobileChannelService.getEntity(mobileChannelId );
		try {
			if(StringUtil.isNotEmpty(contentId)){
				
				CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class);
				cq.eq("relevanceid", String.valueOf(mobileChannelId));
				cq.eq("catid", contentId);
				cq.add();
				List<ContentsMobileEntity> old=commonService.getListByCriteriaQuery(cq, false);
				//指定频道中如果已经存在当前内容将不再执行
				
				if (old != null && old.size() > 0) {
					return j.accumulate("isSuccess", false).accumulate("msg", mobileChannel.getName() + "频道下已经存在当前稿件，不能重复操作");
				}
				
				
				content = contentsService.getEntity(ContentsEntity.class, contentId );
				MyBeanUtils.copyBean2Bean(contentsMobile,content);
				contentsMobile.setId(null);
				contentsMobile.setCatid( mobileChannelId );
				contentsMobile.setListThumbnail(content.getThumb());
				contentsMobile.setPathids(mobileChannel.getPathids());
				contentsMobile.setRelevanceid( contentId );
				saveOrUpdate(contentsMobile);
			}
			if(null!=mobileChannel){
				
				if(classify.equals(ContentMobileClassify.CONTENT_ARTICLE)){
					//文章
					List<ArticleEntity> articleList = articleService.findByProperty(ArticleEntity.class, "contentid",  contentId );
					ArticleEntity article = new ArticleEntity();
					if(articleList.size()!=0){
						article = articleList.get(0);
						ArticleMobileEntity articleMobile = new ArticleMobileEntity();
						MyBeanUtils.copyBean2Bean(articleMobile,article);
						articleMobile.setId(null);
						articleMobile.setContentid(contentsMobile.getId().toString());
						articleMobileService.saveOrUpdate(articleMobile);
					}
					
				}
				if(classify.equals(ContentMobileClassify.CONTENT_LINK)){
					//链接
					List<ContentLinkEntity> linkList = contentLinsService.findByProperty(ContentLinkEntity.class, "contentid",  contentId );
					ContentLinkEntity link = new ContentLinkEntity();
					if(linkList.size()!=0){
						link = linkList.get(0);
						ContentLinkMobileEntity linkMobile = new ContentLinkMobileEntity();
						MyBeanUtils.copyBean2Bean(linkMobile,link);
						linkMobile.setId(null);
						linkMobile.setContentid(contentsMobile.getId());
						contentLinkMobileService.saveOrUpdate(linkMobile);
					}
				}
				if(classify.equals(ContentMobileClassify.CONTENT_PICTUREGROUP)){
					//组图
					List<PictureGroupEntity> pictureGroupList = pictureGroupService.findByProperty(PictureGroupEntity.class, "contentid",  contentId );
					PictureGroupEntity pictureGroup = new PictureGroupEntity();
					if(pictureGroupList.size()!=0){
						pictureGroup = pictureGroupList.get(0);
						PictureGroupMobileEntity pictureGroupMobile = new PictureGroupMobileEntity();
						MyBeanUtils.copyBean2Bean(pictureGroupMobile, pictureGroup);
						pictureGroupMobile.setId(null);
						pictureGroupMobile.setContentid(contentsMobile.getId());
						pictureGroupMobileService.saveOrUpdate(pictureGroupMobile);

						String groupId = pictureGroupList.get(0).getId();
						List<PictureAloneEntity> aloneList = pictureAloneService.findByProperty(PictureAloneEntity.class, "picturegroupid", groupId + "");
						if (aloneList != null && aloneList.size() > 0) {
							for (int i = 0; i < aloneList.size(); i++) {
								PictureAloneMobileEntity newMobile = new PictureAloneMobileEntity();
								MyBeanUtils.copyBean2Bean(newMobile, aloneList.get(i));
								newMobile.setId(null);
								newMobile.setPicturegroupid(pictureGroupMobile.getId() + "");
								pictureAloneMobileService.saveOrUpdate(newMobile);
							}
						}
					}
				}
				if(classify.equals(ContentMobileClassify.CONTENT_VIDEO)){
					//视频
					List<ContentVideoEntity> videoList = contentVideoService.findByProperty(ContentVideoEntity.class, "contentid",  contentId );
					ContentVideoEntity video = new ContentVideoEntity();
					if(videoList.size()!=0){
						video = videoList.get(0);
						ContentVideoMobileEntity videoMobile = new ContentVideoMobileEntity();
						MyBeanUtils.copyBean2Bean(videoMobile,video);
						videoMobile.setId(null);
						videoMobile.setContentid(contentsMobile.getId());
						contentVideoMobileService.saveOrUpdate(videoMobile);
					}
				}
				if(classify.equals(ContentMobileClassify.CONTENT_VOTE)){
					//投票
					List<VoteEntity> voteList = voteService.findByProperty(VoteEntity.class, "contentid",  contentId );
					VoteEntity vote = new VoteEntity();
					if(voteList.size()!=0){
						vote = voteList.get(0);
						VoteMobileEntity voteMobile = new VoteMobileEntity();
						MyBeanUtils.copyBean2Bean(voteMobile,vote);
						voteMobile.setId(null);
						voteMobile.setVoteid(vote.getId()+"");
						voteMobile.setContentid(contentsMobile.getId());
						voteMobile.setVoteOptionList(null);
						voteMobileService.saveOrUpdate(voteMobile);
					}
				}
				if(classify.equals(ContentMobileClassify.CONTENT_SURVEY)){
					//调查
					SurveyEntity survey = new SurveyEntity();
					List<SurveyLogEntity> surveyLogList = new ArrayList<SurveyLogEntity>();
					List<SurveyEntity> surveyList = surveyService.findByProperty(SurveyEntity.class,"contentid",  contentId );
					if(surveyList.size()!=0){
						survey = surveyList.get(0);
						SurveyMobileEntity surveyMobile = new SurveyMobileEntity();
						MyBeanUtils.copyBean2Bean(surveyMobile,survey);
						surveyMobile.setId(null);
						surveyMobile.setContentid(contentsMobile.getId()+"");
						surveyMobile.setSurveyid(survey.getId()+"");
						surveyMobile.setOptionList(null);
						surveyMobileService.saveOrUpdate(surveyMobile);
					}
				}
			}
			//发布静态页
			staticMobileImpl.staticMobileArticle(PlatFormUtil.getSessionSite(), contentsMobile, mobileChannel);
			//生成二维码
			encode(contentsMobile.getId()+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j.accumulate("isSuccess", true);
		
	}
	@Override
	public void saveOrDel(String contentId, String specialids) {
		//查询该文章ID下的所有 专题ID
		String[] speconids = specialids.split(",,,"); 
		String hql = "from SimplespecialContentEntity as sc where sc.contentid ='"+contentId+"'  " ;
		List<SimplespecialContentEntity> specialidsAlllist=simplespecialContentService.findByQueryString(hql);
		Map<String,Object> mapsql = new HashMap<String, Object>();
		for (int i = 0; i < specialidsAlllist.size(); i++) {
			mapsql.put(specialidsAlllist.get(i).getSimplespecialid()+"", specialidsAlllist.get(i));
		}
		//添加新的ID
		for (String speconid : speconids) {
			SimplespecialContentEntity entity = (SimplespecialContentEntity) mapsql.get(speconid);
			if(entity!=null){
				mapsql.remove(speconid);
				specialidsAlllist.remove(entity);
			}else{
				entity = new SimplespecialContentEntity();
				entity.setContentid(String.valueOf(contentId));
				entity.setSimplespecialid(String.valueOf(speconid));
				entity.setCreatedtime(new Date());
				simplespecialContentService.save(entity);
			}
		}
		//去删除的ID
		for (SimplespecialContentEntity entity : specialidsAlllist) {
			simplespecialContentService.delete(entity);
		}
		
	}

	/**
	 * 生成内容二维码
	 * @param contents
	 * @throws Exception 
	 */
	public void encode(String contentId) throws Exception{
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String ip = oConvertUtils.getIp();//192.168.1.100
		String deal = request.getScheme();//http
		ContentsMobileEntity contents = getEntity(contentId);
		String fileNamePath = CmsConstants.getUploadFilesPath(PlatFormUtil.getSessionSiteId())+"/upload/twocode/mobile/";
		String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
		org.apache.commons.io.FileUtils.forceMkdir(new File(fileDir + "/"));
		String imgPath = fileNamePath+contents.getTitle()+"_"+contents.getId()+".png";  
		contents.setTwoCode("/upload/twocode/mobile/"+contents.getTitle()+"_"+contents.getId()+".png");
		saveOrUpdate(contents);
        String encoderContent = deal+"://"+ip+contents.getUrl();  
        TwoDimensionCode handler = new TwoDimensionCode();  
        handler.encoderQRCode(encoderContent, imgPath, "png");  
        String decoderContent = handler.decoderQRCode(imgPath);  
        System.out.println("解析结果如下：");  
        System.out.println(decoderContent);  
	}
	
	/**
	 * 增加浏览量
	 * 
	 * @param contentId
	 */
	@Override
	public void addPv(String contentId) {
		String sql = "update cm_content set pv = pv+1 where id = '" + contentId + "'";
		commonService.executeSql(sql);
	}
	
	/**
	 * 保存移动稿件
	 * 
	 * @param mobile
	 * @return
	 */
	public String save(ContentsMobileEntity mobile) {
		return (String)commonService.save(mobile);
	}
	
	/**
	 * 更新移动稿件
	 * 
	 * @param mobile
	 */
	@Override
	public void saveOrUpdate(ContentsMobileEntity mobile) {
		commonService.saveOrUpdate(mobile);
	}
	
	/**
	 * 通过id获取移动稿件
	 * 
	 * @param id
	 *            移动稿件id
	 * @return
	 */
	@Override
	public ContentsMobileEntity getEntity(String id) {
		return commonService.getEntity(ContentsMobileEntity.class , id);
	}
	
	/**
	 * 获取分页后的移动稿件数据集
	 * 
	 * @param mobile
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return mobileList移动稿件数据集 pageCount总页数
	 */
	@Override
	public Map<String , Object> getPageList(ContentsMobileEntity mobile , Map param , int pageSize , int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class , pageSize , pageNo , "" , "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq , mobile , param);
		
		cq.addOrder("isTop", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq , true);
		List<ContentsMobileEntity> resultList = pageList.getResultList();
		
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0) {
			pageCount = 1;
		}
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("mobileList" , resultList);
		m.put("pageCount" , pageCount);
		return m;
	}
	
	/**
	 * 删除移动稿件
	 * 
	 * @param mobile
	 */
	@Override
	public void delete(ContentsMobileEntity mobile) {
		if (mobile == null)
			return;
		
		String contentsMobileId = mobile.getId();//移动稿件id
		
		//删除级联文章
		List<ArticleMobileEntity> articleList = articleMobileService.findByProperty(ArticleMobileEntity.class, "contentid",  contentsMobileId );
		mobile.setArticleList(articleList);
		//删除级联组图
		List<PictureGroupMobileEntity> pictureGroupList = pictureGroupMobileService.findByProperty(PictureGroupMobileEntity.class, "contentid", contentsMobileId);
		for(PictureGroupMobileEntity pictureGroup:pictureGroupList){
			pictureGroupMobileService.delete(pictureGroup);
		}
		//删除级联链接
		List<ContentLinkMobileEntity> contentLinkList = contentLinkMobileService.findByProperty(ContentLinkMobileEntity.class, "contentid", contentsMobileId);
	
		for(ContentLinkMobileEntity contentLink:contentLinkList){
			contentLinkMobileService.delete(contentLink);
		}
		//删除级联活动
		List<ActivityEntity> activityList = activityService.findByProperty(ActivityEntity.class, "contentid", contentsMobileId);
	
		for(ActivityEntity activity:activityList){
			activityService.delete(activity);
		}
		
		//删除级联视频
		List<ContentVideoMobileEntity> contentVideoList = contentVideoMobileService.findByProperty(ContentVideoMobileEntity.class, "contentid", contentsMobileId);
		for(ContentVideoMobileEntity contentVideo:contentVideoList){
			contentVideoMobileService.delete(contentVideo);
		}
		//删除级联投票以及选项
		List<VoteEntity> voteList = voteService.findByProperty(VoteEntity.class, "contentid", contentsMobileId);
		for(VoteEntity vote:voteList){
			List<VoteOptionEntity> voteOptionList = voteOptionService.findByProperty(VoteOptionEntity.class, "voteid",  vote.getId() );
			for(VoteOptionEntity voteOption:voteOptionList){
				voteOptionService.delete(voteOption);
			}
			voteService.delete(vote);
		}
		/*//删除级联访谈
		List<InterviewEntity> interviewList = interviewGuestService.findByProperty(InterviewEntity.class, "contentid", contentsMobileId);
		for(InterviewEntity interview:interviewList){
			interviewGuestService.delete(interview);
		}*/
		//删除级联调查
		List<SurveyEntity> surveyList = surveyService.findByProperty(SurveyEntity.class, "contentid", contentsMobileId);
		for(SurveyEntity survey:surveyList){
			surveyService.delete(survey);
		}
		//删除级联专题
		SpecialEntity special = specialService.findByContentId(contentsMobileId);
		specialService.delete(special);
		commonService.delete(mobile);
		systemService.addLog("移动内容【"+mobile.getTitle()+"】\t删除成功" , Globals.Log_Leavel_INFO , Globals.Log_Type_DEL);		
		
	}
	
	/**
	 * 通过频道删除包含稿件
	 * 
	 * @param id
	 */
	@Override
	public void deleteByCat(String id) {
		List<ContentsMobileEntity> contentsMobiles = getListByCat(id);
		if (contentsMobiles != null && contentsMobiles.size() > 0) {
			for (ContentsMobileEntity contentsMobile : contentsMobiles) {
				this.delete(contentsMobile);
			}
		}
	}
	
	/**
	 * 通过频道获取所有包含的稿件
	 * 
	 * @param id
	 *            频道id
	 * @return
	 */
	@Override
	public List<ContentsMobileEntity> getListByCat(String id) {
		CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class);
		cq.eq("catid", id);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.addOrder("isTop", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
	/***
	 * 通过PC稿件获取关联的移动稿件
	 */
	@Override
	public List<ContentsMobileEntity> getListByRelevanceContent(String id) {
		CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class);
		cq.eq("relevanceid", id);
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.addOrder("isTop", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}
	
	/**
	 * 根据多条件获取移动稿件
	 * 
	 * @param publishedEnd 发布结束时间
	 * @param publishedStart 发布开始时间
	 * @param tab tab=all/alone,分别表示显示全部/当前栏目下的所有内容
	 * @param mobileChannel 频道
	 * @param contentsMobile 移动稿件(此实体所有值在这里都是查询条件)
	 * @param param
	 * @param pageSize 每页条数
	 * @param pageNo 当前页码
	 * @return
	 */
	@Override
	public Map<String,Object> getPageList(String publishedEnd,
			String publishedStart, String tab, MobileChannelEntity mobileChannel,
			ContentsMobileEntity contentsMobile, Map param, int pageSize,
			int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class , pageSize , pageNo , "" , "");
		
		HqlGenerateUtil.installHql(cq , contentsMobile , param);
		
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		
		// 查询条件组装器
		if(null!=mobileChannel&&StringUtils.isNotEmpty(mobileChannel.getId()) && !"-1".equals(mobileChannel.getId())) {
			cq.eq("catid", mobileChannel.getId());
		}
		//发布时间
		if(StringUtil.isNotEmpty(publishedStart)&&StringUtil.isNotEmpty(publishedEnd)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				cq.gt("published", sdf.parse(publishedStart+" 00:00:00"));
				cq.lt("published", sdf.parse(publishedEnd+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		cq.addOrder("isTop", SortDirection.desc);
		cq.addOrder("isTopPic", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
	
		cq.add();
		// 查询条件组装器
		// 排序条件
		PageList pageList = commonService.getPageList(cq , true);
		
		List<ContentsMobileEntity> resultList = pageList.getResultList();
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0) {
			pageCount = 1;
		}
		
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("contentMobileList" , resultList);
		m.put("pageCount" , pageCount);
		return m;
	}
	@Override
	public Map<String, Object> getPageListByClassify(String classify1,
			ContentsMobileEntity contentsMobile, Map param, int pageSize,
			int pageNo) {
		
		CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class , pageSize , pageNo , "" , "");
		
		HqlGenerateUtil.installHql(cq , contentsMobile , param);
		
		if(!"0".equals(classify1)) {
			cq.eq("classify" , classify1);
		}
		
		cq.addOrder("isTop", SortDirection.desc);
		cq.addOrder("isTopPic", SortDirection.desc);
		cq.addOrder("created", SortDirection.desc);
		cq.addOrder("published", SortDirection.desc);
		cq.add();
		
		PageList pageList = commonService.getPageList(cq , true);
		
		List<ContentsMobileEntity> resultList = pageList.getResultList();
		for(ContentsMobileEntity content : resultList) {
			MobileChannelEntity mobileChannel = mobileChannelService.getEntity(content.getCatid());
			if(mobileChannel != null) {
				content.setChannelName(mobileChannel.getName());
			}
		}
		int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0) {
			pageCount = 1;
		}
		
		Map<String , Object> m = new HashMap<String , Object>();
		m.put("contentMobileList" , resultList);
		m.put("pageCount" , pageCount);
		return m;
	}
	 
}