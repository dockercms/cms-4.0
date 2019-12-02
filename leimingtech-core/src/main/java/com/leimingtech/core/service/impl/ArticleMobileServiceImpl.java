package com.leimingtech.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.entity.ContentsMobileEntity;
import com.leimingtech.core.service.ArticleMobileServiceI;

@Service("articleMobileService")
@Transactional
public class ArticleMobileServiceImpl extends CommonServiceImpl implements ArticleMobileServiceI {

	@Override
	public String getArticleMobile(String all, String contentId)
			throws Exception {
		String url = "";
		if(all.equals("y")){
			url = "http://m.cmstop.cn/article/489";
		}else{
			//获取内容
			ContentsMobileEntity contentMobile = this.get(ContentsMobileEntity.class, String.valueOf(contentId));
			url = contentMobile.getUrl();
		}
		return url;
	}

	
}