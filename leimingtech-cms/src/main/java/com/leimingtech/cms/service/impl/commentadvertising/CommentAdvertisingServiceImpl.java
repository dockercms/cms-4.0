package com.leimingtech.cms.service.impl.commentadvertising;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leimingtech.cms.service.impl.comments.CommentsServiceImpl;
import com.leimingtech.core.base.SortDirection;
import org.apache.commons.collections.MapUtils;
import org.apache.maven.artifact.versioning.Restriction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.commentadvertising.CommentAdvertisingEntity;
import com.leimingtech.cms.service.commentadvertising.CommentAdvertisingServiceI;

import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;

/**
 * @Title: interface
 * @Description: 评论广告接口实现
 * @author
 * @date 2017-04-26 11:55:55
 * @version V1.0
 * 
 */
@Service("commentAdvertisingService")
@Transactional
public class CommentAdvertisingServiceImpl  implements CommentAdvertisingServiceI {

	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存评论广告
	 * 
	 * @param commentAdvertising
	 * @return
	 */
	public java.lang.String save(CommentAdvertisingEntity commentAdvertising) {
		return (java.lang.String) commonService.save(commentAdvertising);
	}

	/**
	 * 更新评论广告
	 * 
	 * @param commentAdvertising
	 */
	@Override
	public void saveOrUpdate(CommentAdvertisingEntity commentAdvertising) {
		commonService.saveOrUpdate(commentAdvertising);
	}

	/**
	 * 通过id获取评论广告
	 * 
	 * @param id
	 *            评论广告id
	 * @return
	 */
	@Override
	public CommentAdvertisingEntity getEntity(java.lang.String id) {
		return commonService.getEntity(CommentAdvertisingEntity.class, id);
	}

	/**
	 * 获取分页后的评论广告数据集
	 * 
	 * @param commentAdvertising
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return commentAdvertisingList评论广告数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(CommentAdvertisingEntity commentAdvertising, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(CommentAdvertisingEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, commentAdvertising, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<CommentAdvertisingEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("commentAdvertisingList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除评论广告
	 * 
	 * @param commentAdvertising
	 */
	@Override
	public void delete(CommentAdvertisingEntity commentAdvertising) {
		commonService.delete(commentAdvertising);
	}

	/**
	 * 获取有效期内的评论广告
	 * @return
     */
	@Override
	public Object getCommentAdvertisingByTag(Map params){
		int count=2;
		String siteId=(String) params.get("siteId");
		if(MapUtils.isNotEmpty(params)){
			String value=params.get("count")+"";
			if(value!=""){
				count=Integer.parseInt(value);
			}
		}
		Date date= new Date();
		CriteriaQuery cq = new CriteriaQuery(CommentAdvertisingEntity.class, count, 1,"", "");
		cq.eq("siteId",siteId);
		cq.eq("isUsing",1);
		cq.or(Restrictions.isNull("endTime"),Restrictions.gt("endTime",date));
		cq.or(Restrictions.isNull("startTime"),Restrictions.lt("startTime",date));
		cq.addOrder("weight", SortDirection.desc);
		cq.addOrder("createTime",SortDirection.desc);
		cq.add();
		PageList list = commonService.getPageList(cq,true);
		List<CommentAdvertisingEntity> commentAdvertisingList=list.getResultList();
		return commentAdvertisingList;
	}
}