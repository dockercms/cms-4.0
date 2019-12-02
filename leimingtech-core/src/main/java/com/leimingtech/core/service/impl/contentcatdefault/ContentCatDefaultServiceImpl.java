package com.leimingtech.core.service.impl.contentcatdefault;

import com.leimingtech.core.entity.ContentCatDefault;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.contentcatdefault.ContentCatDefaultServiceI;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: interface
 * @Description: 会员默认栏目权限接口实现
 * @author
 * @date 2016-04-22 14:17:39
 * @version V1.0
 * 
 */
@Service("contentCatDefaultService")
@Transactional
public class ContentCatDefaultServiceImpl implements ContentCatDefaultServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存会员默认栏目权限
	 * 
	 * @param contentCatDefault
	 * @return
	 */
	public java.lang.String save(ContentCatDefault contentCatDefault) {
		return (java.lang.String) commonService.save(contentCatDefault);
	}

	/**
	 * 更新会员默认栏目权限
	 * 
	 * @param contentCatDefault
	 */
	@Override
	public void saveOrUpdate(ContentCatDefault contentCatDefault) {
		commonService.saveOrUpdate(contentCatDefault);
	}

	/**
	 * 通过id获取会员默认栏目权限
	 * 
	 * @param id
	 *            会员默认栏目权限id
	 * @return
	 */
	@Override
	public ContentCatDefault getEntity(java.lang.String id) {
		return commonService.getEntity(ContentCatDefault.class, id);
	}

	/**
	 * 获取分页后的会员默认栏目权限数据集
	 * 
	 * @param contentCatDefault
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentCatDefaultList会员默认栏目权限数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(ContentCatDefault contentCatDefault, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentCatDefault.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, contentCatDefault, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ContentCatDefault> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("contentCatDefaultList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除会员默认栏目权限
	 * 
	 * @param contentCatDefault
	 */
	@Override
	public void delete(ContentCatDefault contentCatDefault) {
		commonService.delete(contentCatDefault);
	}

	/**
	 * 删除跟此栏目相关的会员默认栏目权限
	 * 
	 * @param idArray
	 *            栏目id数据
	 */
	@Override
	public void deleteByContentCats(String[] idArray) {
		
		if (ArrayUtils.isEmpty(idArray)) {
			return;
		}

		for (String string : idArray) {
			ContentCatDefault contentCatDefault = this.findByContentCat(string);
			if (contentCatDefault != null) {
				this.delete(contentCatDefault);
			}
		}
	}

	/**
	 * 通过栏目id获取
	 * 
	 * @param channelId
	 * @return
	 */
	private ContentCatDefault findByContentCat(String channelId) {
		return commonService.findUniqueByProperty(ContentCatDefault.class,
				"channelId", channelId);
	}

	/**
	 * 获取所有会员默认的栏目id
	 *
	 * @return
	 */
	@Override
	public List<String> getAllContentCatId() {
		CriteriaQuery cq = new CriteriaQuery(ContentCatDefault.class);

		DetachedCriteria dc = cq.getDetachedCriteria();
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("channelId").as("id"));
		dc.setProjection(pList);
		cq.add();
		return this.commonService.getListByCriteriaQuery(cq,
				false);
	}
}