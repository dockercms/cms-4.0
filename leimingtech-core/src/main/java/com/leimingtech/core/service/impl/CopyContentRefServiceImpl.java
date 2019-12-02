package com.leimingtech.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.CopyContentRefEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.CopyContentRefServiceI;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * @Title: interface
 * @Description: 稿件复制或引用关联接口实现
 * @author
 * @date 2015-10-26 17:20:01
 * @version V1.0
 * 
 */
@Service("copyContentRefService")
@Transactional
public class CopyContentRefServiceImpl implements CopyContentRefServiceI {

	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	/** 内容管理接口 */
	@Autowired
	private ContentsServiceI contentsService;

	/**
	 * 保存稿件复制或引用关联
	 * 
	 * @param copyContentRef
	 * @return
	 */
	public java.lang.String save(CopyContentRefEntity copyContentRef) {

		Date date = new Date();
		String uid = PlatFormUtil.getSessionUser().getId();
		copyContentRef.setCreateTime(date);
		copyContentRef.setCreateBy(uid);
		copyContentRef.setUpdateBy(uid);
		copyContentRef.setUpdateTime(date);
		if (StringUtils.isBlank(copyContentRef.getRemarks())) {
			copyContentRef.setRemarks("");
		}

		return (java.lang.String) commonService.save(copyContentRef);
	}

	/**
	 * 更新稿件复制或引用关联
	 * 
	 * @param copyContentRef
	 */
	@Override
	public void saveOrUpdate(CopyContentRefEntity copyContentRef) {
		commonService.saveOrUpdate(copyContentRef);
	}

	/**
	 * 通过id获取稿件复制或引用关联
	 * 
	 * @param id
	 *            稿件复制或引用关联id
	 * @return
	 */
	@Override
	public CopyContentRefEntity getEntity(java.lang.String id) {
		return commonService.getEntity(CopyContentRefEntity.class, id);
	}

	/**
	 * 获取分页后的稿件复制或引用关联数据集
	 * 
	 * @param copyContentRef
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return copyContentRefList稿件复制或引用关联数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(CopyContentRefEntity copyContentRef,
			Map param, int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(CopyContentRefEntity.class,
				pageSize, pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, copyContentRef, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<CopyContentRefEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("copyContentRefList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除稿件复制或引用关联
	 * 
	 * @param copyContentRef
	 */
	@Override
	public void delete(CopyContentRefEntity copyContentRef) {
		commonService.delete(copyContentRef);
	}

	/**
	 * 添加关联
	 * 
	 * @param contents
	 *            主稿件
	 * @param id
	 *            子稿件
	 */
	@Override
	public void save(ContentsEntity contents, String id) {
		if (contents == null || StringUtils.isBlank(id))
			return;

		CopyContentRefEntity ref = new CopyContentRefEntity();
		ref.setDelFlag(0);
		ref.setLockFlag("true".equalsIgnoreCase(contents.getLockContent()) ? 1
				: 0);
		ref.setMainContentId(contents.getId());
		ref.setSubContentId(id);
		this.save(ref);
	}

	/**
	 * 通过复制后的文章获取锁定的源稿件
	 * 
	 * @param id
	 *            复制后的稿件id
	 * @return
	 */
	@Override
	public ContentsEntity findLockMainContent(String id) {
		CriteriaQuery cq = new CriteriaQuery(CopyContentRefEntity.class);
		cq.eq("subContentId", id);
		cq.eq("lockFlag", 1);
		cq.addOrder("createTime", SortDirection.desc);
		cq.add();
		List<CopyContentRefEntity> refList = commonService
				.getListByCriteriaQuery(cq, false);
		if (refList != null && refList.size() > 0) {

			CopyContentRefEntity ref = refList.get(0);
			return contentsService.getContensById(ref.getMainContentId());
		}

		return null;

	}

}