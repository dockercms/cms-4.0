package com.leimingtech.cms.service.impl.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.tag.IVideoTagMng;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.impl.CommonServiceImpl;

/**
 * 视频标签接口实现
 * 
 * @author zhangxiaoqiang 2014年6月3日13:11:42
 * 
 */
@Service("videoTagMng")
@Transactional
public class VideoTagMng extends CommonServiceImpl implements
		IVideoTagMng {

	/**
	 * 获取视频
	 */
	@Override
	public Object getVideoByTag(Map params) {
		if (MapUtils.isEmpty(params))
			return null;

		String contentid = (String) params.get("contentid");
		if (StringUtils.isEmpty(contentid) || !StringUtils.isNumeric(contentid))
			return null;
		CriteriaQuery cq = new CriteriaQuery(ContentVideoEntity.class);
		cq.eq("contentid", Integer.valueOf(contentid));
		cq.add();
		List<ContentVideoEntity> videoList = getListByCriteriaQuery(cq,false);
		Map<String, Object> map = new HashMap<String, Object>();
		if (videoList != null && videoList.size() > 0) {
			ContentsEntity contents = getEntity(ContentsEntity.class, Integer.valueOf(contentid));

			map.put("video", videoList.get(0));
			map.put("contents", contents);
		}
		return map;
	}

}
