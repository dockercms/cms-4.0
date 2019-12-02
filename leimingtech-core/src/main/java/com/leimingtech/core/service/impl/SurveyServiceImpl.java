package com.leimingtech.core.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.common.Globals;
import com.leimingtech.core.base.ContextHolderUtils;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.entity.SurveyEntity;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

@Service("surveyService")
@Transactional
public class SurveyServiceImpl extends CommonServiceImpl implements SurveyServiceI {
	@Autowired
	private SystemService systemService;
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private IStatic staticImpl;
	/**公共dao接口*/
	@Autowired
	private CommonService commonService;

	@Override
	public void saveSurvey(ContentsEntity contents, SurveyEntity survey) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		//顺序值
		String divValue = request.getParameter("divValue");
		ContentCatEntity contentcat=getEntity(ContentCatEntity.class, contentCatId);
		String message = "";
		if (StringUtil.isNotEmpty(survey.getId())){
			//修改调查
			updateSurvey(contents, survey, contentcat, temporary, divValue);
		}else{
			//添加调查
			message = "调查["+contents.getTitle()+"]添加成功";
			survey.setContentid(contents.getId());
			survey.setSiteid(contents.getSiteid());
			survey.setCreatedtime(new Date());//创建时间
			save(survey);
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
	public void updateSurvey(ContentsEntity contents, SurveyEntity survey,
			ContentCatEntity contentcat, String temporary, String divValue) {
		String message = "";
		try {
			message = "调查["+contents.getTitle()+"]更新成功";
			SurveyEntity t = get(SurveyEntity.class, survey.getId());
			MyBeanUtils.copyBeanNotNull2Bean(survey, t);
			saveOrUpdate(t);
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
			message = "调查["+contents.getTitle()+"]更新失败";
		}
		
	}

	/**
	 * 根据内容id获取调查
	 *
	 * @param contentId 内容id
	 * @return 调查
	 */
	@Override
	public SurveyEntity getSurveyByContentId(String contentId) {
		return this.commonService.findUniqueByProperty(SurveyEntity.class, "contentid", contentId);
	}
}