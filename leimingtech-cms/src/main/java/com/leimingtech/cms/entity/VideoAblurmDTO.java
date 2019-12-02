package com.leimingtech.cms.entity;

import java.util.List;

import com.leimingtech.core.entity.ContentsEntity;

/**
 * 视频专辑大实体
 * @author LKANG
 */
@SuppressWarnings("serial")
public class VideoAblurmDTO extends VideoalburmEntity {
	/**视频列表*/
	private List<ContentsEntity> contentslist;

	public List<ContentsEntity> getContentslist() {
		return contentslist;
	}

	public void setContentslist(List<ContentsEntity> contentslist) {
		this.contentslist = contentslist;
	}
	
	
}
