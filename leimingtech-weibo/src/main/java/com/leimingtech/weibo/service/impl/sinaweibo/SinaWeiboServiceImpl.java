package com.leimingtech.weibo.service.impl.sinaweibo;

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
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.weibo.entity.sinaweibo.SinaWeiboEntity;
import com.leimingtech.weibo.service.sinaweibo.SinaWeiboServiceI;

/**
 * @Title: interface
 * @Description: 新浪微博接口实现
 * @author
 * @date 2015-12-03 14:22:46
 * @version V1.0
 * 
 */
@Service("sinaWeiboService")
@Transactional
public class SinaWeiboServiceImpl implements SinaWeiboServiceI {
	
	/** 公共Dao接口 */
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存新浪微博
	 * 
	 * @param sinaWeibo
	 * @return
	 */
	public java.lang.String save(SinaWeiboEntity sinaWeibo) {
		return (java.lang.String) commonService.save(sinaWeibo);
	}

	/**
	 * 更新新浪微博
	 * 
	 * @param sinaWeibo
	 */
	@Override
	public void saveOrUpdate(SinaWeiboEntity sinaWeibo) {
		commonService.saveOrUpdate(sinaWeibo);
	}

	/**
	 * 通过id获取新浪微博
	 * 
	 * @param id
	 *            新浪微博id
	 * @return
	 */
	@Override
	public SinaWeiboEntity getEntity(java.lang.String id) {
		return commonService.getEntity(SinaWeiboEntity.class, id);
	}

	/**
	 * 获取分页后的新浪微博数据集
	 * 
	 * @param sinaWeibo
	 * @param param
	 *            字段结尾含有"_begin"或"_end"的查询条件，无此类型时传参null
	 * @param pageSize
	 *            每页获取数量
	 * @param pageNo
	 *            当前页码
	 * @return sinaWeiboList新浪微博数据集 pageCount总页数
	 */
	@Override
	public Map<String, Object> getPageList(SinaWeiboEntity sinaWeibo, Map param,
			int pageSize, int pageNo) {
		CriteriaQuery cq = new CriteriaQuery(SinaWeiboEntity.class, pageSize, pageNo,
				"", "");
		// 查询条件组装器
		HqlGenerateUtil.installHql(cq, sinaWeibo, param);
		String site=PlatFormUtil.getSessionSite().getId();
		cq.eq("siteId", site);
		cq.add();
		PageList pageList = commonService.getPageList(cq, true);
		List<SinaWeiboEntity> resultList = pageList.getResultList();

		int pageCount = (int) Math.ceil((double) pageList.getCount()
				/ (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("sinaWeiboList", resultList);
		m.put("pageCount", pageCount);
		return m;
	}

	/**
	 * 删除新浪微博
	 * 
	 * @param sinaWeibo
	 */
	@Override
	public void delete(SinaWeiboEntity sinaWeibo) {
		commonService.delete(sinaWeibo);
	}

	@Override
	public String getWeiboinfo(String id, String site) {
		List<String> info=commonService.findListbySql("select "+id+" from cms_weibo where site_id="+site);
		if(info.isEmpty()){
		return null;
		}else{
		return info.get(0);
		}
	}

	@Override
	public SinaWeiboEntity getSitEntity(String id) {
		CriteriaQuery cq = new CriteriaQuery(SinaWeiboEntity.class);
		String site=PlatFormUtil.getSessionSite().getId();
		cq.eq("siteId", site);
		cq.add();
		PageList pageList = commonService.getPageList(cq, false);
		List<SinaWeiboEntity> resultList = pageList.getResultList();
		if(resultList.size()>0){
			SinaWeiboEntity sinaWeibo=resultList.get(0);
			return sinaWeibo;
		}else{
			SinaWeiboEntity sinaWeibo=new SinaWeiboEntity();
			return sinaWeibo;
		}
		
		
	}
	
}