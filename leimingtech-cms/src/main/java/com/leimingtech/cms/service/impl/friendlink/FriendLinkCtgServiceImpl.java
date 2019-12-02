package com.leimingtech.cms.service.impl.friendlink;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.friendlink.FriendLinkCtgEntity;
import com.leimingtech.cms.entity.friendlink.FriendLinkEntity;
import com.leimingtech.cms.service.FriendLinkCtgServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.PlatFormUtil;

@Service("friendLinkCtgService")
@Transactional
public class FriendLinkCtgServiceImpl extends CommonServiceImpl implements FriendLinkCtgServiceI {
	public void delMain(FriendLinkCtgEntity friendLinkCtg) {
		// 删除主表信息
		this.delete(friendLinkCtg);
		// 获取参数
		Object id0 = friendLinkCtg.getId();
		// 删除-动态报表配置明细
		String hql0 = "from FriendLinkEntity where 1 = 1 AND gid = ? ";
		List<FriendLinkEntity> friendLinkOldList = this.findHql(hql0, id0);
		this.deleteAllEntitie(friendLinkOldList);
	}

	/**
	 * 获取当前站点中全部友情链接类别
	 * 
	 * @return
	 */
	@Override
	public List<FriendLinkCtgEntity> getAll() {
		CriteriaQuery cq = new CriteriaQuery(FriendLinkCtgEntity.class);
		cq.eq("siteId", PlatFormUtil.getSessionSiteId());
		cq.addOrder("priority", SortDirection.desc);
		cq.addOrder("id", SortDirection.desc);
		List<FriendLinkCtgEntity> friendLinkCtgList = getListByCriteriaQuery(cq, false);
		return friendLinkCtgList;
	}
}