package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.ContentClassify;
import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ArticleMobileEntity;
import com.leimingtech.core.entity.ContentLinkMobileEntity;
import com.leimingtech.core.entity.ContentVideoMobileEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.entity.InterviewEntity;
import com.leimingtech.core.entity.InterviewGuestEntity;
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.entity.PictureGroupEntity;
import com.leimingtech.core.entity.SpecialEntity;
import com.leimingtech.core.entity.SurveyEntity;
import com.leimingtech.core.entity.SurveyMobileEntity;
import com.leimingtech.core.entity.VoteMobileEntity;
import com.leimingtech.core.entity.VoteOptionEntity;
import com.leimingtech.core.service.IArticleMobileTagMng;

@Service("articleMobileTagMng")
@Transactional
public class ArticleMobileTagMng extends CommonServiceImpl implements IArticleMobileTagMng {

	@Override
	public Map<String, Object> getContent(ContentsMobileEntity content) {
		if (content == null) {
			return null;
		}
		String contentid = content.getId()+"";
		String modelType = content.getClassify();

		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentF", content);
		m.put("contentidF", content.getId());

		if (ContentClassify.CONTENT_ACTIVITY.equals(modelType)) {
			// 活动
			m.putAll(getActivity(contentid));
		}

		if (ContentClassify.CONTENT_ARTICLE.equals(modelType)) {
			// 文章
			m.putAll(getArticleData(contentid));
		}

		if (ContentClassify.CONTENT_INTERVIEW.equals(modelType)) {
			// 访谈
			m.putAll(getInterview(contentid));
		}

		if (ContentClassify.CONTENT_LINK.equals(modelType)) {
			// 链接
			m.putAll(getLink(contentid));
		}

		if (ContentClassify.CONTENT_PICTUREGROUP.equals(modelType)) {
			// 组图
			m.putAll(getPicturegroup(contentid));
		}

		if (ContentClassify.CONTENT_SPECIAL.equals(modelType)) {
			// 专题
			m.putAll(getSpecial(contentid));
		}

		if (ContentClassify.CONTENT_SURVEY.equals(modelType)) {
			// 调查
			m.putAll(getSurvey(contentid));
		}

		if (ContentClassify.CONTENT_VIDEO.equals(modelType)) {
			// 视频
			m.putAll(getVideoData(contentid));
		}

		if (ContentClassify.CONTENT_VOTE.equals(modelType)) {
			// 投票
			m.putAll(getVote(contentid));
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
	private Map<String, Object> getArticleData(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<ArticleMobileEntity> articleList = findByProperty(ArticleMobileEntity.class, "contentid", contentid);
		if (articleList != null && articleList.size() > 0) {
			ArticleMobileEntity article = articleList.get(0);
			article.setContent(replaceWapStr(article.getContent()));
			m.put("articleDataF", article);
		}else{
			m.put("articleDataF", new ArticleMobileEntity());
		}
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
	 * 视频模型
	 * 
	 * @param articleid
	 * 
	 * @return
	 */
	private Map<String, Object> getVideoData(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<ContentVideoMobileEntity> videoList = findByProperty(ContentVideoMobileEntity.class, "contentid", contentid);
		if (videoList != null && videoList.size() > 0) {
			ContentVideoMobileEntity video = videoList.get(0);
			m.put("videoF", video);
		}else{
			m.put("videoF", new ContentVideoMobileEntity());
		}
		return m;
	}

	/**
	 * 链接模型
	 * 
	 * @param contentid
	 * @return
	 */
	private Map<String, Object> getLink(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<ContentLinkMobileEntity> linkList = findByProperty(ContentLinkMobileEntity.class, "contentid", contentid);
		if (linkList != null && linkList.size() > 0) {
			m.put("linkF", linkList.get(0));
		}else{
			m.put("linkF", new ContentLinkMobileEntity());
		}
		return m;
	}

	/**
	 * 组图模型
	 * 
	 * @param contentid
	 * @return
	 */
	private Map<String, Object> getPicturegroup(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<PictureGroupEntity> pictureGroupList = findByProperty(PictureGroupEntity.class, "contentid", contentid);
		if (pictureGroupList != null && pictureGroupList.size() > 0) {
			m.put("pictureGroupF", pictureGroupList.get(0));

			List<PictureAloneEntity> pictureAloneList = findByProperty(PictureAloneEntity.class, "picturegroupid", pictureGroupList.get(0)
					.getId().toString());
			if (pictureAloneList != null && pictureAloneList.size() > 0) {
				m.put("pictureAloneListF", pictureAloneList);
			}else{
				m.put("pictureAloneListF", new ArrayList<PictureAloneEntity>());
			}
		}else{
			m.put("pictureAloneListF", new ArrayList<PictureAloneEntity>());
			m.put("pictureGroupF", new PictureGroupEntity());
		}
		return m;
	}

	/**
	 * 专题模型
	 * 
	 * @param contentid
	 * @return
	 */
	private Map<String, Object> getSpecial(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<SpecialEntity> specialList = findByProperty(SpecialEntity.class, "contentid", contentid);
		if (specialList != null && specialList.size() > 0) {
			m.put("specialF", specialList.get(0));
		}else{
			m.put("specialF", new SpecialEntity());
		}
		return m;
	}

	/**
	 * 调查模型
	 * 
	 * @param contentid
	 * @return
	 */
	private Map<String, Object> getSurvey(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<SurveyMobileEntity> surveyMObileList = findByProperty(SurveyMobileEntity.class, "contentid", contentid);
		if (surveyMObileList != null && surveyMObileList.size() > 0) {
			List<SurveyEntity> surveyList = findByProperty(SurveyEntity.class, "id", surveyMObileList.get(0).getSurveyid());
			if (surveyList != null && surveyList.size() > 0) {
				m.put("surveyF", surveyList.get(0));
			}else{
				m.put("surveyF", new SurveyEntity());
			}
		}else{
			m.put("surveyF", new SurveyEntity());
		}
		return m;
	}

	/**
	 * 投票模型
	 * 
	 * @param contentid
	 * @return
	 */
	private Map<String, Object> getVote(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<VoteMobileEntity> voteList = findByProperty(VoteMobileEntity.class, "contentid", contentid);
		if (voteList != null && voteList.size() > 0) {
			VoteMobileEntity vote = voteList.get(0);
			m.put("voteF", vote);
			m.put("sysdate",new Date());
			List<VoteOptionEntity> voteOptionList = findByProperty(VoteOptionEntity.class, "voteid", vote.getVoteid().toString());
			if (voteOptionList != null && voteOptionList.size() > 0) {
				m.put("voteOptionListF", voteOptionList);
			}else{
				m.put("voteOptionListF", new ArrayList<VoteOptionEntity>());
			}
		}else{
			m.put("voteF", new VoteMobileEntity());
			m.put("voteOptionListF", new ArrayList<VoteOptionEntity>());
		}
		return m;
	}

	/**
	 * 访谈模型
	 * 
	 * @param contentid
	 * @return
	 */
	private Map<String, Object> getInterview(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<InterviewEntity> interviewList = findByProperty(InterviewEntity.class, "contentid", contentid);
		if (interviewList != null && interviewList.size() > 0) {
			m.put("interviewF", interviewList.get(0));

			List<InterviewGuestEntity> interviewGuestList = findByProperty(InterviewGuestEntity.class, "interviewid", interviewList.get(0)
					.getId().toString());
			if (interviewGuestList != null && interviewGuestList.size() > 0) {
				m.put("interviewGuestListF", interviewGuestList);
			}else{
				m.put("interviewGuestListF", new ArrayList<InterviewGuestEntity>());
			}
		}else{
			m.put("interviewF", new InterviewEntity());
			m.put("interviewGuestListF", new ArrayList<InterviewGuestEntity>());
		}
		return m;
	}

	/**
	 * 活动模型
	 * 
	 * @param contentid
	 * 
	 * @return
	 */
	private Map<String, Object> getActivity(String contentid) {
		Map<String, Object> m = new HashMap<String, Object>();

		List<ActivityEntity> activityList = findByProperty(ActivityEntity.class, "contentid", contentid);
		if (activityList != null && activityList.size() > 0) {
			m.put("activityF", activityList.get(0));
		}else{
			m.put("activityF", new ActivityEntity());
		}
		return m;
	}
}
