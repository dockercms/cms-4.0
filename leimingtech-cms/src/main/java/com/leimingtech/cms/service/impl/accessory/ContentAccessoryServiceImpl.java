package com.leimingtech.cms.service.impl.accessory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.accessory.ContentAccessoryServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ContentAccessoryEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * 附件管理接口实现
 * @author liuzhen
 *
 */
@Service("contentAccessoryService")
@Transactional
public class ContentAccessoryServiceImpl implements ContentAccessoryServiceI {

	@Autowired
	private CommonService commonService;

	/**
	 * 获取内容的附件
	 * @param id
	 * @return
	 */
	@Override
	public List<ContentAccessoryEntity> findByContentId(String id) {
		CriteriaQuery cq = new CriteriaQuery(ContentAccessoryEntity.class);
		cq.eq("contentId", id);
		cq.addOrder("createdtime", SortDirection.asc);
		cq.add();
		List<ContentAccessoryEntity> accessoryList = this.commonService.getListByCriteriaQuery(cq, false);
		return accessoryList;
	}

	@Override
	public ContentAccessoryEntity getEntity(String accessoryId) {
		return this.commonService.getEntity(ContentAccessoryEntity.class, accessoryId);
	}

	@Override
	public void delete(ContentAccessoryEntity contentAccessory) {
		this.commonService.delete(contentAccessory);
	}

	@Override
	public void save(ContentAccessoryEntity contentAccessory) {
		this.commonService.save(contentAccessory);
	}

	@Override
	public void saveOrUpdate(ContentAccessoryEntity t) {
		this.saveOrUpdate(t);
	}

	/**
	 * 获取分页后的附件数据集
	 *
	 * @param contentAccessory
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return contentAccessoryList投稿配置数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(
			ContentAccessoryEntity contentAccessory, Map param, int pageSize,
			int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(ContentAccessoryEntity.class,
				pageSize, pageNo, "", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, contentAccessory, param);
		cq.addOrder("createdtime", SortDirection.asc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<ContentAccessoryEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("pageList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	@Override
	public void delete(List<ContentAccessoryEntity> accessoryList) {
		this.commonService.deleteAllEntitie(accessoryList);
	}
	
}