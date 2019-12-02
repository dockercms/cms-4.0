package com.leimingtech.core.service;

import java.util.List;

import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.VideoalburmarticleEntity;

public interface VideoalburmarticleServiceI extends CommonService{
	
	/**
	 * 根据视频专辑id获取视频列表
	 * @param ablrumid
	 * @return
	 */
	List<ContentsEntity> videoAblurmArticleList(String ablrumid);
	
	/**
	 * 保存视频专辑视频
	 * @param blurmArticle
	 */
	void saveOrUpdateAlburmArticle(VideoalburmarticleEntity blurmArticle);
	
	/**
	 * 删除专辑中的视频
	 * @param blurmArticle
	 */
	void deleteAlburmArticle(VideoalburmarticleEntity blurmArticle);
	
	/**
	 * 根据内容id获取专辑中的视频
	 * @param contentId
	 * @return
	 */
	VideoalburmarticleEntity getVideoAlburmArticleByContentId(String contentId);
}
