package com.leimingtech.core.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.common.CmsConstants;
import com.leimingtech.core.entity.VideoSourcesEntity;
import com.leimingtech.core.service.VideoSourcesServiceI;
import com.leimingtech.core.util.PathFormat;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.core.util.VideoUtil;


/**
 * 视频资源接口实现
 * 
 * @author liuzhen 2014年7月22日 14:12:30
 * 
 */
@Service("videoSourcesService")
@Transactional
public class VideoSourcesServiceImpl extends CommonServiceImpl implements VideoSourcesServiceI {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VideoSourcesServiceImpl.class);

	/**
	 * 获取视频信息 并且重新生成视频6张展示图
	 * 
	 * @param id
	 *            视频库id
	 * @param flag
	 *            是否只取数据不生成新图
	 * @return 视频信息
	 */
	@Override
	public VideoSourcesEntity getVideoAndnewRandomImage(String id, Boolean flag) {
		flag = flag == null ? false : true;
		if (id == null)
			return null;

		VideoSourcesEntity v = getEntity(VideoSourcesEntity.class, id);
		if (null == v)
			return null;

		if (!flag) {
			if (!newRandomImage(PathFormat.format(CmsConstants.getSitePath(ContextHolderUtils.getSession()) + v.getTranspath()))) {
				return null;
			}
		}
		return v;
	}

	/**
	 * 生成视频6张展示图
	 * 
	 * @param videopath
	 *            视频路径
	 * @return 是否生成成功
	 */
	@Override
	public Boolean newRandomImage(String videopath) {
		if (!new File(videopath).exists()) {
			return false;
		}

		try {
			VideoUtil.captureRandomImage(videopath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 改变视频默认图
	 * 
	 * @param videoSources
	 */
	@Override
	public void changeVideoDefaultImage(VideoSourcesEntity v) {
		if (null == v || StringUtils.isEmpty(v.getDefaultimage()) || StringUtils.isEmpty(v.getTranspath()))
			return;

		String domain = PlatFormUtil.getSessionSite().getDomain();
		String sitePath = CmsConstants.getSitePath(ContextHolderUtils.getSession());
		String imagePath = v.getDefaultimage();
		String videoPath = PathFormat.format(sitePath + v.getTranspath());

		String localImagePath="";
		if (-1 == imagePath.indexOf(domain)){
			localImagePath = PathFormat.format(sitePath + imagePath);
		}else{
			localImagePath = PathFormat.format(sitePath + imagePath.substring(imagePath.indexOf(domain)).replaceFirst(domain, ""));
		}

		File file = new File(localImagePath);
		if (!file.exists())
			return;

		try {
			FileUtils.copyFile(file, new File(videoPath + CmsConstants.VIDEO_DEFIMAGE_SUFFIX));
		} catch (IOException e) {
			logger.error("视频默认预览图修改失败！！！");
			e.printStackTrace();
		}

	}
}