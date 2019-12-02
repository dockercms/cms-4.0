package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentsEntity;

public interface ContentVideoServiceI extends CommonService{

	/**
	 * 根据内容id获取视频列表
	 * @param contentid
	 * @return
	 */
	List<ContentVideoEntity> videoList(String contentid);

	/**
	 * 保存视频
	 * @param contents
	 * @param contentVideo
	 */
	void saveVideo(ContentsEntity contents,ContentVideoEntity contentVideo);
	/**
	 * 修改视频
	 * @param contents
	 * @param contentVideo
	 * @param contentcat
	 * @param temporary
	 * @param divValue
	 */
	void updateVideo(ContentsEntity contents,ContentVideoEntity contentVideo,ContentCatEntity contentcat,String temporary,String divValue);
}
