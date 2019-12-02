package com.leimingtech.cms.service.impl.advertising;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;
import com.leimingtech.cms.entity.advertising.AdvertisingSpaceEntity;
import com.leimingtech.cms.service.advertising.AdvertisingServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 广告管理
 * 
 * @author liuzhen 2014年4月25日 10:11:08
 * 
 */

@Service("advertisingService")
@Transactional
public class AdvertisingServiceImpl extends CommonServiceImpl implements AdvertisingServiceI {
	public void delMain(AdvertisingEntity advertising) {
		// 删除字表信息
		this.delete(advertising);
	}

	/**
	 * 获取广告列表 （此方法由标签调用）
	 * 
	 * @param params
	 *            广告参数<br/>
	 *            key(name) value=可以是广告位名称（全名）也可以是广告位id <br/>
	 *            key(count) value=获取条数 默认全部
	 * @return List 广告列表
	 */
	public List<AdvertisingEntity> getAdvListByTag(Map params) {
		if (MapUtils.isEmpty(params))
			return null;

		String name = (String) params.get("name");

		if (StringUtils.isNotEmpty(name)) {
			return findByCriteria(params);
		}

		return new ArrayList<AdvertisingEntity>();
	}

	/**
	 * 通过Hql语句查询广告列表
	 * 
	 * @param params
	 *            查询条件
	 * @return List 广告列表
	 */
	private List<AdvertisingEntity> findByHql(Map params) {
		String name = (String) params.get("name");
		String count = (String) params.get("count");
		Timestamp time = new Timestamp(new Date().getTime());

		StringBuilder hql = new StringBuilder();
		hql.append(" from AdvertisingEntity a ");
		hql.append(" where a.advertisingSpace.adName = :adName ");
		hql.append(" and a.isEnabled=1 ");
		hql.append(" and a.startTime <= :startTime ");
		hql.append(" and a.endTime >= :endTime ");
		hql.append(" order by a.adWeight desc,a.id asc ");
		Query query = getSession().createQuery(hql.toString());
		query.setString("adName", name);
		query.setDate("startTime", time);
		query.setDate("endTime", time);
		if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)) {
			query.setFirstResult(0);
			query.setMaxResults(Integer.valueOf(count));
		}
		return query.list();
	}

	/**
	 * 通过Hibernate查询器查询广告列表
	 * 
	 * @param params
	 *            查询条件
	 * @return List 广告列表
	 */
	private List<AdvertisingEntity> findByCriteria(Map params) {
		List<AdvertisingEntity> advList = new ArrayList<AdvertisingEntity>();

		String name = (String) params.get("name");
		String count = (String) params.get("count");
		
		AdvertisingSpaceEntity advlanwei=null;
		if(StringUtils.isNotEmpty(name)){
			advlanwei= getEntity(AdvertisingSpaceEntity.class, name);
		}

		if(advlanwei==null){
			List<AdvertisingSpaceEntity> advlanweiList=findByProperty(AdvertisingSpaceEntity.class, "adName", name);
			if(advlanweiList!=null && advlanweiList.size()>0){
				advlanwei=advlanweiList.get(0);
			}
		}
		
		if(advlanwei!=null && "1".equals(advlanwei.getIsEnabled())){
			CriteriaQuery cq = new CriteriaQuery(AdvertisingEntity.class);
			cq.eq("advertisingSpace.id", advlanwei.getId());
			cq.eq("isEnabled", "1");// 已经启用的广告
			Date date = new Date();
//			cq.gt("endTime", date);
			cq.or(Restrictions.isNull("endTime"), Restrictions.gt("endTime", date));
			cq.lt("startTime", date);
			cq.addOrder("adWeight", SortDirection.desc);
			cq.add();
			advList = getListByCriteriaQuery(cq, false);
		}

		if (StringUtils.isNotEmpty(count)  && advList != null && advList.size() > 0 ) {

			if (advList.size() > Integer.valueOf(count)) {
				advList = advList.subList(0, Integer.valueOf(count));
			}
		}
		return advList;
	}

	/**
	 * 广告展现次数增加
	 * @param advId 广告id
	 * @return
	 */
	@Override
	public void advDisplayAdd(String advId) {
		String sql = "update cms_advertising t set t.display_count = t.display_count+1 where t.id = '" + advId + "'";
		executeSql(sql);
	}

	/**
	 * 广告点击次数增加
	 * @param advId 广告id
	 * @return
	 */
	@Override
	public void advClickAdd(String advId) {
		String sql = "update cms_advertising t set t.click_count = t.click_count+1 where t.id = '" + advId + "'";
		executeSql(sql);
	}
}