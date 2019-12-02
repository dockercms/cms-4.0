package com.leimingtech.cms.service.videoalburm;

import java.util.List;
import java.util.Map;

import com.leimingtech.cms.entity.VideoAblurmDTO;
import com.leimingtech.cms.entity.VideoalburmEntity;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.CommonService;

/**
 * 视频专辑接口
 * @author LKANG
 */
public interface VideoalburmServiceI extends CommonService{
	/**
	 * 获取视频专辑列表
	 * @return
	 */
	List<VideoAblurmDTO> videoAlburmList();  
	
	/**
	 * 获取视频专辑分页列表
	 * @param pageSize
	 * @param pageNo
	 * @param param
	 * @param videoAlburm
	 * @return
	 */
	PageList videoAblurmPageList(int pageSize, int pageNo, Map param, VideoalburmEntity videoAlburm);
	
	/**
	 * 根据id获取视频专辑
	 * @param alburmId
	 * @return
	 */
	VideoalburmEntity getVideoAlburmById(String alburmId);
	
	/**
	 * 根据内容id获取所属视频专辑及专辑下面的所有视频
	 * @param contentid
	 * @return
	 */
	VideoAblurmDTO getVideoAblurmByContentId(String contentid);
}
