package com.leimingtech.platform.service.impl;

import org.springframework.stereotype.Service;

import com.leimingtech.platform.service.TaskDemoServiceI;
@Service("taskDemoService")
public class TaskDemoServiceImpl implements TaskDemoServiceI {

	
	public void work() {
		//org.lmcmsframework.core.util.LogUtil.info(new Date().getTime());
		//org.lmcmsframework.core.util.LogUtil.info("----------任务测试-------");
	}

}
