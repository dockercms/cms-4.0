package com.leimingtech.core.entity;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;

import com.leimingtech.core.base.ApplicationContextUtil;
import com.leimingtech.core.util.StringUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class TagCreator implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		String beanid = (String) args.get(0);
		if (StringUtil.isEmpty(beanid)) {
			throw new TemplateModelException("标签beanid参数不能为空");
		}
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) ApplicationContextUtil.getContext();
		Object obj = applicationContext.getBean(beanid);
		return obj;
	}

}
