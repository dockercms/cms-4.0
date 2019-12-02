package com.leimingtech.cms.tag.lmtag;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.entity.VideoAblurmDTO;
import com.leimingtech.cms.service.videoalburm.VideoalburmServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 视频专辑根据内容获取视频所属视频专辑
 * @author LKANG
 *
 */
@Component
public class VideoAblurm2Tag extends BaseFreeMarkerTag {
	@Autowired
	private VideoalburmServiceI videoalburmService;
	
	/**
	 * 初始化标签数据
	 */
	@SuppressWarnings("rawtypes")
	protected Object exec(Map params) throws TemplateModelException {
		String contentid = params.get("contentid") + "";
		VideoAblurmDTO videoDTO = videoalburmService.getVideoAblurmByContentId(contentid);
		return videoDTO;
	}

}
