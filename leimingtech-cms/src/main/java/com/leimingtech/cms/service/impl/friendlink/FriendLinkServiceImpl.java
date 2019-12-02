package com.leimingtech.cms.service.impl.friendlink;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.friendlink.FriendLinkCtgEntity;
import com.leimingtech.cms.entity.friendlink.FriendLinkEntity;
import com.leimingtech.cms.service.FriendLinkServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 友情链接管理
 * 
 * @author liuzhen 2014年4月25日 14:52:41
 * 
 */

@Service("friendLinkService")
@Transactional
public class FriendLinkServiceImpl extends CommonServiceImpl implements FriendLinkServiceI {
	public void delMain(FriendLinkEntity friendLink) {
		// 删除字表信息
		this.delete(friendLink);
	}

	/**
	 * 获取友情链接 （此方法由标签调用）
	 * 
	 * @param params
	 *            友情链接参数<br/>
	 *            key(count) 获取数据条数<br/>
	 *            key(name) 类型id或类型全名
	 * @return list 友情链接集合
	 */
	public List<FriendLinkEntity> getFriendLinkListByTag(Map params) {
		if (MapUtils.isEmpty(params))
			return null;
		
		String name = (String) params.get("name");
		
		FriendLinkCtgEntity friendLinkCtg = getEntity(FriendLinkCtgEntity.class, name);//根据id来获取实体的数据
		if (friendLinkCtg == null) {//实体的数据是空时
			friendLinkCtg = findUniqueByProperty(FriendLinkCtgEntity.class,"friendlinkctgName", name);//根据name来获取实体的数据，实体有数据执行下面的查询方法，无数据进入下一个判断
		}
		if (friendLinkCtg == null) {//当name这个值在表中是不存在的执行下边的操作,返回一个list集合，结束
			return new ArrayList<FriendLinkEntity>();
		}
		
		String count=(String)  params.get("count");
		
		CriteriaQuery cq = new CriteriaQuery(FriendLinkEntity.class);
		cq.eq("friendLinkCtg.id", friendLinkCtg.getId());
		cq.eq("isEnabled", "1");// 已经启用
		cq.addOrder("priority", SortDirection.desc);
		cq.add();
		List<FriendLinkEntity> friendLinkList = getListByCriteriaQuery(cq,false);
		if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count) && friendLinkList != null) {

			if (friendLinkList.size() > Integer.valueOf(count)) {
				friendLinkList = friendLinkList.subList(0, Integer.valueOf(count));
			}
		}
		
		return friendLinkList;
	}

}