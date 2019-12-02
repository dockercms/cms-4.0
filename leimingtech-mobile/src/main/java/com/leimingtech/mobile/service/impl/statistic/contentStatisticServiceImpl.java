package com.leimingtech.mobile.service.impl.statistic;

import com.leimingtech.core.entity.MobileChannelEntity;
import com.leimingtech.core.entity.model.ModelsEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.mobile.service.statistic.contentStatisticServiceI;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("contentStatisticService")
@Transactional
public class contentStatisticServiceImpl extends CommonServiceImpl implements contentStatisticServiceI {

	/**
	 * 获取频道集合
	 */
	@Override
	public List<MobileChannelEntity> getListChannel() {
		List<MobileChannelEntity> list = null;
		try {
			list = this.getList(MobileChannelEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取频道
	 */
	@Override
	public MobileChannelEntity getChannelById(String id) {
		MobileChannelEntity entity = null;
		try {
			entity = this.get(MobileChannelEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 获取模型集合
	 */
	@Override
	public List<ModelsEntity> getListMobileChannel() {
		List<ModelsEntity> list = null;
		try {
			list = this.getList(ModelsEntity.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取模型
	 */
	@Override
	public ModelsEntity getMobileChannelById(String id) {
		ModelsEntity entity = null;
		try {
			entity = this.get(ModelsEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 统计移动内容根据频道
	 */
	@Override
	public JSONArray getStatistic(String catId, String classify, String startDate, String stopDate, String type) {
		JSONArray array = new JSONArray();
		// 定义浏览量集合
		List<Map<Integer, Integer>> listPv = null;
		// 定义发稿量集合
		List<Map<Integer, Integer>> listCon = null;
		// 定义评论量集合
		List<Map<Integer, Integer>> listCom = null;

		// 参数封装处理
		Object[] object = parameter(catId, classify, startDate, stopDate);

		// 定义拼接访问量HQL
		StringBuffer contentPvBufferHql = new StringBuffer();
		// 定义拼接点发稿量HQL
		StringBuffer contentIssueBufferHql = new StringBuffer();
		// 定义拼接点评论量HQL
		StringBuffer commentaryBufferHql = new StringBuffer();
		// 定义拼接公共分组HQL
		StringBuffer contentGroupBufferHql = new StringBuffer();
		if ("dateTime".equals(type) ) {
			// 拼接访问量HQL
			contentPvBufferHql
					.append("select CONCAT(year(ct.published),'/',month(ct.published),'/',day(ct.published)) , sum(ct.pv) from ContentsMobileEntity ct where 1 = 1 ");
			// 拼接点发稿量HQL
			contentIssueBufferHql
					.append("select CONCAT(year(ct.published),'/',month(ct.published),'/',day(ct.published)) , count(ct.id) from ContentsMobileEntity ct where 1 = 1 ");
			// 拼接点评论量HQL
			commentaryBufferHql
					.append("select CONCAT(year(ct.published),'/',month(ct.published),'/',day(ct.published)) , count(cf.id) from ContentsMobileEntity ct ,CommentaryFrontEntity cf where ct.id=cf.contentid ");
			// 拼接分组HQL(根据日期)
			contentGroupBufferHql.append(" group by (year(ct.published)-month(ct.published)-day(ct.published))");

		} else {
			// 拼接访问量HQL
			contentPvBufferHql.append("select ct.catid , sum(ct.pv) from ContentsMobileEntity ct where 1 = 1 ");
			// 拼接点发稿量HQL
			contentIssueBufferHql.append("select ct.catid , count(ct.id) from ContentsMobileEntity ct where 1 = 1 ");
			// 拼接点评论量HQL
			commentaryBufferHql
					.append("select ct.catid , count(cf.id) from ContentsMobileEntity ct ,CommentaryFrontEntity cf where ct.id=cf.contentid ");
			// 拼接分组HQL(根据频道)
			contentGroupBufferHql.append(" group by ct.catid");
		}
		// 拼接公共参数HQL
		StringBuffer contentParamBufferHql = new StringBuffer();
		if (catId != null && !catId.equals("0")) {
			contentParamBufferHql.append(" and ct.catid = ? ");
		}
		if (classify != null && classify.length() > 0  && !classify.equals("0")) {
			contentParamBufferHql.append(" and ct.classify = ? ");
		}
		if (startDate != null && !startDate.equals("")) {
			contentParamBufferHql.append(" and ct.published >= ? ");
		}
		if (stopDate != null && !startDate.equals("")) {
			contentParamBufferHql.append(" and ct.published <= ? ");
		}
		// 参数拼接在访问量的后面
		contentPvBufferHql.append(contentParamBufferHql);
		// 分组拼接在访问量的后面
		contentPvBufferHql.append(contentGroupBufferHql);

		// 参数拼接在发稿量的后面
		contentIssueBufferHql.append(contentParamBufferHql);
		// 分组拼接在发稿量的后面
		contentIssueBufferHql.append(contentGroupBufferHql);

		// 参数拼接在评论量的后面
		commentaryBufferHql.append(contentParamBufferHql);
		// 分组拼接在评论量的后面
		commentaryBufferHql.append(contentGroupBufferHql);

		// 查询访问量
		listPv = this.findHql(contentPvBufferHql.toString(), object);

		// 查询发稿量
		listCon = this.findHql(contentIssueBufferHql.toString(), object);

		// 查询评论量
		listCom = this.findHql(commentaryBufferHql.toString(), object);


		// 把统计结果添加到Map集合
		if (listPv != null && listPv.size() > 0) {
			array.add(0, listPv);
		} else {
			 array.add(0, 1);
		}
		if (listCon != null && listCon.size() > 0) {
			array.add(1, listCon);
		} else {
			 array.add(1, 2);
		}
		if (listCom != null && listCom.size() > 0) {
			array.add(2, listCom);
		} else {
			array.add(2, 3);
		}
		return array;
	}

	/**
	 * 参数封装处理
	 * 
	 * @param catId
	 *            频道ID
	 * @param classify
	 *            模型ID
	 * @param startDate
	 *            开始时间
	 * @param stopDate
	 *            结束时间
	 * @return
	 */
	private Object[] parameter(String catId, String classify, String startDate, String stopDate) {
		// 封装参数
		Object[] objects = null;

		// 初始化参数
		List<Object> ojlist = null;
		if (catId != null || classify != null || startDate != null || stopDate != null) {
			ojlist = new ArrayList<Object>();
		}
		if (catId != null && !catId.equals("0")) {
			ojlist.add(catId);
		}
		if (classify != null && classify.length() > 0 && !classify.equals("0")) {
			ojlist.add(classify);
		}
		java.sql.Date dateStart = null;
		java.sql.Date dateStop = null;
		if (startDate != null && !startDate.equals("")) {
			dateStart = java.sql.Date.valueOf(startDate);
			ojlist.add(dateStart);
		}
		if (stopDate != null && !startDate.equals("")) {
			dateStop = java.sql.Date.valueOf(stopDate);
			// 在截止日期的基础上加1天
			long timeLong = 1000 * 60 * 60 * 24;
			dateStop.setTime(dateStop.getTime() + timeLong);
			ojlist.add(dateStop);
		}

		// 转换参数容器类型为object数组
		if (ojlist != null) {
			objects = new Object[ojlist.size()];
			for (int i = 0; i < ojlist.size(); i++) {
				if (ojlist.get(i) != null) {
					objects[i] = ojlist.get(i);
				}
			}
		}
		return objects;
	}
}
