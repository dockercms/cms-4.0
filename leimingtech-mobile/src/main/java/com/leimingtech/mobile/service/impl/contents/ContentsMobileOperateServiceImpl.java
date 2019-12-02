package com.leimingtech.mobile.service.impl.contents;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.mobile.service.contents.ContentsMobileOperateServiceI;

/**
 * 移动内容操作接口实现
 * 
 * @author liuzhen 2015-1-21 12:18:45
 * 
 */
@Service("contentsMobileOperateService")
@Transactional
public class ContentsMobileOperateServiceImpl extends CommonServiceImpl implements ContentsMobileOperateServiceI {

	/**
	 * 取消置顶
	 * 
	 * @param contentId
	 *            移动内容id
	 * @return 是否操作成功
	 */
	@Override
	public Boolean cancelTop(String contentId) {
		try {
			ContentsMobileEntity m = getEntity(ContentsMobileEntity.class, String.valueOf(contentId));
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
		String sql = " select max(is_top)  max_top from cm_content where SITE_ID='" + siteId + "' ";
		List<Map<String, Object>> data = findForJdbc(sql, null);
		if (data != null && data.size() > 0) {
			return Integer.valueOf(data.get(0).get("max_top").toString());
		}
		return 0;
	}

	/**
	 * 设置置顶移动内容置顶
	 * 
	 * @param contentId
	 *            移动内容id
	 * @param setTopValue
	 *            置顶值
	 * @return 是否操作成功
	 */
	@Override
	public Boolean setTop(String contentId, Integer setTopValue) {
		try {
			ContentsMobileEntity m = getEntity(ContentsMobileEntity.class, String.valueOf(contentId));
			m.setIsTop(setTopValue);
			saveOrUpdate(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 更改移动头图<br/>
	 * 调用方法如果当前内容已经为头图将会取消头图,否则将设为头图
	 * 
	 * @param contentId
	 * @return
	 */
	@Override
	public Boolean changeTopPic(String contentId) {
		try {
			ContentsMobileEntity m = getEntity(ContentsMobileEntity.class, String.valueOf(contentId));
			if (0 == m.getIsTopPic()) {
				m.setIsTopPic(1);
			} else {
				m.setIsTopPic(0);
			}
			saveOrUpdate(m);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
