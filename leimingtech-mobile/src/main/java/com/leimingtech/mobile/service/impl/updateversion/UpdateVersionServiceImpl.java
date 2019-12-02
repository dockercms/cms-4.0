package com.leimingtech.mobile.service.impl.updateversion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.mobile.service.updateversion.UpdateVersionServiceI;

@Service("updateVersionService")
@Transactional
public class UpdateVersionServiceImpl extends CommonServiceImpl implements UpdateVersionServiceI {
	
}