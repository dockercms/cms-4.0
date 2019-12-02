package com.leimingtech.wechat.service.impl.wechatconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.WechatConfigEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.wechat.service.wechatconfig.WechatConfigServiceI;

/**
 * @Title: interface
 * @Description: 微信参数接口实现
 * @author
 * @date 2015-08-13 14:56:06
 * @version V1.0
 * 
 */
@Service("wechatConfigService")
@Transactional
public class WechatConfigServiceImpl implements WechatConfigServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存微信参数
	 * 
	 * @param wechatConfig
	 * @return
	 */
	public String save(WechatConfigEntity wechatConfig) {
		return (String) commonService.save(wechatConfig);
	}

	/**
	 * 更新微信参数
	 *
	 * @param wechatConfig
	 */
	@Override
	public void saveOrUpdate(WechatConfigEntity wechatConfig) {
		commonService.saveOrUpdate(wechatConfig);
	}

	/**
	 * 通过id获取微信参数
	 *
	 * @param id
	 *            微信参数id
	 * @return
	 */
	@Override
	public WechatConfigEntity getEntity(String id) {
		return commonService.getEntity(WechatConfigEntity.class, id);
	}

	/**
	 * 获取分页后的微信参数数据集
	 * 
	 * @param wechatConfig
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatConfigList微信参数数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(WechatConfigEntity wechatConfig, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(WechatConfigEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, wechatConfig, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<WechatConfigEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("wechatConfigList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除微信参数
	 * 
	 * @param wechatConfig
	 */
	@Override
	public void delete(WechatConfigEntity wechatConfig) {
		commonService.delete(wechatConfig);
	}
	
}