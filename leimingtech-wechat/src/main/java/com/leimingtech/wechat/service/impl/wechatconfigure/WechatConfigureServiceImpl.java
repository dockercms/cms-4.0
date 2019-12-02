package com.leimingtech.wechat.service.impl.wechatconfigure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.wechat.entity.wechatconfigure.WechatConfigureEntity;
import com.leimingtech.wechat.service.wechatconfigure.WechatConfigureServiceI;

/**
 * @Title: interface
 * @Description: 微信公众账号配置接口实现
 * @author
 * @date 2015-12-02 15:53:37
 * @version V1.0
 * 
 */
@Service("wechatConfigureService")
@Transactional
public class WechatConfigureServiceImpl implements WechatConfigureServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存微信公众账号配置
	 * 
	 * @param wechatConfigure
	 * @return
	 */
	public String save(WechatConfigureEntity wechatConfigure) {
		return (String) commonService.save(wechatConfigure);
	}

	/**
	 * 更新微信公众账号配置
	 *
	 * @param wechatConfigure
	 */
	@Override
	public void saveOrUpdate(WechatConfigureEntity wechatConfigure) {
		commonService.saveOrUpdate(wechatConfigure);
	}

	/**
	 * 通过id获取微信公众账号配置
	 *
	 * @param id
	 *            微信公众账号配置id
	 * @return
	 */
	@Override
	public WechatConfigureEntity getEntity(String id) {
		return commonService.getEntity(WechatConfigureEntity.class, id);
	}

	/**
	 * 获取分页后的微信公众账号配置数据集
	 * 
	 * @param wechatConfigure
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return wechatConfigureList微信公众账号配置数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(WechatConfigureEntity wechatConfigure, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(WechatConfigureEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, wechatConfigure, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<WechatConfigureEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("wechatConfigureList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除微信公众账号配置
	 * 
	 * @param wechatConfigure
	 */
	@Override
	public void delete(WechatConfigureEntity wechatConfigure) {
		commonService.delete(wechatConfigure);
	}
	
}