package com.leimingtech.core.service.impl.departchannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.TSDepartChannel;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;
import com.leimingtech.core.service.departchannel.TSDepartChannelServiceI;

/**
 * @Title: interface
 * @Description: 部门下关联栏目接口实现
 * @author
 * @date 2016-04-22 14:53:34
 * @version V1.0
 * 
 */
@Service("tSDepartChannelService")
@Transactional
public class TSDepartChannelServiceImpl implements TSDepartChannelServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存部门下关联栏目
	 * 
	 * @param tSDepartChannel
	 * @return
	 */
	public java.lang.String save(TSDepartChannel tSDepartChannel) {
		return (java.lang.String) commonService.save(tSDepartChannel);
	}

	/**
	 * 更新部门下关联栏目
	 * 
	 * @param tSDepartChannel
	 */
	@Override
	public void saveOrUpdate(TSDepartChannel tSDepartChannel) {
		commonService.saveOrUpdate(tSDepartChannel);
	}

	/**
	 * 通过id获取部门下关联栏目
	 * 
	 * @param id
	 *            部门下关联栏目id
	 * @return
	 */
	@Override
	public TSDepartChannel getEntity(java.lang.String id) {
		return commonService.getEntity(TSDepartChannel.class, id);
	}

	/**
	 * 获取分页后的部门下关联栏目数据集
	 * 
	 * @param tSDepartChannel
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return tSDepartChannelList部门下关联栏目数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(TSDepartChannel tSDepartChannel, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(TSDepartChannel.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, tSDepartChannel, param);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<TSDepartChannel> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("tSDepartChannelList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除部门下关联栏目
	 * 
	 * @param tSDepartChannel
	 */
	@Override
	public void delete(TSDepartChannel tSDepartChannel) {
		commonService.delete(tSDepartChannel);
	}

	/**
	 * 根据栏目删除部门与栏目的关联
	 * @param idArray
	 */
	@Override
	public void deleteByContentCats(String[] idArray) {
		if (ArrayUtils.isEmpty(idArray)) {
			return;
		}

		for (String string : idArray) {
			List<TSDepartChannel> departChannelList = this.findByContentCat(string);
			if (departChannelList != null&& departChannelList.size()>0 ) {
				for (TSDepartChannel tsDepartChannel : departChannelList) {
					this.delete(tsDepartChannel);
				}
			}
		}
		
	}

	/**
	 * 通过栏目id获取
	 * 
	 * @param channelId
	 * @return
	 */
	private List<TSDepartChannel> findByContentCat(String channelId) {
		return commonService.findByProperty(TSDepartChannel.class,
				"channel.id", channelId);
	}
	
	/**
	 * 通过部门id获取
	 * 
	 * @param channelId
	 * @return
	 */
	private List<TSDepartChannel> findByDepart(String departId) {
		return commonService.findByProperty(TSDepartChannel.class,
				"depart.id", departId);
	}

	/**
	 * 根据部门删除栏目跟部门间的关联
	 */
	@Override
	public void deleteByDepart(String departId) {
		List<TSDepartChannel> departChannelList=findByDepart(departId);
		if(departChannelList!=null && departChannelList.size()>0){
			for (TSDepartChannel tsDepartChannel : departChannelList) {
				this.delete(tsDepartChannel);
			}
		}
	}
	
}