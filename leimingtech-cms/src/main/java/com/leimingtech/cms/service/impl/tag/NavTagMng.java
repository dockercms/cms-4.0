package com.leimingtech.cms.service.impl.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.cms.service.tag.INavTagMng;
import com.leimingtech.core.entity.ContentCatEntity;
import com.leimingtech.core.service.impl.CommonServiceImpl;

@Service("navTagMng")
@Transactional
public class NavTagMng extends CommonServiceImpl implements INavTagMng{
	
	public Object getnavTag(Map params){
		if (MapUtils.isEmpty(params)){
			return null;
		}

		String catId = (String)params.get("name");
		ContentCatEntity contentCat = get(ContentCatEntity.class,String.valueOf(catId));
		List<ContentCatEntity> contentCats =  getParent(String.valueOf(catId));
		contentCats.add(contentCat);
		return contentCats;
	}
	public List<ContentCatEntity> getParent(String contentCatId){

		List<ContentCatEntity> contentCatList = new ArrayList<ContentCatEntity>();
		
			ContentCatEntity contentCat = get(ContentCatEntity.class,String.valueOf(contentCatId));
			String[] str = contentCat.getParentids().split(",");
	
			for(int i=1;i<str.length;i++){
					ContentCatEntity contentCat1 = get(ContentCatEntity.class,String.valueOf(str[i]));
					contentCatList.add(contentCat1);
			}
			
			return contentCatList;
		}
	}