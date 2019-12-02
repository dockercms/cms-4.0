package com.leimingtech.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.PictureAloneEntity;
import com.leimingtech.core.entity.PictureGroupEntity;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

/**
 * 组图接口
 */
@Service("pictureGroupService")
@Transactional
public class PictureGroupServiceImpl extends CommonServiceImpl implements PictureGroupServiceI {

	@Autowired
	private SystemService systemService;
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private IStatic staticImpl;
	/**
	 * 公共dao接口
	 */
	@Autowired
	private CommonService commonService;
	@Override
	public void savePictureGroup(ContentsEntity contents,PictureGroupEntity pictureGroup) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//顺序值
		String divValue = request.getParameter("divValue");
		ContentCatEntity contentcat=getEntity(ContentCatEntity.class, contentCatId);
		List<PictureAloneEntity> pictureAloneList = new ArrayList<PictureAloneEntity>();
		if(StringUtil.isNotEmpty(temporary)){
			pictureAloneList = findByProperty(PictureAloneEntity.class, "picturegroupid", temporary);
		}
		String message = "";
		if(StringUtil.isNotEmpty(pictureGroup.getId())){
			//修改
			updatePictureGroup(contents, pictureGroup, contentcat,pictureAloneList, temporary, divValue);
		}else{
			//添加
			message = "PC内容\t【组图\t"+contents.getTitle()+"】添加成功";
			pictureGroup.setContentid(contents.getId());
			pictureGroup.setSiteid(contents.getSiteid());
			pictureGroup.setCreatedtime(new Date());//创建时间
			save(pictureGroup);
			//保存组图中 每张图片
			for(PictureAloneEntity pictureAlone:pictureAloneList){
				pictureAlone.setPicturegroupid(String.valueOf(pictureGroup.getId()));
				saveOrUpdate(pictureAlone);
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
	public void updatePictureGroup(ContentsEntity contents,PictureGroupEntity pictureGroup, ContentCatEntity contentcat,
			List<PictureAloneEntity> pictureAloneList,String temporary, String divValue) {
		String message = "";
		try {
			message = "PC内容\t【组图\t"+contents.getTitle()+"】更新成功";
			PictureGroupEntity t = get(PictureGroupEntity.class, pictureGroup.getId());
			MyBeanUtils.copyBeanNotNull2Bean(pictureGroup, t);
			saveOrUpdate(t);
			//保存组图中 每张图片
			for(PictureAloneEntity pictureAlone:pictureAloneList){
				pictureAlone.setPicturegroupid(String.valueOf(t.getId()));
				saveOrUpdate(pictureAlone);
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
			message = "组图更新失败";
		}
	}
	
	
	@Override
	public List<PictureAloneEntity> pictureList(String contentid) {
		List<PictureGroupEntity> pictureList = new ArrayList<PictureGroupEntity>();
		List<PictureAloneEntity> picturealoneList = new ArrayList<PictureAloneEntity>();
		pictureList = findByProperty(PictureGroupEntity.class, "contentid", contentid);
		if(pictureList.size() > 0){
			PictureGroupEntity picture = pictureList.get(0);
			String id = picture.getId();
			picturealoneList = findByProperty(PictureAloneEntity.class, "picturegroupid", id );
		}
		return picturealoneList;
	}

	/**
	 * 根据内容id获取组图
	 *
	 * @param contentId
	 * @return 组图
	 */
	@Override
	public PictureGroupEntity getPictureGroupByContentId(String contentId) {
		return this.commonService.findUniqueByProperty(PictureGroupEntity.class, "contentid", contentId);
	}
}