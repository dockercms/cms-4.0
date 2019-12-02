package com.leimingtech.cms.service.impl.tag;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.tag.IBelowTagMng;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.common.ContentStatus;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;


/**
 * 新闻下一篇接口实现
 * @author wangyu
 *
 */
@Service("belowTagMng")
@Transactional
public class BelowTagMng implements IBelowTagMng{
	@Autowired
	private CommonService commonService;
	@Override
	public List getBelowTag(String cid) {
		if(StringUtils.isEmpty(cid)){
			return null;
		}
			int pageSize = 1;
			int pageNo = 1;
			
			Date date = new Date();
			ContentsEntity ce = commonService.get(ContentsEntity.class, cid);
			ContentCatEntity cat = commonService.get(ContentCatEntity.class, ce.getCatid());
			SiteEntity site = commonService.get(SiteEntity.class,cat.getSiteid());
			CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class,pageSize, pageNo, "", "");
			cq.eq("siteid", site.getId());
			cq.eq("catid", cat.getId());
			cq.or(Restrictions.isNull("noted"), Restrictions.ge("noted", date));
			cq.lt("published",ce.getPublished());
			cq.le("published", date);
			cq.in("constants", new String[] { ContentStatus.CONTENT_COMMITTED,
				  ContentStatus.CONTENT_PUBLISHED });
			cq.addOrder("published", SortDirection.desc);
			cq.add();
			PageList pageList = commonService.getPageList(cq, true);
			List<ContentsEntity> belowList = pageList.getResultList();
			return belowList;
	}
}
