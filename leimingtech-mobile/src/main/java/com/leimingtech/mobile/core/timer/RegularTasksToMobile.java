package com.leimingtech.mobile.core.timer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.service.StaticMobileHtmlServiceI;
import com.leimingtech.core.util.LogUtil;

@Component
public class RegularTasksToMobile {
	@Autowired
	private StaticMobileHtmlServiceI staticMobileImpl;// 移动客户端发布
	
	@Autowired
	private SiteServiceI siteService;
	
	/**
	 * 定时发布移动内容，每10分钟执行一次
	 */
	@Scheduled(cron = "0 0/35 * * * ?")
	public void publishMobileContentTenMinutes() {
		Long time=System.currentTimeMillis();
		LogUtil.info("定时发布移动内容开始");
		List<SiteEntity> siteList = new ArrayList<SiteEntity>();
		siteList = siteService.siteList();
		for (SiteEntity site : siteList) {
			try {
				staticMobileImpl.staticAllMobile(site);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LogUtil.info("定时发布移动内容完成，耗时："+(System.currentTimeMillis()-time)+"毫秒");
	}

}
