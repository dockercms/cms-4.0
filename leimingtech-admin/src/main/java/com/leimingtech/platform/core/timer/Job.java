package com.leimingtech.platform.core.timer;

import org.springframework.stereotype.Component;

@Component
public class Job {
	// private static long time=0;
	/*
	 * @Scheduled(cron="0 0/10 * * * ?") public void s10(){
	 * 
	 * System.out.println("建立索引 开始"); luceneServiceImpl.creatIndex();
	 * System.out.println("建立索引 结束");
	 * 
	 * //org.lmcmsframework.core.util.LogUtil.info("==== 十秒执行一次=======10s");
	 * //一分钟进入方法一次 if(System.currentTimeMillis()-time > 60*1000){
	 * System.out.println("建立索引 开始"); luceneServiceImpl.creatIndex();
	 * System.out.println("建立索引 结束"); time = System.currentTimeMillis();
	 * System.out.println(time); } }
	 */
	//
	// @Scheduled(cron="0 */1 * * * *")
	// public void m1(){
	// org.lmcmsframework.core.util.LogUtil.info("1m");
	// }

	/**
	 * 每天1点执行一次
	 * */
	// @Scheduled(cron="0 0 1 * * ?")
	// public void oneOClockPerDay(){
	// org.lmcmsframework.core.util.LogUtil.info("1h");
	// }
}