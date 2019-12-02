package com.leimingtech.cms.service.acquisition;

import com.leimingtech.cms.entity.acquisition.AcquisitionEntity;
import com.leimingtech.core.service.CommonService;
/**
 * 数据采集管理
 * @author liuzhen 2014年4月17日 10:03:22
 *
 */
public interface AcquisitionServiceI extends CommonService{
	/**
	 * 判断有多少已经开始采集数据
	 * @return
	 */
	public Integer hasStarted();
	
	/**
	 * 将需要采集的加入到队列
	 * @param ids 需要采集的ID
	 * @param queueNum 当前队列ID
	 */
	public void addToQueue(String[] ids, Integer queueNum);
	
	/**
	 * 开始采集时改变状态为采集状态
	 * 
	 * @param id
	 *            采集管理ID
	 * @return AcquisitionEntity
	 */
	public AcquisitionEntity startAcq(String id);

	public String getPath(String outFileName);

	public AcquisitionEntity popAcquFromQueue();

	/**
	 * 结束采集
	 * 
	 * @param id
	 *            需要结束采集的ID
	 */
	public void end(String id);

	public String[] getAllPlans(AcquisitionEntity acqu);

	/**
	 * 判断当前采集的数据是否需要关闭
	 * 
	 * @param id
	 *            当前采集的数据Id
	 * @param currNum
	 * @param currItem
	 * @param totalItem
	 * @return
	 */
	public boolean isNeedBreak(String id, int i, int j, int size);

	public boolean getImgAcqu(AcquisitionEntity acqu);
}
