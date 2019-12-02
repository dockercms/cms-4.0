package com.leimingtech.platform.service.impl.userinfo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.userinfo.UserinfoServiceI;

@Service("userinfoService")
@Transactional
public class UserinfoServiceImpl extends CommonServiceImpl implements UserinfoServiceI {
	
}