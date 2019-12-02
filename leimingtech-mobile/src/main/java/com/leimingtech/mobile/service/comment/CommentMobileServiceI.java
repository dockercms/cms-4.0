package com.leimingtech.mobile.service.comment;

import net.sf.json.JSONObject;

import com.leimingtech.core.service.CommonService;

public interface CommentMobileServiceI extends CommonService{
	/**
	 * 评论列表
	 * @param all
	 * @param contentsMobileId
	 * @param pageSize
	 * @param pageNo
	 */
	public JSONObject getCommentMobileList(String all,String contentsMobileId,String userId,int pageSize,int pageNo);
	/**
	 * 评论提交
	 * @param contentsMobileId
	 * @param title
	 * @param content
	 * @return string
	 */
	public JSONObject saveComment(String all,String contentsMobileId,String title,String content,String userId);
}
