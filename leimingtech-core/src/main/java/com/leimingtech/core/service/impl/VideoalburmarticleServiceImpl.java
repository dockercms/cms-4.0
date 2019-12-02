package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.VideoalburmarticleEntity;
import com.leimingtech.core.service.VideoalburmarticleServiceI;

@Service("videoalburmarticleService")
@Transactional
public class VideoalburmarticleServiceImpl extends CommonServiceImpl implements VideoalburmarticleServiceI {
	/**
	 * 根据视频专辑id获取视频列表
	 * @param ablurmId
	 * @return
	 */
	public List<ContentsEntity> videoAblurmArticleList(Integer ablurmId) {
		// 视频专辑列表
		List<VideoalburmarticleEntity> alburmlist = new ArrayList<VideoalburmarticleEntity>();
		// 视频列表
		List<ContentsEntity> contentslist = new ArrayList<ContentsEntity>();
		alburmlist = findByProperty(VideoalburmarticleEntity.class, "alburmid", ablurmId);
		for (VideoalburmarticleEntity videoalburmarticle : alburmlist) {
			String articleid = videoalburmarticle.getArticleid();
			ContentsEntity contents = get(ContentsEntity.class, articleid);
			contentslist.add(contents);
		}
		return contentslist;
	}
	
	
	/**
	 * 保存视频专辑视频
	 * @param blurmArticle
	 */
	public void saveOrUpdateAlburmArticle(VideoalburmarticleEntity blurmArticle) {
		saveOrUpdate(blurmArticle);
	}
	
	/**
	 * 删除专辑中的视频
	 * @param blurmArticle
	 */
	public void deleteAlburmArticle(VideoalburmarticleEntity blurmArticle) {
		delete(blurmArticle);
	}
	
	/**
	 * 根据内容id获取专辑中的视频
	 * @param contentId
	 * @return
	 */
	public VideoalburmarticleEntity getVideoAlburmArticleByContentId(
			String contentId) {
		VideoalburmarticleEntity blurmArticle = new VideoalburmarticleEntity();
		blurmArticle = findUniqueByProperty(VideoalburmarticleEntity.class, "articleid", contentId);
		return blurmArticle;
	}


	@Override
	public List<ContentsEntity> videoAblurmArticleList(String ablrumid) {
		// TODO Auto-generated method stub
		return null;
	}


}