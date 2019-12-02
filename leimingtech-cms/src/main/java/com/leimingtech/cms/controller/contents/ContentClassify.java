package com.leimingtech.cms.controller.contents;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

/**
 * 内容分类常量类
 * @author zhangxiaoqiang
 * 
 */

public class ContentClassify {

	/** 内容文章分类*/
	public static final String CONTENT_ARTICLE = "1";
	/** 内容文章分类*/
	public static final String CONTENT_ARTICLE_STR = "文章";
	
	/** 内容组图分类*/
	public static final String CONTENT_PICTUREGROUP = "2";
	/** 内容组图分类*/
	public static final String CONTENT_PICTUREGROUP_STR = "组图";
	
	/** 内容链接分类*/
	public static final String CONTENT_LINK = "3";
	/** 内容链接分类*/
	public static final String CONTENT_LINK_STR = "链接";
	
	/** 内容视频分类*/
	public static final String CONTENT_VIDEO = "4";
	/** 内容视频分类*/
	public static final String CONTENT_VIDEO_STR = "视频";
	
	/** 内容活动分类*/
	public static final String CONTENT_ACTIVITY = "5";
	/** 内容活动分类*/
	public static final String CONTENT_ACTIVITY_STR = "活动";
	
	/** 内容投票分类*/
	public static final String CONTENT_VOTE = "6";
	/** 内容投票分类*/
	public static final String CONTENT_VOTE_STR = "投票";
	
	/** 内容访谈分类*/
	public static final String CONTENT_INTERVIEW = "7";
	/** 内容访谈分类*/
	public static final String CONTENT_INTERVIEW_STR = "访谈";
	
	/** 内容调查分类*/
	public static final String CONTENT_SURVEY = "8";
	/** 内容调查分类*/
	public static final String CONTENT_SURVEY_STR = "调查";
	
	/** 内容专题分类*/
	public static final String CONTENT_SPECIAL = "9";
	/** 内容专题分类*/
	public static final String CONTENT_SPECIAL_STR = "专题";
	/** 分类键值对*/
	public static final Map<String, String> map = new HashMap<String, String>();
	
	static {
		map.put(ContentClassify.CONTENT_ARTICLE, ContentClassify.CONTENT_ARTICLE_STR);
		map.put(ContentClassify.CONTENT_PICTUREGROUP, ContentClassify.CONTENT_PICTUREGROUP_STR);
		map.put(ContentClassify.CONTENT_LINK, ContentClassify.CONTENT_LINK_STR);
		map.put(ContentClassify.CONTENT_VIDEO, ContentClassify.CONTENT_VIDEO_STR);
		map.put(ContentClassify.CONTENT_ACTIVITY, ContentClassify.CONTENT_ACTIVITY_STR);
		map.put(ContentClassify.CONTENT_VOTE, ContentClassify.CONTENT_VOTE_STR);
		map.put(ContentClassify.CONTENT_INTERVIEW, ContentClassify.CONTENT_INTERVIEW_STR);
		map.put(ContentClassify.CONTENT_SURVEY, ContentClassify.CONTENT_SURVEY_STR);
		map.put(ContentClassify.CONTENT_SPECIAL, ContentClassify.CONTENT_SPECIAL_STR);
		
	}
	
}
