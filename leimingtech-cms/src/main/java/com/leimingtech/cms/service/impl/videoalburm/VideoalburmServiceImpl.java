package com.leimingtech.cms.service.impl.videoalburm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.entity.VideoAblurmDTO;
import com.leimingtech.cms.entity.VideoalburmEntity;
import com.leimingtech.cms.service.videoalburm.VideoalburmServiceI;
import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.ContentBean;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.extend.hqlsearch.HqlGenerateUtil;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.VideoalburmarticleServiceI;
import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.StringUtil;

/**
 * 视频专辑实现
 * @author LKANG
 */
@Service("videoalburmService")
@Transactional
public class VideoalburmServiceImpl extends CommonServiceImpl implements VideoalburmServiceI {
	
	@Autowired
	private VideoalburmarticleServiceI videoalburmarticleService;
	@Autowired
	private ContentsServiceI contentsService;
	/**
	 * 获取视频专辑列表
	 * @return
	 */
	public List<VideoAblurmDTO> videoAlburmList() {
		// 大实体列表
		List<VideoAblurmDTO> videoDTOList = new ArrayList<VideoAblurmDTO>();
		// 视频专辑列表
		List<VideoalburmEntity> list = new ArrayList<VideoalburmEntity>();
		list = findByProperty(VideoalburmEntity.class, "siteId", PlatFormUtil.getSessionSiteId());
		try {
			for (VideoalburmEntity videoalburm : list) {
				VideoAblurmDTO ablurmdto = new VideoAblurmDTO();
				String ablrumid = videoalburm.getId();
				// 视频列表
				List<ContentsEntity> contentslist = new ArrayList<ContentsEntity>();
				contentslist = videoalburmarticleService.videoAblurmArticleList(ablrumid);
				// 复制视频
				MyBeanUtils.copyBeanNotNull2Bean(videoalburm, ablurmdto);
				ablurmdto.setContentslist(contentslist);
				videoDTOList.add(ablurmdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return videoDTOList;
	}
	
	/**
	 * 获取视频专辑分页列表
	 * @param pageSize
	 * @param pageNo
	 * @param param
	 * @param videoAlburm
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PageList videoAblurmPageList(int pageSize, int pageNo, Map param,
			VideoalburmEntity videoAlburm) {
		
		CriteriaQuery cq = new CriteriaQuery(VideoalburmEntity.class, pageSize, pageNo, "", "");
		HqlGenerateUtil.installHql(cq, videoAlburm, param);
		//排序条件
		cq.addOrder("id", SortDirection.desc);
		PageList pageList = getPageList(cq, true);
		return pageList;
	}
	
	/**
	 * 根据id获取视频专辑
	 * @param alburmId
	 * @return
	 */
	public VideoalburmEntity getVideoAlburmById(String alburmId) {
		VideoalburmEntity videoAlburm = new VideoalburmEntity();
		videoAlburm = getEntity(VideoalburmEntity.class, alburmId);
		return videoAlburm;
	}
	
	/**
	 * 根据内容id获取所属视频专辑及专辑下面的所有视频
	 * @param contentid
	 * @return
	 */
	public VideoAblurmDTO getVideoAblurmByContentId(String contentid) {
		VideoAblurmDTO videoDTO = new VideoAblurmDTO();
		try {
			// 根据id获取内容
			ContentBean content = contentsService.loadContentDetail(contentid);
			ContentVideoEntity contentvideo = content.getVideo();
			String ablurmId = contentvideo.getSpecial();
			if(StringUtil.isNotEmpty(ablurmId)){
				VideoalburmEntity videoAlburm = getVideoAlburmById(String.valueOf(ablurmId));
				// 复制视频
				MyBeanUtils.copyBeanNotNull2Bean(videoAlburm, videoDTO);
				// 视频列表
				List<ContentsEntity> contentslist = new ArrayList<ContentsEntity>();
				contentslist = videoalburmarticleService.videoAblurmArticleList(ablurmId);
				videoDTO.setContentslist(contentslist);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return videoDTO;
	}


	
}