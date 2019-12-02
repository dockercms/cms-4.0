package com.leimingtech.core.service.impl.thirdlogin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.core.service.thirdlogin.ThirdloginServiceI;

@Service("thirdloginService")
@Transactional
public class ThirdloginServiceImpl extends CommonServiceImpl implements ThirdloginServiceI {
	
}