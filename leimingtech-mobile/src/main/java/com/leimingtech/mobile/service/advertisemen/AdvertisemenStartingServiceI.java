package com.leimingtech.mobile.service.advertisemen;

import java.util.Map;

import com.leimingtech.core.service.CommonService;

public interface AdvertisemenStartingServiceI extends CommonService{
	Map<String, Object> getListByPage(Integer pageNo ,Integer pageSize);
}
