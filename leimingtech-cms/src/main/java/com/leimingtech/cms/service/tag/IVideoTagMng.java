package com.leimingtech.cms.service.tag;

import java.util.Map;

import com.leimingtech.core.service.CommonService;

/**
 * 文档标签接口
 * 
 * @author zhangxiaoqiang 2014年6月3日11:56:50
 * 
 */
public interface IVideoTagMng extends CommonService {

	Object getVideoByTag(Map params);

}
