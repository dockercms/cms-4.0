package com.leimingtech.cms.tag.lmtag;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.cms.entity.VideoAblurmDTO;
import com.leimingtech.cms.service.videoalburm.VideoalburmServiceI;
import com.leimingtech.core.common.BaseFreeMarkerTag;

import freemarker.template.TemplateModelException;

/**
 * 视频专辑
 * @author LKANG
 *
 */
@Component
public class VideoAblurmTag extends BaseFreeMarkerTag{
	
	@Autowired
	private VideoalburmServiceI videoalburmService;
	
	@Override
	protected Object exec(Map params) throws TemplateModelException {
		List<VideoAblurmDTO> ablurmdtoList = videoalburmService.videoAlburmList();
		return ablurmdtoList;
	}
}
