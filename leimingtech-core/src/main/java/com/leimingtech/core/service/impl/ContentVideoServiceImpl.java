package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentVideoEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.VideoalburmarticleEntity;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentVideoServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.service.VideoalburmarticleServiceI;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

@Service("contentVideoService")
@Transactional
public class ContentVideoServiceImpl extends CommonServiceImpl implements ContentVideoServiceI {

	@Autowired
	private SystemService systemService;
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private VideoalburmarticleServiceI videoalburmarticleService;
	@Autowired
	private IStatic staticImpl;
	@Override
	public void saveVideo(ContentsEntity contents,ContentVideoEntity contentVideo) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//顺序值
		String divValue = request.getParameter("divValue");
		ContentCatEntity contentcat=getEntity(ContentCatEntity.class, contentCatId);
		String message = "";
		if (StringUtil.isNotEmpty(contentVideo.getId())){
			//修改视频
			updateVideo(contents, contentVideo, contentcat, temporary, divValue);
		}else{
			//添加视频
			message = "视频["+contentVideo.getVideoname()+"]添加成功";
			contentVideo.setContentid(contents.getId());
			contentVideo.setSiteid(contents.getSiteid());
			contentVideo.setCreatedtime(new Date());//创建时间
			save(contentVideo);
			//保存视频专辑
			if(StringUtil.isNotEmpty(contentVideo.getSpecial())){
				VideoalburmarticleEntity ablurmarticle = new VideoalburmarticleEntity();
				ablurmarticle.setArticleid(contents.getId());
				ablurmarticle.setArticleid(String.valueOf(contentVideo.getSpecial()));
				videoalburmarticleService.saveOrUpdateAlburmArticle(ablurmarticle);
			}
			//在modelExt中保存modelId/attrName
			contentsService.saveModelItem(contents);
			//保存相关内容
			contentsService.saveRelateContent(contents, temporary, divValue);
			//生成预览页面
			staticImpl.staticContentActOnPreview(contents);
			//进入判断工作流和状态
			constantsService.isWorkFlow(contents, contentcat);
			//日志添加
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}
	}
	@Override
	public void updateVideo(ContentsEntity contents,ContentVideoEntity contentVideo, 
			ContentCatEntity contentcat,String temporary, String divValue) {
		String message = "";
		try {
			message = "视频["+contentVideo.getVideoname()+"]更新成功";
			ContentVideoEntity t = get(ContentVideoEntity.class, contentVideo.getId());
			MyBeanUtils.copyBeanNotNull2Bean(contentVideo, t);
			saveOrUpdate(t);
			// 保存视频专辑
			if(StringUtil.isNotEmpty(t.getSpecial())){
				VideoalburmarticleEntity ablurmarticle = videoalburmarticleService.getVideoAlburmArticleByContentId(contents.getId());
				if(ablurmarticle!=null){
					ablurmarticle.setAlburmid(String.valueOf(t.getSpecial()));
					videoalburmarticleService.saveOrUpdateAlburmArticle(ablurmarticle);
				}
			}
			//保存扩展参数名称和value
			contentsService.saveModelExt(contents);
			//保存相关内容
			contentsService.saveRelateContent(contents, temporary, divValue);
			//生成预览页面
			staticImpl.staticContentActOnPreview(contents);
			//进入判断工作流和状态
			constantsService.isWorkFlow(contents, contentcat);
			//日志添加
			systemService.addLog(message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "视频["+contentVideo.getVideoname()+"]更新失败";
		}
	}

	/**
	 * 根据内容id获取视频列表
	 * @param contentid
	 * @return
	 */
	public List<ContentVideoEntity> videoList(String contentid) {
		List<ContentVideoEntity> videoList = new ArrayList<ContentVideoEntity>();
		videoList = findByProperty(ContentVideoEntity.class, "contentid", contentid);
		return videoList;
	}

}