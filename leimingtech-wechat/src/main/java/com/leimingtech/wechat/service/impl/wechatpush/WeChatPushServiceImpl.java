package com.leimingtech.wechat.service.impl.wechatpush;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.wechat.entity.wechatcontent.WeChatContentEntity;
import com.leimingtech.wechat.entity.wechatpush.WeChatPushEntity;
import com.leimingtech.wechat.service.wechatpush.WeChatPushServiceI;

/**
 * @Title: interface
 * @Description: 微信推送接口实现
 * @author
 * @date 2015-09-11 14:47:12
 * @version V1.0
 * 
 */
@Service("weChatPushService")
@Transactional
public class WeChatPushServiceImpl implements WeChatPushServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存微信推送
	 * 
	 * @param weChatPush
	 * @return
	 */
	public String save(WeChatPushEntity weChatPush) {
		return (String) commonService.save(weChatPush);
	}

	/**
	 * 更新微信推送
	 *
	 * @param weChatPush
	 */
	@Override
	public void saveOrUpdate(WeChatPushEntity weChatPush) {
		commonService.saveOrUpdate(weChatPush);
	}

	/**
	 * 通过id获取微信推送
	 *
	 * @param id
	 *            微信推送id
	 * @return
	 */
	@Override
	public WeChatPushEntity getEntity(String id) {
		return commonService.getEntity(WeChatPushEntity.class, id);
	}

	/**
	 * 获取分页后的微信推送数据集
	 *
	 * @param weChatPush
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return weChatPushList微信推送数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(WeChatPushEntity weChatPush, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(WeChatPushEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, weChatPush, param);
		String siteId = PlatFormUtil.getSessionSite().getId();
		cq.eq("siteId", siteId);
		cq.addOrder("createTime", SortDirection.desc);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<WeChatPushEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("weChatPushList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除微信推送
	 *
	 * @param weChatPush
	 */
	@Override
	public void delete(WeChatPushEntity weChatPush) {
		if (weChatPush == null)
			return;

		List<WeChatContentEntity> weChatContentList = getListByPid(weChatPush.getId());
		if (weChatContentList != null && weChatContentList.size() > 0) {
			int weChatContentMaxIndex = weChatContentList.size() - 1;
			for (int i = weChatContentMaxIndex; i >= 0; i--) {
				delete(weChatContentList.get(i));
			}
		}
		commonService.delete(weChatPush);
	}

	/***
	 * 获取全部数据
	 *
	 * @return
	 */
	@Override
	public List<WeChatPushEntity> getAllData() {
		CriteriaQuery cq = new CriteriaQuery(WeChatPushEntity.class);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 通过主表id获取关联数据
	 *
	 * @param id
	 * @return
	 */
	@Override
	public List<WeChatContentEntity> getListByPid(String id) {
		CriteriaQuery cq = new CriteriaQuery(WeChatContentEntity.class);
		cq.eq("weixinId", id);
		cq.add();
		return commonService.getListByCriteriaQuery(cq, false);
	}

	/**
	 * 获取分页后的微信内容数据集
	 *
	 * @param weChatContent
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @param id
	 *            主表id
	 * @return weChatContentList微信内容数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getListByPid(WeChatContentEntity weChatContent, Map param,
			int pageSize, int pageNo,String id) {
		CriteriaQuery cq = new CriteriaQuery(WeChatContentEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, weChatContent, param);
		cq.eq("weixinId", String.valueOf(id));
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<WeChatContentEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("weChatContentList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除微信内容
	 *
	 * @param weChatContent
	 */
	@Override
	public void delete(WeChatContentEntity weChatContent) {
		commonService.delete(weChatContent);
	}

	/**
	 * 通过id获取微信内容
	 *
	 * @param id
	 *            微信内容id
	 * @return
	 */
 	@Override
	public WeChatContentEntity getWeChatContentEntity(String id){
		return commonService.getEntity(WeChatContentEntity.class, id);
	}

	/**
	 * 保存微信内容
	 *
	 * @param weChatContent
	 * @return
	 */
	public String save(WeChatContentEntity weChatContent) {
		return (String) commonService.save(weChatContent);
	}

	/**
	 * 更新微信内容
	 * 
	 * @param weChatContent
	 */
	@Override
	public void saveOrUpdate(WeChatContentEntity weChatContent) {
		commonService.saveOrUpdate(weChatContent);
	}
	
}