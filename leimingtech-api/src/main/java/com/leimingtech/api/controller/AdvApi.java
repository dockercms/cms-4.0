package com.leimingtech.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.cms.entity.advertising.AdvertisingEntity;
import com.leimingtech.cms.entity.advertising.AdvertisingSpaceEntity;
import com.leimingtech.cms.service.advertising.AdvertisingServiceI;
import com.leimingtech.cms.service.advertising.AdvertisingSpaceServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;

/**
 * 广告接口
 * 
 * @author liuzhen 2015年4月1日 09:40:15
 * 
 */
@Controller
@RequestMapping("/front/advApi")
public class AdvApi {

	/** 广告栏目接口 */
	@Autowired
	private AdvertisingSpaceServiceI advertisingSpaceService;
	/** 广告接口 */
	@Autowired
	private AdvertisingServiceI advertisingService;
	/** App启动图栏位id */
	private static final String START_PIC_ID = "402881834e046e48014e046f81b20001";

	/**
	 * app启动图 <br/>
	 * 使用广告、广告位做启动图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startPic")
	@ResponseBody
	public JSONObject startPic(HttpServletRequest request) {

		JSONObject j = new JSONObject();

		AdvertisingSpaceEntity advP = advertisingSpaceService.getEntity(AdvertisingSpaceEntity.class, START_PIC_ID);
		if (advP == null || "0".equals(advP.getIsEnabled())) {
			j.accumulate("resultCode", 0);
			j.accumulate("resultMsg", "App启动图不存在");
			return j;
		}

		CriteriaQuery cq = new CriteriaQuery(AdvertisingEntity.class);
		cq.eq("advertisingSpace.id", START_PIC_ID);
		cq.eq("isEnabled", "1");
		Date date = new Date();
		cq.or(Restrictions.isNull("endTime"), Restrictions.gt("endTime", date));
		cq.lt("startTime", date);
		cq.addOrder("adWeight", SortDirection.desc);
		cq.add();
		List<AdvertisingEntity> advList = advertisingService.getListByCriteriaQuery(cq, false);
		if (advList == null || advList.size() == 0) {
			j.accumulate("resultCode", 0);
			j.accumulate("resultMsg", "App启动图不存在");
			return j;
		}

		j.accumulate("resultCode", 1);
		j.accumulate("resultMsg", "获取App启动图成功");
		j.accumulate("startPicUrl", advList.get(0).getImgUrl());

		return j;
	}
}
