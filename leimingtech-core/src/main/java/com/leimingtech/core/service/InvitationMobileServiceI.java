package com.leimingtech.core.service;

import net.sf.json.JSONObject;

public interface InvitationMobileServiceI extends CommonService{

	/**
	 * 跟帖提交
	 * @param contentId
	 * @param content
	 * @return String
	 */
	public JSONObject saveInvitation(String all,String userId,String contentId,String content);
	public JSONObject saveInvitationWap(String all,String userId,String contentId,String content);
	/**
	 * 跟帖列表
	 * @param all
	 * @param contentsMobileId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject getInvitationMobileListWap(String all,String contentsMobileId,String userId,int pageSize,int pageNo);
	/**
	 * 跟帖列表
	 * @param all
	 * @param contentsMobileId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject getInvitationMobileList(String all,String contentsMobileId,String userId,int pageSize,int pageNo);
	
	/**
	 * 所有内容跟帖列表
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject getInvitationList(int pageSize, int pageNo);
	/**
	 * 根据跟帖id更新是否通过审核
	 * @param invitationId
	 * @param status
	 * @return
	 */
	public JSONObject updateStatus(String contentsId,String mobileChannelId,String invitationId,String status);
}
