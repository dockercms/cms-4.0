package com.leimingtech.cms.controller.video;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.cms.service.tag.IVideoTagMng;
import com.leimingtech.core.controller.BaseController;

/**
 * 视频标签(前台js加载数据)
 * 
 * @author zhangxiaoqiang 2014年6月3日17:35:50
 *
 */
@Controller
@RequestMapping("/front/videoTagData")
public class VideoTagAction extends BaseController {
	@Autowired
	private IVideoTagMng videoMng;
	@RequestMapping(params = "getVideo")
	public ModelAndView getVideo(HttpServletRequest request,String videourl ){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("videourl", videourl);
		return new ModelAndView("/cms/tag/videoPlayer", m);
	}
}
