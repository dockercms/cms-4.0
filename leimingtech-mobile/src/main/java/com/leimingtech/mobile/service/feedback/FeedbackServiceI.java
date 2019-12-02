package com.leimingtech.mobile.service.feedback;

import java.util.Map;

import com.leimingtech.core.service.CommonService;
import com.leimingtech.mobile.entity.feedback.FeedbackEntity;

public interface FeedbackServiceI extends CommonService{
	public Map<String, Object> getListByPage(Integer pageNo, Integer pageSize);
	public Boolean saveEntity(FeedbackEntity entity);
}
