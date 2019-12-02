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
import com.leimingtech.core.entity.ActivityEntity;
import com.leimingtech.core.entity.ActivityOptionContentEntity;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.service.ActivityServiceI;
import com.leimingtech.core.service.ConstantsServiceI;
import com.leimingtech.core.service.ContentsServiceI;
import com.leimingtech.core.service.IStatic;
import com.leimingtech.core.service.SystemService;
import com.leimingtech.core.util.MyBeanUtils;
import com.leimingtech.core.util.StringUtil;

@Service("activityService")
@Transactional
public class ActivityServiceImpl extends CommonServiceImpl implements ActivityServiceI {
	@Autowired
	private SystemService systemService;
	@Autowired
	private ConstantsServiceI constantsService;
	@Autowired
	private ContentsServiceI contentsService;
	@Autowired
	private IStatic staticImpl;
	public void saveActivity(ContentsEntity contents, ActivityEntity activity) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		//栏目id
		String contentCatId = request.getParameter("contentCatId");
		//获取当前毫秒数
		String temporary = request.getParameter("temporary");
		String id=request.getParameter("id");
		//顺序值
		String divValue = request.getParameter("divValue");
		
		String[] optionidss = request.getParameterValues("optionids");
		String[] isEnableds = request.getParameterValues("isEnableds");
		String[] isRequireds = request.getParameterValues("isRequired");
		String[] isReceptionshows = request.getParameterValues("isReceptionshow");

		
		ContentCatEntity contentcat=getEntity(ContentCatEntity.class, contentCatId);
		List<ActivityOptionContentEntity> activityOptionContentList = new ArrayList<ActivityOptionContentEntity>();
		if(StringUtil.isNotEmpty(id)){
			//获取表单选项
			activityOptionContentList = findByProperty(ActivityOptionContentEntity.class, "activityids", id);
		}
		String message = "";
		if (StringUtil.isNotEmpty(activity.getId())){
			//修改投票
			updateActivity(contents, activity, contentcat,activityOptionContentList, temporary, divValue);
		}else{
			
			//添加投票
			message = "投票["+contents.getTitle()+"]添加成功";
			activity.setContentid(contents.getId());
			activity.setSiteid(contents.getSiteid());
			activity.setCreatedtime(new Date());//创建时间
			save(activity);
			//添加表单选项
			for (int i = 0; i < optionidss.length; i++) {
				ActivityOptionContentEntity aoce=new ActivityOptionContentEntity();
				aoce.setOptionids(optionidss[i]);
				aoce.setIsEnableds(Integer.parseInt(isEnableds[i]));//是否启用
				aoce.setIsRequired(Integer.parseInt(isRequireds[i]));//是否必选
				aoce.setIsReceptionshow(Integer.parseInt(isReceptionshows[i]));//是否前台显示
				aoce.setActivityids(activity.getId());//活动id
				aoce.setCreatetime(new Date());//创建时间
				save(aoce);
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

	

	public void updateActivity(ContentsEntity contents,
			ActivityEntity activity, ContentCatEntity contentcat,
			List<ActivityOptionContentEntity> activityOptionList,
			String temporary, String divValue) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		ActivityEntity t = findUniqueByProperty(ActivityEntity.class, "contentid", activity.getContentid());
		
		String[] optionidss = request.getParameterValues("optionids");
		String[] isEnableds = request.getParameterValues("isEnableds");
		String[] isRequireds = request.getParameterValues("isRequired");
		String[] isReceptionshows = request.getParameterValues("isReceptionshow");
		
		String message = "";
		String logInfo = "PC内容【" + contents.getTitle() + "】";
		try {
			message = "更新成功";
			MyBeanUtils.copyBeanNotNull2Bean(activity, t);
			saveOrUpdate(t);
			//保存扩展参数名称和value
			contentsService.saveModelExt(contents);
			//保存相关内容
			contentsService.saveRelateContent(contents, temporary, divValue);
			//修改表单选项
			
			if(activityOptionList!=null){
				
				
				
						for(int i = 0; i < optionidss.length; i++){
						
							updateBySqlString("UPDATE  cms_activity_optioncontent SET is_enableds="+Integer.parseInt(isEnableds[i])+" , " +
									"is_required="+Integer.parseInt(isRequireds[i])+"  ,is_receptionShow ="+Integer.parseInt(isReceptionshows[i])+" WHERE optionids='"+optionidss[i]+"' and activityids='"+t.getId()+"'; ");
					
					}
				
				}
				
						//生成预览页面
			staticImpl.staticContentActOnPreview(contents);
			//进入判断工作流和状态
			constantsService.isWorkFlow(contents, contentcat);
			//日志添加
		} catch (Exception e) {
			e.printStackTrace();
			message = "更新失败";
		}
		systemService.addLog(logInfo+message, Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
	 }
	
}

	
	