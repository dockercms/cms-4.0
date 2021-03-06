package com.leimingtech.mobile.service.impl.feedback;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.mobile.entity.feedback.FeedbackEntity;
import com.leimingtech.mobile.service.feedback.FeedbackServiceI;

@Service("feedbackService")
@Transactional
public class FeedbackServiceImpl extends CommonServiceImpl implements FeedbackServiceI {

	@Override
	public Map<String, Object> getListByPage(Integer pageNo, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>(); // 返回的结果的容器
		List<FeedbackEntity> list = null; // 返回的内容
		int pageCount = 0; // 返回最大页码
		pageNo = pageNo < 1 ? 1 : pageNo;
		pageSize = (pageSize == null || pageSize == 0) ? 10 : pageSize;
		// 查询方法的封装，需要的参数是当前操作的实体类
		CriteriaQuery query = new CriteriaQuery(FeedbackEntity.class, pageSize, pageNo, "", "");
		// 排序
		query.addOrder("id", SortDirection.desc);
		// 加载参数
		query.add();
		// 分页查询结果封装类
		PageList pageList = this.getPageList(query, true);
		list = pageList.getResultList();
		pageCount = (int) Math.ceil((double) pageList.getCount() / (double) pageSize);
		if (pageCount <= 0) {
			pageCount = 1;
		}
		if (list != null & list.size() > 0) { // 判断是否有数据
			map.put("list", list);
		} else {
			map.put("list", "空值");
		}

		map.put("pageCount", pageCount);
		map.put("pageSize", pageSize);

		return map;
	}

	@Override
	public Boolean saveEntity(FeedbackEntity entity) {
		try {
			entity.setCreatedtime(new Date());//创建时间
			this.save(entity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}