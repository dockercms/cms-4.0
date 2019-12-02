package com.leimingtech.core.service;

import com.leimingtech.core.entity.VideoSourcesEntity;

/**
 * 视频库接口
 * 
 * @author liuzhen 2014年7月22日 13:44:17
 * 
 */
public interface VideoSourcesServiceI extends CommonService {

	/**
	 * 获取视频信息 并且重新生成视频6张展示图
	 * 
	 * @param id
	 *            视频库id
	 * @param flag
	 *            是否只取数据不生成新图
	 * @return 视频信息
	 */
	VideoSourcesEntity getVideoAndnewRandomImage(String id, Boolean flag);

	/**
	 * 生成视频6张展示图
	 * 
	 * @param videopath
	 *            视频路径
	 * @return 是否生成成功
	 */
	Boolean newRandomImage(String videopath);

	/**
	 * 改变视频默认图
	 * 
	 * @param videoSources
	 */
	void changeVideoDefaultImage(VideoSourcesEntity videoSources);

}
