package com.leimingtech.cms.core.timer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.service.LuceneServiceI;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SiteEntity;
import com.leimingtech.core.service.ContentIllegalServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.SiteServiceI;
import com.leimingtech.core.util.LogUtil;

@Component
public class RegularTasksToCMS {
	@Autowired
	private LuceneServiceI luceneServiceImpl;
	@Autowired
	private IStatic staticImpl;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private ContentIllegalServiceI contentIllegalServiceImpl;
	@Autowired
	private SiteServiceI siteService;

	// private static long time=0;
	/*
	 * @Scheduled(cron="0 0/10 * * * ?") public void s10(){
	 * 
	 * System.out.println("建立索引 开始"); luceneServiceImpl.creatIndex();
	 * System.out.println("建立索引 结束");
	 * 
	 * //LogUtil.info("==== 十秒执行一次=======10s"); //一分钟进入方法一次
	 * if(System.currentTimeMillis()-time > 60*1000){
	 * System.out.println("建立索引 开始"); luceneServiceImpl.creatIndex();
	 * System.out.println("建立索引 结束"); time = System.currentTimeMillis();
	 * System.out.println(time); } }
	 */
	//
	// @Scheduled(cron="0 */1 * * * *")
	// public void m1(){
	// LogUtil.info("1m");
	// }

	/**
	 * 每天1点执行一次
	 * */
	// @Scheduled(cron="0 0 1 * * ?")
	// public void oneOClockPerDay(){
	// LogUtil.info("1h");
	// }

	/**
	 * 定时发布内容，每10分钟执行一次
	 */
	@Scheduled(cron = "0 0/30 * * * ?")
	public void publishContentTenMinutes() {
		List<SiteEntity> siteList = new ArrayList<SiteEntity>();
		siteList = siteService.siteList();
		for (SiteEntity siteEntity : siteList) {
			if(siteEntity.getIsSwitch().equals("0")){
				long curTime = System.currentTimeMillis();
				try {
					LogUtil.info("站点《" + siteEntity.getSiteName() + "》开始发布");
					staticImpl.staticSite(siteEntity, -1);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					LogUtil.info("站点《" + siteEntity.getSiteName() + "》发布结束\t总耗时："
							+ (System.currentTimeMillis() - curTime) + "毫秒");
				}
			}	
		}
	}

	/**
	 * 定时下架内容，每1分钟执行一次
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void oneOClockPerDay() {
		List<ContentsEntity> list = new ArrayList<ContentsEntity>();
		try {
			list = contentsService.getOfflineContent();
			for (ContentsEntity contentsEntity : list) {
				System.out.println("id:" + contentsEntity.getId() + "《"
						+ contentsEntity.getTitle() + "》\t已下架");
				contentIllegalServiceImpl.delContentHtmlFile(contentsEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定时生成索引, 每40分钟执行一次
	 */
	@Scheduled(cron = "0 0/40 * * * ?")
	public void s10() {
		LogUtil.info("建立索引 开始");
		long starttime = System.currentTimeMillis();
		List<SiteEntity> siteList = new ArrayList<SiteEntity>();
		siteList = siteService.siteList();
		for (SiteEntity siteEntity : siteList) {
			luceneServiceImpl.creatIndex(siteEntity);
		}
		LogUtil.info("建立索引 结束 生成索引耗时"
				+ (System.currentTimeMillis() - starttime) + "毫秒.");
	}
}
