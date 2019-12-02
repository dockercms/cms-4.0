package com.leimingtech.platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.ConfigServiceI;

@Service("configService")
@Transactional
public class ConfigServiceImpl extends CommonServiceImpl implements
		ConfigServiceI {

}
