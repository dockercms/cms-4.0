package com.leimingtech.wap.controller.freemarklabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leimingtech.core.common.BaseFreeMarkerTag;
import com.leimingtech.core.entity.ContentsEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.ContentsServiceI;

import freemarker.template.TemplateModelException;

@Component
public class HeadlineList extends BaseFreeMarkerTag {
	@Autowired
	private ContentsServiceI contentsService;

	@Override
	protected Object exec(Map params) throws TemplateModelException {
		
		String catalog =params.get("catalog").toString();
		 
		 String isGetImage=(String) params.get("isGetImage");//是否得到图片
		List<ContentsEntity> contentList = new ArrayList<ContentsEntity>();
		// 查出头条
		CriteriaQuery cq = new CriteriaQuery(ContentsEntity.class, 5, 1, null, null);
		
			if(StringUtils.isNotEmpty(isGetImage)){
				if(isGetImage.equals("true")){
				cq.eq("catid", catalog);
				cq.eq("isHeadline", 1);
				cq.notEq("thumb"," ");
				cq.add();
				}
			}else{
				//isGetImage="false";
				cq.eq("catid", catalog);
				cq.eq("isHeadline", 1);
				cq.add();
			}
			
		contentList = contentsService.getListByCriteriaQuery(cq, true);
		return contentList;
	}

}
