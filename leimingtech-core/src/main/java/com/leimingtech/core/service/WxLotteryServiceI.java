package com.leimingtech.core.service;

import com.leimingtech.core.entity.LotteryEntity;


/**
 * 刮刮卡、大转盘、砸金蛋接口
 * @author liuzhen 2015-3-11 09:49:29
 *
 */
public interface WxLotteryServiceI extends CommonService{

	/**
	 * 静态化大转盘页面
	 * @param wxLottery
	 */
	void staticDaZhuanPan(LotteryEntity wxLottery);
	
	/**
	 * 静态化砸金蛋页面
	 * @param wxLottery
	 */
	void staticZaJinDan(LotteryEntity wxLottery);
	
}
