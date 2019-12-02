package com.leimingtech.mobile.service.impl.comment;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leimingtech.core.entity.member.MemberEntity;
import jodd.util.StringUtil;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.BeanApi;
import com.leimingtech.core.entity.BeanListApi;
import com.leimingtech.core.entity.CommentaryFrontEntity;
import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.MobileChannelServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.mobile.service.comment.CommentMobileServiceI;

@Service("commentMobileService")
@Transactional
public class CommentMobileServiceImpl extends CommonServiceImpl implements CommentMobileServiceI {
	
	/**移动频道管理接口*/
	@Autowired
	private MobileChannelServiceI mobileChannelService;
	
	/**
	 * 评论列表
	 * @param all
	 * @param contentsMobileId
	 * @param pageSize
	 * @param pageNo
	 */
	public JSONObject getCommentMobileList(String all,String contentsMobileId,String userId,int pageSize,int pageNo){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.getItems().add(mobileChannelService.setValue());
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				ContentsMobileEntity contentsMobile = new ContentsMobileEntity();
				List<CommentaryFrontEntity> testList = new ArrayList<CommentaryFrontEntity>();
				int pageCount = 0;
				if(StringUtil.isNotEmpty(contentsMobileId)&&StringUtil.isEmpty(userId)){
					contentsMobile = this.get(ContentsMobileEntity.class, String.valueOf(contentsMobileId));
					//排序条件
					CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, pageSize, pageNo, "", "");
					cq.eq("contentid", String.valueOf(contentsMobileId));
					cq.addOrder("commentarytime", SortDirection.desc);
					cq.add();
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
					beanApi.setId(String.valueOf(contentsMobile.getId()));
					beanApi.setType(mobileChannelService.getContentType(contentsMobile.getClassify()));
					beanApi.setItems(getCMList(testList,contentsMobile));
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
				if(StringUtil.isNotEmpty(userId)&&StringUtil.isEmpty(contentsMobileId)){
					//排序条件
					CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, pageSize, pageNo, "", "");
					cq.eq("personid", String.valueOf(userId));
					cq.addOrder("commentarytime", SortDirection.desc);
					cq.add();
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
//					beanApi.setId(String.valueOf(contentsMobile.getId()));
//					beanApi.setType(contentsMobile.getDescription());
					beanApi.setItems(getCMList(testList,contentsMobile));
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
				if(StringUtil.isNotEmpty(userId)&&StringUtil.isNotEmpty(contentsMobileId)){
					contentsMobile = this.get(ContentsMobileEntity.class, String.valueOf(contentsMobileId));
					//排序条件
					CriteriaQuery cq = new CriteriaQuery(CommentaryFrontEntity.class, pageSize, pageNo, "", "");
					cq.eq("contentid", String.valueOf(contentsMobileId));
					cq.eq("personid", String.valueOf(userId));
					cq.addOrder("commentarytime", SortDirection.desc);
					cq.add();
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
					beanApi.setId(String.valueOf(contentsMobile.getId()));
					beanApi.setType(contentsMobile.getDescription());
					beanApi.setItems(getCMList(testList,contentsMobile));
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
				if(StringUtil.isEmpty(userId)&&StringUtil.isEmpty(contentsMobileId)){
					beanApi.setResultCode("0");
					beanApi.setResultMsg("输入有误，请核对！");
					beanApi.setItems(null);
					json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
				}
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
	 * 评论列表下所有评论
	 * @param contentsMobileList
	 * @param contentsMobile
	 * @return
	 */
	public List<BeanListApi> getCMList(List<CommentaryFrontEntity> commentMobileList,ContentsMobileEntity contentsMobile){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BeanListApi> beanList = new ArrayList<BeanListApi>();
		try{
			for(CommentaryFrontEntity commentMobile:commentMobileList){
				BeanListApi item = new BeanListApi();
				item.setId(String.valueOf(commentMobile.getId()));
				item.setTitle(commentMobile.getTitle());
				item.setContent(contentsMobile.getDigest());
				item.setPubDate(String.valueOf(sdf.format(commentMobile.getCommentarytime())));
				item.setListUrl("wwwroot/www/newsDeatil.htm?size=14");
				item.setType(mobileChannelService.getContentType(contentsMobile.getClassify()));
				if(null!=commentMobile.getPersonid()){
					item.setPictureAll(getFaceImg(commentMobile.getPersonid()));
				}
				beanList.add(item);
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
		if(beanList.size()==0){
			beanList = null;
		}
		return beanList;
	}
	/**
	 * 通过评论人ID获取头像
	 * @param userId
	 * @return
	 */
	public String getFaceImg(String userId){
		MemberEntity member = get(MemberEntity.class, userId);
		String str = member.getFaceImg();
		return str;
	}
	/**
	 * 评论提交
	 * @param contentsMobileId
	 * @param title
	 * @param content
	 * @return string
	 */
	public JSONObject saveComment(String all,String contentsMobileId,String title,String content,String userId){
		JSONObject json = new JSONObject();
		BeanApi beanApi = new BeanApi();
		try{
			if(StringUtil.isNotEmpty(all)){
				beanApi.setResultCode("1");
				beanApi.setResultMsg("成功");
				beanApi.setListUrl("/front/commentMobileApi/loadCommentList.do?contentsMobileId=&userId=&pageSize=&pageNo=&all=y");
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
			}else{
				CommentaryFrontEntity commentMobile = new CommentaryFrontEntity();
				if(StringUtil.isNotEmpty(userId)){
					MemberEntity member = get(MemberEntity.class, String.valueOf(userId));
					if(member!=null){
						commentMobile.setPersonid(member.getId());
						commentMobile.setCommentaryperson(member.getUsername());
					}
				}


				commentMobile.setContentid( contentsMobileId );


				content = URLDecoder.decode(content, "utf-8");
				commentMobile.setContent(content);
				commentMobile.setCommentarytime(new Date());
				commentMobile.setCommentType("1");
				title = URLDecoder.decode(title, "utf-8");
				commentMobile.setTitle(title);
				this.saveOrUpdate(commentMobile);
				beanApi.setResultMsg("评论成功");
				beanApi.setResultCode("1");
				beanApi.setListUrl("/front/commentMobileApi/loadCommentList.do?contentsMobileId="+contentsMobileId+"&userId="+userId+"&pageSize=20&pageNo=1");
				json = json.fromObject(beanApi,mobileChannelService.getJsonConfig());
//					}else{
//						beanApi.setResultMsg("找不到用户，请重新登录后评论");
//						beanApi.setResultCode("0");
//						beanApi.setItems(null);
//						json = json.fromObject(beanApi,getJsonConfig());
//					}
//				}else{
//					beanApi.setResultMsg("未登录，请登录后评论");
//					beanApi.setResultCode("0");
//					beanApi.setItems(null);
//					json = json.fromObject(beanApi,getJsonConfig());
//				}
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
}