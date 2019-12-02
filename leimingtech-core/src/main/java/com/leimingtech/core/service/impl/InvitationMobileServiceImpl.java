package com.leimingtech.core.service.impl;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.*;
import com.leimingtech.core.entity.member.MemberEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.*;
import com.leimingtech.core.util.oConvertUtils;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("invitationMobileService")
@Transactional
public class InvitationMobileServiceImpl extends CommonServiceImpl implements InvitationMobileServiceI {
	
	@Autowired
	private ContentsMobileServiceI contentsMobileService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private MemberServiceI memberService;
	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;

	/**
	 * 跟帖提交
	 * @param contentId
	 * @param content
	 * @return String
	 */
	@Override
	public JSONObject saveInvitation(String all,String userId,String contentId, String content){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.setResultCode("1");
				beanApi.setResultMsg("成功");
				beanApi.setListUrl("/front/commentMobileApi/loadCommentList.do?contentsMobileId=userId=&pageSize=&pageNo=&all=y");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				ContentsEntity contentMobile = new ContentsEntity();
				if(StringUtil.isNotEmpty(contentId)){
					contentMobile = contentsService.get(ContentsEntity.class, String.valueOf(contentId));
				}
				InvitationMobileEntity invitationMobile = new InvitationMobileEntity();
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class,  userId );
					if(member!=null){
						invitationMobile.setUserId(member.getId()+"");
						invitationMobile.setUserName(member.getUsername());
					}
				}
				content = URLDecoder.decode(content, "utf-8");
				invitationMobile.setContent(content);

				invitationMobile.setContentsId(String.valueOf(contentId));

				invitationMobile.setCreatetime(new Date());
				invitationMobile.setInvitationType("1");
				invitationMobile.setIp(oConvertUtils.getIp());
				invitationMobile.setContentsTitle(contentMobile.getTitle());
				invitationMobile.setContentsUrl(contentMobile.getUrl());
				invitationMobile.setDisabled("0");
				this.saveOrUpdate(invitationMobile);
				String sql = "update cms_content c set c.extendCount = c.extendCount+1 where c.id = '" + contentId + "'";
				executeSql(sql);
				beanApi.setResultMsg("发帖成功");
				beanApi.setResultCode("1");
				beanApi.setListUrl("/front/invitationMobileApi/loadInvitationList.do?contentsMobileId="+contentId+"&userId="+userId+"&pageSize=&pageNo=");
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
 
				
			}
		}catch(Exception e1){
			e1.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	/**
	 * wap端--跟帖提交
	 * @param contentId
	 * @param content
	 * @return String
	 */
	public JSONObject saveInvitationWap(String all,String userId,String contentId, String content){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.setResultCode("1");
				beanApi.setResultMsg("成功");
				beanApi.setListUrl("/front/commentMobileApi/loadCommentList.do?contentsMobileId=userId=&pageSize=&pageNo=&all=y");
				beanApi.setItems(null);
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				ContentsEntity contentMobile = new ContentsEntity();
				if(StringUtil.isNotEmpty(contentId)){
					contentMobile = contentsService.get(ContentsEntity.class, String.valueOf(contentId));
				}
				InvitationMobileEntity invitationMobile = new InvitationMobileEntity();
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class,  userId );
					if(member!=null){
						invitationMobile.setUserId(member.getId()+"");
						invitationMobile.setUserName(member.getUsername());
					}
				}
				content = URLDecoder.decode(content, "utf-8");
				invitationMobile.setContent(content);
				
				invitationMobile.setContentsId(String.valueOf(contentId));
				
				invitationMobile.setCreatetime(new Date());
				invitationMobile.setInvitationType("1");
				invitationMobile.setIp(oConvertUtils.getIp());
				invitationMobile.setContentsTitle(contentMobile.getTitle());
				invitationMobile.setContentsUrl(contentMobile.getUrl());
				invitationMobile.setDisabled("0");
				this.saveOrUpdate(invitationMobile);
				String sql = "update cm_content c set c.extendCount = c.extendCount+1 where c.id = '" + contentId + "'";
				executeSql(sql);
				beanApi.setResultMsg("发帖成功");
				beanApi.setResultCode("1");
				beanApi.setListUrl("/front/invitationMobileApi/loadInvitationList.do?contentsMobileId="+contentId+"&userId="+userId+"&pageSize=&pageNo=");
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				
				
			}
		}catch(Exception e1){
			e1.printStackTrace();
			beanApi.setResultMsg("服务器繁忙，稍后重试！");
			beanApi.setResultCode("0");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}

	/**
	 * 跟帖列表
	 * @param all
	 * @param contentsMobileId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@Override
	public JSONObject getInvitationMobileList(String all, String contentsMobileId,String userId,int pageSize, int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.getItems().add(mobileChannelService.setValue());
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				json = isForList(contentsMobileId, userId, pageSize, pageNo);
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
	/**
	 * 跟帖列表 wap 端
	 * @param all
	 * @param contentsMobileId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@Override
	public JSONObject getInvitationMobileListWap(String all, String contentsMobileId,String userId,int pageSize, int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.getItems().add(mobileChannelService.setValue());
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				json = isForListWap(contentsMobileId, userId, pageSize, pageNo);
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
	
	
	
	public JSONObject isForList(String contentsMobileId,String userId,int pageSize, int pageNo){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		ContentsMobileEntity contentsMobile = new ContentsMobileEntity();
		if(StringUtil.isNotEmpty(contentsMobileId)&&StringUtil.isEmpty(userId)){
			contentsMobile = this.get(ContentsMobileEntity.class,  contentsMobileId );
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
			cq.eq("contentsId", String.valueOf(contentsMobileId));
			cq.or(Restrictions.isNull("disabled"), Restrictions.ne("disabled", "1"));//审核不是禁止的评论
			cq.addOrder("time", SortDirection.desc);
			cq.add();
			addBean(pageSize, cq, beanApi, contentsMobile);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			
		}
		if(StringUtil.isEmpty(contentsMobileId)&&StringUtil.isNotEmpty(userId)){
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
			cq.eq("userId", String.valueOf(userId));
			cq.or(Restrictions.isNull("disabled"), Restrictions.ne("disabled", "1"));//审核不是禁止的评论
			cq.addOrder("time", SortDirection.desc);
			cq.add();
			addBean(pageSize, cq, beanApi, contentsMobile);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		if(StringUtil.isNotEmpty(contentsMobileId)&&StringUtil.isNotEmpty(userId)){
			contentsMobile = this.get(ContentsMobileEntity.class,  contentsMobileId );
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
			cq.eq("contentsId", String.valueOf(contentsMobileId));
			cq.eq("userId", String.valueOf(userId));
			cq.or(Restrictions.isNull("disabled"), Restrictions.ne("disabled", "1"));//审核不是禁止的评论
			cq.addOrder("time", SortDirection.desc);
			cq.add();
			addBean(pageSize, cq, beanApi, contentsMobile);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(contentsMobileId)){
			beanApi.setResultCode("0");
			beanApi.setResultMsg("输入有误，请核对！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	
	/**
	 * 
	 * @param contentsMobileId
	 * @param userId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public JSONObject isForListWap(String contentsMobileId,String userId,int pageSize, int pageNo){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		ContentsEntity contentsMobile = new ContentsEntity();
		if(StringUtil.isNotEmpty(contentsMobileId)&&StringUtil.isEmpty(userId)){
			contentsMobile = this.get(ContentsEntity.class,  contentsMobileId );
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
			cq.eq("contentsId", String.valueOf(contentsMobileId));
			cq.or(Restrictions.isNull("disabled"), Restrictions.ne("disabled", "1"));//审核不是禁止的评论
			cq.addOrder("time", SortDirection.desc);
			cq.add();
			addBeanWap(pageSize, cq, beanApi, contentsMobile);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			
		}
		if(StringUtil.isEmpty(contentsMobileId)&&StringUtil.isNotEmpty(userId)){
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
			cq.eq("userId", String.valueOf(userId));
			cq.or(Restrictions.isNull("disabled"), Restrictions.ne("disabled", "1"));//审核不是禁止的评论
			cq.addOrder("time", SortDirection.desc);
			cq.add();
			addBeanWap(pageSize, cq, beanApi, contentsMobile);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		if(StringUtil.isNotEmpty(contentsMobileId)&&StringUtil.isNotEmpty(userId)){
			contentsMobile = this.get(ContentsEntity.class,  contentsMobileId );
			//排序条件
			CriteriaQuery cq = new CriteriaQuery(InvitationMobileEntity.class, pageSize, pageNo, "", "");
			cq.eq("contentsId", String.valueOf(contentsMobileId));
			cq.eq("userId", String.valueOf(userId));
			cq.or(Restrictions.isNull("disabled"), Restrictions.ne("disabled", "1"));//审核不是禁止的评论
			cq.addOrder("time", SortDirection.desc);
			cq.add();
			addBeanWap(pageSize, cq, beanApi, contentsMobile);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(contentsMobileId)){
			beanApi.setResultCode("0");
			beanApi.setResultMsg("输入有误，请核对！");
			beanApi.setItems(null);
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
		}
		return json;
	}
	public void addBean(int pageSize,CriteriaQuery cq,BeanApi beanApi,ContentsMobileEntity contentsMobile){
		List<InvitationMobileEntity> testList = new ArrayList<InvitationMobileEntity>();
		int pageCount = 0;
		PageList pageList = this.getPageList(cq, true);
		testList = pageList.getResultList();
		pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		beanApi.setResultCode("1");
		beanApi.setResultMsg("成功");
		beanApi.setPageCount(String.valueOf(pageCount));
		beanApi.setPageSize(String.valueOf(pageSize));
		//内容详细页
		beanApi.setListUrl(contentsMobile.getUrl());
		if(contentsMobile.getId()!=null){
			beanApi.setId(String.valueOf(contentsMobile.getId()));
			beanApi.setType(mobileChannelService.getContentType(contentsMobile.getClassify()));
		}
		beanApi.setItems(getIMList(testList));
	}
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @param pageSize
	 * @param cq
	 * @param beanApi
	 * @param contentsMobile
	 */
	public void addBeanWap(int pageSize,CriteriaQuery cq,BeanApi beanApi,ContentsEntity contentsMobile){
		List<InvitationMobileEntity> testList = new ArrayList<InvitationMobileEntity>();
		int pageCount = 0;
		PageList pageList = this.getPageList(cq, true);
		testList = pageList.getResultList();
		pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		beanApi.setResultCode("1");
		beanApi.setResultMsg("成功");
		beanApi.setPageCount(String.valueOf(pageCount));
		beanApi.setPageSize(String.valueOf(pageSize));
		//内容详细页
		beanApi.setListUrl(contentsMobile.getUrl());
		if(contentsMobile.getId()!=null){
			beanApi.setId(String.valueOf(contentsMobile.getId()));
			beanApi.setType(mobileChannelService.getContentType(contentsMobile.getClassify()));
		}
		beanApi.setItems(getIMList(testList));
	}
	/**
	 * 跟帖列表下所有帖子
	 * @param contentsMobileList
	 * @return
	 */
	public List<BeanListApi> getIMList(List<InvitationMobileEntity> invitationMobileList){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		for(InvitationMobileEntity invitationMobile:invitationMobileList){
			BeanListApi item = new BeanListApi();
			item.setId(String.valueOf(invitationMobile.getId()));
			item.setContent(invitationMobile.getContent());
			item.setPubDate(String.valueOf(sdf.format(invitationMobile.getCreatetime())));
			item.setListUrl("wwwroot/www/newsDeatil.htm?size=14");
			item.setType(invitationMobile.getInvitationType());
			if(null!=invitationMobile.getUserId()){
				item.setPictureAll(memberService.getMember(invitationMobile.getUserId()).getFaceImg());
				item.setUserName(memberService.getMember(invitationMobile.getUserId()).getRealname());
			}
			beanList.add(item);
		}
		if(beanList.size()==0){
			beanList = null;
		}
		return beanList;
	}
	@Override
	public JSONObject getInvitationList(int pageSize, int pageNo) {
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			CriteriaQuery cq = new CriteriaQuery(ContentsMobileEntity.class, pageSize, pageNo, "", "");
			cq.notEq("extendcount", 0);
			cq.addOrder("extendcount", SortDirection.desc);
			cq.addOrder("published", SortDirection.desc);
			cq.add();
			PageList pageList = this.getPageList(cq, true);
			List<ContentsMobileEntity> testList = pageList.getResultList();
			int pageCount = (int)Math.ceil((double)pageList.getCount() / (double)pageSize);
			if(pageCount <= 0){
				pageCount = 1;
			}
			beanApi.setResultCode("1");
			beanApi.setResultMsg("成功");
			beanApi.setPageCount(String.valueOf(pageCount));
			beanApi.setPageSize(String.valueOf(pageSize));
			beanApi.setItems(contentsMobileService.getAllContentsMobileList(testList));
			json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
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
	public JSONObject updateStatus(String contentsId,String mobileChannelId,String invitationId,String status) {
		JSONObject json = new JSONObject();
		String message = "";
		InvitationMobileEntity invitationMobile = null;
		try {
			if(StringUtil.isNotEmpty(invitationId)){
				invitationMobile = get(InvitationMobileEntity.class, invitationId );
			}
			if(null!=invitationMobile){
				invitationMobile.setDisabled(status);
				saveOrUpdate(invitationMobile);
			}
			if("0"==status){
				message = "该条评论通过审核";
			}else{
				message = "该条评论未通过审核";
				//未通过的评论需要在内容表中的跟帖数“减1”
				String sql = "update cm_content c set c.extendCount = c.extendCount-1 where c.id = '" + contentsId + "'";
				executeSql(sql);
			}
			json.accumulate("isSuccess", true);
			
		} catch (Exception e) {
			json.accumulate("isSuccess", false);
			message = "审核操作不成功";
		}
		json.accumulate("msg", message);
		json.accumulate("toUrl", "invitationMobileController.do?invitationMobile&contentId="+contentsId+"&mobileChannelId="+mobileChannelId);
		return json;
	}
}