package com.leimingtech.cms.service.impl.constants;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.ContentsOperateServiceI;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.PlatFormUtil;

/**
 * 移动内容操作接口实现
 * 
 * @author liuzhen 2015-1-21 12:18:45
 * 
 */
@Service("contentsOperateService")
@Transactional
public class ContentsOperateServiceImpl extends CommonServiceImpl implements ContentsOperateServiceI {

	/**
	 * 取消置顶
	 * 
	 * @param contentId
	 *            内容id
	 * @return 是否操作成功
	 */
	@Override
	public Boolean cancelTop(String contentId) {
		try {
			ContentsEntity m = getEntity(ContentsEntity.class, String.valueOf(contentId));
			m.setIsTop(0);
			saveOrUpdate(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取当前移动内容中最大置顶值
	 * 
	 * @return
	 */
	@Override
	public Integer getCurrentMaxTop() {
		String siteId = PlatFormUtil.getSessionSiteId();
		String sql = " select max(is_top)  max_top from cms_content where SITE_ID='" + siteId + "' ";
		List<Map<String, Object>> data = findForJdbc(sql, null);
		if (data != null && data.size() > 0) {
			return Integer.valueOf(data.get(0).get("max_top").toString());
		}
		return 0;
	}

	/**
	 * 设置置顶内容置顶
	 * 
	 * @param contentId
	 *            内容id
	 * @param setTopValue
	 *            置顶值
	 * @return 是否操作成功
	 */
	@Override
	public Boolean setTop(String contentId, Integer setTopValue) {
		try {
			ContentsEntity m = getEntity(ContentsEntity.class, String.valueOf(contentId));
			m.setIsTop(setTopValue);
			saveOrUpdate(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

}
